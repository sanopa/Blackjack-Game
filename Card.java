/**
 * APCSSec01YL12
 * Blackjack: Card
 * Java 1.7, MacOSX10.8
 * May 14, 2013
 */
public class Card
{
    private int suit; //1 = Clubs, 2 = Diamonds, 3 = Hearts, 4 = Spades
    private String value;
    
    /**
     * Default Constructor
     */
    public Card() {
    }
    
    /**
     * Normal Constructor
     */
    public Card(int s, String v) {
        suit = s;
        value = v;
    }
}
