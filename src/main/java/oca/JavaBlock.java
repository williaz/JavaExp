/**
 * # package must be first non-comment
 * Order PIC: package, import, and cla
 */
package oca;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by williaz on 10/18/16. 2.5d
 * # Secure: Java code runs inside the JVM. This creates a sandbox that
 *   makes it hard for Java code to do evil things to the computer it is running on.
 * ###for the exam, your goal is to know details about the language and not have the IDE hide them for you.
 *
 * #1. classes are the basic building block. An object is a runtime instance of a class in memory.
 * variable - state; method - behavior.
 *
 * #at most one of the classes in the file is allowed to be public
 *
 * #2. a method signature is the method name and the number and type of its parameters.
 * Return types and thrown exceptions are not considered to be a part of the method signature.
 *
 * #3. You do not need to have a JDK to run the code—a JRE is enough.
 *
 * #4. Java classes are grouped into packages;
 * The rule for package names is that they are mostly letters or numbers separated by dots.
 *
 * #5. The * is a wildcard that matches all classes in the package. It doesn’t import child packages, fields, or methods;
 * you can only have one wildcard and it must be at the end
 *
 * The compiler figures out what’s actually needed. Java automatically looks in the current package for other classes.
 *
 * When the class is found in multiple packages, Java gives you the compiler error
 * -->If you explicitly import a class name, it takes precedence over any wildcards present.
 * --->fully qualified class name
 *
 * # When running a Java program, you pass just the name of the class without the .class extension.
 *
 */
public class JavaBlock {
    int a =1 ; // 1 -> 5 -> 7
    int b =4;  // 4 ->6
    int e;
    private String name;

    //instance initializer
    /**
     *     Order matters for the fields and blocks of code.
     *     You can’t refer to a variable before its declaration:
     */
    {

        a=5;
        b = 6;
        c = 4;
        System.out.println("In field: "+e);
        System.out.println("In field: "+name);
    }

    int c = 8; // 4 ->8

    /**
     * #The purpose of a constructor is to initialize fi elds, although you can put any code in there.
     * Another way to initialize fi elds is to do so directly on the line on which they’re declared.
     *
     * #!!Fields and instance initializer blocks are run in the order in which they appear in the file.
     *
     * #The constructor runs after all fields and instance initializer blocks have run.
     */
    public JavaBlock() {
        a=7;
        d = 10;

    }

    int d = 9; // 9 ->10


    int x;
    /**
     * # Instance and class variables do not require you to initialize them.
     *  As soon as you declare these variables, they are given a default value.
     *  boolean - false; 0; 0.0; '\u0000'; null
     *
     * # A local variable is a variable defined within a method. Local variables must be initialized before use.
     * They do not have a default value and contain garbage data until initialized.
     * Until it is assigned a value, it cannot appear within an expression,
     *
     * # a method parameter. It is also local to the method.
     *
     * # Local variables—in scope from declaration to end of block
     *  Instance variables—in scope from declaration until object garbage collected
     *  Class variables—in scope from declaration until program ends
     *
     * @return
     */
    public int getOneNum(int c){
        int a = 5;
        int b;

        if(a > 5){
            b =5;
            c =b * 2;
        }else{
            b =0;
            c = b;
        }

        return c;

    }

    /**
     * # Just keep in mind that finalize() might not get called and that it definitely won’t be called twice.
     *   0-1
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Calling finalize");
    }

    /**
     * # A main() method is tht gateway between the startup of a Java process,
     * which is managed by the Java Virtual Machine (JVM), and the beginning of the programmer’s code.
     *
     * # An array is a fixed-size list of items that are all of the same type.
     * @param args
     *
     * # Since 0 needs to be included in the range, Java takes it away from the positive side.
     * byte: 8bit, 1 byte
     * short: 16bit, 2 byte
     * int, float: 32bit, 4 byte
     * long, double: 64bit, 8 byte
     *
     * octal (digits 0–7), which uses the number 0 as a prefix—for example, 017
     * hexadecimal (digits 0–9 and letters A–F), which uses the number 0 followed by x or X as a prefix—for example, 0xFF
     *   A - 10, B 11, C 12, D 13, E 14, F 15
     * binary (digits 0–1), which uses the number 0 followed by b or B as a prefix—for example, 0b10
     *
     * #You can declare many variables in the same declaration as long as they are all of the same type.
     *  You can also initialize any or all of those values inline.
     *  The shortcut to declare multiple variables in the same statement only works when they share a type.
     *
     *  #The name must begin with a letter or the symbol $ or _.
     *  Subsequent characters may also be numbers.
     *  You cannot use the same name as a Java reserved word.
     *  ---->The exam will mostly use CamelCase common conventions for identifiers, but not always.
     *  When you see a nonstandard identifier, be sure to check if it is legal.
     *  $Don’t start any identifiers with $. The compiler uses this symbol for some files.
     *
     *  #An object sits on the heap and does not have a name.
     *   An object cannot be assigned to another object,
     *   nor can an object be passed to a method or returned from a method.
     *
     */


    public static void main(String[] args) {

        String _s1 = "Will", $s2 = "Is", s3 = "Best";
        JavaBlock jb = new JavaBlock();


        System.out.println("Scope "+ jb.getOneNum(4));



//        System.out.println(args[0]);
//        System.out.println(args[1]);

        System.out.println(jb.a);
        System.out.println(jb.b);
        System.out.println(jb.c);
        System.out.println(jb.d);

        double maxInt = Math.pow(2, 32)/2-1;
        System.out.println(Integer.MAX_VALUE+ " "+ maxInt);

        int octNum = 013;
        int hexNum = 0x31a;
        int biNum = 0b101011;

        short ss = 3;
        long ll = 44543L;
        double dd = 34;

        System.out.println("Different Base: "+octNum+ " "+ hexNum+ " "+ biNum);

        //You can add underscores anywhere
        // except at the beginning of a literal, the end of a literal,
        // right before a decimal point, or right after a decimal point.
        int j7= 1_000_000;

        System.out.println(j7);

        /*
        You do need to know that System.gc() is not guaranteed to run,
        and you should be able to recognize when objects become eligible for garbage collection.
        ----> For eligible for garbage collection Q: Draw arrow figure
         */
        List<JavaBlock> l = new ArrayList<>();
        l.add(jb);
        System.out.println("Before calling gc(): "+ l.size());
        jb = null;
        System.gc();
        System.out.println("After calling gc(): "+ l.size());



    }

}


/**
 * # multiple classes can be defined in the same file, but only one of them is allowed to be public.
 * The public class matches the name of the file.
 * A file is also allowed to have neither class be public
 */
class AnotherClass{

}
