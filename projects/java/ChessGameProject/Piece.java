import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
/**
 * Piece Class
 * Creates a Piece, is the super class that contains the main attributes and methods of a piece,
 * subclasses have specific types of pieces
 * Haley Yerxa
 * December 2017
 */
public class Piece // This is the superclass for all of our pieces, there are inherited classes that are more specific for each piece, but all pieces have these variables and methods
{
    // all attributes are protected so that subclasses inherit them
    protected int row; // every piece has a row attribute
    protected int column; // it has a column attribute
    protected char colour; // white or black
    protected char picChar; // this keeps track of the type of piece for pictures, and for telling piees apart
    //p=pawn, r=rook, k=knight, b=bishop, q=queen, s=king.
    protected ImageIcon bb; // Image Icon for black piece on black background 
    protected ImageIcon wb; // Image Icon for white piece on black background
    protected ImageIcon bw; // black piece on white background
    protected ImageIcon ww; // white piece on white background
    protected ImageIcon taken; // picture for when the piece gets captures
    protected boolean isAlive; // whether it is taken or not
    protected boolean[][] isAvailable = new boolean[8][8]; // possible spots that a piece can move to
    protected boolean isSelected; // keeps track of whether or not a piece has been selected

    public Piece() // blank constructor for subclasses
    {}
    
    public Piece(int x, int y) // takes a starting location
    {
        setRow(x);
        setColumn(y);
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
    
    public void setPicChar(char c)
    {
        picChar = c;
        return;
    }
    
    public char getPicChar()
    {
        return picChar;
    }
    
    public void setSelected(boolean b)
    {
        isSelected = b;
        return;
    }
    
    public boolean getSelected()
    {
        return isSelected;
    }
    
    public void setColour(char c)
    {
        colour = c;
        return;
    }
    
    public char getColour()
    {
        return colour;
    }
    
     public void setBlackOnBlack()
    {
        bb = new ImageIcon("Pics/b"+picChar+"b.png"); // files are all saved so that this works with any piece
        return;
    }
    
    public ImageIcon getBlackOnBlack()
    {
        return bb;
    }

    public void setWhiteOnBlack()
    {
        wb = new ImageIcon("Pics/w"+picChar+"b.png");
        return;
    }
    
    public ImageIcon getWhiteOnBlack()
    {
        return wb;
    }
    
    public void setBlackOnWhite()
    {
        bw = new ImageIcon("Pics/b"+picChar+"w.png");
        return;
    }
    
    public ImageIcon getBlackOnWhite()
    {
        return bw;
    }
    
    public void setWhiteOnWhite()
    {
        ww = new ImageIcon("Pics/w"+picChar+"w.png");
        return;
    }
    
    public ImageIcon getWhiteOnWhite()
    {
        return ww;
    }
    
    public void setTaken() // sets the pictures for when a piece gets captured
    {
        if (colour == 'w')
        {
            taken = new ImageIcon("Pics/w"+picChar+"_taken.png");
        }
        else // if it's not white, it's black
        {
            taken = new ImageIcon("Pics/b"+picChar+"_taken.png");
        }
    }
    
    public ImageIcon getTaken()
    {
        return taken;
    }
    
    public void setHasMoved(boolean b) // used for pawns, because they can move two spots on the first turn only
    {
        return; // returns nothing, just a blank function that gets overwritten in the pawn class. Only pawns will call this function
    }
    
    public void setAlive(boolean b) // sets the player to alive or dead
    {
        isAlive = b;
        return;
    }
    
    public boolean getAlive()
    {
        return isAlive;
    }
    
    public void move(int x, int y) // used to move a piece to a new spot
    {
        if (isAvailable[x][y] == true) // as long as the spot that your trying to move to is a legitimate move
        {
            setRow(x);
            setColumn(y);
        }
    }
    
    public void setAvailable(Board b) // this function is overwritten in each subclass, as the way each piece moves is different
    {
        return;
        /* Currently does nothing because it will be overwritten later. The function takes a board
           as a parameter because it needs to check what is on each square (whether it is empty,
           has a white piece, or has a black piece on it)*/
    }
    
    public boolean getAvailable(int a, int b)
    {
        return isAvailable[a][b];
        // returns the availability of that square
    }
}
