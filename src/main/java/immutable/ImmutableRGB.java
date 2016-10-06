package immutable;

/**
 * Created by williaz on 10/2/16.
 */
public final class ImmutableRGB {
    // Values must be between 0 and 255.
    private final int red;
    private final int green;
    private final int blue;
    private final String name;

    private static void check(int red,
                       int green,
                       int blue) {
        if (red < 0 || red > 255
                || green < 0 || green > 255
                || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    private ImmutableRGB(int red,
                           int green,
                           int blue,
                           String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    public static ImmutableRGB getImmutableRGB(int red,
                                               int green,
                                               int blue,
                                               String name){
        check(red, green, blue);
        return new ImmutableRGB(red,green,blue,name);

    }

    /*
    public void set(int red,
                    int green,
                    int blue,
                    String name) {
        check(red, green, blue);
        synchronized (this) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }
    }
    */

    public synchronized int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized ImmutableRGB invert() {
       return new ImmutableRGB(255 - red,
                255 - green, 255 - blue,
                "Inverse of " + name);
    }


    public static void main(String[] args) {
        ImmutableRGB color =ImmutableRGB.getImmutableRGB(45,123,222,"im");
        int myColorInt = color.getRGB();      //Statement 1
        String myColorName = color.getName(); //Statement 2
        System.out.println(myColorInt+" "+myColorName);
    }
}
