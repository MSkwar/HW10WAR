/*
   Mike Skwar
   CS110
   11/26/14 - HW 10 - WAR
*/

import java.util.Random;
import java.util.ArrayList;

public class CardCollection
{
   /** The collection of Cards */
   private ArrayList<Card> collection = new ArrayList<Card>();
   
   //Default constructor
   CardCollection(){}
   
   /** 
     * Remove and return the top Card on the Card Collection
     * @return A reference to a Card that was top on the Card Collection
     */
   public Card useCard()
   {
      Card c = collection.remove(0);  //  remove it (returns removed object)
      return c;
   }
   
   /*
    * Adds 
    * @param c a Card to the card collection
    */
   public void addCard(Card c)
   {
      collection.add(0, c);
   }
   
   /*
      The freshdeck method creates a fresh new deck. 52 unique cards.
   */
   public void freshDeck()
   {
      collection = new ArrayList<Card>();
      System.out.println(collection.size());

      for (int r = 2; r <= Card.ACE; r++)
      {
         for (int s = Card.SPADES; s <= Card.DIAMONDS; s++)
         {
           collection.add(new Card(s,r));
         }
      }
   } 
   
   /** 
     * Return current number of Cards in Deck
     * @return number of Cards in Deck
     */
   public int cardsRemaining()
   {  
      return collection.size();
   }
   
   /** 
     * Randomize the order of Cards in Deck
     */

   public void shuffle()
   {
      int randNum;
      Card temp;
      Random r = new Random();
      for (int i = 0; i < collection.size(); i++)
      {
         randNum = r.nextInt(collection.size());
         temp = collection.get(i);
         collection.set(i,collection.get(randNum));
         collection.set(randNum,temp);
      }      
   }
   
   /** 
     * Determine if Deck is empty
     * @return true if there are no more cards, false otherwise
     */
   
   public boolean isEmpty()
   {
      return (collection.size() == 0);
   }
   
   /*
      highCard method takes in
      @param cards, cards, Card objects and determines
      which among them has the highest rank.
   */
   public static Card highCard(Card...cards)
   {
   
      Card high = cards[0];
      for (int i=1;i<cards.length;i++)
      {
         if (cards[i].getRank() > high.getRank())
         {
         
            high = cards[i];
         }
      }
      return high;
   }
}