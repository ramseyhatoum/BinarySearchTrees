public class TreeNode <T extends Comparable<T>>
{
	private T value;
	private TreeNode<T> left;
	private TreeNode<T> right;
	
	public TreeNode(T initValue)
	{ value = initValue; left = null; right = null; }
	
	public TreeNode(T initValue, TreeNode<T> initLeft, TreeNode<T> initRight)
	{ value = initValue; left = initLeft; right = initRight; }
	
	public T getValue() { return value; }
	public TreeNode<T> getLeft() { return left; }
	public TreeNode<T> getRight() { return right; }
	
	public void setValue(T theNewValue)  { value = theNewValue; }
	public void setLeft(TreeNode<T> theNewLeft)  { left = theNewLeft; }
	public void setRight(TreeNode<T> theNewRight)  { right = theNewRight; }	
	
}