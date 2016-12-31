package ocp;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.APPEND;
import static org.junit.Assert.*;
/**
 * Created by williaz on 12/14/16 - 12/17 3d.
 * Nonblocking Input/Output API
 * watch out:
 * 1. Path vs Paths
 * 2. methods param allow Path vs String
 * 3. only toRealPath() implicitly call normalize()
 * 4. moving file always preserve meta, even if without COPY_ATTRIBUTES
 * 5. isSameFile() returns true only if the files point to in the file system are the same, no counting content
 * 6. deleteIfExist() may throw DirectoryNotEmptyException
 */
public class NioTest {
    /**
     * root in Linux start with a forward slash, /; Windows' start with a drive letter.
     * Linux- case sensitive; Windows insensitive.
     *
     * buffers and channels: load the data from a file channel into a temporary buffer that,
     *        unlike byte streams, can be read forward and backward without blocking on the underlying resource.<br>
     * A Path object represents a hierarchical path on the storage system to a file or directory.
     *     creating a file or directory is considered a file system–dependent task in NIO.2.
     *     JVM gives you back an object that unlike java.io.File transparently handles system-specific details for the current platform.
     * Path interface contains support for symbolic links.
     * A symbolic link is a special file within an operating system that serves as a reference or pointer
     *       to another file or directory.<br>
     * A uniform resource identifier (URI) is a string of characters that identify a resource.
     *     It begins with a schema that indicates the resource type,(file://, http://, https://, and ftp://.)
     *     followed by a path value.
     *     <- URIs must reference absolute paths at runtime, runtime err
     *
     * @see java.net.URI
     */
    @Test
    public void test_Path() throws IOException {
        //File niof = new File("/Users/williaz/IdeaProjects/JavaExp/nio");
        //niof.mkdir();

        Path nio = Paths.get("/Users/williaz/IdeaProjects/JavaExp/nio");
        Path nio1 = Paths.get("/", "Users", "williaz", "IdeaProjects", "JavaExp", "nio");
        assertEquals(nio, nio1);
        try {
            Path nio2 = Paths.get(new URI("file:///Users/williaz/IdeaProjects/JavaExp/nio"));
            assertEquals(nio, nio2);
            URI nioUri = nio2.toUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //work with default local file system
        Path nio3 = FileSystems.getDefault().getPath("/Users/williaz/IdeaProjects/JavaExp/nio");
        assertEquals(nio, nio3);

        File nio4 = new File("/Users/williaz/IdeaProjects/JavaExp/nio");
        assertEquals(nio, nio4.toPath());//legacy File
        Path nio5 = Paths.get("nio");
        assertEquals(nio, nio5.toAbsolutePath());
        assertTrue(Files.isSameFile(nio, nio5));
        System.out.println(Paths.get("/JavaExp/../nio/zoo.txt").normalize());

        Path nio6 = FileSystems.getDefault().getPath("./nio");
        assertTrue(Files.isSameFile(nio, nio6));

    }

    /**
     * The Path.getPath() method is actually shorthand for the class java.nio.file.FileSystem method getPath().
     * reference external file systems - use FileSystems factory class to connect to a remote file system
     * TODO FileSystemProvider
     */
    @Test(expected = ProviderNotFoundException.class)
    public void test_FileSystem() throws URISyntaxException {
        Path remote = FileSystems.getFileSystem(new URI("https://github.com/williaz/OracleExp"))
                .getPath("OSE.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(remote.toFile()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * a Path object is not a file but a representation of a location within the file system.
     *   -> most operations available in the Path and Paths classes can be accomplished
     *      regardless of whether the underlying file that the Path object references actually exists
     *      <- Path.toRealPath(), do require the file to exist
     * an atomic move is one in which any process monitoring the fi le system never sees an incomplete
     *      or partially written file. -> AtomicMoveNotSupportedException
     *
     * NOFOLLOW_LINKS: (not traverse symbolic link) Test file existing, Read file data, Copy file, Move file
     * FOLLOW_LINKS: (traverse symbolic link) Traverse a directory tree
     * COPY_ATTRIBUTES: (copy with meta) Copy file
     * REPLACE_EXISTING: (replace if exist) Copy file, Move file
     * ATOMIC_MOVE: (all complete file) Move file
     *
     * Only toString() return String, other methods return Path
     *
     * Path Symbols: double period value .. can be used to reference the parent directory.
     *               single period value . can be used to reference the current directory within a path.
     *
     * @see Path
     *
     * getNameCount(), getName(int), getFileName()
     * getParent(), getRoot(),
     * isAbsolute(), toAbsolute(),
     * subpath(int inc, int exc),
     * relativize(Path)
     * resolve(Path): join
     * normalize(), toRealPath()(impilict normalize())
     *
     */
    @Test
    public void test_PathMethods() {
        assertEquals(1, Paths.get(".").normalize().getNameCount());
        Path local = Paths.get("/Users/williaz/IdeaProjects/JavaExp/io/io.txt");
        assertEquals(6, local.getNameCount());
        for (int i = 0; i < local.getNameCount(); i++) {
            System.out.print(local.getName(i) + " ");
        }
        System.out.println("\n" + local.getFileName()); //farthest element from the root.
        System.out.println(local.getParent()); //parent path,
                                              // it does not traverse relative directories outside the working directory.
        System.out.println(local.getRoot()); // null if relative url

        Path rel = Paths.get("io/io.txt");
        assertFalse(rel.isAbsolute());
        System.out.println(rel.toAbsolutePath());
        System.out.println(Paths.get("c:/goats/Food.java").isAbsolute()); //false as in mac
        //return relative url, an inclusive start index and an exclusive end index, does not include the root of the file
        System.out.println(local.subpath(1, 2));
        assertEquals(local.subpath(4, 6), rel);

        Path spv = Paths.get("./io/io.txt"); //A reference to the current directory
        Path dpv = Paths.get("../io.txt"); //A reference to the parent of the current directory

        //If both path values are relative, then the relativize() method computes the paths as if
        //they are in the same current working directory
        //if both path values are absolute, then the method computes the relative
        //path from one absolute location to another, regardless of the current working directory.
        Path current = Paths.get("/Users/williaz/IdeaProjects/JavaExp");
        System.out.println("local.relativize(current) : " + local.relativize(current)); //requires that both paths be absolute or both relative
                                                      //for windows, also require same driver
        //relativize() and resolve() method does not clean up path symbols,
        //resolve(Path) method for creating a new Path by joining an existing path to the current path.
        //If an absolute path is provided as parameter to the resolve(), just print, ref would be ignored
        assertEquals(current.resolve(rel), local);
        System.out.println(rel.resolve(current));
        System.out.println(current.resolve(spv));
        System.out.println(current.resolve(dpv) + " => " + current.resolve(dpv).normalize());
        assertEquals(current.resolve(spv).normalize(), local);
        //toRealPath() is also the only Path method to support the NOFOLLOW_LINKS option.
        //it implicitly calls normalize() on the resulting absolute path.
        try {
            System.out.println(spv.toRealPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //use the toRealPath() method to gain access to the current working directory
        try {
            assertEquals(current, Paths.get(".").toRealPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Unlike the methods in the Path and Paths class,
     *  most of the options within the Files class will throw an exception
     *  if the file to which the Path refers does not exist.
     *
     *  exists(Path)
     *  createDirectory() = mkdir()
     *  createDirectories() = mkdirs()
     *  move() = renameTo()
     *  isSameFile()
     *  delete(),
     *  deleteIfExists(): throws DirectoryNotEmptyException,
     *                    if the file is a directory and could not otherwise be deleted,
     *                    because the directory is not empty
     *  copy()
     *  newBufferedReader(), newBufferedWriter()
     *  readAllLines()
     *
     * @see java.nio.file.Files
     */
    @Test
    public void test_Files() throws IOException {
        assertTrue(Files.exists(Paths.get(".").resolve(Paths.get("io/io.txt")).toRealPath()));
        //This isSameFile() method does not compare the contents of the file.
        Path local = Paths.get("/Users/williaz/IdeaProjects/JavaExp/io/io.txt");
        assertTrue(Files.isSameFile(Paths.get("./io/io.txt"), local));
        //The directory-creation methods can throw the checked IOException,
        // such as when the directory cannot be created or already exists.
        Files.delete(Paths.get("./nio/dirs"));
        Files.delete(Paths.get("./nio/dir/sample"));
        Files.createDirectory(Paths.get("./nio/dirs"));
        Files.createDirectories(Paths.get("./nio/dir/sample"));

        //Directory copies are shallow rather than deep, meaning that files and subdirectories
        // within the directory are not copied.
        //default: it will not overwrite a file or directory if it already exists, nor will it copy file attributes.
        // additional options NOFOLLOW_LINKS, REPLACE_EXISTING, and COPY_ATTRIBUTES
        Files.copy(Paths.get("./io/zoo.txt"), Paths.get("./nio/zoo.txt"), REPLACE_EXISTING);
        try (InputStream in = new FileInputStream("io/zoo.txt");
             OutputStream out = new FileOutputStream("nio/zooCp2.txt")) {
            Files.copy(in, Paths.get("nio/zooCp.txt"), REPLACE_EXISTING);
            Files.copy(Paths.get("io/zoo.txt"), out); //does not support optional vararg values,
            // written to a stream that may not represent a file system resource.
        }
        //The Files.move(Path,Path) method moves or renames a file or directory within the file system.
        /*
        While moving an empty directory across a drive is supported,
        moving a non-empty directory across a drive will throw an NIO.2 DirectoryNotEmptyException .
         */
        assertFalse(Files.deleteIfExists(Paths.get("nio/dirs/zooMv.txt")));
        Files.move(Paths.get("nio/zooCp.txt"), Paths.get("nio/dirs/zooMv.txt"));
        //If the target of the path is a symbol link, then the symbolic link will be deleted, not the target of the link.
        Files.delete(Paths.get("nio/dirs/zooMv.txt"));

        try (BufferedReader in = Files.newBufferedReader(Paths.get("nio/zoo.txt"));
            BufferedWriter out = Files.newBufferedWriter(Paths.get("nio/zooCp2.txt"), APPEND)) {
            String line = in.readLine();
            System.out.println(line);
            out.write("\n" + line);
        }
        //the entire file is read when readAllLines() is called -> OutOfMemoryError
        try {
            List<String> lines = Files.readAllLines(Paths.get("nio/zooCp2.txt"));
            ListIterator<String> li = lines.listIterator(lines.size());
            System.out.println(li.previous());
            System.out.println(li.previous());
        } catch (IOException e) {}
    }

    /**
     * File attributes: metadata, which is data that describes other data.
     * three methods for determining if a path refers to a directory, a regular file, or a symbolic link.
     * a regular file as one that contains content
     *   -> it is possible for isRegularFile() to return true for a symbolic link,
     *      as long as the link resolves to a regular file.
     * if no exist, return false: isRegularFile(), isDirectory(), isSymbolicLink();
     *                            isHidden(), isReadable(), isExecutable();
     */
    @Test
    public void test_FileAttributes() {
        //directories can have extensions in many file systems
        assertTrue(Files.isRegularFile(Paths.get("./nio/zoo.txt")));
        assertTrue(Files.isDirectory(Paths.get("./nio/dir")));
        try {
            Files.deleteIfExists(Paths.get("./nio/zoo1.txt"));
            Files.createSymbolicLink(Paths.get("./nio/zoo1.txt"), Paths.get("./nio/zoo.txt"));
            assertTrue(Files.isSymbolicLink(Paths.get("./nio/zoo1.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(Files.isRegularFile(Paths.get("./nio/zoo23.txt"))); // no exception if no exist, no need exists()
        try {
            assertTrue(Files.isHidden(Paths.get("./nio/.ss.txt")));
            /*
            In Linux- or Mac-based systems, this is often denoted by file or directory entries that begin with a period character (.)
             while in Windows-based systems this requires the hidden attribute to be set.
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(Files.isExecutable(Paths.get("./nio/.ss.txt"))); //isHidden
        assertFalse(Files.isReadable(Paths.get("./nio/zoo1.txt")));//SymbolicLink
        assertTrue(Files.isReadable(Paths.get("./nio/.ss.txt")));

        /**
         * The size returned by this method represents the conceptual size of the data,
         *  and this may differ from the actual size on the persistence storage device
         *  due to file system compression and organization.
         */
        //number of bytes in the file
        try {
            System.out.println(Files.size(Paths.get("./nio/zoo.txt")));
            System.out.println(Files.size(Paths.get("./nio"))); //os dependent, no files inside size
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Files.getLastModifiedTime(Paths.get("./nio/zoo.txt")));
            System.out.println(Files.getLastModifiedTime(Paths.get("./nio/zoo.txt")).toMillis()); //epoh time
            Files.setLastModifiedTime(Paths.get("./nio/zoo.txt"), FileTime.fromMillis(System.currentTimeMillis()));
            System.out.println(Files.getLastModifiedTime(Paths.get("./nio/zoo.txt")).toMillis()); //epoh time

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(Files.getOwner(Paths.get("./nio/zoo.txt")).getName());
            UserPrincipal user = Paths.get("./nio/zoo.txt").getFileSystem()
                    .getUserPrincipalLookupService().lookupPrincipalByName("jenkins");
            Files.setOwner(Paths.get("./nio/zoo.txt"), user);
            System.out.println(Files.getOwner(Paths.get("./nio/zoo.txt")).getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * A view is a group of related attributes for a particular file system type. -> fewer round-trips
     *
     * Files.readAttributes(), returns a read-only view of the file attributes.
     *     can be access file system dependent attributes, Files cannot
     * Files.getFileAttributeView(), returns the underlying attribute view, and it provides a direct resource for modifying file information.
     *     setTimes()
     *
     * BasicFileAttributeView is used to modify a file’s set of date/time values.
     *
     */
    @Test
    public void test_View() {
        //The fileKey() method returns a file system value that represents a unique
        //identifier for the file within the file system or null if it is not supported by the file system.
        try {
            BasicFileAttributes meta = Files.readAttributes(Paths.get("./nio/zoo.txt"), BasicFileAttributes.class);
            System.out.println(meta.creationTime() + " " + meta.lastAccessTime() + " " + meta.lastModifiedTime() + " " + meta.size());
            System.out.println(meta.isDirectory() + " " + meta.isRegularFile() + " " + meta.isSymbolicLink() + " " + meta.isOther());
            System.out.println(meta.fileKey());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //The NIO.2 API allows us to pass null for any date/time value that we do not wish to modify.
        BasicFileAttributeView view = Files.getFileAttributeView(Paths.get("./nio/zoo.txt"), BasicFileAttributeView.class);
        try {
            BasicFileAttributes meta = view.readAttributes();
            System.out.println(meta.creationTime() + " " + meta.lastAccessTime() + " " + meta.lastModifiedTime());
            view.setTimes(FileTime.fromMillis(System.currentTimeMillis() - 10000), null, null); // only one set method
            meta = view.readAttributes(); //read again
            System.out.println(meta.creationTime() + " " + meta.lastAccessTime() + " " + meta.lastModifiedTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Walking or traversing a directory is the process by which you start with a parent directory
     *    and iterate over all of its descendants until some condition is met
     *    or there are no more elements over which to iterate.
     *
     * A depth-first search traverses the structure from the root to an arbitrary leaf
     *   and then navigates back up toward the root, traversing fully down any paths it skipped along the way.
     *   -> less memory
     * A breadth-first search starts at the root and processes all elements of each particular depth,
     *   or distance from the root, before proceeding to the next depth level.
     *
     * The search depth is the distance from the root to current node.
     *   -> Streams API uses depth-first searching with a default maximum depth of Integer.MAX_VALUE.
     *
     * The Files.walk(path) method returns a Stream<Path> object that traverses the directory in a depth-first, lazy manner.
     * By lazy, we mean the set of elements is built and read while the directory is being traversed.
     *
     * the walk() method will not traverse symbolic links by default.
     *
     * Files.lines(Path) method that returns a Stream<String>
     *
     * Stream<Path> Files.walk(Path): may throw exception when encounter no access
     * Stream<Path> Files.find(Path, int, BiPredicate)
     * Stream<Path> Files.list(Path)
     * Stream<String> Files.lines(Path)
     *
     */
    @Test
    public void test_FileStream() {
        try {
            Files.deleteIfExists(Paths.get("./nio/zoo2.txt"));
            Files.createSymbolicLink(Paths.get("./nio/zoo2.txt"), Paths.get("./nio/zoo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // maximum directory depth: depth 1 for direct children, 0 for the top-level dir
        try {
            Files.walk(Paths.get("./nio"), 2, FileVisitOption.FOLLOW_LINKS).filter(p -> p.toString().endsWith(".txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("------------");
        try {
            Stream<Path> fstream = Files.find(Paths.get("./nio"), 3, (p, a) -> p.toString().endsWith(".txt") && a.isSymbolicLink());
            fstream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("------------");
        //direct children
        try {
            Files.list(Paths.get("./nio"))
                    .filter(f -> f.toString().endsWith(".txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //The contents of the file are read and processed lazily,
            Files.lines(Paths.get("./nio/zoo.txt")).filter(s -> s.startsWith("75")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
