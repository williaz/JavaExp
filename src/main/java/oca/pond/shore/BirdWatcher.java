package oca.pond.shore;

/**
 * Created by williaz on 10/28/16.
 */
public class BirdWatcher {
    public void watchBird() {
        Bird bird = new Bird();
        bird.floatInWater(); // calling protected member
        System.out.println(bird.text); // calling protected member
    }
}
