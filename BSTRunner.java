import java.awt.Color;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class BSTRunner 
{
	public static void changeJOP()
	{

		// The font of the message text
		UIManager.put("Label.font", new FontUIResource 
				(new Font("Tempus Sans ITC", Font.BOLD, 58)));
		// The color of the message text
		UIManager.put("OptionPane.messageForeground",Color.black);

		// color for text field (where you are inputting data)
		UIManager.put("TextField.background", Color.black);
		// font for message in text field
		UIManager.put("TextField.font", new FontUIResource(new Font
				("Dialog", Font.ITALIC, 24)));
		// color for message in text field
		UIManager.put("TextField.foreground", Color.red);

		// The color of the panel 
		UIManager.put("Panel.background",new Color(224,132,30));
		// The color around the outside of the panel
		UIManager.put("OptionPane.background", new Color(224,132,30));

		// Buttons at bottom
		UIManager.put("Button.background", Color.orange);
		UIManager.put("Button.foreground", Color.black);
		UIManager.put("Button.font", new FontUIResource	(new Font
				("Tempus Sans ITC", Font.BOLD, 14)));
	}
	public static void main(String[] args)
	{
		changeJOP();
		BSTHeader<Character> ch = new BSTHeader<Character>();
		String answer = JOptionPane.showInputDialog("Enter a string");
		
		for(int i = 0; i < answer.length(); i++) {
			ch.add(answer.charAt(i));
		}
		
		ch.print();
		
		String[] options = {"Add", "Delete", "Search", "Print", "Exit"};
		
		int choice = JOptionPane.showOptionDialog
				(null, "What would you like to do? ", "TreeNodes",
				0, 3, null, options, null);
		
		char letter = ' ';
		while(choice != 4) 
		{
			switch(choice) 
			{
				case 0: letter = JOptionPane.showInputDialog("Enter a character").charAt(0); 
					ch.add(letter); 
				break;
				case 1: letter = JOptionPane.showInputDialog("Enter a character").charAt(0); 
					boolean deleted = ch.delete(letter); 
					if(!deleted)
						JOptionPane.showMessageDialog(null, "'" + letter + "' is NOT in the tree."
								+ " Failed to delete.");
				break;
				case 2: letter = JOptionPane.showInputDialog("Enter a character").charAt(0); 
					boolean contains = ch.contains(letter); 
					if(contains)
						JOptionPane.showMessageDialog(null, "'" + letter + "' is in the tree.");
					else
						JOptionPane.showMessageDialog(null, "'" + letter + "' is NOT in the tree.");
				break;
				case 3: ch.print(); 
			}
			
			choice = JOptionPane.showOptionDialog
					(null, "What would you like to do? ", "TreeNodes",
					0, 3, null, options, null);
		} 
		
		ch.closeWindow();
	} 
}