package ocp;

import org.junit.Test;

import oca.ood.Animal;
import oca.ood.Bird;
import oca.ood.CanFly;
import oca.ood.CanRun;
import oca.ood.CanadaGoose;
import oca.ood.Lion;
import oca.ood.Swan;

import static org.junit.Assert.*;
/**
 * Created by williaz on 11/21/16.
 * <p>
 * The compilation check only applies when instanceof is called on a class.
 * When checking whether an object is an instanceof an interface, Java waits until runtime to do the check.
 * The reason is that a subclass could implement that interface and the compiler wouldn’t know it.
 * -> instanceof unrelated interface = runtime check, true / false
 * -> instanceof unrelated class = compile exception
 * <p>
 * Virtual Method Invocation
 * Use @Override to avoid accidentally overloading
 * <p>
 * Override equals(): 1. reflective: x.equals(x); 2. symmetric: x->y, y->x
 *                    3. transitive: x->y, y->z => x->z
 *                    4. consistent: keep x->y; 5. false for null: x->null =>false, no NullPointerException
 */
public class AdvClassTest {

    /**
     * Bird -> Swan -> CanadaGoose
     */
    @Test
    public void test_Instanceof() {
        Bird bird = new CanadaGoose();
        assertTrue(bird instanceof Swan);

        //bird instanceof Lion; // ce
        assertFalse(bird instanceof CanRun);
        Animal lion = new Lion(12);
        assertFalse(lion instanceof CanFly);

        Bird swan = new Swan();
        assertFalse(swan instanceof CanadaGoose);

        if (swan instanceof Swan) {
            String color = ((Swan) swan).getColor();
            assertEquals("White", color);
        }
        assertFalse(null instanceof Object); // you can check

        assertTrue(null == null);
    }

    /**
     * It is common to multiply by a prime number
     * when combining multiple fields in the hash code.
     */

    /**
     * Enum: it is a type; UPPERCASE convention; static
     *  1. You cannot extend enum;
     *  2. its valueOf(String) - The String passed in must match exactly
     *  3. For using in switch, must be unqualified name
     *  4. must have ; if use (value)
     *  5. The first time that we ask for any of the enum values,
     *     Java constructs all of the enum values.
     *     After that, Java just returns the already‐constructed enum values.
     *  6. The constructor is private because it can only be called from within the enum .
     */

    public enum Season {
        WINTER("Low"), SPRING("High"), SUMMER("High"), FALL("Medium");
        private String people;
        private Season(String s) {
            people = s;
            System.out.println("enum constructor only got called once for each type!");
        }
    }

    @Test
    public void test_Enum() {
        for (Season s : Season.values()) {
            switch (s) {
                case WINTER: System.out.println(Season.WINTER); break;
                case SPRING: System.out.println(Season.SPRING); break; //must be unqualified name
                case SUMMER: System.out.println(Season.SUMMER); break;
                case FALL: System.out.println(Season.FALL); break;
                //case "High": System.out.println(Season.FALL); break; // no value
            }
        }

        assertEquals(Season.SUMMER, Season.valueOf("SUMMER"));
        //assertEquals(Season.SUMMER, Season.valueOf("SUmmER")); // exact type, case sensitive

        Season season = Season.FALL;

    }

    public enum Weekday {
        MONDAY {
            public int NumOfEmployees(){ return 30;};
            public void printWorkHours(){System.out.println("11 AM - 5 PM");}
        }, TUESDAY {
            public int NumOfEmployees(){ return 60;};
        }, WEDNESDAY {
            public int NumOfEmployees(){ return 50;};
        }, THURSDAY {
            public int NumOfEmployees(){ return 40;};
        }, FRIDAY {
            public int NumOfEmployees(){ return 100;};
            public void printWorkHours(){System.out.println("9 AM - 3 PM");}
        };
        public void printWorkHours(){System.out.println("9 AM - 5 PM");}
        public abstract int NumOfEmployees();
    }

    @Test
    public void test_Enum1() {
        for (Weekday day : Weekday.values()) {
            System.out.println(day); //Weekday.type
            day.printWorkHours();
            System.out.println("This day will has " + day.NumOfEmployees() + "employees");
            System.out.println();
        }
    }

    /**
     *   4 type Nested Classes:
     * 1. Member inner class, like instance variable
     *      can be any access, no static, can abstract / final,
     *      can access outer class members including private;
     *      compile as Outer$Inner.class
     *      2 level is the deepest for using inner class name directly
     *      interface can also private
     *<p>
     * 2. Local inner class, like local variable in method
     *      no access modifier to use
     *      no static,
     *      can only access effectively final or final
     *<p>
     * 3. Anonymous inner class, no name local inner class
     *      Anonymous inner classes are required to extend an existing class
     *        or implement an existing interface
     *      it is an implementation, no abstract / final
     * 4. Static nested class, like static variable
     *      Java treats it as a namespace
     *      enclosing class can refer its member
     *      import / import static
     *
     * @see OuterClass
     */


}
