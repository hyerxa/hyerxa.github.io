import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.Random;
/**
 * Board Class
 * A chess board with 8 by 8 squares
 * Haley Yerxa
 * December 2017
 */
public class Board
{
    ImageIcon black; // black square
    ImageIcon white; // white square
    ImageIcon green; // green square (to show when the knight has hit the square)
    char squares[][] = new char[8][8]; // array for the colour of the square
    int priority[][] = new int[8][8];
    boolean isTaken[][] = new boolean[8][8]; // array to see if the quare has been landed on

    public Board()
    {
        black = new ImageIcon("Pics/black.jpg"); // sets up images
        white = new ImageIcon("Pics/white.jpg");
        green = new ImageIcon("Pics/taken.png");
        setPriority();
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                setTaken(i,j,false); // sets all sqaures to not taken
            }
        }
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
    public ImageIcon getGreen()
    {
        return green;
    }
    public void setSquare(int i, int j, char c) // sets the colour of the square (w, b, or g)
    {
        squares[i][j] = c;
    }
    public char getSquare(int i, int j)
    {
        return squares[i][j];
    }
    public void setTaken(int i, int j, boolean b)
    {
        isTaken[i][j] = b;
        return;
    }
    public boolean getTaken(int i, int j)
    {
        return isTaken[i][j];
    }
    /* to give the program better odds of succeeding, it prioritizes the
       corners and edges so that if possible, they will hit a corner first,
       then look for an edge, and then lowest priority are the middle squares.*/
    public void setPriority()
    {
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (i==0 || j==0 || i==7 || j==7) // if it is in the top or bottom row, or left or right column
                {
                    priority[i][j] = 2;
                }
                else // it is in the middle
                {
                    priority[i][j] = 3;
                }
                // the corners need to be a higher priority, right now they are just priority 2
                if (i==0 && j==0) // top left corner
                {
                    priority[i][j] = 1;
                }
                else if (i==0 && j==7) // top right corner
                {
                    priority[i][j] = 1;
                }
                if (i==7 && j==0) // bottom left corner
                {
                    priority[i][j] = 1;
                }
                else if (i==7 && j==7) // bottom right corner
                {
                    priority[i][j] = 1;
                }
            }
        }
        return;
    }
    public int getPriority(int i, int j)
    {
        return priority[i][j];
    }
}
