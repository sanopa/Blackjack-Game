/**
 * Alex Yang
 * APCSSec01YL12
 * Blackjack: checkChoice (reused from Mathey)
 * Java 1.7, MacOSX10.8
 * May 14-21, 2013
 */
import java.util.*;
public class checkChoice {
    /**
     * Checks the validity of user input
     * Pre: User input
     * Post: User input that is unchanged or "-1" if the user input is invalid
     */
    public static int checkValidity (String stringChoice) {
        int choice;
        Scanner parser = new Scanner(stringChoice);
        String modStringChoice = stringChoice.replaceAll("[\\d]", "");
        if (modStringChoice.length() == 0 && parser.hasNextInt()) {
            choice = parser.nextInt();
        }
        else {
            choice = -1;
        }
        parser.close();
        return choice;
    }
}