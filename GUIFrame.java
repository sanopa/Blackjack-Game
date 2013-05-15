/**
 * Sophia Anopa/Alex Yang
 * APCSSec01Yl12
 * Blackjack: Main/GUI
 * Java 1.7, MacOSX10.8
 * May 14-21, 2013
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GUIFrame extends JFrame{
	JPanel pane = new JPanel();
	GUIFrame() {
		super("Black Jack"); setBounds(100, 100, 300, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.add(pane);
		setVisible(true);
	}
	public static void main(String[] args) {
		new GUIFrame();
	}
}
