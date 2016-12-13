package ocp;

import java.io.Serializable;

/**
 * Created by williaz on 12/12/16.
 */
public class SerialObject implements Serializable {
    private static final long serialVerisonUID = 1L;
    private int num;
    private String name;
    private transient double height;
    private static String type;
    private long age;

    public SerialObject(int num, String name, double height) {
        this.num = num;
        this.name = name;
        this.height = height;
        this.age = 30;
    }

    public SerialObject() {
        this.num = 8;
        this.name = "Harrison";
        this.height = 3.1;
        this.age = 50;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        SerialObject.type = type;
    }

    @Override
    public String toString() {
        return "SerialObject{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", age=" + age +
                '}';
    }
}
