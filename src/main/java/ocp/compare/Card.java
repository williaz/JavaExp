package ocp.compare;

import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

/**
 * Created by williaz on 11/27/16.
 */
public class Card implements Comparable<Card>{

//    if only implements Comparable without Generic
//    @Override
//    public int compareTo(Object o) {
//        return 0;
//    }

    private int getNum() {
        return (this.suit.ordinal() * 13) + this.rank - 1;
    }
    @Override
    public int compareTo(Card o) {
        return getNum() - o.getNum();
    }

    //clubs (♣), diamonds (♦), hearts (♥) and spades (♠)
    public enum Suit{CLUB, DIAMOND, HEART, SPADE} //no need ;
    private Suit suit;
    private int rank;

    public Card(Suit suit, int rank) {
        if (rank <=0 || rank >13) throw new IllegalArgumentException("only 13 ranks for each suit!"); // data invariant
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    // make compareTo consistent with equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        if (rank != card.rank) return false;
        return suit == card.suit;

    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank;
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }
}
