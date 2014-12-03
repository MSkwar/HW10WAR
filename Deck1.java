/*
   Mike Skwar
   CS110
   HW10 -- WAR -- edited Deck1 class
*/

/**
 * Representation of a Deck of cards.  
 * Initialized to a standard 52 card deck. 
 *
 * @author Jackie Horton
 */

public class Deck1 extends CardCollection
{
   /** 
   *  Number of cards in standard deck {@value #CARDS_IN_DECK}
   **/
   final int CARDS_IN_DECK = 52;
   
   /**
    * Constructs a regular 52-card deck.  Initially, the cards
    * are in a sorted order.  The shuffle() method can be called to
    * randomize the order.  
    */
   public Deck1()
   {
      freshDeck();
   }  
}
