package oca.ood;

/**
 * Created by williaz on 10/31/16.
 */
public class Animal {
    private int age;
    protected String name;

    public Animal(int age) {
        super();
        this.age = age;
        name = "Animal";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Animal getSample() {
        return new Animal(4);
    }

    private void makeSound() {
        System.out.println("ZzzZzz...");
    }

    public static String getInfo() {
        return "in Animal static";
    }
    public String getInfo(String ss) {
        return "www";
    }

    public String getInfoInst() {
        return "in Animal overridden method";
    }

    public void testInAnimal(){
        System.out.println("For static hiding in parent class(Animal) : "
                + getInfo() + " For overriding method : " + getInfoInst());
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}