/**
 * Sophia Anopa/Alex Yang
 * APCSSec01Yl12
 * Blackjack: Game
 * Java 1.7, MacOSX10.8
 * May 14-21, 2013
 */
import javax.swing.*; 
import javax.swing.border.*;
import javax.swing.text.*;
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
    private int currentp = 1;
    private ArrayList<Card> deck;
    
    private JButton draw = new JButton("Draw");
    private JButton stand = new JButton("Stand");
    private JButton play = new JButton("Play!");
    private JButton next = new JButton("Next");
    private JFrame frame = new JFrame("Blackjack Game");
    private JTextField dkn = new JTextField(10);
    private JTextField pkn = new JTextField(10);
    private JTextField pname = new JTextField(10);
    private JPanel gpanel, beginningimg, beginning, pdisplay;
    private JPanel pnames = new JPanel();
    private JLabel pn = new JLabel();
    private JLabel stats1 = new JLabel(), stats2 = new JLabel(), stats3 = new JLabel();
    
    /** Default Constructor **/
    public GameG() {
        deck = new ArrayList<Card>();
        players = new ArrayList<Player>();
        Dealer d = new Dealer();    
        players.add(d);
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
            play.removeActionListener(this);
            createDeck();
            frame.getContentPane().remove(beginning);
            createPlayers();
        } else if (event.getSource() == next) {
            if (players.size() < pnum) {
                Player p = new Player(pname.getText());
                pname.setText("");
                p.draw(deck);
                p.draw(deck);
                players.add(p);
                next.removeActionListener(this);
                createPlayers();
            } else {
                next.removeActionListener(this);
                Player p = new Player(pname.getText());
                pname.setText("");
                p.draw(deck);
                p.draw(deck);
                players.add(p);
                frame.getContentPane().remove(pnames);
                gpanel = new JPanel();
                gpanel.setLayout(new GridLayout(1,2));
                draw.addActionListener(this);
                stand.addActionListener(this);
                gpanel.add(draw);
                gpanel.add(stand);
                pdisplay = new JPanel();
                pdisplay.setLayout(new BoxLayout(pdisplay, BoxLayout.PAGE_AXIS));
                frame.add(pdisplay);
                frame.add(gpanel, BorderLayout.SOUTH);
                frame.repaint();
                play();
            }
        } else if (event.getSource() == draw) {
            (players.get(currentp)).draw(deck);
            if (currentp == players.size()-1) {
                (players.get(0)).draw(deck);
                currentp = 1;
            } else {
                currentp++;
            }
            int stands = 0;
            for (Player ps: players) {
                if (ps.getStand()) {
                    stands++;
                }
            }
            if (stands < pnum)
                play();
            else
                showResult();
        } else if (event.getSource() == stand) {
            players.get(currentp).stand();
            if (currentp == players.size()-1) {
                (players.get(0)).draw(deck);
                currentp = 1;
            } else {
                currentp++;
            }
            int stands = 0;
            for (Player ps: players) {
                if (ps.getStand()) {
                    stands++;
                }
            }
            if (stands < pnum)
                play();
            else
                showResult();
        }
    }
    
    /** Accessors **/
    public void showResult() {
        String winner = "";
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
        frame.getContentPane().remove(pdisplay);
        frame.getContentPane().remove(gpanel);
        JPanel winnerp = new JPanel();
        winnerp.setLayout(new BoxLayout(winnerp, BoxLayout.PAGE_AXIS));
        JTextPane winarea = new JTextPane();
        winarea.setEditable(false);
        winarea.setText(winner);
        StyledDocument doc = winarea.getStyledDocument();
        SimpleAttributeSet sa = new SimpleAttributeSet();
        StyleConstants.setAlignment(sa, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), sa, false);
        winnerp.add(winarea);
        frame.add(winnerp);
        frame.setSize(600, 700);
        frame.validate();
        frame.repaint();
    }
    
    /** Other methods **/
    /**
     * Plays the game, going through all players and letting them draw or stand until everybody stands
     * Pre: none
     * Post: the results are calculated using a different method and displayed
     */
    public void play() {
        pdisplay.removeAll();
        stats1.setText("Name: " + players.get(currentp).getName());
        stats2.setText("Hand: " + players.get(currentp).getHand());
        stats3.setText("Value: " + players.get(currentp).getValue());
        stats1.setAlignmentX(Component.CENTER_ALIGNMENT);
        stats2.setAlignmentX(Component.CENTER_ALIGNMENT);
        stats3.setAlignmentX(Component.CENTER_ALIGNMENT);
        pdisplay.add(stats1);
        pdisplay.add(stats2);
        pdisplay.add(stats3);
        frame.validate();
        frame.repaint();
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
        (players.get(0)).draw(deck);
        (players.get(0)).draw(deck);
    }
    
    /**
     * Creates the Players in the game and the dealer, drawing two cards for each
     * Pre: none
     * Post: the requested number of Players is created and each Player receives two cards
     */
    public void createPlayers() {
        pnames.removeAll();
        pn.setText("Enter Player " + players.size() + "'s name");
        next.addActionListener(this);
        pnames.add(pn);
        pnames.add(pname);
        pnames.add(next);
        frame.add(pnames, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    
    public static void main(String[]args) {
        GameG g = new GameG();
    }
}
