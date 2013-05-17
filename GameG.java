/**
 * Sophia Anopa/Alex Yang
 * APCSSec01Yl12
 * Blackjack: Game
 * Java 1.7, MacOSX10.8
 * May 14-21, 2013
 */
import javax.swing.*; 
import javax.imageio.*;
  
import java.awt.*;
import java.awt.image.*;
  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
 
import java.util.*;
import java.io.*;
public class GameG extends JLabel implements ActionListener
{
    private ArrayList<Player> players;
    private int pnum;
    private int dnum;
    private ArrayList<Card> deck;
    
    private JButton draw = new JButton("Draw");
    private JButton stand = new JButton("Stand");
    private JButton play = new JButton("Play!");
    private JButton next = new JButton("Next");
    private JFrame frame = new JFrame("Blackjack Game");
    private JTextField pln = new JTextField(10);
    private JTextField dkn = new JTextField(10);
    private JTextField pname = new JTextField(10);
    private JPanel gpanel, beginningimg, beginning, pnames;  
    
    /** Default Constructor **/
    public GameG() {
        deck = new ArrayList<Card>();
        players = new ArrayList<Player>();
        frame.setLayout(new BorderLayout());
        
        beginningimg = new JPanel();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("blackjack.jpg"));
        } catch (IOException e) {
        }
        JLabel bimg = new JLabel(new ImageIcon(img));
        beginningimg.add(bimg);
        
        beginning = new JPanel();
        JLabel pl = new JLabel("Enter number of players: ");
        JLabel dk = new JLabel("Enter number of decks: ");
        
        play.addActionListener(this);
        beginning.add(pl);
        beginning.add(pln);
        beginning.add(dk);
        beginning.add(dkn);
        beginning.add(play);
        
        frame.add(beginningimg, BorderLayout.NORTH);
        frame.add(beginning,BorderLayout.CENTER);   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setSize(600,500);  
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == play) {
            pnum = Integer.parseInt(pln.getText());
            dnum = Integer.parseInt(dkn.getText());
            createDeck();
            createPlayers();
            //play();
        } else if (event.getSource() == next) {
            Player p = new Player(pname.getText());
            p.draw(deck);
            p.draw(deck);
            players.add(p);
        }
    }
    
    /** Accessors **/
    public String showResult() {
        String winner = "\n\n--------------=Results=----------------\n";
        int dev = 21;
        boolean over = true;
        ArrayList<Player> winners = new ArrayList<Player>();
        for (Player p: players) {
            winner += p.getName() + "'s Points: " + p.getValue() + "\n";
            if (p.getValue() == 21) {
                winners.add(p);
            }
            else if (Math.abs(p.getValue() - 21) < dev && p.getValue() < 21 && over) {
                dev = Math.abs(p.getValue() - 21);
                over = false;
            }
            else if (Math.abs(p.getValue() - 21) < dev && !over) {
                dev = Math.abs(p.getValue() - 21);
            }
        }
        if (winners.size() == 0) {
            for (Player p: players) {
                if (over) {
                    if (Math.abs(p.getValue() - 21) == dev) {
                        winners.add(p);
                    }
                }
                else if (Math.abs(p.getValue() - 21) == dev && (p.getValue() < 21)) {
                    winners.add(p);
                }
            }
        }
        winner += "\n\nThe winner(s):\n";
        for (int i = 0; i < winners.size(); i++) {
            winner += winners.get(i).toString();
        }
        return winner;
    }
    
    /** Other methods **/
    /**
     * Plays the game, going through all players and letting them draw or stand until everybody stands
     * Pre: none
     * Post: the results are calculated using a different method and displayed
     */
    public void play() {
        gpanel=new JPanel();
        gpanel.setLayout(new GridLayout(1,2));
        draw.addActionListener(this);
        stand.addActionListener(this);
        gpanel.add(draw);
        gpanel.add(stand);
        //frame.getContentPane().remove(beginningimg);
        frame.setContentPane(gpanel);
        frame.validate();
        frame.repaint();
        
        Scanner reader = new Scanner(System.in);
        int stands = 0;
        while (stands < pnum) {
            stands = 0;
            for (Player p : players) {
                if (p.getStand()) {
                    stands++;
                } else if (p instanceof Dealer) {
                    p.draw(deck);
                } else {
                    System.out.println("--------------=Next Player=----------------");
                    System.out.println(p);
                    System.out.print("Draw (1) or Stand (2)? ");
                    int choice = checkChoice.checkValidity(reader.next());
                    while (choice == -1 || (choice != 1 && choice != 2)) {
                        System.out.println("Please enter a valid number.");
                        System.out.print("Draw (1) or Stand (2)? ");
                        choice = checkChoice.checkValidity(reader.next());
                    }
                    if (choice == 1) {
                        p.draw(deck);
                        System.out.println("\nNew Hand");
                        System.out.println(p);
                    } else {
                        p.stand();
                    }
                }
            }
        }
        System.out.println(showResult());
        reader.close();
    }
    
    /**
     * Creates a standard deck of cards
     * Pre: none
     * Post: a new deck is created
     */
    public void createDeck() {
        while (dnum > 0) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values())
                    deck.add(new Card(rank, suit));
            }
            dnum--;
        }
    }
    
    /**
     * Creates the Players in the game and the dealer, drawing two cards for each
     * Pre: none
     * Post: the requested number of Players is created and each Player receives two cards
     */
    public void createPlayers() {
        pnames = new JPanel();
        JLabel pn = new JLabel();
        frame.getContentPane().remove(beginning);
        next.addActionListener(this);
        for (int i = 1; i <= pnum; i++) {
            pn.setText("Enter Player " + i + "'s name: ");
            pnames.add(pn);
            pnames.add(pname);
            pnames.add(next);
            frame.add(pnames, BorderLayout.SOUTH);
            frame.revalidate();
            frame.repaint();
        }
        Dealer d = new Dealer();    
        d.draw(deck);
        d.draw(deck);
        players.add(d);
        //System.out.println("\n-------------------------------------------");
    }
    
    public static void main(String[]args) {
        GameG g = new GameG();
    }
}
