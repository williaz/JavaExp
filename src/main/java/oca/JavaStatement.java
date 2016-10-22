package oca;

/**
 * Created by williaz on 10/20/16.
 *
 * #1 post-unary ++, -- and ++, --pre-unary have the highest order of operator precedence
 *
 * #2 Arithmetic operators are often encountered in early mathematics and
 * include addition (+), subtraction (-), multiplication (*), division (/), and modulus (%).
 *
 * #3 Smaller data types, namely byte, short, and char, are first promoted to int
 *   any time they’re used with a Java binary arithmetic operator, even if neither of the operands is int.
 *
 * #4  The logical operators, (&), (|), and (^), may be applied to both numeric and boolean data types.
 *   When they’re applied to boolean data types, they’re referred to as logical operators.
 *   Alternatively, when they’re applied to numeric data types, they’re referred to as bitwise operators,
 *   as they perform bitwise comparisons of the bits that compose the number.
 *   $Exclusive OR is only true if the operands are different.
 * #5  Another common place the exam may try to lead you astray is by providing code
 *     where the boolean expression inside the if-then statement is not actually a boolean expression.
 *
 *     i = i++;
 *     boolean f = false; while(f = true){}
 *     scope with block!
 *
 */
public class JavaStatement {

    public static void main(String[] args) {

        /*
        As of Java 7, only one of the right-hand expressions of
        the ternary operator will be evaluated at runtime.
         */
        int y3 = 1;
        int z3 = 1;
        final int x3 = y3<10 ? y3++ : z3++;
        System.out.println(y3+","+z3); // Outputs 2,1
        /*
        There is no requirement that second and third expressions
        in ternary operations have the same data types
         */
        int y33 =6;
        System.out.println((y33 > 5) ? 21 : "Zebra");

        /*
        If the numeric values are of different data types,
         the values are automatically promoted as previously described.
         */
        System.out.println(4 == 4.00);

        /*
        A more common example of where short-circuit operators are used is checking for null objects
        before performing an operation
         */
        String str = "Short circuit operator";


        if(str != null && str.length()>10){
            System.out.println(str);
        }

        int i_s = 0;
        boolean ss = true || (i_s++ == 4);
        ss = false && (i_s ++ <10);

        System.out.println("Nothing happen due to short circuit: "+ i_s);

        /*
        Overflow is when a number is so large that it will no longer fit within the data type,
        so the system “wraps around” to the next lowest value and counts up from there.
        There’s also an analogous underflow, when the number is too low to fit in the data type.
         */
        System.out.println(2147483647+1);
        System.out.println(2147483647+2);

        boolean t = true;
        boolean f = false;
        System.out.println(!t);

        long ll = 132435647868L;

        short s = 4;
        byte b = 3;
        float fl = 4.5f;
        /*Smaller data types, namely byte, short, and char, are first promoted to int any time
        they’re used with a Java binary arithmetic operator, even if neither of the operands is
        int.
        $Note that unary operators are excluded from this rule.
        */
        int sb = s+b;
        short sbs = (short) sb; //explicit cast
        short sp = s++;
        /*
          compound assignment operators
          Compound operators are useful for more than just shorthand—they can also save us
          from having to explicitly cast a value.
        */
        sp+=1;
        long l1 = 4543L;
        int i1 =3;
        i1 *= l1;
        System.out.println("save: "+ i1);

        /*
        the result of the assignment is an expression in and of itself,
        equal to the value of the assignment.
        */
        int x = 123;
        int y = (x=5);
        System.out.println("expression as value: "+ x + " -- "+ y);


        int i = -5;
        double d = 2.3;
        System.out.println("modulus to negative integers: "+ i%3);
        System.out.println("modulus to floating-point integers:(with a rounding error) "+ d%2 +" "+ d%.2);

    }


}
