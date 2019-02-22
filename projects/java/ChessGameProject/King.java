import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.lang.*;
/**
 * King Class
 * Creates a King with a specific location and whether it is white or black
 * Haley Yerxa
 * December 2017
 */
public class King extends Piece // has all of Piece's protected attributes and public methods
{
    public King(int x, int y, char c) // takes a starting location and whether it is white or black
    {
        setRow(x);
        setColumn(y);
        setColour(c);
        picChar = 's';
        //initialize pics:
        setBlackOnBlack();
        setBlackOnWhite();
        setWhiteOnBlack();
        setWhiteOnWhite();
        setTaken();
    }
    
    public void setAvailable(Board b) // king can move one spot in any direction
    {
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                int rowChange = i-row; //change in row
                int columnChange= j-column; // change in column
                if (b.getSpot(i,j) != colour) // as long as the spot is not already occupied by a piece of the same colour
                {
                    if (Math.abs(rowChange) == Math.abs(columnChange) && Math.abs(rowChange) == 1) // if it is a corner spot, and the move is only one sppot away
                    {
                        isAvailable[i][j] = true; // diagonal moves are allowed
                    }
                    else if (i==row && j!=column && Math.abs(columnChange) == 1) // if it is one square to the left or right
                    {
                        isAvailable[i][j] = true; // sideways good too
                    }
                    else if (j==column && i!=row && Math.abs(rowChange) == 1) // if it is one square to the top or bottom
                    {
                        isAvailable[i][j] = true; // can move up and down
                    }
                    else // anything else is not allowed
                    {
                        isAvailable[i][j] = false;
                    }
                }
                else // can't move to where there is already a piece of the same colour
                {
                    isAvailable[i][j] = false;
                }
            }
        }
        return;
    }
}