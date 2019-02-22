import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.lang.*;
/**
 * Queen Class
 * Creates a Queen with a specific location
 * Haley Yerxa
 * January 2018
 */
public class Queen extends Piece // inherits all protected variables and methods from Piece
{
    
    public Queen(int x, int y, char c) // takes a starting location and a colour (white or black)
    {
        setRow(x);
        setColumn(y);
        setColour(c);
        picChar = 'q';
        setBlackOnBlack();
        setBlackOnWhite();
        setWhiteOnBlack();
        setWhiteOnWhite();
        setTaken();
    }
    
    public void setAvailable(Board b) // can move like a rook or a bishop 
    {
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (b.getSpot(i,j) != colour) // if the spot that we are looking at is not of the same colour (if it's white, it can't move to a spot with a white piece on it)
                {
                    /* queens can move right, left, up, or down, but can't jump over pieces.
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
                            limRight = k; // this becomes the limit (the piece is blocking the queen)
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
                    
                    /* queens can move in four directions diagonally. However, they cannot
                     * jump over pieces, so they are limited by the pieces that block them*/
                    
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
                            limUpRight = k; // this becomes the limit (queen can't move past this point)
                            if (colour == 'w') // if the piece that is limiting is of the other colour, than it could be captured.
                            {
                                if (b.getSpot(row-k, column+k) == 'b') // if the queen is white and the limiting piece is black, the limit increases to include that spot
                                {
                                    limUpRight = k+1;
                                }
                            }
                            else // otherwise the piece is a black piece
                            {
                                if (b.getSpot(row-k, column+k) == 'w') // the black queen can capture a white piece
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
                            if (colour == 'w') // if it is a white queen, and the piece blocking it is a black piece, it can be captured
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
                    
                    if (Math.abs(rowChange) == Math.abs(columnChange) && rowChange!=0) // first check to make sure the move is a legitimate queen move: to a corner and not in the same spot
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
                    else if (i==row && j!=column && j<limRight && j>limLeft)
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
                    else // otherwise not in any possible path, so no good
                    {
                        isAvailable[i][j] = false;
                    }
                }
                else // can never go to a spot that is already occupied with a piece of the same colour
                {
                    isAvailable[i][j] = false;
                }
            }
        }
        return;
    }
}
