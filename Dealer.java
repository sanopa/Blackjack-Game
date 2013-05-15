/**
 * Sophia Anopa/Alex Yang
 * APCSSec01YL12
 * Blackjack: Dealer
 * Java 1.7, MacOSX10.8
 * March 14-21, 2013
 */
import java.util.*;
public class Dealer extends Player {
	public void draw(ArrayList<Card> deck) {
		if (getValue() < 17){
			super.draw(deck);
		}
	}
}

