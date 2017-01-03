package ocp;

/**
 * Created by williaz on 11/22/16.
 */
public class OuterClass {
    private String name = "Outer";
    private int length = 5;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OuterClass)) return false;

        OuterClass that = (OuterClass) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /**
     * Member Inner Class
     */
    public class InnerClass {
        private String name = "Inner";
        public int age = 5;

        public String getName() {
            return this.name + " " + OuterClass.this.name;
        }// OuterClass.this.name
    }

    public String getName() {
        InnerClass inner = new InnerClass();
        return this.name + " " + inner.name ;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Local Inner Class
     */

    public int getArea() {
        int width = 10;
        class LocalInner {  // no access modifier
            public int getArea() {
                return width * length;  // effectively final
            }
        }
        LocalInner li = new LocalInner();
        return li.getArea();
    }

    /**
     * Anonymous Inner Class
     */
    private abstract class Discount {
        public abstract double getDiscount();
    }

    public double getPrice() {
        double price = 15;
        Discount discount = new Discount() {
            @Override
            public double getDiscount() {
                return 0.7;
            }
        };// do not miss ;

        return price * discount.getDiscount();
    }

    static class StaticInner { // normal class
        private String name = "Static";

        public String getName() {
            return name ;
        }
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        System.out.println(outer.getName());
        InnerClass inner = outer.new InnerClass(); // use instance of outer class to instantiate
        System.out.println(inner.getName());
        outer.setName("Will");
        System.out.println(inner.getName());

        System.out.println(outer.getArea());

        System.out.println(outer.getPrice());

        StaticInner staticInner = new StaticInner();
        System.out.println(staticInner.getName());
    }
}
