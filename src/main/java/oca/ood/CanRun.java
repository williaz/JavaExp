package oca.ood;

/**
 * Created by williaz on 11/4/16.
 */
public interface CanRun extends CanWalk {
    public default String getAbility() {
        return "runner";
    }
    static String printAction() {
        return "Runing";
    }
}
