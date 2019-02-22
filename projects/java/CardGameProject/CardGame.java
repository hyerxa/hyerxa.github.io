
/**
 * Card Game
 * Creates a deck and a hand. Allows user to change format and deal cards to hand and discard back to deck
 * Haley
 * December 2017
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class CardGame extends JFrame implements ActionListener
{
    
    Deck deck = new Deck(52); // creates a deck with 52 cards
    Hand hand1 = new Hand(0, 50, 500); // creates a hand with 0 cards, and position (50, 500)
    Player player1 = new Player("Player 1", 0, 0); // creates a player with the name player1, and a score of 0
    
    JPanel feltTable = new JPanel(); // panel for table       
    JPanel controlBar = new JPanel(); // panel for control bar with buttons       
    JPanel scoreBar = new JPanel();  // panel for bar on top to display score       
    JPanel currentBet = new JPanel();  // panel for the current bet (not used in this program but could in future programs)     
    
    /*  Panels arranged like this:
     
     * ---------------------------------------------
     *        score Panel    |    Bet Panel
     * ---------------------------------------------
     * 
     * 
     *                Felt Panel
     *                 
     * ---------------------------------------------
     *                control Panel
     *----------------------------------------------
     */
    
        
    // buttons:
    JButton shuffleDeck = new JButton("Shuffle Deck"); 
    JButton shuffleHand = new JButton("Shuffle Hand");
    JButton changeArrangement = new JButton("Show deck as pile"); // button changes between a pile or splayed
    JButton changeFace = new JButton("Display Deck Face Down"); // button changes between face up or back up
    JButton dealCard = new JButton("Deal a card"); // from deck to player
    JButton discardButton = new JButton("Discard"); // from player to deck
    JButton handArrangement = new JButton("Show hand as pile");
    JButton handFace = new JButton("Display Hand Face Down");
    JLabel scoreLabel = new JLabel(player1.getName()+" Score: "+""+player1.getScore()); // displays score
    JLabel betLabel = new JLabel (player1.getName()+" Current Bet: "+""+player1.getBet()); // displays bet
    
    public void actionPerformed(ActionEvent e) // if a button is pressed
    {
        if (e.getSource() == shuffleDeck) // if the shuffle button is pressed
        {
            deck.shuffle(); // calls the shuffle method from the deck class
            repaint();
        }
        else if (e.getSource() == shuffleHand) // if the hand wants to be shuffled
        {
            hand1.shuffle(); // hand inherits from deck, so hand shuffles the same way (methods are public)
            repaint();
        }
        else if (e.getSource() == changeArrangement) // button to change arrangement
        {
            if (deck.getArrangement() == false) // if the deck is splayed (false)
            {
                changeArrangement.setText("Show deck splayed"); // changes the button so that the next time the button is pressed, it will offer the opposite function
                deck.setArrangement(true); // sets the arrangement to true (a pile)
                repaint();
            }
            else // does the opposite
            {
                changeArrangement.setText("Show deck as pile");
                deck.setArrangement(false);
                repaint();
            }
        }
        else if (e.getSource() == changeFace) // changes the face to display up or down
        {
            if (deck.getFace() == false) // if it is facing down
            {
                deck.setFace(true); // flips it up
                changeFace.setText("Display deck face down"); // changes the button
            }
            else // if it is facing up
            {
                deck.setFace(false);
                changeFace.setText("Display deck face up");
            }
        }
        else if (e.getSource() == dealCard) // wants to deal a card to the hand from the deck
        {
            deck.deal(hand1); // deck deals the card to hand
        }
        else if (e.getSource() == discardButton) // wants to give a card back to the deck
        {
            hand1.discard(deck); // hand gives the card back to the deck
        }
        else if (e.getSource() == handArrangement) // wants the change teh arrangement of the hand
        {
            if (hand1.getArrangement() == false) // is currently splayed
                {
                    handArrangement.setText("Show hand splayed");
                    hand1.setArrangement(true);
                    repaint();
                }
            else // is currently in a pile
                {
                    handArrangement.setText("Show hand as pile");
                    hand1.setArrangement(false);
                    repaint();
                }
        }
        else if (e.getSource() == handFace) // wants to change the face as up or down
        {
            if (hand1.getFace() == false) // is facing down
                {
                    hand1.setFace(true);
                    handFace.setText("Display hand face down");
                }
                else // is facing up
                {
                    hand1.setFace(false);
                    handFace.setText("Display hand face up");
                }
        }
        repaint();
        return;
    }

    public CardGame()
    {
        super ("Card Game");      
      
        Container pane = getContentPane(); // creates the pane
        pane.setLayout(new BorderLayout()); // gives it a border layout
        
        pane.add(feltTable, BorderLayout.CENTER);   // put felt in middle   
        Color feltGreen = new Color(10,100,10); // creates the colour for the table
        feltTable.setBackground(feltGreen); 
        
        pane.add(controlBar, BorderLayout.SOUTH);   // put button bar at bottom
        Color grey = new Color(140,160,160);
        controlBar.setBackground(grey);
        
        // adds action listener to buttons so the program will know if a button is pressed
        shuffleDeck.addActionListener(this);
        shuffleHand.addActionListener(this);
        changeArrangement.addActionListener(this);
        changeFace.addActionListener(this);
        dealCard.addActionListener(this);
        discardButton.addActionListener(this);
        handArrangement.addActionListener(this);
        handFace.addActionListener(this);
        
        // adding all the buttons to the control bar (flow layout by default, will just put them side by side)
        controlBar.add(shuffleDeck);
        controlBar.add(shuffleHand);
        controlBar.add(changeArrangement);
        controlBar.add(changeFace);
        controlBar.add(handArrangement);
        controlBar.add(handFace);
        controlBar.add(dealCard);
        controlBar.add(discardButton);
        
        pane.add(scoreBar, BorderLayout.NORTH);     // put score at top
        scoreBar.setBackground(grey);
        scoreBar.setLayout(new GridLayout(1,2));    // make one row, two columns
        scoreBar.add(scoreLabel);   // puts the score label in the first column                 
        scoreBar.add(betLabel);     // puts the bet label in the second column
        
        Font font = new Font("Comic Sans MS", Font.PLAIN, 18);
        scoreLabel.setFont(font);
        betLabel.setFont(font);
             
        setSize (1500, 700);
        setVisible(true);
        repaint();
        
    }
    
    public void paint(Graphics g) // paint function
    {
       
      super.paint(g);
      Font font2 = new Font("Comic Sans MS", Font.PLAIN, 20); // font for any words painted on screen
      g.setFont(font2);
      g.setColor(Color.gray);
      deck.drawDeck(feltTable,g); // draws deck using draw function in deck class
      hand1.drawDeck(feltTable,g); // inherits from deck, uses same method
      g.drawString(""+player1.getName(),100,625); // draws player name (not ideal, but easier than trying to make a label with the layout)
      return;
      
    }
    
    public static void main( String args[]) 
    {
        CardGame game = new CardGame();     // construct the card game (call the constructor)
        game.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);     // allows you to hit the X button to close
        return;
    }
       
    
    
}