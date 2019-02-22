import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.lang.*;
/**
 * Bishop Class
 * Creates a Bishop with a specific location and colour
 * Haley Yerxa
 * December 2017
 */
public class Bishop extends Piece // all bishops are pieces
{
    public Bishop(int x, int y, char c) // takes a starting location and colour
    {
        setRow(x);
        setColumn(y);
        setColour(c);
        picChar = 'b'; // tells us it's a bishop
        // Sets all image icons:
        setBlackOnBlack();
        setBlackOnWhite();
        setWhiteOnBlack();
        setWhiteOnWhite();
        setTaken();
    }
    
    public void setAvailable(Board b) // determines the availability of every spot on the board
    {
        /* bishops can move in four directions diagonally. However, they cannot
           jump over pieces, so they are limited by the pieces that block them*/
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (b.getSpot(i,j) != colour) // as long as the piece is a different colour (empty or black), it could be available
                {    
                    int rowChange = i-row; // finds the difference in row between the current spot and the spot we are looking at
                    int columnChange = j-column; // finds the difference between the current column and the one we are looking at
                    int limUpRight = 0; // limit in the upwards and right direction
                    int limDownRight = 0; // limit in the downwards and right direction
                    int limDownLeft = 0; // limit in the downwards and left direction 
                    int limUpLeft = 0; // limit in the upwards and left direction
                    
                    for (int k=1; (column+k)<8 && (row-k)>-1; k++) 
                    /* To check in the upwards and right direction, it checks
                       each square in the corner and determines if the spot has 
                       a piece on it*/
                    {
                        if (b.getSpot(row-k, column+k) != 'e') // if a spot in the path in the upper corner is not empty
                        {
                            limUpRight = k; // this becomes the limit (bishop can't move past this point)
                            if (colour == 'w') // if the piece that is limiting is of the other colour, than it could be captured.
                            {
                                if (b.getSpot(row-k, column+k) == 'b') // if the bishop is white and the limiting piece is black, the limit increases to include that spot
                                {
                                    limUpRight = k+1;
                                }
                            }
                            else // otherwise the piece is a black piece
                            {
                                if (b.getSpot(row-k, column+k) == 'w') // the black bishop can capture a white piece
                                {
                                    limUpRight = k+1;
                                }
                            }
                            break; // once the limit has been set, it breaks the loop
                        }
                        else if (row-(k+1) == -1 || column+(k+1) == 8) // if it gets all the way to the top or the right side (no more moves on the board)
                        {
                            limUpRight = k+1; // the limit becomes that point
                        }
                    }
                    
                    for (int k=1; (row+k)<8 && (column+k)<8; k++)  // checking donwards and right direction, checks every square until it reaches the end of the board  
                    {   
                        if (b.getSpot(row+k, column+k) != 'e') // once it finds a square that isn't empty, it hits the limit
                        {
                            limDownRight = k; // once a spot in the path is blocked, that's the limit
                            if (colour == 'w') // if it is a white bishop, and the piece blocking it is a black piece, it can be captured
                            {
                                if (b.getSpot(row+k, column+k) == 'b')
                                {
                                    limDownRight = k+1; // limit increases by one
                                }
                            }
                            else // same thing but if black can attack white
                            {
                                if (b.getSpot(row+k, column+k) == 'w')
                                {
                                    limDownRight = k+1;
                                }
                            }
                            break; // breaks from loop when limit is set
                        }
                        else if (row+(k+1) == 8 || column+(k+1) == 8) // if it gets all the way tho the end without finding a piece in its way, limit becomes the edge
                        {
                            limDownRight = k+1;
                        }
                    }
                        
                    for (int k=1; (row+k)<8 && (column-k)>-1; k++) // check for limit in downwards left direction (goes down 1 and left 1, then tries down 2 and left 2, and so on)
                    {
                        if (b.getSpot(row+k, column-k) != 'e') // once it finds a square in its path that is occupied
                        {
                            limDownLeft = k; // this becomes the limit
                            if (colour == 'w') // white capturing black
                            {
                                if (b.getSpot(row+k, column-k) == 'b') // if the piece blocking it can be captured
                                {
                                    limDownLeft = k+1; // increase the limit
                                }
                            }
                            else //black capturing white
                            {
                                if (b.getSpot(row+k, column-k) == 'w')
                                {
                                    limDownLeft = k+1;
                                }
                            }
                            break; // once limit is set, it can leave
                        }
                        else if (row+(k+1) == 8 || column-(k+1) == -1) // if it reaches the end, this is the limit
                        {
                            limDownLeft = k+1;
                        }
                    }
                        
                    for (int k=1; (row-k)>-1 && (column-k)>-1; k++) // lastly checks the limit of the upward left path   
                    {   
                        if (b.getSpot(row-k, column-k) != 'e') // finds a piece blocking it
                        {
                            limUpLeft = k; // this becomes the limit
                            if (colour == 'w')
                            {
                                if (b.getSpot(row-k, column-k) == 'b')
                                {
                                    limUpLeft = k+1; // limit increases so piece can be captured
                                }
                            }
                            else 
                            {
                                if (b.getSpot(row-k, column-k) == 'w')
                                {
                                    limUpLeft = k+1;// limit increases so piece can be captured
                                }
                            }
                            break;
                        }
                        else if (row-(k+1) == -1 || column-(k+1) == -1) // reaches the end of the board, this is the limit
                        {
                            limUpLeft = k+1;
                        }
                    }
                    
                    if (Math.abs(rowChange) == Math.abs(columnChange) && rowChange!=0) // first check to make sure the move is a legitimate bisop move: to a corner and not in the same spot
                    // the change in row and the change in column (absolute value) must be the same for it to be a corner move
                    {
                        if (rowChange<0 && columnChange>0 && columnChange<limUpRight) // if it is in the upwards right direction, and between the current location and the limit
                        {
                            isAvailable[i][j] = true; // the move is a possibility
                        }
                        else if (rowChange>0 && columnChange>0 && rowChange<limDownRight) // if the spot is in between the current location and the limit in the downwards right direction
                        {
                            isAvailable[i][j] = true; // move is good
                        }
                        else if (rowChange>0 && columnChange<0 && rowChange<limDownLeft) // moving down left, less than limit
                        {
                            isAvailable[i][j] = true; //good
                        }
                        else if (rowChange<0 && columnChange<0 && Math.abs(rowChange)<limUpLeft) // moving up left, less than limit
                        {
                            isAvailable[i][j] = true; //good
                        }
                    }
                    else // if it doesn't meet these conditions, it is not a spot a bishop can move to, so its availability is false
                    {
                        isAvailable[i][j] = false;
                    } 
                }
                else // it can never move to a spot already occupied by a piece of its own colour
                {
                    isAvailable[i][j] = false; // spot with other like piece not an option
                }
            }
        }
        return;
    }
    
    public boolean getAvailable(int a, int b) // returns availability of a certain square
    {
        return isAvailable[a][b];
    }
}