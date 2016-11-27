package ocp.compare;

import java.util.Comparator;

/**
 * Created by williaz on 11/27/16.
 */
public class SuitFirstComparator implements Comparator<Card> {
    //private int computeNum(Card c) { return (c.getRank() - 1) * 4 + c.getSuit().ordinal() + 1;}
    private int computeNum(Card c) { return c.getRank() + 13 * c.getSuit().ordinal() - 1;}
    @Override
    public int compare(Card o1, Card o2) {
        return computeNum(o1) - computeNum(o2);
    }
}
