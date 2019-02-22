import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
/**
 * Knight Class
 * Creates a knight with a specific location
 * Haley Yerxa
 * December 2017
 */
public class Knight
{
    int row; // it has a row attribute
    int column; // it has a column attribute
    ImageIcon blackPic; // for when it is on a black square (knight is always black)
    ImageIcon whitePic; // for when it is on a white square
    boolean isOnBoard; // whether it is within the grid of the board

    public Knight(int x, int y) // takes a starting location
    {
        setRow(x);
        setColumn(y);
        setWhitePic(); // default sets the pictures so they are accesible
        setBlackPic();
    }

    // get and set methods:
    public void setRow(int x)
    {
        row = x;
        return;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public void setColumn(int y)
    {
        column = y;
        return;
    }
    
    public int getColumn()
    {
        return column;
    }
    
    public void setWhitePic()
    {
        whitePic = new ImageIcon("Pics/whitePic.png");
        return;
    }
    
    public ImageIcon getWhitePic()
    {
        return whitePic;
    }
    
    public void setBlackPic()
    {
        blackPic = new ImageIcon("Pics/blackPic.png");
        return;
    }
    
    public ImageIcon getBlackPic()
    {
        return blackPic;
    }
    
    public void move(int r, int c) // tells how many squares to move by
    {
        row = row + r; // increases row by amount given (would be negative argument if moving left)
        column = column + c;
        return;
    }
    
}
