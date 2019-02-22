import javax.swing.*;
import java.awt.*;
/**
 * Card Class
 * Creates a card with a suit, a number, an icon, and a display direction (faceUp or down)
 * Haley Yerxa
 * December 2017
 */
public class Card
{
    // Attributes:
    int number;
    char suit;
    int x;
    int y;
    ImageIcon front;
    ImageIcon back;
    boolean faceUp;
    
    public Card(int n, char s)
    {
        setNumber(n);
        setSuit(s);
        setFaceUp(true);
        setX(10); // default position, will be changed later
        setY(100);
    }
    
    // Get and set functions:
    public void setNumber(int n)
    {
        number = n;
        return;
    }
    
    public int getNumber()
    {
        return number;
    }
    
    public void setSuit(char s)
    {
        suit = s;
        return;
    }
    
    public char getSuit()
    {
        return suit;
    }
    
    public void setX(int x1)
    {
        x=x1;
        return;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setY(int y1)
    {
        y = y1;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setFront() // cards were saved as a character and a number (spades ace was s1)
    {
        front = new ImageIcon("CardBitmaps/"+""+suit+""+number+".png");
        return;
    }
    
    public ImageIcon getFront()
    {
        return front;
    }
    
    public void setBack() // all backs are the same
    {
        back = new ImageIcon("CardBitmaps/r.png");
        return;
    }
    
    public ImageIcon getBack()
    {
        return back;
    }
    
    public void setFaceUp(boolean u)
    {
        faceUp = u;
        return;
    }
    
    public boolean getFaceUp()
    {
        return faceUp;
    }
    
    public void draw(Container c, Graphics g)
    {
        if (faceUp == true) // if it is facing up
        {
            front.paintIcon(c,g,x,y); // draw the front of the card in its position
        }
        else // it is facing down, paints back
        {
            back.paintIcon(c,g,x,y);
        }
        return;
    }

}
