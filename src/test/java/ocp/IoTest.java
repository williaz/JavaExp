package ocp;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by williaz on 12/12/16 - 12/14 2.5d.
 * Wrapping is the process by which an instance is passed to the constructor of another class
 *       and operations on the resulting instance are filtered and applied to the original instance.
 *
 * watch out:
 * 1. low level stream only interact with resource, no other stream
 *    high-level stream only take stream
 *    PrintStream and PrintWriter can take both
 * 2. to move File, use renameTo()
 * 3. Java would convert slash to the right one when working with path
 * 4. benefit for writer/reader: built-in methods on String, encoding
 * 5. static variable will not be serialized
 * 6. not all stream support mark(), so be careful with undetermined.
 * 7. PrintStream and PrintWriter provide convenient method println()
 *
 *
 * @see java.io.PrintStream
 * @see FileOutputStream
 * @see BufferedOutputStream
 */
public class IoTest {
    private File local;
    @Before
    public void setUp() throws Exception {
        local = new File("/Users/williaz/IdeaProjects/JavaExp/io");

    }
    /**
     * A file is record within a file system that stores user and system data. Files are organized using directories.
     * A directory is a record within a file system that contains files as well as other directories.
     *   root directory is the topmost directory in the file system,
     * The file system is in charge of reading and writing data within a computer.
     * A path is a String representation of a file or directory within a file system.
     */

    /**
     * The File class cannot read or write data within a file,
     *     although it can be passed as a reference to many stream classes to read or write data
     * File class can be used to represent directories as well as files.
     * A File object often is initialized with String containing
     *   either an absolute or relative path to the file or directory within the file system.
     * The absolute path of a file or directory is the full path from the root directory to the file or directory,
     *       including all subdirectories that contain the file or directory.
     * The relative path of a file or directory is the path from the current working directory to file or directory.
     *
     * boolean exists(), isFile(), isDirectory()
     * getName(), getAbsolutePath(), getParent()
     * lastModified(), delete(), length() - bytes, renameTo(File)
     * mkdir(), mkdirs(), listFiles()
     *
     * @see File
     */
    @Test
    public void test_File() throws IOException {

        //retrieve the local separator character
        assertEquals("/", System.getProperty("file.separator"));
        assertEquals("/", File.separator);

        File file = new File("/Users/williaz/IdeaProjects/JavaExp/io");
        File test = new File(file, "/test.txt");

        assertFalse(test.exists());
        test.createNewFile();
        assertTrue(test.exists());
        assertTrue(file.isDirectory());
        //
        System.out.println(test.getAbsolutePath() + " : " + test.getParent() + " : " + test.getName());
        System.out.println(test.lastModified() + " : " + test.length() + " bytes");
        for (File f : file.listFiles()) {
            System.out.print(f.getName() + " ");
        }
        assertTrue(test.exists());
        File test1 = new File(file, "/io.txt");
        test.renameTo(test1);
        assertFalse(test.exists());
        assertTrue(test1.exists());
        //assertTrue(test.isFile());
        //test.delete();

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
     * By using the abstract parent class as <b>input</b>, the high level stream classes can be used much more often
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
     *                 -> call the markSupported() method, which returns true only if mark() is supported.
     *                        Not all java.io input stream classes support
     *                 -> if you call reset() after you have passed your mark() read limit, an exception may be thrown at runtime
     * 4. Skip: The InputStream and Reader classes also include a skip(long) method,
     *          which as you might expect skips over a certain number of bytes.
     *          <- skip() will often be faster, because it will use arrays to read the data, comparing to read() and discard
     *
     *
     */
    //call mark(int) with a read-ahead limit value.
    @Test
    public void test_MarkStream() throws IOException {
        File file = new File(local, "zoo.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            if (in.markSupported()) {
                in.skip(1000);
                System.out.println(in.readLine());
                in.mark(40);
                System.out.println(in.readLine());
                System.out.println(in.readLine());
                in.reset();
                System.out.println(in.readLine());

            } else System.out.print("Not support mark");
        }
    }

