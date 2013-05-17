/**
 * Sophia Anopa/Alex Yang
 * APCSSec01YL12
 * Blackjack: Dealer
 * Java 1.7, MacOSX10.8
 * March 14-21, 2013
 */
import java.util.*;
public class Dealer extends Player {
    private final String name = "Dealer";
    
    /** Accessor **/
    public String getName() {
        return name; 
    }
    
    /**
     * Overwritten method for drawing as the Dealer only draws if his hand value is less than 17
     * Pre: ArrayList<Card> deck is a list of Cards
     * Post: nothing
     */
	public void draw(ArrayList<Card> deck) {
		if (getValue() < 17){
			super.draw(deck);
		}
	}
}

