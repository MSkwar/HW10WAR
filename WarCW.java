/*
   Mike Skwar
   CS110
   11/26/14 - HW 10 - WAR
*/
import java.util.Scanner;

public class WarCW
{

   // Program entry point
   public static void main(String[] args) 
   {
      // Create a new War game and play it
      WarCW warGame = new WarCW();
      warGame.playWar();
   }
   
   
   public final int HALF_DECK = 26;
   
   //declariables
   public static int round = 0;
   public static int warNum = 0;
   public static int warsThisWar = 0;
   
   private String input;
   private String quit = "-99";                          //This will represent events until I setup GUI
   public static Card temp, p1played, p2played,          //Card object placeholders. Will be used to compare/transfer cards.
           warP1, warP2;
                 
   private CardCollection theDeck;                //actual deck
   
   //The hands
   public static CardCollection p1;               //p1s hand
   public static CardCollection p1e;              //p1s pile of cards won
   public static CardCollection p2;               //p2s hand
   public static CardCollection p2e;              //p2s pile of cards won
   public static CardCollection warPile;          //cards up for grabs in a war go here
   
   /*
      Default constructor creates a Game of War
   */
   public WarCW() 
   {
      theDeck = new Deck1();
      
      //shuffle the deck
      theDeck.shuffle();

      int halfDeckSize = theDeck.cardsRemaining()/2;
      p1 = new Hand(theDeck,halfDeckSize);
      p1e = new Hand();
      p2 = new Hand(theDeck,halfDeckSize);
      p2e = new Hand();
      warPile = new Hand();
   }
   
   /*
      the playWar method contains most of the game logic
   */ 
   public void playWar()
   {    
      //Create Scanner object to read input.
      Scanner keyboard = new Scanner(System.in);
           
      //I didn't need this bit for a while, then JGrasp starting telling me some of these
      //weren't being initialized properly. So, gave them a concrete value so as to 'properly' initialize them.
      p1played = new Card(1,14);          
      p2played = new Card(1,14);
     
      //Welcometo WAR
      System.out.println("Welcome to the game of war. Let me grab a deck. Hit enter to continue or type '-99' to quit.");
      input = keyboard.nextLine(); //User initiates game //EVENT
      if (input.equals(quit))
      {
         System.exit(1);
      }
      
      //Shuffle 
      p1.shuffle();
      p2.shuffle();    
      
      System.out.println("I just dealt the deck out. Are you ready to begin? Hit enter to continue or type '-99' to quit.");
      input = keyboard.nextLine();      //EVENT
      if (input.equals(quit))
      {
         System.exit(1);
      }
      
      //Start game
      while ((!(p1.isEmpty())) || (!(p2.isEmpty())))
      {
         warsThisWar = 0;
         p1.shuffle();
         p2.shuffle();
         round++;
         System.out.println("Round: " + round);
         
         p1played = p1.useCard();
         p2played = p2.useCard();
         
         System.out.println("You drew" + p1played);
         System.out.println("I drew" + p2played);
         
         //determine winner
         if (p1played.isGreater(p2played))
         {
            System.out.println("Your " + p1played + " wins this round!");
            System.out.println("");
            p1e.addCard(p1played);
            p1e.addCard(p2played);
            p1e.shuffle();
         }
         else if (p1played.isLess(p2played))
         {
            System.out.println("My " + p2played + " wins this round!");
            System.out.println("");
            p2e.addCard(p1played);
            p2e.addCard(p2played);
            p2e.shuffle();
         }
         else //WAR WAR WAR!!!
         {
            warNum++;
            consolidate();
            p1.shuffle();
            p2.shuffle();
            warPile.addCard(p1played);
            warPile.addCard(p2played);
            war();           
         }     
         
         //UPDATE STATS
         System.out.println("You have: " + p1.cardsRemaining() + " in your hand, with "
                           + p1e.cardsRemaining() + " in your pile. A total of " + 
                           (p1.cardsRemaining() + p1e.cardsRemaining()));
         System.out.println("I have: " + p2.cardsRemaining() + " in my hand, with " 
                           + p2e.cardsRemaining() + " in my pile. A total of " + 
                           (p2.cardsRemaining() + p2e.cardsRemaining()));
         System.out.println("");
         
         System.out.println("Hit enter for next turn or '-99' to quit."); //EVENT
         input = keyboard.nextLine();
                  
         if (input.equals(quit))
         {
            System.exit(1);
         }
         
         System.out.println("");
         
         consolidate();
      }        
   }
   