    /**
     * The data in a FileInputStream object is commonly accessed by successive calls
     *    to the read() method until a value of -1 is returned,
     *    indicating that the end of the stream—in this case the end of the file—has been reached.
     *    -> the read() method returns a primitive int value rather than a byte value.(for -1)
     */
    @Test
    public void test_LowLevelStream() {
        File parent = new File("/Users/williaz/IdeaProjects/JavaExp/io");
        File source = new File(parent, "zoo.txt");
        File copy = new File(parent, "zooCopy.txt");
        try(FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(copy)) {
            int bit;
            while ((bit = in.read()) != -1) {
                out.write(bit);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * use Buffered classes anytime you are reading or writing data with byte arrays.
     * read(byte[]) return int: 1. 0 for end; 2. last read for partial filling
     *
     * It is also common to choose a power of 2 for the buffer size,
     *   since most underlying hardware is structured with file block and cache sizes that are a power of 2.
     */
    @Test
    public void test_BufferedStream() throws IOException {
        File parent = new File("/Users/williaz/IdeaProjects/JavaExp/io");
        File source = new File(parent, "zoo.txt");
        File copy = new File(parent, "zooCopy1.txt");
        /*
         * The character encoding determines how characters are encoded and stored in bytes
         *     and later read back or decoded as characters.
         * use one byte for Latin characters, UTF-8 and ASCII
         * to using two or more bytes per character, such as UTF-16 .
         */
        Charset usAsii = Charset.forName("US-ASCII");
        Charset utf8 = Charset.forName("UTF-8");
        Charset utf16 = Charset.forName("UTF-16");
        try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(copy))) {
            byte[] buffer = new byte[256]; //depend on a number of factors including file system block size and CPU hardware.
            int len;
            while ((len = in.read(buffer)) > 0) {
                //System.out.println(new String(buffer, usAsii));
                System.out.println(new String(buffer, utf8));
                //System.out.println(new String(buffer, utf16));
                out.write(buffer, 0, len);
                out.flush(); //ensure that the written data actually makes it to disk before the next buffer of data is read.
            }
        }

    }

    /**
     * read/write char values
     * gives us structured access to the text data.
     * Writer offers a write(String) method that allows a String object to be written directly to the stream.
     * Using FileReader also allows you to pair it with BufferedReader in order
     *    to use the very convenient readLine() method,
     *
     * @see java.io.Writer
     */
    @Test
    public void test_BufferedRW() throws IOException {
        File parent = new File("/Users/williaz/IdeaProjects/JavaExp/io");
        File source = new File(parent, "zoo.txt");
        File copy = new File(parent, "zooCopy.txt");
        List<String> data = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = in.readLine()) != null) {
                data.add(line);
            }
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(copy))) {
            for (String s : data) {
                System.out.println(s);
                out.write(s);
                out.newLine();
            }

        }

    }

    /**
     * Serialization: The process of converting an in-memory object to a stored data format,
     * Deserialization: the reciprocal process of converting stored data into an object.
     *
     * The purpose of implementing the Serializable interface is to inform any process
     *   attempting to serialize the object that you have taken the proper steps to make the object serializable,
     *   which involves making sure that the classes of all instance variables within the object
     *   are also marked Serializable .
     *   -> NotSerializableException
     *   <- transient - skip, lost
     * Transient instance variables and static class members will also be ignored during the serialization and deserialization process.
     *
     * Good practice: 1. Added static variable serialVersionUID,
     *                   and update this static class variable anytime you modify the class.
     *                2. provide and manage the serialVersionUID in all of your Serializable classes,
     *                   updating it anytime the instance variables in the class are changed.
     *
     * The serialization process uses the serialVersionUID to identify uniquely a version of the class.
     *    -> If an older version of the class is encountered during deserialization, an exception may be thrown.
     *
     */

    /**
     * ObjectOutputStream class includes a method to serialize the object to the stream called void writeObject(Object).
     * ObjectInputStream class includes a deserialization method that returns an object called readObject().
     *  <- return Object
     *
     * @see ObjectOutputStream
     */
    @Test
    public void test_ObjectIoStream() throws IOException, ClassNotFoundException {
        List<SerialObject> list = new ArrayList<>();
        list.add(new SerialObject(25, "will", 6.9));
        list.add(new SerialObject(27, "Bill",7.0));
        list.add(new SerialObject(30, "William", 7.1));
        List<SerialObject> output = new ArrayList<>();
        File file = new File("/Users/williaz/IdeaProjects/JavaExp/io/obj.txt");
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            for (SerialObject o : list)
                out.writeObject(o);
        }

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));) {
            //swallow an exception
            //to catch an EOFException, which marks the program encountering the end of the file.
            while (true) {
                Object object = in.readObject();
                if (object instanceof SerialObject)
                    output.add((SerialObject) object);
            }

        } catch (EOFException e) {

        }
        System.out.println(output);
        System.out.println(SerialObject.getType());
    }

    /**
     * Java calls the first no-arg constructor for the first nonserializable parent class,
     *   skipping the constructors of any serialized class in between.
     * the constructor and any default initializations are ignored during the deserialization process.
     */

    /**
     * The PrintStream and PrintWriter classes are high-level stream classes
     *    that write formatted representation of Java objects to a text-based output stream.
     * PrintWriter class even has a constructor that takes an OutputStream as input,
     * System.out and System.err are actually PrintStream objects.
     * Both classes provide a method, checkError() ,
     *    that can be used to detect the presence of a problem after attempting to write data to the stream.
     * Console class includes two methods, format() and printf() , which take an optional vararg and format the output,
     *
     * print() : perform String.valueOf()(call toString()) on the argument and call the underlying stream’s write() method,
     * println() : insert a line break after the String value is written,
     *             line break or separator character is JVM (OS) dependent.
     * printf()
     * format()
     * @see java.io.PrintStream
     * @see PrintWriter
     */
    @Test
    public void test_PrintStream() {
        File log = new File(local, "print.log");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(log)))) {
            out.print("My score is: ");
            out.println(98);
            Locale cn = Locale.CHINA;
            out.printf(cn,"I am coming back! " + 80000);
            out.format("\nWill is the best");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * InputStreamReader class takes an InputStream instance and returns a Reader object.
     * OutputStreamWriter class takes an OutputStream instance and returns a Writer object.
     * The Filter classes(FilterInputStream, FilterOutputStream) are the superclass of all classes
     *    that filter or transform data.
     */

    /**
     * Console - the recommended technique for interacting with and displaying information
     *           to the user in a text-based environment.
     * Singleton, available in the JVM. It is created automatically for you by the JVM
     *     and accessed by calling the System.console() method(return null if not available).
     * reader(), writer() - similar to System.in, System.out
     * reader() return Reader; writer() return PrintWriter()
     * format(), printf() - String.format() but uses the default system locale
     * flush() - It is recommended that you call the flush() method prior to calling any
     *           readLine() or readPassword() methods in order to ensure that no data is pending during the read.
     * The readPassword() method is similar to the readLine() method, except that echoing is disabled.
     *      (user does not see the text they are typing)
     *      -> returns an array of characters
     * @see ConsoleSample
     * @see Console
     */







}
