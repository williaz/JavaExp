package oca;

/**
 * Created by williaz on 11/15/16.
 * Order of Initialization -
 * 1. If there is a superclass, initialize it first
 * 2. Static variable declarations and then static initializer in the order they appear in the file.
 * 3. Instance variable declarations and then instance initializer in the order they appear in the file.
 * 4. The constructor.
 */
public class OrderInst {
    static String stat1 = "first static declaration";
    String inst1 = "first instance declaration";
    static {
        System.out.println("\n# in static initialized");
        System.out.println(stat1);
        System.out.println("second static initial value: "+ OrderInst.stat2);
        stat1 = "first static be static initialized";
        stat2 = "second static be static initialized";
        System.out.println(stat1);
        System.out.println(OrderInst.stat2);
    }
    static String stat2 = "second static declaration";

    {
        System.out.println("\n# in instance initialized");
        System.out.println(stat1);
        System.out.println(stat2);


        System.out.println(inst1);
        System.out.println("second instance initial value: "+ this.inst2);
        inst1 = "first instance be initialized";
        inst2 = "second instance be initialized";
        System.out.println(inst1);
        System.out.println(this.inst2);

    }
    String inst2 = "second instance declaration";

    public OrderInst() {
        System.out.println("\n# in Constructor");
        System.out.println(stat1);
        System.out.println(stat2);
        System.out.println(inst1);
        System.out.println(inst2);
    }

    public static void main(String[] args) {
        OrderInst oi = new OrderInst();
    }
}
