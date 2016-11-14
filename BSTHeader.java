import javax.swing.JOptionPane;

/********************************************************************
ASSIGNMENT:  Write a binary search tree class. 

Your class should have the following:
	- two instance attributes - the root and an instance of TreePainter. 
	- a default constructor that sets root to null, creates an instance
	of TreePainter and sets the location for the TreePainter window
	to 0,0.

Your class should have public methods that do the following:
	adds - takes a Comparable, adds it to the tree and returns nothing.
	delete - takes a Comparable, deletes the first instance it finds and
	 		returns true (for deleted) or false (for not found) 
	print - prints the pre, in & postorder traversals, LABELED and in
	 		ONE JOP window AND updates the tree in TreePainter.
	 		The TreePainter window should start at 0,0. 
	contains - takes a Comparable and returns true if the value is
			in the tree, false otherwise.
	closeWindow - disposes of the TreePainter window.
	
Your class should have 6 private methods that do the following:
	the recursive pre, in & postorder traversals
	delete no children, delete 1 child, delete 2 children

Write a BSTRunner file that does the following:
	asks the user to input a string
	creates an instance of your BST class using the characters
		in the input string
	call your print method 
	prints a menu in a JOptionPane that lets the user choose to
		add, delete, search, print or exit.  
		Add & delete should ask the user what character to add or
			delete.  The updated tree should show up in the
			TreePainter window.  
		Print should print the pre, in & postorder traversals
		Contains should print a JOP window with the value and whether
		 	or not the value is stored in the tree.
	Your program should continue until the user chooses exit.

********************************************************************/
public class BSTHeader<T extends Comparable<T>> 
{
	/* Instance Attributes */
	private TreeNode<T> root;
	private TreePainter tp;
	
	/* Public Methods */	
	public BSTHeader() 
	{
		root = null;	
		tp = new TreePainter();
		tp.setLocation(0,0);
	}
	
	public void add(T value) 
	{
		if(root == null) {
			root = new TreeNode<T>(value);
		} 
		else {
			TreeNode<T> p = null, q = root;
			while(q != null) 
			{
				p = q;
				if(value.compareTo(p.getValue()) < 0) 
					q = p.getLeft();
				else
					q = p.getRight();
			}
			if (value.compareTo(p.getValue()) < 0)
				p.setLeft(new TreeNode<T>(value));
			else 
				p.setRight(new TreeNode<T>(value));
		}
	}
	
	public boolean delete(T value) 
	{
		TreeNode<T> back = root, trav = root;

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
				if (value.compareTo(back.getValue()) < 0)
					trav = back.getLeft();
				else
					trav = back.getRight();
			}
		}		
		if (value.compareTo(back.getValue()) < 0)
			trav = back.getLeft();
		else
			trav = back.getRight();	
		
		return false;
	}
	
	public void print() 
	{		
		tp.setTree(root);
		
		String answer = "";
		answer += "pre: " + preorder(root) + " \n";
		answer += "in: " + inorder(root) + " \n";
		answer += "post: " + postorder(root);
		
		JOptionPane.showMessageDialog(null, answer);	
	}
	
	
	public boolean contains(T value) 
	{
		TreeNode<T> p = root;
		while(p != null && value.compareTo(p.getValue()) != 0) {
			if(value.compareTo(p.getValue()) < 0) 
				p = p.getLeft();
			else
				p = p.getRight();
		}	
		if(p != null) 
			return true;
		else
			return false;
	}
	
	public void closeWindow() 
	{
		tp.dispose();
	}
	
	/* Private Methods */	
	private String preorder(TreeNode<T> root)
	{
		return doPreorder(root);
	}
	private String doPreorder(TreeNode<T> t) {
		String pre = "";
		if (t != null)
		{
		   return(pre += t.getValue() +
			doPreorder(t.getLeft()) +
			doPreorder(t.getRight()) );
		}
		return ""; 
	}
	
	private String postorder(TreeNode<T> root)
	{ 
		return doPostorder(root); 
	}
	private String doPostorder(TreeNode<T> t)				
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
	
	private String inorder(TreeNode<T> root)
	{ 
		return doInorder(root); 
	}

	private String doInorder(TreeNode<T> t)				
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
	
	private void deleteZeroChildren(TreeNode<T> back, TreeNode<T> trav) {	
		if(back.getLeft() == trav)
			back.setLeft(null);
		else
			back.setRight(null);
	}
	
	private void deleteOneChild(TreeNode<T> back, TreeNode<T> trav) 
	{	
		if(back.getRight() != null) 
			back.setRight(trav.getRight());
		else 
			back.setRight(trav.getLeft());
	}
	
	private void deleteTwoChildren(TreeNode<T> back, TreeNode<T> trav) {
		TreeNode<T> b2 = trav, t2 = trav;

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
