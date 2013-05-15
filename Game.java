/**
 * Sophia Anopa/Alex Yang
 * APCSSec01Yl12
 * Blackjack: Game
 * Java 1.7, MacOSX10.8
 * May 14-21, 2013
 */
import java.util.*;
public class Game
{
    private ArrayList<Player> players;
    private Player winner;
    private int pnum;
    private ArrayList<Card> deck;
    
    /** Default Constructor **/
    public Game() {
        players = new ArrayList<Player>();
        winner = new Player();
        pnum = 0;
        deck = new ArrayList<Card>();
        createDeck();
    }
    
    /** Normal Constructor **/
    public Game(int num) {
        players = new ArrayList<Player>();
        winner = new Player();
        pnum = num;
        deck = new ArrayList<Card>();
        createDeck();
        createPlayers();
    }
    
    /** Accessors **/
    public void showResult() {
    }
    
    /** Other methods **/
    public void createDeck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values())
                deck.add(new Card(rank, suit));
        }
    }
    
    public void createPlayers() {
        Scanner reader = new Scanner(System.in);
        for (int i = 0; i < pnum; i++) {
            System.out.print("Please enter Player " + i + "'s name: ");
            String name = reader.next();
            Player p = new Player(name);
            p.draw(deck);
            p.draw(deck);
            players.add(p);
            System.out.println();
        }
        Dealer d = new Dealer();    
        d.draw(deck);
        d.draw(deck);
        players.add(d);
    }
}
