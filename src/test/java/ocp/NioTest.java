package ocp;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;

import static org.junit.Assert.*;
/**
 * Created by williaz on 12/14/16.
 * Nonblocking Input/Output API
 */
public class NioTest {
    /**
     * root in Linux start with a forward slash, /; Windows' start with a drive letter.
     * Linux- case sensitive; Windows insensitive.
     *
     * buffers and channels: load the data from a file channel into a temporary buffer that,
     *        unlike byte streams, can be read forward and backward without blocking on the underlying resource.
     * A Path object represents a hierarchical path on the storage system to a file or directory.
     *     creating a file or directory is considered a file system–dependent task in NIO.2.
     *     JVM gives you back an object that unlike java.io.File transparently handles system-specific details for the current platform.
     * Path interface contains support for symbolic links.
     * A symbolic link is a special file within an operating system that serves as a reference or pointer
     *       to another file or directory.
     * A uniform resource identifier (URI) is a string of characters that identify a resource.
     *     It begins with a schema that indicates the resource type,(file://, http://, https://, and ftp://.)
     *     followed by a path value.
     *     <- URIs must reference absolute paths at runtime, runtime err
     *
     * @see java.net.URI
     */
    @Test
    public void test_Path() {
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
}
