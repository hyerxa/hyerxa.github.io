import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.lang.*;
/**
 * Pawn Class
 * Creates a Pawn with a specific location and whether it is white or black
 * Haley Yerxa
 * December 2017
 */
public class Pawn extends Piece
{

    boolean hasMoved = false; // starts off not having moved. Once this gets set to true, it can no longer move two squares instead of 1
    
    public Pawn(int x, int y, char c) // takes a starting location and a char to say white or black
    {
        setRow(x);
        setColumn(y);
        setColour(c);
        picChar = 'p';
        //sets up images:
        setBlackOnBlack();
        setBlackOnWhite();
        setWhiteOnBlack();
        setWhiteOnWhite();
        setTaken();
    }
    
    // get and set methods for pawn:
    public void setHasMoved(boolean b)
    {
        hasMoved = b; // will be set to true once it makes a move
        return;
    }

    public boolean getHasMoved()
    {
        return hasMoved;
    }
    
    public void setAvailable(Board b) // pawns can only move forward 1 sqaure at a time (2 squares on first move) an can only attack diagonally
    {
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                int rowChange = i-row; // change in row between current square and possible square
                int columnChange = j-column; // change in column
                if (colour == 'w') // white pawns move in increasing rows (down board)
                {
                    if (b.getSpot(i,j) != colour) // square can't have piece of same colour on it
                    {
                        if (b.getSpot(i,j) == 'e') // if the spot is empty, it can move forward
                        {
                            if (column == j && row != i) // if the column stays the same but the row is different, could be a possible move
                            {
                                if (hasMoved == false && rowChange == 2) // if it hasn't moved yet and the move is up 2, that is a possibility
                                {
                                    isAvailable[i][j] = true;
                                }
                                else if (rowChange == 1) // whether it is the first turn or not, it can move forward 1
                                {
                                    isAvailable[i][j] = true;
                                }
                                else // any other square will not be good
                                {
                                    isAvailable[i][j] = false;
                                }
                            }
                        }
                        else if (b.getSpot(i,j) == 'b') // if the square has a piece of the other colour on it, it can possibly attack and take it, which means it would move diagonally
                        {
                            if (rowChange == 1 && Math.abs(columnChange) == 1) // can only move one square diagonally, but can be to either side
                            {
                                isAvailable[i][j] = true; // would be an available move
                            }
                        }
                    }
                    else // otherwise spot is already taken by another piece of same colour
                    {
                        isAvailable[i][j] = false; // can't move there
                    }
                }
                else // otherwise it is a black piece, same ideas but the pawns move up the board, so their rows are decreasing
                {
                    if (b.getSpot(i,j) != colour)
                    {
                        if (b.getSpot(i,j) == 'e')
                        {
                            if (column == j && row != i)
                            {
                                if (hasMoved == false && rowChange == -2)
                                {
                                    isAvailable[i][j] = true;
                                }
                                else if (rowChange == -1)
                                {
                                    isAvailable[i][j] = true;
                                }
                                else
                                {
                                    isAvailable[i][j] = false;
                                }
                            }
                        }
                        else if (b.getSpot(i,j) == 'w')
                        {
                            if (rowChange == -1 && Math.abs(columnChange) == 1)
                            {
                                isAvailable[i][j] = true;
                            }
                        }
                    }
                    else 
                    {
                        isAvailable[i][j] = false;
                    }
                }
            }
        }
        return;
    }
}