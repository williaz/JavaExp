package spring;

/**
 * Created by williaz on 10/27/16.
 *
 */
public class BeanInjected {
    private String name;
    private int nums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    @Override
    public String toString() {
        return "BeanInjected{" +
                "name='" + name + '\'' +
                ", nums=" + nums +
                '}';
    }
}
