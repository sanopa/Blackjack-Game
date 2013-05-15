/**
 * Sophia Anopa/Alex Yang
 * APCSSec01YL12
 * Blackjack: Card
 * Java 1.7, MacOSX10.8
 * May 14-21, 2013
 */
public class Card
{
    public enum Rank { 
        Deuce(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), 
        Ten(10), Jack(10), Queen(10), King(10), Ace(0);
        private int value;
        private Rank(int v) {
            value = v;
        }
        public int getValue() {
          return value;
        }
    }
    
    public enum Suit { Clubs, Diamonds, Hearts, Spades }
    
    private Rank rank;
    private Suit suit;
    
    /**
     * Default Constructor
     */
    public Card() {
        rank = null;
        suit = null;
    }
    
    /**
     * Normal Constructor
     */
    public Card(Rank r, Suit s) {
        rank = r;
        suit = s;
    }
     
    /**
     * Accessors
     */
    public Rank getRank() {
        return rank;
    }
    public Suit getSuit() {
        return suit;
    }
    public int getValue() {
        return rank.getValue();
    }
    
    /**
     * Other
     */
    public String toString() {
        String result = rank + " of " + suit;
        return result;
    }
}
