import java.util.Random;
import javax.swing.*;
import java.awt.*;
/**
 * Player Class
 * Class for a player with a name, a score, and a bet
 * Haley Yerxa
 * December 2017
 */
public class Player extends Hand // player can have all the same attributes as a hand
{
    // attributes:
    String name;
    int score;
    int bet;
    
    public Player(String n, int s, int b) // constructor that takes a name, a score, and a bet
    {
        name = n;
        score = s;
        bet = b;
    }
    
    public void setName(String n)
    {
        name = n;
        return;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setScore(int s)
    {
        score = s;
        return;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public void setBet(int b)
    {
        bet = b;
        return;
    }
    
    public int getBet()
    {
        return bet;
    }
}
