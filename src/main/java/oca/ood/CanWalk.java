package oca.ood;

/**
 * Created by williaz on 11/2/16.
 */
public interface CanWalk {
    public default String getAbility() {
        return "walker";
    }
    public abstract int getSpeed();
    static String printAction() {
        return "Walking";
    }
}
