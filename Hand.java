/*
   Mike Skwar
   CS110
   HW 10. WAR
*/

/**
 * Representation of a Hand of cards 
 *  
 * based on
 * @author Jackie Horton's
 * Deck1.java
 */

public class Hand extends CardCollection
{
   /**
     * Constructs an empty hand
     */
   public Hand() {}
   
    /**
     * Constructs a regular hand of specified size from a Card Collection
     * @param deck a CardCollection
     * @param handSize an integer value representing the size of the hand to be dealt from the 
     * aforementioned deck CardCollection
     */
   public Hand(CardCollection deck, int handSize)
   {
      for(int i = 0; i < handSize; i++ )
      {
         addCard(deck.useCard());
      }
   }  
}
