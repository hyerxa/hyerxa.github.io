import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.Random;
import java.io.*;
import javax.sound.sampled.*;
/**
 * Summative: Last Man Standing Chess
 * A version of chess that has all the same pieces as regular chess, but the
 * objective is to capture all of your opponents pieces before your game clock 
 * runs out or the other player gets all of your pieces.
 * Haley Yerxa
 * January 2018
 */
public class Game extends JFrame implements Runnable, ActionListener // uses frames, a thread, and buttons (action listener)
{
    Board board = new Board(); //establishes the board from board class
    JButton blocks[][] = new JButton[8][8]; // array of buttons for each square. This allows us to select a piece
    JLabel piecesTaken[][] = new JLabel[10][4]; // Array of labels for displaying the pieces that have been taken (shows player1 in first five rows and player2 in other rows)
    Piece player1Pieces[][] = new Piece[8][8]; // Array of pieces for player1. Will be filled in with pieces in constructor
    Piece player2Pieces[][] = new Piece[8][8]; // player1 is white, player2 is black
    
    Container pane = getContentPane(); // pane for window
    JPanel boardPanel = new JPanel(); // panel for chess board
    JPanel whitePanel = new JPanel(); // panel for player1's name and info
    JPanel blackPanel = new JPanel(); // panel for player2's name and info
    JPanel leftPanel = new JPanel(); // where the taken pieces are displayed
    JPanel rightPanel = new JPanel(); // where the game clocks are displayed
    
    int timer1 = 600; //player1 game clock. Starts at 10 min (600 s)
    int timer1Min = timer1/60; // this allows the clock to be displayed as minutes:seconds
    int timer1Sec = timer1-(timer1Min*60); // seconds is what is left that doesn't make up a whole minute
    int timer2 = 600;
    int timer2Min = timer2/60;
    int timer2Sec = timer2-(timer2Min*60);
    JLabel timerTitle1 = new JLabel("Player 1 Time: "); // label for timer
    JLabel timerLabel1 = new JLabel(""+timer1Min+":0"+timer1Sec); // starts at 10:00
    JLabel timerTitle2 = new JLabel("Player 2 Time: ");
    JLabel timerLabel2 = new JLabel(""+timer2Min+":0"+timer2Sec);
    
    Player playerWhite = new Player("Player 1", 0, true); // creates a white player
    Player playerBlack = new Player("Player 2", 0, false); // creates a black player
    JLabel player1Label = new JLabel(playerWhite.getName()); // creates a label with their name on it
    JLabel player2Label = new JLabel(playerBlack.getName());
    
    Thread thread; // creates a thread to be used to count down game clock
    
    boolean buttonPressed = false; // to determine whether a piece has been selected yet or not
    boolean playerTurn = true; // determines whose turn it is. If it's true, it's white's turn, if it's false, it's black's turn
    boolean clock = true; // determines which clock is running (true for white, false for black)
    JButton clock1 = new JButton("Stop Clock"); // button that allows player1 to stop their game clock
    JButton clock2 = new JButton("Stop Clock"); // player2's game clock button
    
    Piece piecePressed; // piece that acts as a copy of a piece that gets selected, so that piece can be moved
    
    int player1PiecesTaken = 0; // keeps track of how many pieces white has taken
    int rowTracker1 = 1; // used to display the pictures of taken pieces (they are shown in four rows) - will see this later when pieces get captured
    int player2PiecesTaken = 0;
    int rowTracker2 = 6; // starts at 6 because player2 has its pieces below player1
    
    // the following buttons are used when a pawn gets promoted, buttons are displayed to allow user to pick which piece they want
    JButton queenWhite; 
    JButton bishopWhite;
    JButton rookWhite;
    JButton knightWhite;
    JButton queenBlack;
    JButton bishopBlack;
    JButton rookBlack;
    JButton knightBlack;
    // can only promote tp queen, bishop, rook, or knight (not allowed to make it a king, no point making it a pawn that can't move)
    int promoteRow; // used to keep track of the position of the pawn that is being promoted
    int promoteColumn; // these are used to make the new piece (constructors take x, y, colour)
    JPanel piecesPanel = new JPanel(); // panel used for buttons to be displayed when a pawn is promoted
    // These are pieces that are currently not in use, but get changed if a pawn is promoted, and the chosen piece takes the place of the pawn
    Queen queenpromoteWhite = new Queen(0,0,'w');
    Queen queenpromoteBlack = new Queen(0,0,'b');
    Knight knightpromoteWhite = new Knight(0,0,'w');
    Knight knightpromoteBlack = new Knight(0,0,'b');
    Rook rookpromoteWhite = new Rook(0,0,'w');
    Rook rookpromoteBlack = new Rook(0,0,'b');
    Bishop bishoppromoteWhite = new Bishop(0,0,'w');
    Bishop bishoppromoteBlack = new Bishop(0,0,'b');
    
