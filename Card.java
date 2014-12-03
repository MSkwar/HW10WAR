/* Mike Skwar
 CS 110
 HW #5, problem #3, Card class
*/


/*
   Represents a playing Card
*/
public class Card
{
   //Final ints so they can't be changed... here's a list of our suits, our non-numeric card ranks.
   final static int SPADES = 1;
   final static int CLUBS = 2;
   final static int HEARTS = 3;
   final static int DIAMONDS = 4;
   final static int ACE = 14
   ;
   final static int JACK = 11;
   final static int QUEEN = 12;
   final static int KING = 13;
   
   private int m_rank;
   private int m_suit;
   private String m_rankStr;
   private String m_suitStr;

   /*
      The Card constructor creates a Card with
      @param suit the int value that corresponds to the suit, and
      @param rank the int value that corresponds to the rank.
   **/
   public Card(int suit, int rank)
   {
      m_suit = suit;
      m_rank = rank;
      setupSuit();
      setupRank();
   }
   
   /*
      The getSuit method returns 
      @return m_suit the integer value of the Card's suit.
   **/
   public int getSuit()
   {
      return m_suit;
   }
   
   /*
      The getRank method returns 
      #return m_rank the integer value of the Card's rank
   **/
   public int getRank()
   {
      return m_rank;
   }
   
   /*
      The getSuit method returns 
      @return m_suitStr the String value of the Card's suit.
   **/
   public String getSuitStr()
   {
      String m_suitStr = setupSuit();
      return m_suitStr;
   }
   
   /*
      The getRank method returns 
      @return m_rankStr the String value of the Card's rank
   **/
   public String getRankStr()
   {
      String m_rankStr = setupRank();
      return m_rankStr;
   }
   
   /*
      The toString method returns 
      @return strRank and strSuit concatonated to a String suit and rank of the Card.
   **/
   public String toString()
   {
      String strRank = (m_rankStr);
	   String strSuit = (m_suitStr);
			 
      return (" " + strRank + " of " + strSuit);
   }
   
   /*
      The equals method determines if two Cards have equal rank.
      @return true if equal
   **/
   public boolean equals(Card other)
   {
      if (m_rank == other.m_rank)
         return true;
      return false;
   }
   
   /*
      The equals method determines if a card is less than another
      @return true if the card is of lower rank
   **/
   public boolean isLess(Card other)
   {
      if (m_rank < other.m_rank)
         return true;
      return false;
   }
   
   /*
      The equals method determines if a card is greater than another
      @return true if the card is of higher rank
   **/
   public boolean isGreater(Card other)
   {
      if (m_rank > other.m_rank)
         return true;
      return false;
   }
   
   /*
      The setupRank method uses the rank's integer value for a switch statement, which returns the appropriate
      rank for each integer value
      @return m_rankStr the String rank of the card
   **/
   private String setupRank()
   {
      switch (m_rank)
      {
         case ACE:
         {
            m_rankStr = "ACE";
            return m_rankStr;
         }         
         case 2:
         {
            m_rankStr = "TWO";
            return m_rankStr;
         }         
         case 3:
         {
            m_rankStr = "THREE";
            return m_rankStr;
         }         
         case 4:
         {
            m_rankStr = "FOUR";
            return m_rankStr;
         }
         
         case 5:
         {
            m_rankStr = "FIVE";
            return m_rankStr;
         }
         
         case 6:
         {
            m_rankStr = "SIX";
            return m_rankStr;
         }
         
         case 7:
         {
            m_rankStr = "SEVEN";
            return m_rankStr;
         }
         
         case 8:
         {
            m_rankStr = "EIGHT";
            return m_rankStr;
         }
         
         case 9:
         {
            m_rankStr = "NINE";
            return m_rankStr;
         }
         
         case 10:
         {
            m_rankStr = "TEN";
            return m_rankStr;
         }
         
         case JACK:
         {
            m_rankStr = "JACK";
            return m_rankStr;
         }
         
         case QUEEN:
         {
            m_rankStr = "QUEEN";
            return m_rankStr;
         }
         
         case KING:
         {
            m_rankStr = "KING";
            return m_rankStr;
         }
         
         default:
         {
            m_rankStr = "JOKER"; 
            return m_rankStr;
         }
      }
   }
   
   /*
      The setupSuit method uses the suit's integer value for a switch statement, which returns the appropriate
      suit for each integer value
      @return m_suitStr the string value of the card's suit
   **/
   private String setupSuit()
   {
      switch (m_suit)
      {
         case SPADES:
         {
            m_suitStr = "SPADES";
            return m_suitStr;
         }
         
         case CLUBS:
         {
            m_suitStr = "CLUBS";
            return m_suitStr;
         }
         
         case HEARTS:
         {
            m_suitStr = "HEARTS";
            return m_suitStr;
         }
         
         case DIAMONDS:
         {
            m_suitStr = "DIAMONDS";
            return m_suitStr;
         }
         
         default:
         {
            m_suitStr = "THE DECK";
            return m_suitStr;
         }
      }
   }
}