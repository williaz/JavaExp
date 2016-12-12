package ocp;

import org.junit.Test;

import java.io.File;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by williaz on 12/12/16.
 * Wrapping is the process by which an instance is passed to the constructor of another class
 *       and operations on the resulting instance are filtered and applied to the original instance.
 */
public class IoTest {
    /**
     * A file is record within a file system that stores user and system data. Files are organized using directories.
     * A directory is a record within a file system that contains files as well as other directories.
     *   root directory is the topmost directory in the file system,
     * The file system is in charge of reading and writing data within a computer.
     * A path is a String representation of a fi le or directory within a fi le system.
     */

    /**
     * The File class cannot read or write data within a file,
     *     although it can be passed as a reference to many stream classes to read or write data
     * File class can be used to represent directories as well as files.
     * A File object often is initialized with String containing
     *   either an absolute or relative path to the file or directory within the file system.
     * The absolute path of a file or directory is the full path from the root directory to the file or directory, including all subdirectories that contain the file or directory.
     * The relative path of a file or directory is the path from the current working directory to file or directory.
     *
     * exists(), isFile(), isDirectory()
     * getName(), getAbsolutePath(), getParent()
     * lastModified(), delete(), length() - bytes, renameTo(File)
     * mkdir(), mkdirs(), listFiles()
     *
     * @see File
     */
    @Test
    public void test_File() {

        //retrieve the local separator character
        assertEquals("/", System.getProperty("file.separator"));
        assertEquals("/", File.separator);

        File file = new File("/Users/williaz/IdeaProjects/JavaExp");
        File test = new File(file, "/test.txt");
        File test1 = new File(file, "/io.txt");

        assertTrue(file.exists());
        assertTrue(file.isDirectory());
        //
        System.out.println(test.getAbsolutePath() + " : " + test.getParent() + " : " + test.getName());
        System.out.println(test.lastModified() + " : " + test.length() + " bytes");
        for (File f : file.listFiles()) {
            System.out.print(f.getName() + " ");
        }
        //test.renameTo(new File("io.txt"));
//        assertTrue(test1.isFile());
        //File newPath = new File("/Users/williaz/IdeaProjects/JavaExp/io");
        //newPath.mkdir(); //create dir
        //System.out.print(newPath.exists());
        //assertTrue(test1.delete());

    }

    /**
     * a stream is so large that all of the data contained in it could not possibly fit into memory.
     *  -> focus on only a small portion of the overall stream at any given time.
     * By utilizing a BufferedOutputStream, the Java application can
     *    write a large chunk of bytes at a time, reducing the round-trips and drastically improving performance.
     *
     * 1. The stream classes are used for inputting and outputting all types of binary or byte data.
     *    <- name with InputStream / OutputStream
     * 2. The reader and writer classes are used for inputting and outputting only character and String data.
     *    <- name with Reader / Writer
     *   -> Reader/Writer without necessarily having to worry about the underlying byte encoding of the file
     *
     * PrintWriter has no accompanying PrintReader class; PrintStream class has no corresponding InputStream class.
     *
     * 1. A low-level stream connects directly with the source of the data,
     *   process the raw data or resource and are accessed in a direct and unfiltered manner.
     * 2. A high-level stream is built on top of another stream using wrapping.
     *
     * Buffered classes read or write data in groups, rather than a single byte or character at a time.
     *
     * The java.io library defines four <b>abstract</b> classes that are the parents of all stream classes
     *    defined within the API: InputStream, OutputStream, Reader, and Writer.
     * By using the abstract parent class as <b>input</b>, the highlevel stream classes can be used much more often
     *    without concern for the particular underlying stream subclass.
     *
     * @see java.io.ObjectInputStream
     */

    /**
     * 1. Close: failing to close a file properly could leave it locked by the operating system
     *         such that no other processes could read/write to it until the program is terminated
     * 2. Flush: When data is written to an OutputStream, the underlying operating system does not
     *           necessarily guarantee that the data will make it to the file immediately.
     *           <- Java provides a flush() method,
     *              which requests that all accumulated data be written immediately to disk
     *              -> helps reduce the amount of data lost, cause a noticeable delay in the application
     *              -> be used intermittently. close() method will automatically call flush()
     * 3. Mark stream: The InputStream and Reader classes include mark(int) and reset() methods
     *                 to move the stream back to an earlier position.
     *                 -> call the markSupported() method, which returns true only if mark() is supported. Not all java.io input stream classes support
     *                 -> if you call reset() after you have passed your mark() read limit, an exception may be thrown at runtime
     * 4. Skip: The InputStream and Reader classes also include a skip(long) method,
     *          which as you might expect skips over a certain number of bytes.
     *          <- skip() will often be faster, because it will use arrays to read the data, comparing to read() and discard
     *
     *
     */


}
