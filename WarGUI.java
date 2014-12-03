/*
   Mike Skwar
   CS110
   11/26/14 - HW 10 - WAR GUI
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
   Graphical User Interface for a game of War
*/
public class WarGUI extends JFrame
{
   private String temp,temp1,temp2;         //last minute string variable to try to display the score...
   private WarCW warGame;              // the guts
   private JPanel topPanel,gamePanel;  // break up regions
   private JButton button;             // grid of buttons
   private JLabel sup, p1, p2;         // "What's up?" What's going on in the game, "P1", and "P2".
   
   private JLabel picP1;                //player 1s hand
   private JLabel picP2;                //player 2s hand
   private JLabel p1Played;             //p1's card played
   private JLabel p2Played;             //p2's card played
   private JLabel rounds;               //rounds played
   private ImageIcon frontP1, frontP2,
           back, joker;                 //front of given card, back of given card
           
   private int p1Num, p2Num, roundNum;  //to display scores and round number
   
   public WarGUI()
   {
      setLayout(new GridLayout(2,1));
      
      //start a game    
      warGame = new WarCW();
      
      //Top panel for game details
      topPanel = new JPanel();
      topPanel.setBackground(Color.green);  //it's green.
      
      //bottom panel for gameplay
      gamePanel = new JPanel();
      
      //Top panel stuff   
      sup = new JLabel("**** Mike's  WAR game ****");
      sup.setFont(new Font("ARIAL",Font.BOLD,46));
      topPanel.add(sup);
      
      //Bottom panel stuff
      roundNum = 1;
      temp = (" || Round number " + roundNum + "  ||      ");
      rounds = new JLabel(temp);
      
      back = new ImageIcon("back.jpg");
      frontP1 = new ImageIcon();  //set it when it happens.
      frontP1 = new ImageIcon();  
      joker = new ImageIcon("joker.jpg");
      
      p1 = new JLabel("P1 ->");
      p2 = new JLabel("<- P2");
      
      picP1 = new JLabel(back); 
      picP2 = new JLabel(back); 
      p1Played = new JLabel(joker);
      p2Played = new JLabel(joker);
      
      //The button that makes the game go and keep going
      button = new JButton("NEXT");
      button.addActionListener(new ButtonListener());
      gamePanel.add(button);   
      
      gamePanel.add(p1); 
      gamePanel.add(picP1);
      gamePanel.add(p1Played);
      gamePanel.add(p2Played);
      gamePanel.add(picP2);
      gamePanel.add(p2);
      gamePanel.add(rounds);
      
      //trying to display the score in poor fashion
      p1Num = (WarCW.p1.cardsRemaining() + WarCW.p1e.cardsRemaining());
      p2Num = (WarCW.p2.cardsRemaining() + WarCW.p2e.cardsRemaining());
      
      temp1 = ("P1: "+p1Num + "   || ");
      temp2 = ("P2: "+p2Num);  
      
      p1 = new JLabel(temp1);  //26
      p2 = new JLabel(temp2);  //26  
      
      topPanel.add(p1);
      topPanel.add(p2); 

      add(topPanel);
      add(gamePanel);
   }
   
   // handle button events
   private class ButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {         
         WarCW.p1played = WarCW.p1.useCard();
         WarCW.p2played = WarCW.p2.useCard();        

         frontP1 = new ImageIcon(p1GetFront());
         frontP2 = new ImageIcon(p2GetFront());
         
         p1Played.setIcon(frontP1);
         p2Played.setIcon(frontP2);         
         
         //determine winner
         if (WarCW.p1played.isGreater(WarCW.p2played))
         {
            roundNum++;
            temp = (" || Round number " + roundNum + "  ||      ");
            rounds.setText(temp);
            
            temp = ("P1's " + WarCW.p1played + " wins this round!");
            sup.setText(temp);
             
            //topPanel.add(sup);
            topPanel.setBackground(Color.green);
            WarCW.p1e.addCard(WarCW.p1played);
            WarCW.p1e.addCard(WarCW.p2played);
            WarCW.p1e.shuffle();
            gamePanel.add(rounds);
         }         
         else if (WarCW.p1played.isLess(WarCW.p2played))
         {
            roundNum++;
            temp = (" || Round number " + roundNum + "  ||      ");
            rounds .setText(temp);

            temp = ("P2's " + WarCW.p2played + " wins this round!");
            sup.setText(temp);
            
            topPanel.setBackground(Color.green);
            WarCW.p2e.addCard(WarCW.p1played);
            WarCW.p2e.addCard(WarCW.p2played);
            WarCW.p2e.shuffle();
            gamePanel.add(rounds);
         }
         else //WAR WAR WAR!!!
         {
            temp = ("RANKS EQUAL: THIS. MEANS. WAR.");
            sup.setText(temp);
            sup.setFont(new Font("ARIAL",Font.BOLD,46)); 
            
            topPanel.setBackground(Color.red);
            
            consolidate();
            WarCW.p1.shuffle();
            WarCW.p2.shuffle();
            WarCW.warPile.addCard(WarCW.p1played);
            WarCW.warPile.addCard(WarCW.p2played);
            war();
         }
         
