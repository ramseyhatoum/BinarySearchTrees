import java.awt.Color;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/********************************************************************
Written by:

ASSIGNMENT:  Write a program that does the following:
	- uses your changeJOP method to change the colors & fonts
		to something I can read.
	- asks the user for a string.
	- has a fill method that creates a binary search tree from the
	 	letters in the string. Print the tree using TreePainter.
	- has a print method that calls recursive methods to print the tree 
		in pre, in and postorder (labeled).
	- has a delete method that asks the user which letter to delete and
	 	then deletes the letter if possible. Print a message saying weather 
		or not it was deleted. Print the tree again using TreePainter.
		
HAND IN:  Put a jar file in my handin folder and print.

********************************************************************/
public class BSTPractice {
	public static void changeJOP()
	{
		// The font of the message text
		UIManager.put("Label.font", new FontUIResource 
				(new Font("Calibri", Font.BOLD, 58)));
		// The color of the message text
		UIManager.put("OptionPane.messageForeground",new Color(219,219,219));

		// color for text field (where you are inputting data)
		UIManager.put("TextField.background", new Color(219,219,219));
		// font for message in text field
		UIManager.put("TextField.font", new FontUIResource(new Font
				("Calibri", Font.BOLD, 24)));
		// color for message in text field
		UIManager.put("TextField.foreground", Color.red);

		// The color of the panel 
		UIManager.put("Panel.background",new Color(112,184,71));
		// The color around the outside of the panel
		UIManager.put("OptionPane.background", new Color(219,219,219));

		// Buttons at bottom
		UIManager.put("Button.background", new Color(112,184,71));
		UIManager.put("Button.foreground", Color.black);
		UIManager.put("Button.font", new FontUIResource	(new Font
				("Calibri", Font.BOLD, 14))); 
	}
	public static void main(String args[]) 
	{
		changeJOP();
		String str = JOptionPane.showInputDialog("Enter a string: ");
		TreeNode<Character> root = new TreeNode<Character>(str.charAt(0));
				
		if(str.isEmpty()) {
			root = null;
		}
		else {
			for(int i = 1; i < str.length(); i++) {
				char item = str.charAt(i);
				fill(root, item);
			}
		
			TreePainter tp = new TreePainter();
			tp.setLocation(0,0);
			tp.setTree(root);
			print(root);
			
			char ch = JOptionPane.showInputDialog("Enter a  character to delete: ").charAt(0);
			TreePainter tp2 = new TreePainter();
			
			while(ch != 0) {
				tp.dispose();			
									
				if(ch == 'l') {
					while(delete(root, ch))
						delete(root, ch);
				}
				else {
					delete(root, ch);
				}
				
				tp2.setLocation(0,0);
				tp2.setTree(root);	

				ch = JOptionPane.showInputDialog("Enter a  character to delete: ").charAt(0);
			}
		}
		
	}
	public static void fill(TreeNode<Character> root, char item) 
	{
		TreeNode<Character> p = null, q = root;

		while (q != null)
		{
			p = q;
			if (item < p.getValue())
				q = p.getLeft();
			else
				q = p.getRight();
		}
		if (item < p.getValue())
			p.setLeft(new TreeNode<Character>(item));
		else
			p.setRight(new TreeNode<Character>(item));
	}
	
	public static void print(TreeNode<Character> root) 
	{		
		String answer = "";
		
		answer += "pre: " + preorder(root) + " \n";
		answer += "in: " + inorder(root) + " \n";
		answer += "post: " + postorder(root);
		
		JOptionPane.showMessageDialog(null, answer);	
	}
	
	public static String preorder(TreeNode<Character> root)
	{
		return doPreorder(root);
	}
	public static String doPreorder(TreeNode<Character> t) {
		String pre = " ";
		if (t != null)
		{
		   return(pre += t.getValue() +
			doPreorder(t.getLeft()) +
			doPreorder(t.getRight()) );
		}
		return ""; 
	}
	
	public static String postorder(TreeNode<Character> root)
	{ 
		return doPostorder(root); 
	}

	private static String doPostorder(TreeNode<Character> t)				
	{
		String post = "";
		if (t != null)
		{
			return(post += doPostorder(t.getLeft()) +
			doPostorder(t.getRight()) +
			t.getValue());
		}
		return ""; 
	}
	
	public static String inorder(TreeNode<Character> root)
	{ 
		return doInorder(root); 
	}

	private static String doInorder(TreeNode<Character> t)				
	{
		String in = "";
		if (t != null)
		{
			return(in += doInorder(t.getLeft()) +
			t.getValue() +
			doInorder(t.getRight()));
		}
		return ""; 
	}
	
	public static boolean delete(TreeNode<Character> root, char value) 
	{
		TreeNode<Character> back = root, trav = root;

		while (trav != null)
		{
			if(value == trav.getValue()) {
				if(trav.getLeft() == null && trav.getRight() == null) {
					deleteZeroChildren(back, trav);
					trav = null;
					return true;
				}
				else if((trav.getLeft() == null && trav.getRight() != null)
						|| trav.getRight() == null && trav.getLeft() != null) {
					deleteOneChild(back, trav);
					trav = null;
					return true;
				}
				else {
					deleteTwoChildren(back, trav);
					trav = null;
					return true;
				}
			}
			else {
				back = trav;
				if (value < back.getValue())
					trav = back.getLeft();
				else
					trav = back.getRight();
			}
		}		
		if (value < back.getValue())
			trav = back.getLeft();
		else
			trav = back.getRight();	
		
		return false;
	}
	
	private static void deleteZeroChildren(TreeNode<Character> back, TreeNode<Character> trav) {	
		if(back.getLeft() == trav)
			back.setLeft(null);
		else
			back.setRight(null);
	}
	
	private static void deleteOneChild(TreeNode<Character> back, TreeNode<Character> trav) 
	{	
		if(back.getRight() != null) 
			back.setRight(trav.getRight());
		else 
			back.setRight(trav.getLeft());
	}
	
	private static void deleteTwoChildren(TreeNode<Character> back, TreeNode<Character> trav) {
		TreeNode<Character> b2 = trav, t2 = trav;

		if (trav.getLeft() != null && trav.getRight() != null) {
			b2 = trav.getRight();
			if(b2.getLeft() != null) 
				t2 = b2.getLeft();
			else
				t2 = b2.getRight();	
		}
					
		trav.setValue(t2.getValue());
		
		if(b2.getLeft() == t2 && b2.getLeft() != null) 
			b2.setLeft(t2.getRight());
		else if(b2.getLeft() == t2) 
			b2.setLeft(null);
		else 
			b2.setRight(null);
	}
	
}