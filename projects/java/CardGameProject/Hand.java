import java.util.Random;
import javax.swing.*;
import java.awt.*;
/**
 * Hand Class
 * Extends from deck class, but does not default to 52 cards, starts with set number of cards
 * Haley Yerxa
 * December 2107
 */
public class Hand extends Deck // very few differences, just doesn't have a default amount of cards
{
    
    public Hand()
    {}
    
    public Hand(int n, int x, int y) // initializes deck with number of cards, position of hand
    {
        numCards = n;
        cardX = x;
        cardY = y;
        setArrangement(false); // default arrangement
        setFace(true); // default orientation
    }
    
    public void discard(Deck deck) // versy similar to deal function, but gives card from hand to deck
    {
        // sets the card on top of the deck to be the same as the card on top of the hand (essentially copying the card, not really giving it)
        if (numCards>0)
        {
            deck.setCard(deck.getNumCards(), cards[numCards-1].getNumber(), cards[numCards-1].getSuit(), deck.getFace());
            numCards = numCards - 1; // decreases the number of cards in the hand
            deck.setNumCards(deck.getNumCards()+1); // increases the number of cards in the deck
        }
        return;
    }

}
