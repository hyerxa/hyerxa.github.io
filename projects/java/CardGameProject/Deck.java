import java.util.Random;
import javax.swing.*;
import java.awt.*;
/**
 * Deck Class
 * Class for a deck of up to 52 cards.
 * Haley Yerxa
 * December 2017
 */
public class Deck
{
    // attributes of the deck:
    protected Card cards[] = new Card[52]; // a card array that can hold up to 52 cards
    protected int numCards;
    protected boolean arrangement; // whether it is splayed or in a pile
    int cardX; // used for location
    int cardY;
    protected int cardShuffle1; // used as a random number for shuffling
    protected int cardShuffle2;
    protected Card swapCard; // placeholder for shuffling
    protected boolean face; // whether it is facing up or down
    
    public Deck() // basic constructor
    {}
    
    public Deck(int n) // constructer with number of cards
    {
        setCards(); // initializes cards (full deck)
        numCards = n;
        setArrangement(false); // default as splayed
        setFace(true); // default facing up
        cardX = 50; // first card is at (50, 100)
        cardY = 100;
    }
    
    public void setCards() // sets the deck, when it is drawn, it will only draw up to the number of cards
    {
        for (int i=0; i<13; i++) // sets up clubs suit
        {
            cards[i] = new Card(i+1,'c'); // card constructer with number and suit (1-13, clubs (face cards are given number values))
            cards[i].setFront(); // method that sets the image icon for the front of the card based on its number and suit
            cards[i].setBack(); // back is the same for all cards
        }
        for (int i=13; i<26; i++) // sets up spades suit
        {
            cards[i] = new Card(i-12,'s');
            cards[i].setFront();
            cards[i].setBack();
        }
        for (int i=26; i<39; i++) // sets up hearts suit
        {
            cards[i] = new Card(i-25,'h');
            cards[i].setFront();
            cards[i].setBack();
        }
        for (int i=39; i<52; i++) // sets up diamonds suit
        {
            cards[i] = new Card(i-38,'d');
            cards[i].setFront();
            cards[i].setBack();
        }
        return;
    }
    
    // get and set functions:
    public void setNumCards(int n)
    {
        numCards = n;
        return;
    }
    
    public int getNumCards()
    {
        return numCards;
    }
    
    public Card getCard(int c)
    {
        return cards[c];
    }
    
    public void setCard(int c, int n, char s, boolean b) // passes the number for the array, the number of the card, the suit, and the desired face direction
    {
        cards[c] = new Card(n,s);
        cards[c].setFront();
        cards[c].setBack();
        cards[c].setFaceUp(b);
        return;
    }
    
    public boolean getFace()
    {
        return face;
    }
    
    public void setFace(boolean f)
    {
        face = f;
        for (int i=0; i<numCards; i++)
        {
            cards[i].setFaceUp(f);
        }
        return;
    }
    
    public void setArrangement(boolean a)
    {
        arrangement = a;
    }
    
    public boolean getArrangement()
    {
        return arrangement;
    }    
    
    public void setX(int x1)
    {
        cardX=x1;
        return;
    }
    
    public int getX()
    {
        return cardX;
    }
    
    public void setY(int y1)
    {
        cardY = y1;
    }
    
    public int getY()
    {
        return cardY;
    }
    
    public void shuffle() // shuffle method
    {
        Random random = new Random();
        for (int i=0; i<1000; i++) // goes through 1000 times to shuffle up well
        {
            cardShuffle1 = random.nextInt(numCards); // random number that will be the index of one of the cards in the deck
            cardShuffle2 = random.nextInt(numCards);
            swapCard = cards[cardShuffle1]; // placeholder, takes on the attributes of the first card that is being replaced so that it can be changed
            cards[cardShuffle1] = cards[cardShuffle2]; // the first card becomes the first card
            cards[cardShuffle2] = swapCard; // the second card becomes the swapcard (which is a doppelganger of the first card)
            // essentially the two cards are switched. This happens 1000 times so the deck is adequately shuffled
        }
        
    }
    
    public void deal(Hand hand) // deals from the deck to the hand
    { 
        if (numCards > 0)
        {
            hand.setCard(hand.getNumCards(), cards[numCards-1].getNumber(), cards[numCards-1].getSuit(), hand.getFace());
            // sets the next card in the hand array to be the last card in the deck
            numCards = numCards - 1; // decreases the number of cards, the last card will be 'given' to the hand.
            hand.setNumCards(hand.getNumCards()+1); // hand increases in number of cards
        }
        return;
    }
    
    public void drawDeck(Container c, Graphics g) // draws the deck
    {
        if (arrangement == true) // if it is a pile
        {
            // only draws card on top
            if (numCards != 0)
            {
                cards[numCards-1].setX(cardX);
                cards[numCards-1].setY(cardY);
                cards[numCards-1].draw(c,g); // draw method in card class
            }
        }

        else // if it is splayed
        {
            for (int i=0; i<numCards; i++)
            {
                cards[i].setX(cardX+(i*10)); // draws each card 10 units further over
                cards[i].setY(cardY);
                cards[i].draw(c,g);
            }
        }
    }
    

}