         //update the score
         p1Num = (WarCW.p1.cardsRemaining() + WarCW.p1e.cardsRemaining());
         p2Num = (WarCW.p2.cardsRemaining() + WarCW.p2e.cardsRemaining());
         
         temp1 = ("P1: "+p1Num + "   || ");
         temp2 = ("P2: "+p2Num);  
         
         p1.setText(temp1);  
         p2.setText(temp2);    

         consolidate();
      }      
   }
   
   /*
      The consolidate method consolidates player 1's hand with player 1's earned cards
      and consolidates player 2's hand with player 2's earned cards.
   */
   public void consolidate()
   {    
      if (WarCW.p1.cardsRemaining() < 2)
      {
         if ((WarCW.p1.isEmpty()) && (WarCW.p1e.cardsRemaining() == 0))
         {
            //YOU LOSE
            //end game                      
            temp = ("P2 WINS! It took " +  WarCW.round + ". There were " + WarCW.warNum + " wars.");
            
            sup = new JLabel("");
            sup.setFont(new Font("ARIAL",Font.BOLD,46));
            sup.setText(temp);
            
            button.setEnabled(false);
         }
         else
         {
            while (!(WarCW.p1e.isEmpty()))     
            {
               WarCW.p1e.shuffle();
               WarCW.temp = WarCW.p1e.useCard();
               WarCW.p1.addCard(WarCW.temp);
            }
         }
      }
      if (WarCW.p2.cardsRemaining() < 2)
      {
         if ((WarCW.p2.isEmpty()) && (WarCW.p2e.cardsRemaining() == 0))
         {
            //YOU WIN
            //end game
            temp = ("P1 WINS! It took " +  WarCW.round + ". There were " + WarCW.warNum + " wars.");
            
            sup = new JLabel("");
            sup.setFont(new Font("ARIAL",Font.BOLD,46));
            sup.setText(temp);
            
            button.setEnabled(false);
         }
         else
         {
            while (!(WarCW.p2e.isEmpty()))     
            {
               WarCW.p2e.shuffle();
               WarCW.temp = WarCW.p2e.useCard();
               WarCW.p2.addCard(WarCW.temp);
            }
         }
      }
   }
   
   /*
      The war method simulates a war in the card game War. 
   */
   public void war()
   {
      temp = ("RANKS EQUAL: THIS. MEANS. WAR.");
      sup.setFont(new Font("ARIAL",Font.BOLD,46));
      sup.setText(temp);    
         
      //make sure we both have enough cards to have a war. if someone doesn't they lose right here.
      if (WarCW.p1.cardsRemaining() > 2) 
      {
         WarCW.warP1 = WarCW.p1.useCard(); 
      }
      else
      {  
         consolidate();
         if (WarCW.p1.cardsRemaining() < 2) 
         {
            //YOU LOSE
            //end game
            temp = ("P2 WINS! It took " +  WarCW.round + ". There were " + WarCW.warNum + " wars.");
            
            sup = new JLabel("");
            sup.setFont(new Font("ARIAL",Font.BOLD,46));
            sup.setText(temp);
            
            button.setEnabled(false);
         }
         else
         {
            WarCW.warP1 = WarCW.p1.useCard();
         }
      }
      
      if (WarCW.p2.cardsRemaining() > 2) 
      {
         WarCW.warP2 = WarCW.p2.useCard();
      }
      else
      {  
         consolidate();
         if (WarCW.p2.cardsRemaining() < 2)
         {
            //YOU WIN
            //end game
            temp = ("P1 WINS! It took " +  WarCW.round + ". There were " + WarCW.warNum + " wars.");
            
            sup = new JLabel("");
            sup.setFont(new Font("ARIAL",Font.BOLD,46));
            sup.setText(temp);
            
            button.setEnabled(false);
         }
         else
         {
            WarCW.warP2 = WarCW.p2.useCard();
         }
      }
      
      //maybe figure out how to add this to your GUI. FACE DOWN. ...maybe not.
      WarCW.warPile.addCard(WarCW.warP1); //FACE DOWN from P1
      WarCW.warPile.addCard(WarCW.warP2); //FACE DOWN from P2
      
      WarCW.warP1 = WarCW.p1.useCard();
      WarCW.warP2 = WarCW.p2.useCard();

      //determine winner
      if (WarCW.warP1.isGreater(WarCW.warP2))
      {
         temp = ("P1's " + WarCW.warP1 + " wins the war! They won " + WarCW.warPile.cardsRemaining() + " cards!" );
         sup.setText(temp);

         WarCW.p1e.addCard(WarCW.warP1);
         WarCW.p1e.addCard(WarCW.warP2);
         
         while (WarCW.warPile.cardsRemaining() > 0)       //Do this until the war pile is empty
         {
            WarCW.temp = WarCW.warPile.useCard();
            WarCW.p1e.addCard(WarCW.temp);             //addCard from war pile to p1's hand
         }

         WarCW.p1e.shuffle();
      }
      else if (WarCW.warP1.isLess(WarCW.warP2))
      {
         temp = ("P2's " + WarCW.warP2 + " wins the war! They won " + WarCW.warPile.cardsRemaining() + " cards!" );
         sup.setText(temp);

         WarCW.p2e.addCard(WarCW.warP1);
         WarCW.p2e.addCard(WarCW.warP2);
         
         while (WarCW.warPile.cardsRemaining() > 0)          //Do this until the war pile is empty
         {
            WarCW.temp = WarCW.warPile.useCard();
            WarCW.p2e.addCard(WarCW.temp);             //addCard from war pile to p2's hand
         }
         
         WarCW.p2e.shuffle();
      }
      else //WAR WAR WAR!!!
      {
         temp = ("THIS. MEANS. MORE. WAR.");
         sup.setText(temp);
         
         consolidate();
         WarCW.p1.shuffle();
         WarCW.p2.shuffle();
         WarCW.warPile.addCard(WarCW.warP1);
         WarCW.warPile.addCard(WarCW.warP2);
         war();
      }
   }

   /**********************************************
   * MASSIVE SWITCH STATEMENTS. There exist      *
   * more efficient ways to do this, like a map, *
   * but we didn't cover it, and I'm falling     *
   * short on time. This ridiculousness is the   *
   * rest of my code:                            *
   **********************************************/
   
   /*
      The p1GetFront method returns
      @return a string filename of the specified card
      for player 1
   */
   public String p1GetFront()
   {
      String str = "";   
      switch(WarCW.p1played.getSuitStr()){
         case "SPADES":
         {
            switch(WarCW.p1played.getRankStr()){
               case "ACE":
               {
                  str = "aces.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2s.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3s.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4s.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5s.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6s.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7s.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8s.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9s.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10s.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jacks.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queens.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kings.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }                      
         }         
         case "CLUBS":
         {
            switch(WarCW.p1played.getRankStr()){
               case "ACE":
               {
                  str = "acec.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2c.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3c.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4c.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5c.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6c.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7c.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8c.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9c.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10c.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jackc.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queenc.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kingc.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }
         }
         
         case "HEARTS":
         {
            switch(WarCW.p1played.getRankStr()){
               case "ACE":
               {
                  str = "aceh.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2h.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3h.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4h.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5h.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6h.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7h.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8h.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9h.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10h.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jackh.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queenh.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kingh.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }
         }        
         case "DIAMONDS":
         {
            switch(WarCW.p1played.getRankStr()){
               case "ACE":
               {
                  str = "aced.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2d.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3d.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4d.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5d.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6d.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7d.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8d.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9d.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10d.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jackd.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queend.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kingd.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }
         }         
         default:
         {
            str = "joker.jpg";
            return str;
         }
      }
   }
   
   /*
      The p1GetFront method returns
      @return a string filename of the specified card
      for player 1
   */ 
   public String p2GetFront()
   {
      String str = "";     
      switch(WarCW.p2played.getSuitStr()){
         case "SPADES":
         {
            switch(WarCW.p2played.getRankStr()){
               case "ACE":
               {
                  str = "aces.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2s.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3s.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4s.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5s.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6s.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7s.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8s.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9s.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10s.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jacks.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queens.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kings.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }                      
         }         
         case "CLUBS":
         {
            switch(WarCW.p2played.getRankStr()){
               case "ACE":
               {
                  str = "acec.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2c.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3c.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4c.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5c.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6c.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7c.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8c.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9c.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10c.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jackc.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queenc.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kingc.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }
         }
         
         case "HEARTS":
         {
            switch(WarCW.p2played.getRankStr()){
               case "ACE":
               {
                  str = "aceh.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2h.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3h.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4h.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5h.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6h.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7h.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8h.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9h.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10h.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jackh.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queenh.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kingh.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }
         }        
         case "DIAMONDS":
         {
            switch(WarCW.p2played.getRankStr()){
               case "ACE":
               {
                  str = "aced.jpg";
                  return str;
               }              
               case "TWO":
               {
                  str = "2d.jpg";
                  return str;
               }               
               case "THREE":
               {
                  str = "3d.jpg";
                  return str;
               }              
               case "FOUR":
               {
                  str = "4d.jpg";
                  return str;
               }              
               case "FIVE":
               {
                  str = "5d.jpg";
                  return str;
               }              
               case "SIX":
               {
                  str = "6d.jpg";
                  return str;
               }               
               case "SEVEN":
               {
                  str = "7d.jpg";
                  return str;
               }              
               case "EIGHT":
               {
                  str = "8d.jpg";
                  return str;
               }               
               case "NINE":
               {
                  str = "9d.jpg";
                  return str;
               }               
               case "TEN":
               {
                  str = "10d.jpg";
                  return str;
               }               
               case "JACK":
               {
                  str = "jackd.jpg";
                  return str;
               }               
               case "QUEEN":
               {
                  str = "queend.jpg";
                  return str;
               }               
               case "KING":
               {
                  str = "kingd.jpg";
                  return str;
               }               
               default:
               {
                  str = "joker.jpg";
                  return str;
               }
            }
         }         
         default:
         {
            str = "joker.jpg";
            return str;
         }
      }
   }
}  