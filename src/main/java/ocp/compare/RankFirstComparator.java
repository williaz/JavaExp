package ocp.compare;

import java.util.Comparator;

/**
 * Created by williaz on 11/27/16.
 */
public class RankFirstComparator implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        int result = o1.getRank() - o2.getRank();
        if(result != 0) return result;
        return o1.getSuit().ordinal() - o2.getSuit().ordinal();
    }

}
