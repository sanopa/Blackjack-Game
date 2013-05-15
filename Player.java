/**
 * Sophia Anopa/Alex Yang
 * APCSSec01YL12
 * Blackjack: Player
 * Java 1.7, MacOSX10.8
 * March 14-21, 2013
 */
import java.util.*;
public class Player
{
    private ArrayList<Card> hand;
    private boolean stand;
    private int value;
    private String name;
    
    /** Default Constructor **/
    public Player() {
       hand = new ArrayList<Card>();
       stand = false;
       value = 0;
       name = null;
    }
    /** Normal Constructor **/
    public Player(String n) {
       hand = new ArrayList<Card>();
       stand = false;
       value = 0;
       name = n;
    }
    
    /** Mutator **/
    
    public void stand() {
        stand = true;
    }
    
    /** Accessors **/
    public String getName() {
        return name;
    }
    public boolean getStand() {
        return stand;
    }
    public int getValue() {
        return value;
    }
    
    /** Other methods **/
    public void draw(ArrayList<Card> deck) {
        int n = deck.size();
        int key = (int)(Math.random()*n);
        hand.add(deck.get(key));
        deck.remove(key);
        this.sum();
    }
    
    public void sum() {
        int aces = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().equals("Ace")) {
                aces++;
            }
            else {
                value += hand.get(i).getRank().getValue();
            }
        }
        if (aces > 0) {
            value += aces;
            for (int i = 0; i < aces; i++) {
                if ((value + 10) <= 21) {
                    value += 10;
                }
            }
        }
    }
    
    public String toString() {
        String result = "Name: " + name + "\n";
        result += "Hand: ";
        for (int i = 0; i < hand.size(); i++) {
            result += hand.get(i).toString();
            if (!(i == (hand.size()-1))) {
                result += ", ";
            }
        }
        result += "\nValue: " + value;
        return result;
    }
}
