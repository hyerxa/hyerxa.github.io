import java.util.Random;
import javax.swing.*;
import java.awt.*;
/**
 * Player Class
 * Class for a player with a name, a score, and a colour (white or black)
 * Haley Yerxa
 * January 2018
 */
public class Player
{
    // attributes:
    String name; // player has a name (player 1 or player 2)
    int score; // player has a score
    boolean colour; // player is white or black
    
    public Player(String n, int s, boolean b) // constructor that takes a name, a score, and a colour (true is white, false is black)
    {
        name = n;
        score = s;
        colour = b;
    }
    
    // get and set methods
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
    
    public void setColour(boolean b)
    {
        colour = b;
        return;
    }
    
    public boolean getColour()
    {
        return colour;
    }
}