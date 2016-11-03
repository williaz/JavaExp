package oca.ood;

/**
 * Created by williaz on 11/2/16.
 */
public abstract class Bird implements CanFly, CanWalk{
    @Override
    public String getAbility() {
        return "flyer and walker";
    }
}
