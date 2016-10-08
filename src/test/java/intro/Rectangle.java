package intro;

/**
 * Created by williaz on 10/7/16.
 */
public class Rectangle {
    private final int width;
    private final int height;
    public Rectangle(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    public int area() {
        return width * height;
    }
}