    Color green = new Color(181,230,29); // colour used for borders of buttons when they are selected
    LineBorder selectedBorder = new LineBorder(green, 5); // creates a border that can be used when a button is selected
    LineBorder noBorder = new LineBorder(green, 0); // border for when a piece is not selected (regular position)
    
    boolean gameOver = false; // becomes true when someone takes all of the other person's pieces, or someone's clock runs out
    boolean player1Wins = false; // determines which player wins (true: white wins, false: black wins)
    
    Clip backgroundMusic; // clip for background music
    AudioInputStream backgroundAudio;
    
    public void actionPerformed(ActionEvent e) // if a button is pressed
    {
        // the first several buttons are the buttons for when a pawn is promoted. They are only displayed when a pawn reaches the end, and disappear once the player has chosen their piece
        if (e.getSource() == queenWhite)
        {
            promote(queenpromoteWhite, promoteRow, promoteColumn); // tells it to change the pawn into a queen (pawn was white)
        }
        else if (e.getSource() == queenBlack)
        {
            promote(queenpromoteBlack, promoteRow, promoteColumn);
        }
        else if (e.getSource() == bishopWhite)
        {
            promote(bishoppromoteWhite, promoteRow, promoteColumn);
        }
        else if (e.getSource() == bishopBlack)
        {
            promote(bishoppromoteBlack, promoteRow, promoteColumn);
        }
        else if (e.getSource() == rookWhite)
        {
            promote(rookpromoteWhite, promoteRow, promoteColumn);
        }
        else if (e.getSource() == rookBlack)
        {
            promote(rookpromoteBlack, promoteRow, promoteColumn);
        }
        else if (e.getSource() == knightWhite)
        {
            promote(knightpromoteWhite, promoteRow, promoteColumn);
        }
        else if (e.getSource() == knightBlack)
        {
            promote(knightpromoteBlack, promoteRow, promoteColumn);
        }
        
        else if (e.getSource() == clock1) // if player1 stops their clock
        {
            clock = false; // this stops their clock (thereby starting player2's clock)
        }
        else if (e.getSource() == clock2) // player2 stops their clock
        {
            clock = true;
        }
        
        else // all other buttons are on chess board, must go through button array to find which button was pressed
        {
            for (int i=0; i<8; i++)
            {
                for (int j=0; j<8; j++)
                {
                    if (e.getSource() == blocks[i][j]) // goes through each spot on the board to determine which piece was selected
                    {
                        if ((playerTurn == true && clock == true) || (playerTurn == false && clock == false)) // if it's white's turn and their clock is running or vice versa
                        /* Once a move is made, it is no longer that person's turn,
                           however, the clock must also be stopped before the other
                           person can go (same as competitive chess)*/
                        {    
                            if (buttonPressed == false) // if a button hasn't been selected yet
                            {
                                if (playerTurn == true) // if it's white's turn
                                {
                                    if (board.getSpot(i,j) == 'w') // if the spot being selected is white (needs to be white or they can't select it)
                                    {
                                        blocks[i][j].setBorder(selectedBorder); // shows green border so player know they've selected that piece
                                        piecePressed = player1Pieces[i][j]; // the piecePressed Piece takes on the attributes of the piece that was selected so it can be used when the spot it is moving to is selected
                                        buttonPressed = true; // now we have selected a piece. It will then either be moved or unselected
                                    }
                                }
                                else // if it's black's turn
                                {
                                    if (board.getSpot(i,j) == 'b') // it won't work if they don't select a black piece
                                    {
                                        blocks[i][j].setBorder(selectedBorder);
                                        piecePressed = player2Pieces[i][j]; // remembers piece
                                        buttonPressed = true; // ready for next move
                                    }
                                }
                            }
                        
                            else // otherwise, buttonPressed = true, so a piece has already been selected. We are now going to check to see if it is being unselected or moved
                            {
                                if (playerTurn == true) // if it's white's turn
                                {
                                    if (piecePressed == player1Pieces[i][j] && board.getSpot(i,j) == 'w') // if the piece that is currently being pressed is the same as the one that was pressed before, it unselects the piece
                                    {
                                        blocks[i][j].setBorder(noBorder); // piece becomes unselected
                                        buttonPressed = false; // a button hasn't been pressed, so now user is free to pick a new piece.
                                        // This is if they change their mind and want to move a different piece than the one they first selected
                                    }
                                    else // otherwise a new spot has been chosen, which means the user wants to move their piece to that spot
                                    {
                                        piecePressed.setAvailable(board); // finds available spots for the selected piece (will make sure that where the user wants to move it is allowed)
                                        if (piecePressed.getAvailable(i,j) == true) // as long as the chosen square is allowed
                                        {
                                            player1Label.setText("Player 1"); // This is to reset the label if it got changed (it changes when the player tries to make an illegal move)
                                            player1Pieces[i][j] = player1Pieces[piecePressed.getRow()][piecePressed.getColumn()]; // the piece in the selected spot becomes the same as the piece from the previous spot (essentially copying the piece to the new spot)
                                            // next we set the images for the new square and the old square
                                            if (board.getSquare(i,j) == 'w') // if the spot it's moving to is a white square
                                            {
                                                blocks[i][j].setIcon(player1Pieces[i][j].getWhiteOnWhite()); // draws it with a white background
                                            }
                                            else // otherwise it is a black background
                                            {
                                                blocks[i][j].setIcon(player1Pieces[i][j].getWhiteOnBlack()); // draws the piece on its new spot with a black background
                                            }
                                            if (board.getSquare(piecePressed.getRow(),piecePressed.getColumn()) == 'w') // if where it was is a white square
                                            {
                                                blocks[piecePressed.getRow()][piecePressed.getColumn()].setIcon(board.getWhite()); // now displays a blank white square
                                            }
                                            else // otherwise the square is black
                                            {
                                                blocks[piecePressed.getRow()][piecePressed.getColumn()].setIcon(board.getBlack()); // now blank black square
                                            }
                                            if (board.getSpot(i,j) == 'b') // if the spot it is moving to is currrently occupied by a black piece
                                            {
                                                player2Pieces[i][j].setAlive(false); // it captures the piece which makes player2's piece dead
                                                piecesTaken[rowTracker1][player1PiecesTaken].setIcon(player2Pieces[i][j].getTaken()); // now puts the piece that was taken into the slot that shows what pieces white has taken
                                                player1PiecesTaken++; // increases number of pieces they have taken (used to keep track end of game, but also where in the piecesTaken array the newly taken piece needs to be drawn
                                                if (player1PiecesTaken == 4 && rowTracker1 == 4) // if the entire array has filled up (all pieces are taken)
                                                {
                                                    gameOver = true; // the game is over because player1 wins (captured all of black's pieces)
                                                    player1Wins = true;
                                                }
                                                if (player1PiecesTaken == 4) // it gets to the end of the row, needs to start a new row of captured pieces
                                                {
                                                    rowTracker1++; // becomes next row
                                                    player1PiecesTaken = 0; // resets back to 0 (keeps track of column)
                                                }
                                                playerWhite.setScore(playerWhite.getScore()+1); // player1's score increases
                                            }
                                            board.setSpot(i,j,'w'); // the spot now has a white piece on it
                                            buttonPressed = false; // move has been made, no piece is selected anymore
                                            board.setSpot(piecePressed.getRow(), piecePressed.getColumn(), 'e'); // the old spot is now empty
                                            blocks[piecePressed.getRow()][piecePressed.getColumn()].setBorder(noBorder); // the piece used to be selected, returns back to normal
                                            player1Pieces[piecePressed.getRow()][piecePressed.getColumn()].move(i,j); // even though we already 'moved' the piece, the row and column attributes of the piece need to be correct, this sets them to the current square
                                            if (player1Pieces[i][j].getPicChar() == 'p') // if the piece that was moved is a pawn
                                            {
                                                player1Pieces[i][j].setHasMoved(true); // it has now moved (can no longer move forward 2 spots)
                                                if (player1Pieces[i][j].getRow() == 7 && gameOver == false) // if it got to the end of the board, it needs to be promoted!
                                                {
                                                    choosePiece(player1Pieces[i][j]); // this calls the function that displays the buttons and allows the user to choose the new piece
                                                }
                                            }
                                            playerTurn = false; // their move has been made, it is now black's turn
                                        }
                                        else // otherwise it can't move to this square (is either blocked or that piece is unable to move to that spot based on its properties
                                        {
                                            player1Label.setText("Player 1: You can't move that piece there."); // tells them they can't move the piece there
                                        }
                                    }
                                }
                                else if (playerTurn == false && clock == false) // otherwise it's black's turn, same stuff happens, just black instead of white
                                {
                                    if (piecePressed == player2Pieces[i][j] && board.getSpot(i,j) == 'b')
                                    {
                                        blocks[i][j].setBorder(noBorder);
                                        buttonPressed = false;
                                    }
                                    else
                                    {
                                        piecePressed.setAvailable(board);
                                        if (piecePressed.getAvailable(i,j) == true)
                                        {
                                            player2Label.setText("Player 2");
                                            player2Pieces[i][j] = player2Pieces[piecePressed.getRow()][piecePressed.getColumn()];
                                            if (board.getSquare(i,j) == 'w')
                                            {
                                                blocks[i][j].setIcon(player2Pieces[i][j].getBlackOnWhite());
                                            }
                                            else
                                            {
                                                blocks[i][j].setIcon(player2Pieces[i][j].getBlackOnBlack());
                                            }
                                            if (board.getSquare(piecePressed.getRow(),piecePressed.getColumn()) == 'w')
                                            {
                                                blocks[piecePressed.getRow()][piecePressed.getColumn()].setIcon(board.getWhite());
                                            }
                                            else
                                            {
                                                blocks[piecePressed.getRow()][piecePressed.getColumn()].setIcon(board.getBlack());
                                            }
                                            if (board.getSpot(i,j) == 'w')
                                            {
                                                player1Pieces[i][j].setAlive(false);
                                                piecesTaken[rowTracker2][player2PiecesTaken].setIcon(player1Pieces[i][j].getTaken());
                                                player2PiecesTaken++;
                                                if (player2PiecesTaken == 4 && rowTracker2 == 4)
                                                {
                                                    gameOver = true;
                                                    player1Wins = false;
                                                }
                                                if (player2PiecesTaken == 4)
                                                {
                                                    rowTracker2++;
                                                    player2PiecesTaken = 0;
                                                }
                                                playerBlack.setScore(playerBlack.getScore()+1);
                                            }
                                            board.setSpot(i,j,'b');
                                            buttonPressed = false;
                                            board.setSpot(piecePressed.getRow(), piecePressed.getColumn(), 'e');
                                            blocks[piecePressed.getRow()][piecePressed.getColumn()].setBorder(noBorder);
                                            player2Pieces[piecePressed.getRow()][piecePressed.getColumn()].move(i,j);
                                            
                                            if (player2Pieces[i][j].getPicChar() == 'p')
                                            {
                                                player2Pieces[i][j].setHasMoved(true);
                                                if (player2Pieces[i][j].getRow() == 0 && gameOver==false)
                                                {
                                                    choosePiece(player2Pieces[i][j]);
                                                }
                                                
                                            }
                                            playerTurn = true;
                                        }
                                        else
                                        {
                                            player2Label.setText("Player 2: You can't move that piece there.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (gameOver == true) // if gameOver was set to true, the game needs to end
                {
                    endGame(); // calls function that ends game
                }
                
            }
        }
    }
    
    public void run() // when thread is activated
    {
        Thread thisThread = Thread.currentThread();
        while (thisThread == thread)
        {
            while (clock == true && gameOver == false) // while player1's clock is running (and the game is still running)
            {
                timer1Min = timer1/60; // determines how many minutes are left
                timer1Sec = timer1-(timer1Min*60); // determines how many second are left in the unfull minute
                if (timer1Sec<10) // needs to display an extra zero in clock label so it can be 5:05 instead of 5:5 (for example)
                {
                    timerLabel1.setText(""+timer1Min+":0"+timer1Sec); // shows the clock
                }
                else {timerLabel1.setText(""+timer1Min+":"+timer1Sec);}
                try // pause first
                {                        
                    Thread.sleep(1000); // pauses for a second before counting down
                }
                catch(Exception e) // if there is a problem, display error message
                {
                    JOptionPane.showMessageDialog(null,e);
                }
                timer1--; // decreases by one second
                if (timer1 == 0) // if the timer reaches zero, game ends (they lose)
                {
                    player1Wins = false; // white lost
                    gameOver = true; // game is now over
                    timerLabel1.setText("0:00"); // clock gets set to 0
                    endGame(); // ends game
                }
                if (backgroundMusic.getMicrosecondPosition() > 211000000) // the background song got to the end, it restarts
                {
                    backgroundMusic.stop(); // stops it
                    backgroundMusic.setMicrosecondPosition(0); // sets it back to beginning
                    backgroundMusic.start(); // restarts
                }
            }
            while (clock == false && gameOver == false) // same thing, but black's clock is running
            {
                timer2Min = timer2/60;
                timer2Sec = timer2-(timer2Min*60);
                if (timer2Sec<10)
                {
                    timerLabel2.setText(""+timer2Min+":0"+timer2Sec);
                }
                else{timerLabel2.setText(""+timer2Min+":"+timer2Sec);}
                try // pause first
                {                        
                    Thread.sleep(1000);
                }
                catch(Exception e) // if there is a problem, display error message
                {
                    JOptionPane.showMessageDialog(null,e);
                }
                timer2--;
                if (timer2 == 0)
                {
                    player1Wins = true;
                    gameOver = true;
                    timerLabel2.setText("0:00");
                    endGame();
                }
                if (backgroundMusic.getMicrosecondPosition() > 211000000)
                {
                    backgroundMusic.stop();
                    backgroundMusic.setMicrosecondPosition(0);
                    backgroundMusic.start();
                }
            }
            
        }
    }
    
    public void endGame() // called when gameOver = false
    {
        {
            pane.remove(boardPanel); // removes chess board
            JPanel endPanel = new JPanel(); // new panel to go where board was
            endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.PAGE_AXIS)); // Creates a boxlyout so things can be displayed on top of each other
            pane.add(endPanel, BorderLayout.CENTER); // adds the new panel to the centre of the pane (now empty)
            Dimension minSize = new Dimension(10, 200);
            Dimension prefSize = new Dimension(10, 200);
            Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);
            endPanel.add(new Box.Filler(minSize, prefSize, maxSize)); // creates a filler so that text won't be displayed right on top
            JLabel gameOverLabel = new JLabel("GAME OVER"); // sets first label to display this text
            gameOverLabel.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
            gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // sets label to middle on panel
            Font font4 = new Font("impact", Font.PLAIN, 100); // new fonts used for gameOver text
            Font font5 = new Font("impact", Font.PLAIN, 60);
            endPanel.add(gameOverLabel); // add the label to the panel
            gameOverLabel.setFont(font4);
            JLabel playerWins; // creates a label that will say which player wins
            if (player1Wins == true) // if white wins
            {
                playerWins = new JLabel("Player 1 Wins");
            }
            else // black wins
            {
                playerWins = new JLabel("Player 2 Wins");
            }
            playerWins.setFont(font5);
            playerWins.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
            playerWins.setAlignmentX(Component.CENTER_ALIGNMENT);
            endPanel.add(playerWins);
            Color pink = new Color(255,72,132); // new colour to change backgroun
            endPanel.setBackground(pink);
            backgroundMusic.stop(); // stops music
        }
    }
    
    public void choosePiece(Piece p) // called when a pawn gets to the end
    {
        promoteRow = p.getRow(); // remembers row and column so that the promoted peice has the right attributes
        promoteColumn = p.getColumn();
        if (p.getColour() == 'w') // if the pawn is white
        {
            whitePanel.add(piecesPanel, BorderLayout.EAST); // adds the panel for the buttons to the top panel
            queenWhite = new JButton(queenpromoteWhite.getTaken()); // button is displayed with picture of queen
            player1Label.setText("Player 1: Promote your pawn"); // the label tells the user to pick a piece
            piecesPanel.add(queenWhite); // adds the button to the panel
            queenWhite.addActionListener(this); // tells it to listen to that button
            bishopWhite = new JButton(bishoppromoteWhite.getTaken()); // adds button for bishop
            piecesPanel.add(bishopWhite); //displayes button
            bishopWhite.addActionListener(this); //listens for it
            rookWhite = new JButton(rookpromoteWhite.getTaken()); // rook button
            piecesPanel.add(rookWhite);
            rookWhite.addActionListener(this);
            knightWhite = new JButton(knightpromoteWhite.getTaken()); // knight button
            piecesPanel.add(knightWhite);
            knightWhite.addActionListener(this);
        }
        else if (p.getColour() == 'b') // same thing, but for black pawn
        {
            blackPanel.add(piecesPanel, BorderLayout.EAST); // displays buttons on bottom, where black lives
            queenBlack = new JButton(queenpromoteBlack.getTaken());
            player2Label.setText("Player 2: Promote Your Pawn");
            piecesPanel.add(queenBlack);
            queenBlack.addActionListener(this);
            bishopBlack = new JButton(bishoppromoteBlack.getTaken());
            piecesPanel.add(bishopBlack);
            bishopBlack.addActionListener(this);
            rookBlack = new JButton(rookpromoteBlack.getTaken());
            piecesPanel.add(rookBlack);
            rookBlack.addActionListener(this);
            knightBlack = new JButton(knightpromoteBlack.getTaken());
            knightBlack.addActionListener(this);
            piecesPanel.add(knightBlack);
        }
    }
    
    public void promote(Piece u, int x, int y) // once the player picks the piece they want to promote to, this function get called
    {
        if (u.getColour() == 'w') // if it's white's turn
        {
            player1Label.setText("Player 1"); // label goes back to normal
            u.setRow(x); // u is the new piece, it is already whatever type of piece we wanted it to be (ex. queen, knight), but it just deosn't have the right location
            u.setColumn(y); // gets set to the row and column of the pawn (taking over the pawn)
            player1Pieces[x][y] = u; // this spot in the area has now changed to become the promoted piece
            if (board.getSquare(x,y) == 'w') // if the square it is on is white
            {
                blocks[x][y].setIcon(u.getWhiteOnWhite()); // draw the white background
            }
            else // otherwise it is on a black square
            {
                blocks[x][y].setIcon(u.getWhiteOnBlack()); // draws the picture with a black background
            }
            piecesPanel.remove(queenWhite); // removes buttons from panel (and screen)
            piecesPanel.remove(bishopWhite);
            piecesPanel.remove(rookWhite);
            piecesPanel.remove(knightWhite);
            whitePanel.remove(piecesPanel); // remove panel
            // this means that once a piece is chosen, the buttons disappear until another pawn needs to be promoted
        }
        else if (u.getColour() == 'b') // otherwise, the pawn being promoted is black, same stuff but for black
        {
            player2Label.setText("Player 2");
            u.setRow(x);
            u.setColumn(y);
            player2Pieces[x][y] = u;
            if (board.getSquare(x,y) == 'w')
            {
                blocks[x][y].setIcon(u.getBlackOnWhite());
            }
            else
            {
                blocks[x][y].setIcon(u.getBlackOnBlack());
            }
            piecesPanel.remove(queenBlack);
            piecesPanel.remove(bishopBlack);
            piecesPanel.remove(rookBlack);
            piecesPanel.remove(knightBlack);
            blackPanel.remove(piecesPanel);
        }
    }
    
    public Game() // constructor initialises everything to start game
    {
        super ("Chess Game");      
        
        pane.setLayout(new BorderLayout()); // gives the main pane a border layout
        pane.add(boardPanel, BorderLayout.CENTER); // put chess board in middle
        pane.add(whitePanel, BorderLayout.NORTH); // white's pieces are on top
        pane.add(blackPanel, BorderLayout.SOUTH); // black is on bottom
        pane.add(leftPanel, BorderLayout.WEST); // left side is where captured pieces go
        pane.add(rightPanel, BorderLayout.EAST); // right side is for game clock
        boardPanel.setLayout(new GridLayout(8,8)); // sets a grid of 8 by 8 for buttons to be displayed on
        
        Font font1 = new Font("sansserif", Font.PLAIN, 40); // basic font for labels
        Font font2 = new Font("sansserif", Font.PLAIN, 30);
        Font font3 = new Font("impact", Font.PLAIN, 40); // font used for title
        
        whitePanel.setLayout(new BorderLayout()); // border layout allows us to put things on top, bottom, left, right, and middle. For this panel, we care about left,  middle, and right
        JLabel titleLabel = new JLabel("Last Man Standing Chess"); // title goes in top left corner
        whitePanel.add(titleLabel, BorderLayout.WEST); //puts it to the left of the label
        titleLabel.setFont(font3);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // aligns the text all the way to the left
        ImageIcon greenPic = new ImageIcon("Pics/green.png"); // same colour as background, used as a filler for piecesTaken array when spots are empty
        player1Label.setFont(font2);
        whitePanel.add(player1Label, BorderLayout.CENTER); // puts player1's name in the middle of the panel
        player1Label.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        player1Label.setAlignmentX(Component.LEFT_ALIGNMENT); // puts it toward left of panel
        player2Label.setFont(font2);
        blackPanel.add(player2Label); // adds player2's label to the bottom panel
        player2Label.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        player2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Color background = new Color(55, 218, 27); // same colour as greenPic
        whitePanel.setBackground(background); // all of the panels have the same background colour
        blackPanel.setBackground(background);
        leftPanel.setBackground(background);
        rightPanel.setBackground(background);
        
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS)); // boxLayout of right panel allows game clocks to be displayed on top of each other
        Dimension minSize = new Dimension(10, 90); // set to create filler that makes up empty space and helps make the distribution of elements in the panel more even
        Dimension prefSize = new Dimension(10, 90);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 90);
        rightPanel.add(new Box.Filler(minSize, prefSize, maxSize)); // start with a filler on the right side
        
        rightPanel.add(timerTitle1); // then add white's timer title
        rightPanel.add(timerLabel1); // then add white's timer
        rightPanel.add(clock1); // then add the button that stops it
        clock1.addActionListener(this);
        clock1.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        clock1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel clockLabel1 = new JLabel("(You must stop the clock once you've made your move)"); // reminds user to stop clock
        rightPanel.add(clockLabel1); //adds label below button
        clockLabel1.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        clockLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        // these all just align everything in the middle so they are evenly spaced and lined up
        timerTitle1.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        timerTitle1.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel1.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        timerLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        timerTitle1.setFont(font1);
        timerLabel1.setFont(font1);
        timerTitle2.setFont(font1);
        timerLabel2.setFont(font1);
        
        rightPanel.add(new Box.Filler(minSize, prefSize, maxSize)); // add another filler between player1's clock and player2's clock
        rightPanel.add(timerTitle2); // now add black's timer title
        rightPanel.add(timerLabel2); // and the timer
        rightPanel.add(clock2); // add the button to stop it
        clock2.addActionListener(this);
        clock2.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        clock2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel clockLabel2 = new JLabel("(You must stop the clock once you've made your move)"); // reminds them to stop clock
        rightPanel.add(clockLabel2);
        clockLabel2.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        clockLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        timerTitle2.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        timerTitle2.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel2.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        timerLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.setLayout(new GridLayout(10,4)); // grid layout allows the array of piecesTaken labels to be evenely distributed
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<4; j++)
            {
                piecesTaken[i][j] = new JLabel(); // new label gets created for each spot
                if (i==0) // if it is in the first row, it is being used as the title for white's taken pieces
                {
                    if (j==0) // splits text up into each column
                    {
                        piecesTaken[i][j].setText("Player 1");
                        piecesTaken[i][j].setFont(font2);
                    }
                    else if (j==1)
                    {
                        piecesTaken[i][j].setText("  Pieces");
                        piecesTaken[i][j].setFont(font2);
                    }
                    else if (j==2) // Displayed as: "Player 1 Pieces Taken: "
                    {
                        piecesTaken[i][j].setText("Taken: ");
                        piecesTaken[i][j].setFont(font2);
                    }
                }
                else if (i==5) // the 6th row is where black's pieces start, so it's title is here, same as player1's title line
                {
                    if (j==0)
                    {
                        piecesTaken[i][j].setText("Player 2");
                        piecesTaken[i][j].setFont(font2);
                    }
                    else if (j==1)
                    {
                        piecesTaken[i][j].setText("  Pieces");
                        piecesTaken[i][j].setFont(font2);
                    }
                    else if (j==2)
                    {
                        piecesTaken[i][j].setText("Taken: ");
                        piecesTaken[i][j].setFont(font2);
                    }
                }
                else // otherwise the spot is epty until a piece gets captured and drawn there
                {
                    piecesTaken[i][j].setIcon(greenPic); // empty icon
                }
                leftPanel.add(piecesTaken[i][j]); // adds to piece to the gridlayout
            }
        }
        
        for (int i=2; i<6; i++) // starting in the third row and gowing until the sixth row, the spots are empty to begin with (pieces start at either end)
        {
            for (int j=0; j<8; j++)
            {
                board.setSpot(i,j,'e'); // the spot is empty
            }
        }
        
        for (int i=0; i<8; i++) // blocks array of buttons needs to be put into the grid layout in order
        {
            for (int j=0; j<8; j++)
            {
                blocks[i][j] = new JButton(); // establishes the array of buttons
                blocks[i][j].addActionListener(this); // will listen to them
                blocks[i][j].setBorder(noBorder); // starts with unslected border
                boardPanel.add(blocks[i][j]); // adds a button to every square. This way we can change the image of each label to anything we want.
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
        
        // The following section initializes the first row with white pieces in order by creating the pieces and adding them to the array, then displaying the icon of that piece
        player1Pieces[0][0] = new Rook(0,0,'w');
        blocks[0][0].setIcon(player1Pieces[0][0].getWhiteOnWhite());
        player1Pieces[0][1] = new Knight(0,1,'w');
        blocks[0][1].setIcon(player1Pieces[0][1].getWhiteOnBlack());
        player1Pieces[0][2] = new Bishop(0,2,'w');
        blocks[0][2].setIcon(player1Pieces[0][2].getWhiteOnWhite());
        player1Pieces[0][3] = new Queen(0,3,'w');
        blocks[0][3].setIcon(player1Pieces[0][3].getWhiteOnBlack());
        player1Pieces[0][4] = new King(0,4,'w');
        blocks[0][4].setIcon(player1Pieces[0][4].getWhiteOnWhite());
        player1Pieces[0][5] = new Bishop(0,5,'w');
        blocks[0][5].setIcon(player1Pieces[0][5].getWhiteOnBlack());
        player1Pieces[0][6] = new Knight(0,6,'w');
        blocks[0][6].setIcon(player1Pieces[0][6].getWhiteOnWhite());
        player1Pieces[0][7] = new Rook(0,7,'w');
        blocks[0][7].setIcon(player1Pieces[0][7].getWhiteOnBlack());
        for (int i=0; i<8; i++)
        {
            board.setSpot(0,i,'w'); // the entire first row is filled with white squares, board needs to know this
        }
        
        for (int i=0; i<8; i++) // this is used to make pawns in every column in the second row
        {
            player1Pieces[1][i] = new Pawn(1,i,'w');
            if (i%2 == 0) // if the column is even, it is a black square, so the pawn needs to be drawn on a black square
            {
                blocks[1][i].setIcon(player1Pieces[1][i].getWhiteOnBlack());
            }
            else // otherwise it is a white square, needs to be drawn on white
            {
                blocks[1][i].setIcon(player1Pieces[1][i].getWhiteOnWhite());
            }
            board.setSpot(1,i,'w'); // the entire second row is also all white squares
        }
        
        for (int i=2; i<8; i++) //needs to initialize the rest of the pieces array even though it is empty
        {
            for (int j=0; j<8; j++)
            {
                player1Pieces[i][j] = new Piece(i,j); // creates blank pieces in the array in empty spots
                // nothing will be expressed in these spots because the board still knows they're empty
            }
        }
        
        //initializes black pieces in bottom row
        player2Pieces[7][0] = new Rook(7,0,'b');
        blocks[7][0].setIcon(player2Pieces[7][0].getBlackOnBlack());
        player2Pieces[7][1] = new Knight(7,1,'b');
        blocks[7][1].setIcon(player2Pieces[7][1].getBlackOnWhite());
        player2Pieces[7][2] = new Bishop(7,2,'b');
        blocks[7][2].setIcon(player2Pieces[7][2].getBlackOnBlack());
        player2Pieces[7][3] = new Queen(7,3,'b');
        blocks[7][3].setIcon(player2Pieces[7][3].getBlackOnWhite());
        player2Pieces[7][4] = new King(7,4,'b');
        blocks[7][4].setIcon(player2Pieces[7][4].getBlackOnBlack());
        player2Pieces[7][5] = new Bishop(7,5,'b');
        blocks[7][5].setIcon(player2Pieces[7][5].getBlackOnWhite());
        player2Pieces[7][6] = new Knight(7,6,'b');
        blocks[7][6].setIcon(player2Pieces[7][6].getBlackOnBlack());
        player2Pieces[7][7] = new Rook(7,7,'b');
        blocks[7][7].setIcon(player2Pieces[7][7].getBlackOnWhite());
        
        for (int i=0; i<8; i++)
        {
            board.setSpot(7,i,'b'); // board knows entire row is made of black pieces
        }
        
        for (int i=0; i<8; i++)
        {
            player2Pieces[6][i] = new Pawn(6,i,'b'); // pawns are in row above bottow row
            if (i%2 == 0) // even squares are white in this row
            {
                blocks[6][i].setIcon(player2Pieces[6][i].getBlackOnWhite());
            }
            else
            {
                blocks[6][i].setIcon(player2Pieces[6][i].getBlackOnBlack());
            }
            board.setSpot(6,i,'b'); // board needs to know whole row has black pieces
        }
        
        for (int i=0; i<6; i++) // finish of rest of player2Pieces array with empty, unused pieces
        {
            for (int j=0; j<8; j++)
            {
                player2Pieces[i][j] = new Piece(i,j);
            }
        }
        
        for (int i=0; i<2; i++) // go through existant pieces in player1's pieces
        {
            for (int j=0; j<8; j++)
            {
                player1Pieces[i][j].setAvailable(board); // set their availability to begin with
            }
        }
        
        for (int i=6; i<8; i++) // go through player2's pieces
        {
            for (int j=0; j<8; j++)
            {
                player2Pieces[i][j].setAvailable(board); // figure out where each piece can possibly move
            }
        }
        
        try
        {
            File soundFile = new File("Audio/backgroundMusic.wav"); //load background music file
            backgroundAudio = AudioSystem.getAudioInputStream(soundFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(backgroundAudio); // open the file
            backgroundMusic.start(); // start playing the music right from the beginning
        }
        catch(Exception e) // if there is a problem, display error message
        {
            JOptionPane.showMessageDialog(null,e);
        }
        
        if (thread == null) // if the thread is dead
        {
            thread = new Thread(this); // construct a new thread
            thread.start();
        }
        
        setSize (1500, 725); // sets the size
        setVisible(true);
        repaint();     
        
    }
    
    
    public static void main( String args[]) 
    {
        Game game = new Game();     // construct the game (call the constructor)
        game.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);     // allows you to hit the X button to close
        return;
    }
       
}