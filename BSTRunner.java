import javax.swing.JOptionPane;

public class BSTRunner 
{
	public static void main(String args[]) 
	{
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
	} 
}