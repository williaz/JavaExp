package oca.ood;

/**
 * Created by williaz on 10/31/16.
 */
public class Lion extends Animal {
    private String title;
    // hiding variable, Don’t do it in Practice
    private String name;

    public Lion(int age) {
        this(age, "Siba");
    }

    public Lion(int age, String name) {
        super(age);
        this.name = name;
        title = "King";
    }

    public void roar() {
        //this and super may both be used for methods or variables defined in the parent class,
        // but only this may be used for members defi ned in the current class.

        System.out.println("The " + this.getAge() + " year old lion " + title + " " + this.name + " says: Roar! "
                + "(Default name: " + super.name);
    }

    // private methods in parent class can't be overridden
    @Override
    public Lion getSample(){
        return new Lion(12, "Will");
    }

    /*
    In Java, it is not possible to override a private method in a parent class
    since the parent method is not accessible from the child class.
     */
    protected String makeSound(){
        return ("HaulHaul");
    }

    public String getDefName() {
        return super.name;
    }

    public void setDefName(String name) {
        super.name = name;
    }

    // static hiding
    public static String getInfo() {
        return "in Lion static";
    }
    @Override
    public String getInfoInst() {
        return "in Lion overridden method";
    }

    public void testInLion(){
        System.out.println("For static hiding in derived class(Lion) : "
                + getInfo() + " For overriding method : " + getInfoInst());
    }

    @Override
    public String toString() {
        return "Lion{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}