   /*
      The Consolidate method consolidates player 1's earned cards into player 1's hand. It does the same for player 2.
      This method also checks to see if the player is out of cards. If they are, the game ends
   */
   public void consolidate()
   {    
      if (p1.cardsRemaining() < 2)
      {
         if ((p1.isEmpty()) && (p1e.cardsRemaining() == 0))
         {
            System.out.println("I win! You're outta cards! You lose!");
            System.exit(1);
         }
         else
         {
            while (!(p1e.isEmpty()))     
            {
               p1e.shuffle();
               temp = p1e.useCard();
               p1.addCard(temp);
            }
         }
      }
      if (p2.cardsRemaining() < 2)
      {
         if ((p2.isEmpty()) && (p2e.cardsRemaining() == 0))
         {
            System.out.println("You win! I'm outta cards! I lose!");
            System.exit(1);
         }
         else
         {
            while (!(p2e.isEmpty()))     
            {
               p2e.shuffle();
               temp = p2e.useCard();
               p2.addCard(temp);
            }
         }
      }
   }
   
   /*
      The War method simulates a war in the card game war. It also determines a winner, if one can be determined during the war
   */
   public void war()
   {
      warsThisWar++;
      
      //Create Scanner object to read input.
      Scanner keyboard = new Scanner(System.in);
      
      //do the war
      System.out.println("");
      System.out.println("WE'VE GOT A WAR. (hit enter to continue or '-99' to quit)");
      input = keyboard.nextLine();      //EVENT
      if (input.equals(quit))
      {
         System.exit(1);
      }
      
      System.out.println("");
      System.out.println("Both players put down card in the pile FACE DOWN.");
      System.out.println("");      
      
      //make sure we both have enough cards to have a war. if someone doesn't they lose right here.
      if (p1.cardsRemaining() > 2) //hmm
      {
         warP1 = p1.useCard(); 
      }
      else
      {  
         consolidate();
         if (p1.cardsRemaining() < 2) //hmm
         {
            System.out.println("You lose! You're outta cards! I win!");
            System.exit(1);
         }
         else
         {
            warP1 = p1.useCard();
         }
      }
      
      if (p2.cardsRemaining() > 2) //hmm
      {
         warP2 = p2.useCard();
      }
      else
      {  
         consolidate();
         if (p2.cardsRemaining() < 2) //hmm
         {
            System.out.println("You win! I'm outta cards! I lose!");
            System.exit(1);
         }
         else
         {
            warP2 = p2.useCard();
         }
      }
      warPile.addCard(warP1); //FACE DOWN from P1
      warPile.addCard(warP2); //FACE DOWN from P2
      
      warP1 = p1.useCard();
      warP2 = p2.useCard();
      
      System.out.println("War: " + warNum);
      System.out.println("Battle: " + warsThisWar);
      
      System.out.println("You drew" + warP1);
      System.out.println("I drew" + warP2);
 
      //determine winner
      if (warP1.isGreater(warP2))
      {
         System.out.println("Your " + warP1 + " wins this round!");
         System.out.println("");
         p1e.addCard(warP1);
         p1e.addCard(warP2);
         
         while (warPile.cardsRemaining() > 0)       //Do this until the war pile is empty
         {
            temp = warPile.useCard();
            p1e.addCard(temp);             //addCard from war pile to p1's hand
         }

         p1e.shuffle();
      }
      else if (warP1.isLess(warP2))
      {
         System.out.println("My " + warP2 + " wins this round!");
         System.out.println("");
         p2e.addCard(warP1);
         p2e.addCard(warP2);
         
         while (warPile.cardsRemaining() > 0)          //Do this until the war pile is empty
         {
            temp = warPile.useCard();
            p2e.addCard(temp);             //addCard from war pile to p2's hand
         }
         
         p2e.shuffle();
      }
      else //WAR WAR WAR!!!
      {
         consolidate();
         p1.shuffle();
         p2.shuffle();
         warPile.addCard(warP1);
         warPile.addCard(warP2);
         war();
      }
   }
}