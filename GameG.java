/**
 * Sophia Anopa/Alex Yang
 * APCSSec01Yl12
 * Blackjack: Game
 * Java 1.7, MacOSX10.8
 * May 14-21, 2013
 */
import javax.swing.*; 
import javax.swing.border.*;
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
    private int choice = 0;
    private ArrayList<Card> deck;
    
    private JButton draw = new JButton("Draw");
    private JButton stand = new JButton("Stand");
    private JButton play = new JButton("Play!");
    private JButton next = new JButton("Next");
    private JFrame frame = new JFrame("Blackjack Game");
    private JTextField dkn = new JTextField(10);
    private JTextField pkn = new JTextField(10);
    private JTextArea pname = new JTextArea(10, 10);
    private JPanel gpanel, beginningimg, beginning, pnames, pdisplay;
    
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
        JLabel dk = new JLabel("Enter number of decks: ");
        JLabel pk = new JLabel("Enter number of players: ");
        
        play.addActionListener(this);
        beginning.add(pk);
        beginning.add(pkn);
        beginning.add(dk);
        beginning.add(dkn);
        beginning.add(play);
        
        frame.add(beginningimg, BorderLayout.NORTH);
        frame.add(beginning,BorderLayout.CENTER);   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setSize(600,550);  
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == play) {
            dnum = Integer.parseInt(dkn.getText());
            pnum = Integer.parseInt(pkn.getText());
            createDeck();
            createPlayers();
        } else if (event.getSource() == next) {
            String[] names = pname.getText().split(" ");
            pnum = names.length;
            for (int i = 0; i < names.length; i++) {
                Player p = new Player(names[i]);
                p.draw(deck);
                p.draw(deck);
                players.add(p);
            }
        } else if (event.getSource() == draw) {
            choice = 1;
        } else if (event.getSource() == stand) {
            choice = 2;
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
        frame.getContentPane().remove(pnames);
        gpanel = new JPanel();
        gpanel.setLayout(new GridLayout(1,2));
        draw.addActionListener(this);
        stand.addActionListener(this);
        gpanel.add(draw);
        gpanel.add(stand);
        pdisplay = new JPanel();
        pdisplay.setLayout(new BoxLayout(pdisplay, BoxLayout.PAGE_AXIS));
        
        int stands = 0;
        while (stands < pnum) {
            stands = 0;
            for (Player p : players) {
                if (p.getStand()) {
                    stands++;
                } else if (p instanceof Dealer) {
                    p.draw(deck);
                } else {
                    JLabel stats = new JLabel(p.toString());
                    pdisplay.add(stats);
                    frame.add(pdisplay, BorderLayout.CENTER);
                    frame.add(gpanel, BorderLayout.SOUTH);
                    frame.validate();
                    frame.repaint();
                    if (choice == 1) {
                        p.draw(deck);
                        stats.setText(p.toString());
                        frame.validate();
                        frame.repaint();
                    } else if (choice == 2) {
                        p.stand();
                    }
                    
                }
            }
        }
        showResult();
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
        Dealer d = new Dealer();    
        d.draw(deck);
        d.draw(deck);
        players.add(d);
        frame.getContentPane().remove(beginning);
        pnames = new JPanel();
        pnames.setLayout(new BoxLayout(pnames, BoxLayout.PAGE_AXIS));
        JLabel pn = new JLabel("Enter Players' names, separated by spaces");
        next.addActionListener(this);
        pn.setAlignmentX(Component.CENTER_ALIGNMENT);
        pname.setAlignmentX(Component.CENTER_ALIGNMENT);
        next.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnames.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnames.add(pn);
        pnames.add(pname);
        pnames.add(next);
        frame.add(pnames);
        frame.revalidate();
        frame.repaint();
    }
    
    public static void main(String[]args) {
        GameG g = new GameG();
    }
}
