import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.Random;
/**
 * board class
 * A chess board with 8 by 8 squares that keeps track of where pieces are on its squares
 * Haley Yerxa
 * January 2018
 */
public class Board
{
    ImageIcon black; // black square
    ImageIcon white; // white square
    char squares[][] = new char[8][8]; // array for the colour of the square. This is used to determine which pic needs to be painted for the pieces (white or black background).
    char spots[][] = new char[8][8]; // array for type of piece on the square. 'b' for black, 'w' for white, or 'e' for empty
    
    public Board() // constructor
    {
        black = new ImageIcon("Pics/black.png"); // sets up images (black square and white square)
        white = new ImageIcon("Pics/white.png");
    }
    // get and set methods
    public ImageIcon getBlack()
    {
        return black;
    }
    
    public ImageIcon getWhite()
    {
        return white;
    }
    
    public void setSquare(int i, int j, char c) // sets the colour of the square (white or black)
    {
        squares[i][j] = c;
    }
    
    public char getSquare(int i, int j)
    {
        return squares[i][j];
    }
    
    public void setSpot(int i, int j, char s) // sets the spot to white, black, or empty
    {
        spots[i][j] = s;
    }
    
    public char getSpot(int i, int j)
    {
        return spots[i][j];
    }
}
