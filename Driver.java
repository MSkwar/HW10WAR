/*
   Mike Skwar
   CS110
   11/26/14 - HW 10 - WAR Driver
*/

import javax.swing.*;

/*
   War driver
*/
public class Driver
{
   public static void main(String [] args)
   {
      JFrame frame = new WarGUI();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
      // OR
      frame.setSize(1370,500);
      frame.validate();
      frame.setVisible(true);
   }
}