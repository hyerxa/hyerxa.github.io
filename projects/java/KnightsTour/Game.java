import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.Random;
/**
 * Knight's Tour
 * Has a knight on a chess board that randomnly moves until a solution is found that has the knight landing on every square of th board
 * Haley Yerxa
 * December 2017
 */
public class Game extends JFrame implements Runnable // uses frames and a thread
{
    Board board = new Board(); //establishes the board from board class
    JLabel blocks[][] = new JLabel[8][8]; // array of labels for each square
    int orderMoved[] = new int[128]; // array to keep track of order so the pattern can be repeated when a solution is found
    /* the orderMoved array has 128 int because every square moved to gets catalogued as
       two values; the first for the row, and the second for the column. There
       are 64 squares, so if all squares get followed, the array will have every move,
       arranged as row, column. */
    
    Knight knight = new Knight (0,0); // creates a knight and sets its position to top left corner
    Thread thread; // creates a thread to be used to have some pauses after knight moves
    boolean isOnBoard = false; // flag to make sure that the knight doesn't jump to a spot not on the grid
    boolean newTried[] = new boolean[8]; // keeps track of which possible moves have been tried (knight can move one of 8 possible ways)
    int row = 0; // variables to use for moving the knight
    int column = 0;
    int spotsTaken = 0; // used to keep track of how many places on the board are covered
    
    
    public void run() // when thread is activated
    {
        Thread thisThread = Thread.currentThread();
        while (thisThread == thread)
        {
            try // pause first
                {                        
                    Thread.sleep(1);
                }
            catch(Exception e) // if there is a problem, display error message
                {
                    JOptionPane.showMessageDialog(null,e);
                }
            board.setTaken(knight.getRow(), knight.getColumn(), true); // current square becomes taken, the knight has landed on it
            orderMoved[spotsTaken] = knight.getRow(); // the square gets puts into the orderMoved array, every time a move is made, the square gets put in the array in order.
            orderMoved[spotsTaken+1] = knight.getColumn();
            repaint(); // previous spot will become green (shows that it has been landed on)
            try
                {                        
                    Thread.sleep(1); // pause while knight moves
                }
            catch(Exception e) // if there is a problem, display error message
                {
                    JOptionPane.showMessageDialog(null,e);
                }
            
            chooseSpot(); // function that randomly picks a spot, prioritizing the corners nd edges
            knight.move(row, column); // after a spot is chosen, it changes the values of row and column to change the knight's position by the amounts decided. See knight class for move fucntion
            try
                {                        
                    Thread.sleep(10); // pause for a moment
                }
            catch(Exception e) // if there is a problem, display error message
                {
                    JOptionPane.showMessageDialog(null,e);
                }
            repaint(); // draws the knight in its new spot
            spotsTaken = 0; // resets spotsTaken to 0, because it recounts each time:
            for (int i=0; i<8; i++)
            {
                for(int j=0; j<8; j++)
                {
                    if (board.getTaken(i,j) == true) // for every square that has been landed on
                    {
                       spotsTaken = spotsTaken+2; // increases by two, because to go through orderMoved array, it needs to go through 128 spots instead of 64
                    }
                }
            }
            if (spotsTaken == 128) // all of the squares have been landed on (to check if this coded works, this number can be decreased so it will trigger in less moves)
            {
                knight.setRow(0); // resets the knight back to its original spot
                knight.setColumn(0);
                for (int i=0; i<8; i++)
                {
                    for (int j=0; j<8; j++)
                    {
                        board.setTaken(i,j, false); // sets all of the sqaures back to not taken
                        if (board.getSquare(i,j) == 'w') // if it is a white sqaure
                        {
                            blocks[i][j].setIcon(board.getWhite()); // make the label display the white square
                        }
                        else if (board.getSquare(i,j) == 'b') // same for black
                        {
                            blocks[i][j].setIcon(board.getBlack());
                        }
                        repaint(); // shows all the squares back to a regular chess board
                    }
                }
                while (true) // infinite loop to repeat solution
                {
                    for (int i=0; i<129; i=i+2) // loops through moves
                    {
                        board.setTaken(knight.getRow(), knight.getColumn(), true); // spot knight is on becomes taken
                        repaint(); // turns square green
                        try
                        {                        
                            Thread.sleep(200); // pause while knight moves
                        }
                        catch(Exception e) // if there is a problem, display error message
                        {
                            JOptionPane.showMessageDialog(null,e);
                        }
                        knight.setRow(orderMoved[i]); // moves knight to first spot in the order
                        knight.setColumn(orderMoved[i+1]);
                        repaint(); // knight is in new square
                        try
                        {                        
                            Thread.sleep(500);// pauses before moving again
                        }
                        catch(Exception e) // if there is a problem, display error message
                        {
                            JOptionPane.showMessageDialog(null,e);
                        }
                        
                    }
                    for (int i=0; i<8; i++) // once the knight has gone through the solution, it resets
                    {
                        for (int j=0; j<8; j++)
                        {
                            board.setTaken(i,j, false); // all sqaures are not taken anymore
                            if (board.getSquare(i,j) == 'w')
                            {
                                blocks[i][j].setIcon(board.getWhite()); // white squares go back to white
                            }
                            else if (board.getSquare(i,j) == 'b') // same for black
                            {
                                blocks[i][j].setIcon(board.getBlack());
                            }
                            repaint(); // shows board reset
                        }
                    }
                    
                }
            
            }
            isOnBoard = false; // if it gets to this point, a new move needs to be chosen, flag is turned off so new spot can be chosen
            repaint();
        }
        
    }
    public void chooseSpot() // function to randomly move knight
    {
        
        for (int i=0; i<8; i++)
        {
            newTried[i] = false; // sets all values to false, have not been tried yet
        }
        int tried = 0; // to keep track of all the possible moves tried
        int round = 1; // runs through the possible moves 3 times, to check that the highest priority is being followed
        while (isOnBoard == false) // stays false until a legit move is chosen
        {
            tried = 0; // recounts tried every time, needs to be reset before counting again
            Random random = new Random(); // for picking random values
            int newRow = random.nextInt(4); // picks 4 random numbers (0-3)
            boolean sign = random.nextBoolean(); // picks a random boolean (decides if move is up or down)
            /* Knight can move one of 8 ways: right 2 and up 1 or down 1, left 2 and 
               up 1 or down 1, right 1 and up 2 or down 2, and left 1 and up 2 and down 2.
               The random int picks whether it moves right 2, left 2, right 1, or left 1,
               and the boolean chooses whether it moves up 1 (or 2) or down 1 (or 2).
               This goes through less than just choosing 8 different numbers. The newTried[]
               array keeps track of if each possibilility has been chosen, and the program
               won't give up until all options have been exhausted.*/
               
            {
                if (newRow == 0)
                {
                    row = 1; // moves right 1
                    if (sign == true)
                    {
                        column = 2; // moves up two (first possibility)
                        newTried[0] = true; // has tried this possibility
                    }
                    else
                    {
                        column = -2; // moves down two (second possibility)
                        newTried[1] = true; // has tried this possibility
                    }
                }
                else if (newRow == 1) 
                {
                    row = -1; // moves left 1 
                    if (sign == true)
                    {
                        column = 2; // up 2 (third possibility)
                        newTried[2] = true;
                    }
                    else
                    {
                        column = -2; // down 2 (fourth possibility)
                        newTried[3] = true;
                    }
                }
                else if (newRow == 2)
                {
                    row = 2; // right 2
                    if (sign == true)
                    {
                        column = 1; // up 1 (fifth possibility)
                        newTried[4] = true;
                    }
                    else
                    {
                        column = -1; // down 1 (sixth possibility)
                        newTried[5] = true;
                    }
                }
                else if (newRow == 3)
                {
                    row = -2; // left 2
                    if (sign == true)
                    {
                        column = 1; // up 1 (seventh possibility)
                        newTried[6] = true;
                    }
                    else
                    {
                        column = -1; // down 1 (eighth possibility)
                        newTried[7] = true;
                    }
                }
            }
            
            // if the spot chosen is on the board, and the spot isn't taken
            if (((knight.getRow()+row)>(-1)) && ((knight.getRow()+row)<8) && ((knight.getColumn()+column)>-1) && ((knight.getColumn()+column)<8) && (board.getTaken((knight.getRow()+row),(knight.getColumn()+column)) == false))
            {
                if (round == 1) // first round of priority check, checks for corners
                {
                    if (board.getPriority((knight.getRow()+row),(knight.getColumn()+column)) == 1) // if the chosen spot is a corner
                    {
                        isOnBoard = true; // will exit loop if the option is a corner
                    }
                }
                else if (round == 2) // no possibilities were corners, now checks to see if any were edges
                {
                    if (board.getPriority(knight.getRow()+row,knight.getColumn()+column) == 2)
                    {
                        isOnBoard = true; // will exit loop if it is an edge
                    }
                }
                else // no possible corners or edges were available
                {
                    isOnBoard = true; // will exit loop if it is a middle spot
                }
                
            }
            
            for (int i=0; i<8; i++) // checks to see how many possibilities have been explored
            {
                if (newTried[i] == true) // if a possible move has been tried
                {
                    tried++; // tried increases (was reset to 0 at beginning of loop)
                    
                }
                else // otherwise, the option hasn't been checked, must keep looking
                {
                    break;
                }
            }
            
            if (tried == 8 && round < 3) // if all options have been tried, but has not gone through all priority check
            {
                round++; // becomes next round
                for (int i=0; i<8; i++)
                {
                    newTried[i] = false; // sets them back to not tried, the possibility could be an option, just wasn't good for that priority round
                }
                tried = 0; // resets tried to 0, will recount in next loop
            }
            
            if (tried == 8 && round == 3 && isOnBoard == false) // if it has tried all options and is in last priority round, there are no available squares
            {
                for (int i=0; i<8; i++) // need to reset the board
                {
                    for (int j=0; j<8; j++)
                    {
                        
                        board.setTaken(i,j,false); // all squares become not taken
                        if (board.getSquare(i,j) == 'w') // chessboard gets reset too
                        {
                            blocks[i][j].setIcon(board.getWhite());
                        }
                        else if (board.getSquare(i,j) == 'b')
                        {
                            blocks[i][j].setIcon(board.getBlack());
                        }
                        knight.setRow(0); // knight goes back to where he started
                        knight.setColumn(0);
                        row = 0; // these get reset so new moves are chosen
                        column = 0;
                        isOnBoard = true; // leaves loop to start over
                    }
                }
            }
        }
        return;
    }
    public Game() // constructor
    {
        super ("Knight's Tour");      
        JPanel panel = new JPanel(); // main panel
        Container pane = getContentPane(); // pane for window
        pane.setLayout(new BorderLayout()); // gives it a border layout
        pane.add(panel, BorderLayout.CENTER); // put panel on pane
        panel.setLayout(new GridLayout(8,8)); // sets a grid of 8 by 8
        
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                blocks[i][j] = new JLabel(""+i+","+""+j); // establishes the array of labels
                panel.add(blocks[i][j]); // adds a label to every square. This way we can change the image of each label to anything we want.
            }
        }
        
        // the following section sets up the board to alternate square colours
        for (int i=0; i<8; i=i+2) // goes through every other row (each row starts with the opposite colour)
        {
            for (int j=0; j<8; j=j+2) // goes through every other column to set the white squares
            {
                blocks[i][j].setIcon(board.getWhite());
                board.setSquare(i,j,'w');
            }
            for (int j=1; j<8; j=j+2) // goes through every other column to set the black squares
            {
                blocks[i][j].setIcon(board.getBlack());
                board.setSquare(i,j,'b');
            }
        }
        
        for (int i=1; i<8; i=i+2) // repeats the same thing for other rows, but black first
        {
            for (int j=0; j<8; j=j+2)
            {
                blocks[i][j].setIcon(board.getBlack());
                board.setSquare(i,j,'b');
            }
            for (int j=1; j<8; j=j+2)
            {
                blocks[i][j].setIcon(board.getWhite());
                board.setSquare(i,j,'w');
            }
        }

        if (thread == null) // if the thread is dead
        {
            thread = new Thread(this); // construct a new thread
            thread.start();
        }
        
        setSize (700, 700); // sets the size (is a square)
        setVisible(true);
        repaint();     
        
    }
    
    public void paint(Graphics g) // paint function, to draw our graphics
    {
      int knightRow = knight.getRow();
      int knightColumn = knight.getColumn();
      super.paint(g);
      if (board.getSquare(knightRow, knightColumn) == 'w') // if the square that the knight is on is white
      {
          blocks[knightRow][knightColumn].setIcon(knight.getWhitePic()); // make that square have a knight with a white background
      }
      else if (board.getSquare(knightRow, knightColumn) == 'b') // if it's black, paint the black background knight
      {
          blocks[knightRow][knightColumn].setIcon(knight.getBlackPic());
      }
      for (int i=0; i<8; i++)
      {
          for (int j=0; j<8; j++)
          {
              if (board.getTaken(i,j) == true)
              {
                  blocks[i][j].setIcon(board.getGreen()); //sets all of the taken squares to green
              }
          }
      }
    }
    
    public static void main( String args[]) 
    {
        Game game = new Game();     // construct the game (call the constructor)
        game.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);     // allows you to hit the X button to close
        return;
    }
       
    
    
}