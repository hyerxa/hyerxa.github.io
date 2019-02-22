import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
/**
 * Rook Class
 * Creates a Rook with a specific location and colour
 * Haley Yerxa
 * January 2018
 */
public class Rook extends Piece // all rooks are pieces
{
    public Rook(int x, int y, char c) // takes a starting location and a colour
    {
        setRow(x);
        setColumn(y);
        setColour(c);
        picChar = 'r'; // tells it that it is a rook, allows pictures to be loaded properly
        // initializes all images:
        setBlackOnBlack();
        setBlackOnWhite();
        setWhiteOnBlack();
        setWhiteOnWhite();
        setTaken();
    }
    
    public void setAvailable(Board b) // This function determines where the rook can move.
    // This sets every spot on the board to true or false based on whether or not the spot is available
    // allows us to move and already know where it can move.
    {
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (b.getSpot(i,j) != colour) // if the spot that we are looking at is not of the same colour (if it's white, it can't move to a spot with a white piece on it)
                {
                    /* Rooks can move right, left, up, or down, but can't jump over pieces.
                     * This means that pieces in its path are the limit of which it can move.
                     * Any spot past the limit is not available, even if it is in the correct direction.
                     * If no piece is blocking it, the limit is the edge of the board. 
                     */
                    int limRight = 8;
                    int limLeft = -1;
                    int limUp = -1;
                    int limDown = 8;
                    for (int k=column+1; k<8; k++) // determines the right limit by checking every square to the right of its current location until it hits an occupied one.
                    {
                        if (b.getSpot(row,k) != 'e') // as soon as a spot to the right is not an empty spot
                        {
                            limRight = k; // this becomes the limit (the piece is blocking the rook)
                            if (colour == 'w') // if it is a white piece
                            {
                                if (b.getSpot(row,k) == 'b') // if the piece blocking it is black, it can take that piece, so the spot is actually available
                                {
                                    limRight = k+1; // this increases the limit by one, because it can move all the way up to and including that spot
                                }
                            }
                            else // same thing for black taking white
                            {
                                if (b.getSpot(row,k) == 'w')
                                {
                                    limRight = k+1;
                                }
                            }
                            break; // leaves loop once it determines limit
                        }
                    }
                    
                    for (int k=column-1; k>-1; k--) // to determine left limit, must check every spot to the left of the current location
                    {
                        if (b.getSpot(row, k) != 'e') // once it finds a spot that is occupied
                        {
                            limLeft = k; // it can't move past this point
                            if (colour == 'w') // if the piece calling the function is white
                            {
                                if (b.getSpot(row,k) == 'b') // if it can capture the piece, this spot is included in the limit
                                {
                                    limLeft = k-1;
                                }
                            }
                            else // otherwise it is black
                            {
                                if (b.getSpot(row,k) == 'w')
                                {
                                    limLeft = k-1;
                                }
                            }
                            break; // leaves once limit is set
                        }
                    }
                    
                    for (int k=row-1; k>-1; k--) // to check the top limit, must look at every square above the curent piece
                    {
                        if (b.getSpot(k,column) != 'e') // once it finds a piece,
                        {
                            limUp = k; // this is the limit
                            if (colour == 'w')
                            {
                                if (b.getSpot(k,column) == 'b') // can capture the piece
                                {
                                    limUp = k-1;
                                }
                            }
                            else
                            {
                                if (b.getSpot(k,column) == 'w')
                                {
                                    limUp = k-1;
                                }
                            }
                            break; // leaves once it finds the limit
                        }
                    }
                    
                    for (int k=row+1; k<8; k++) // to find lower limit, must check every spot below the piece 
                    {
                        if (b.getSpot(k,column) != 'e') // once it finds an occupied square
                        {
                            limDown = k; // this becomes the limit
                            if (colour == 'w')
                            {
                                if (b.getSpot(k,column) == 'b')
                                {
                                    limDown = k+1; // limit increases if it can capture the piece
                                }
                            }
                            else
                            {
                                if (b.getSpot(k,column) == 'w')
                                {
                                    limDown = k+1;
                                }
                            }
                            break;
                        }
                    }
                    
                    if (i==row && j!=column && j<limRight && j>limLeft)
                    /* if the spot being checked is in the same row, but the
                       column is different and the spot is within the limits
                       on either side. */
                    {
                        isAvailable[i][j] = true; // spot is good
                    }
                    else if (j==column && i!=row && i>limUp && i<limDown)
                    /* if the spot being checked is in the same column but
                       a different row, and the spot is within the upper and
                       lower limits.*/
                    {
                        isAvailable[i][j] = true; // spot is good
                    }
                    else // otherwise the spot can't be good
                    {
                        isAvailable[i][j] = false; // spot not good
                    }
                }
                else // otherwise, the spot is occupied by a piece of the same colour, not an option
                {
                    isAvailable[i][j] = false; // spot not good
                }
            }
        }
        return;
    }
    
}

