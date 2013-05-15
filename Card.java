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
    private int numvalue;
    public static int[] numvals = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    
    /**
     * Default Constructor
     */
    public Card() {
    }
    
    /**
     * Normal Constructor
     */
    public Card(int s, String v, int n) {
        suit = s;
        value = v;
        numvalue = n;
    }
    
    /**
     * Mutators
     */
    public void setNumValue(int n) {
        numvalue = n;
    }
    
    /**
     * Accessors
     */
    public int getSuit() {
        return suit;
    }
    
    public String getValue() {
        return value;
    }
    
    public int getNumValue() {
        return numvalue;
    }
    
    /**
     * Other
     */
    public String toString() {
        String result = value + " of ";
        switch (suit) {
            case 1:
                result += "Clubs";
                break;
            case 2:
                result += "Diamonds";
                break;
            case 3:
                result += "Hearts";
                break;
            case 4:
                result += "Spades";
                break;
        }
        return result;
    }
}
