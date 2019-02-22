import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.lang.*;
/**
 * Knight Class
 * Creates a Knight with a specific location
 * Haley Yerxa
 * January 2018
 */
public class Knight extends Piece // has all of Pieces protected attribues, and public methods
{
    public Knight(int x, int y, char c) // takes a starting location and whether it is white or black
    {
        setRow(x);
        setColumn(y);
        setColour(c);
        picChar = 'k';
        setBlackOnBlack();
        setBlackOnWhite();
        setWhiteOnBlack();
        setWhiteOnWhite();
        setTaken();
    }
    
    public void setAvailable(Board b) // has 8 possible moves 
    {
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (b.getSpot(i,j) != colour) // as long as the spot is not already occupied by a piece of the same colour
                {
                    int rowChange = i-row; // change in row
                    int columnChange = j-column; // change in column
                    if (Math.abs(rowChange) == 2 && Math.abs(columnChange) == 1) // if it moves left or right 2 and up or down 1 (4 possible combos), all are good
                    {
                        isAvailable[i][j] = true; // possible moves for a knight
                    }
                    else if (Math.abs(rowChange) == 1 && Math.abs(columnChange) == 2) // if it moves left or right 1 and up or down 2 (4 possible combos), all are good
                    {
                        isAvailable[i][j] = true; // possible moves
                    }
                    else // any other move is not a possibility
                    {
                        isAvailable[i][j] = false;
                    }
                }
                else // can't move somewhere where a piece of the same colour already resides
                {
                    isAvailable[i][j] = false;
                }
            }
        }
        return;
    }
}
