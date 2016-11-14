/*
 * TreePainter.java
 *
 *Steven Moskovchenko
 *Winston Churchill High School
 *December 4, 2003
 *Period 2 APCS, Ms. Steelman
 *
 *
 *A program to draw visual representations of binary trees.
 *
 *
 *	To use:
 *
 *1. Create a new TreePainter first:
 *   TreePainter painter = new TreePainter();
 *
 *
 *2. Give it the tree:
 *   painter.setTree(myRootNode);	
 *
 * Or you can just pass in the root of the tree you
 * what to draw:
 *
 *   TreePainter painter = new TreePainter(myRootNode);
 *
 *
 *3. If you modify the tree, refresh the image:
 *	 painter.repaint();
 *
 *It does this using 4 classes:
 *  A TreePainter class to create the window and work the graphics
 *    A TreeNode class to actually make the tree
 *     (you have to make the tree yourself, of course)
 *    A NodeInfo class to store temporary information about each
 *        node's position and size.
 *    A TreeDrawCanvas class (Extends the Canvas class) to perform
 *        graphical operations
 *
 *
 *    The TreeNode class is in a separate Java file
 *    The other three classes are in this file.
 *
 *
 * * Created on November 25, 2003, 8:52 AM
 *
 *
 * Files that are included:
 *  TreeNode.java - the binary tree node class by Ms. Steelman
 *  TreePainter.java    - this file
 *  TreePainter.form    - a NetBeans file of the GUI for this program
 *  TreeTest.java       - a simple demonstration
 *  TreeTest.mcp        - a CodeWarrior project for this demonstration
 *
 *
 *  The GUI init section of the TreePainter class was made with
 *  Sun's NetBeans. If you want to edit the GUI, it would 
 *  probably be easier to open the files with NetBeans than to
 *  modify it by hand. But hey, whatever you want to do.
 *
 *
 *
 *
 * And of course the obligatory license statement.
 * ("A license a day keeps the lawyers away")
 *
 * You can do whatever you want with this thing as long as you
 * don't charge people money for it. If you change something
 * big, please be nice to people and leave a comment noting
 * the changes. 
 *
 * Made (for the most part) in LINUX. Go open source software.
 *
 *
 *
 * Changelog:
 *
 * -- 1/12/2004
 *     - Added check for node.getValue() == null in case a node
 *	     itself is not null but its value is.
 *	   - Removed some nonsense comments that sometimes caused
 *       CodeWarrior to spit out meaningless errors.
 *
 */


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.ListIterator;


class TreeDrawCanvas extends Canvas
{
        //This is the color of the text in the nodes. 
        public static Color TextColor = new Color(0,0,0);   
        public static Font TextFont = new Font("Tahoma", Font.PLAIN, 18);
        
        //This is the color of the box around the node
        public static Color OutlineColor = new Color(0,0,255);
        
        //This is the color of the lines connecting the nodes
        public static Color LineColor = new Color(0,0,255);
    
        //This is used to store temporary information about the nodes
        //in between traversal and drawing. You don't need to worry about it.
        Vector vec = new Vector();    
        
        //This is used to determine position. Initially I set it to 5 but then
        //I made the program automatically center the tree if it is small enough
        //to fit in the window. You shouldn't have to mess with this value.
        //Besides, it gets set to 5 somewhere else ANYWAY before it's ever used.
        int xOffset=5;


        //This is a refference to the TreePainter object that contains this
        //TreeDrawCanvas.
        public TreePainter thePainter;

        //This constructor is just here... It is never used anyway. :-)
        public TreeDrawCanvas()
        {
            super();
        }
    
        //THIS is the constructor that is used. When the TreePainter
        //creates an instance of TreeDrawCanvas, it passes in a reference
        //to itself so the TreeDrawCanvas knows where to look for
        //scroll bars, etc.
        public TreeDrawCanvas(TreePainter a)
        {
            super();
            //Here you can set the font if you don't like the default one.
            //Two examples are given below. The boxes around the nodes
            //will automatically change with the horizontal size of the font.
            
            // setFont(new java.awt.Font("Lucida Console", 0, 14));
            // --- OR ---
            // setFont(new java.awt.Font("Times New Roman", 0, 14));
    
            
            //I don't know what the middle parameter does. The third
            //parameter is the size of the font.


            //This assigns the painter object so this class knows where to 
            //pull scroll bars and trees from. Just leave it be.
              thePainter= a;
        }

        //This function checks the bounds for the canvas. It looks at the 
        //given coordinates and the positions of the scroll bars, and returns
        //true if the given point will be seen with these settings. The idea
        //is that if you have a HUGE tree, only those objects within the canvas
        //will be painted, and not the ones outside of it. That should
        //speed things up a bit.
        public boolean inBounds(int x, int y)
        {
            if(x-thePainter.getH() < 0)
                return false;
            if(x-thePainter.getH() > getWidth())
                return false;
            if(y-thePainter.getV() < 0)
                return false;
            if(y-thePainter.getV() > getHeight())
                return false;
            
            return true;
        }
      
        
        //This is the main part of the program right here. It goes thru
        //the tree and calculates the position of every node, puts it in
        //the vector, etc. The program builds a vector of nodes before
        //it draws them. This makes life easier later on.
        private void buildTree(TreeNode where)
        {
               //This thing is recursive. To not walk the end of the tree.
            if(where==null)
                return;

            //Inorder traversal. Go figure
            buildTree(where.getLeft());
        
            //Get the string to be drawn
            
            String theData=null;
            
            if(where.getValue()==null)
				theData = "(null)";
			else
				theData = where.getValue().toString();

                    
            //yOffset is the y position of the node. This is the height of
            //that node in the tree, times the height of each node box,
            //plus a little value so that the node doesn't look stuck to
            //the top of the screen.
            int yOffset=thePainter.getDepth(where)*25+10;
            
            //The width of the TEXT in this node, in pixels
            int dataWidth = getGraphics().getFontMetrics().stringWidth(theData);
 
             //Make a new NodeInfo class for this node
            NodeInfo n = new NodeInfo();
        
            n.x=xOffset;    //Store the X and Y positions for this node
            n.y=yOffset;
            n.width = dataWidth;    //Store the width of this node's text
            n.theData = theData;    //Store the text, too. Why not?
            n.theNode = where;        //Store a refference to the node in the tree.
                                //This is used later to determine left/right lines.
            vec.add(n);    //Put it in the vector for later
            
            //Since this is an inorder traversal, nodes will be processed
            //left-to-right in the tree. Ergo, the next node needs to
            //be pushed over. The new position incrased by the width of the
            //node we just added, plus an arbitrary value (15) so the tree
            //doesn't look all jumbled together
            xOffset += dataWidth+15;
       
            //Inorder traversal. Go figure
            buildTree(where.getRight());
        }        
        
        
        //This is the other big function. It takes the vector made
        //by the function buildTree() and actually draws the nodes
        //on the screen.
        private void drawTree(Graphics g)
        {
            //An iterator of the NodeInfo things
            ListIterator itr = vec.listIterator();
          
            //Paint the nodes
            while(itr.hasNext())
            {
                //Pull up the node's info out of the vector
                NodeInfo n = (NodeInfo) itr.next();
                
                //If this node is within the canvas boundries...
                if(inBounds(n.x, n.y))
                {
                    //Set the color
                    g.setColor(TextColor);
                    g.setFont(TextFont);
                    //Draw the text, off down and to the right a bit because
                    //we need to leave a bit of space for the border.
                    
                    //A note on the getH() and getV() things:
                    //These are values of the scroll bars. If the user drags
                    //the scroll bar over, the view in the canvas should be
                    //shifted. This is why I am always subtracting getH() and getV().
                    g.drawString(n.theData, n.x+2-thePainter.getH(), n.y+12-thePainter.getV());
                    g.setColor(OutlineColor);
                    
                    //Draw the rectangular border
                    g.drawRect(n.x-thePainter.getH(), n.y-thePainter.getV(), n.width+4, 14);
                }
            }
            
            
            //Okay. We drew the nodes, and now we need to connect them with lines.
            
            //Start from the beginning again
            itr = vec.listIterator();
            
            while(itr.hasNext())    
            {
                NodeInfo cur = (NodeInfo)itr.next(); //Grab a node thing

                if(thePainter.findParent(cur.theNode)!=null)    
                //If this is the root node, we don't need to draw any lines going up
                {
                    //Make another iterator, just to look for this node's PARENT.
                    ListIterator itr2 = vec.listIterator();
                    
                    NodeInfo foundParent = null;
                    
                    //Find the NodeInfo class that corresponds to the parent of
                    //the node that the current NodeInfo class refers to.
                    while(itr2.hasNext())
                    {
                        NodeInfo search = (NodeInfo)itr2.next();
                        if( search.theNode == thePainter.findParent(cur.theNode))
                            foundParent = search;
                    }
                    
                    //If this node's parent was never added, then there's
                    //something wrong with the tree itself
                    if(foundParent==null)
                    {
                        System.out.println("BAD NODE: "+cur.theData+"\n  Its parent isn't null, but the parent is not in the tree. Bailing out.");
                        thePainter.setStatusComment("Problem with the tree - see output.");
                        return;                            //bail out
                    }
                    

                    //Set the color to the color of lines
                    g.setColor(LineColor);
                        
                    //If this node is a left node, draw a line from the left corner
                    //of its parent
                    if(foundParent.theNode.getLeft() == cur.theNode)
                        g.drawLine(cur.x+(cur.width+4)/2-thePainter.getH(), cur.y-1-thePainter.getV(), foundParent.x-thePainter.getH(), foundParent.y+14-thePainter.getV());
                    else
                    //Else draw the line from the right corner to the parent
                        g.drawLine(cur.x+(cur.width/2)-thePainter.getH(), cur.y-1-thePainter.getV(), foundParent.x+foundParent.width+4-thePainter.getH(), foundParent.y+14-thePainter.getV());
                } else
                {
                    if(thePainter.tree!=cur.theNode)
                    {
                        System.out.println("BAD NODE: "+cur.theData+"\n  The node's parent is null but this isn't the root node. Bailing out.");
                        thePainter.setStatusComment("Problem with tree - see output.");
                        return;
                    }
                }
            }
            //No errors? Okay.
            thePainter.setStatusComment("All done.");
        }
        
        
        //The system calls this function when it wants the canvas painted.
        //The graphics object is given to us. Whee!
        public void paint(Graphics g)
        {
        	//System.out.println("Painting tree: "+thePainter.tree);
            /*
                YES I KNOW this is a cheap way to do it.
             *  Build the array first and then calculate the 
             *  line positioning THAT way.
             *
             *    YES I KNOW you can just go through the nodes and
             *    calculare the offset on the fly. This is better though.    
             * 
             **/
            vec = new Vector();
            xOffset=5;    //Reset the X offset
            
            //Do some checking first
            if(thePainter.tree==null)
            {
                thePainter.setStatusComment("Root is null - no tree to draw.");            
                return;
            }
            
            //Do some more checking on the tree
            /*if(thePainter.tree!= null) // && thePainter.tree.getParent()!=null)
            {
                thePainter.setStatusComment("Problem with tree - see output.");
                System.out.println("Error: The root node's parent isn't null. Bailing out.");
                return;
            }*/
            
            
            buildTree(thePainter.tree); //Construct the vector
            
            //Find the X and Y size of the tree.
            int sizeX = xOffset;
            int sizeY = thePainter.getHeight(thePainter.tree)*25 + 20;
            
            //Go change the values of the scrollbars so that they will
            //either pan the tree correctly, or, if its small enough,
            //put it in the center of the canvas
            thePainter.setScrolls(sizeX, sizeY);
            drawTree(g);                //Draw the nodes
        }
}

    //This class stores some information about the nodes.
    //See the comments in buildTree()
class NodeInfo
{
    public int x;
    public int y;
    public int width;
    public String theData;
    public TreeNode theNode;
}


public class TreePainter extends javax.swing.JFrame
{
    
    int skipCall=0;    //This is to prevent some stupid annoying swing-related recutsion


    /** Creates new form TreePainter */
    public TreePainter() {
        initComponents();
        show();
    }
    
    //This constructor will create a new tree painter and set
    //the tree that it will draw to the node you pass in.
    public TreePainter(TreeNode root) {
        initComponents();
      	tree = root;
        show();
    }
    
    public TreeNode tree;
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        paintingPanel = new javax.swing.JPanel();
        vScroll = new javax.swing.JSlider();
        hScroll = new javax.swing.JSlider();
        gfx = new TreeDrawCanvas(this);

        statusPanel = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        btnRedraw = new javax.swing.JButton();

        setTitle("Tree View");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        paintingPanel.setLayout(new java.awt.BorderLayout());

        vScroll.setOrientation(javax.swing.JSlider.VERTICAL);
        vScroll.setValue(0);
        vScroll.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                vScrollStateChanged(evt);
            }
        });

        paintingPanel.add(vScroll, java.awt.BorderLayout.EAST);

        hScroll.setValue(0);
        hScroll.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                hScrollStateChanged(evt);
            }
        });

        paintingPanel.add(hScroll, java.awt.BorderLayout.SOUTH);

        gfx.setBackground(new java.awt.Color(255,239,213));
        paintingPanel.add(gfx, java.awt.BorderLayout.CENTER);

        getContentPane().add(paintingPanel, java.awt.BorderLayout.CENTER);

        statusPanel.setLayout(new java.awt.BorderLayout());

        lblStatus.setText("Hello.");
        statusPanel.add(lblStatus, java.awt.BorderLayout.CENTER);

        btnRedraw.setText("Update");
        btnRedraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedrawActionPerformed(evt);
            }
        });

        statusPanel.add(btnRedraw, java.awt.BorderLayout.EAST);

        getContentPane().add(statusPanel, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-489)/2, (screenSize.height-387)/2, 489, 387);
    }

    private void btnRedrawActionPerformed(java.awt.event.ActionEvent evt) {
        gfx.repaint();
    }

    private void vScrollStateChanged(javax.swing.event.ChangeEvent evt) {
        if(skipCall==0)
            gfx.repaint();        gfx.repaint();
    }

    private void hScrollStateChanged(javax.swing.event.ChangeEvent evt) {
        if(skipCall==0)
            gfx.repaint();
    }
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {
        System.exit(0);
    }


	//This returns the position of the horizontal scroll bar.
	//If the tree is big enough to fit in the window, the function
	//instead returns the value that needs to be subtracted from
	//the acutal node position so that the tree appears
	//directly in the center of the drawing area.
    public int getH()
    {
        if(hScroll.getMaximum()==1)
            return (gfx.getWidth()-((TreeDrawCanvas)gfx).xOffset)/-2;
        return hScroll.getValue();
    }
    
    //Same as the function above, only for the vertical direction.
    public int getV()
    {
        if(vScroll.getMaximum()==1)
            return (gfx.getHeight()-(getHeight(tree)*25+10))/-2;
        
        return vScroll.getMaximum()-vScroll.getValue();
    }    


	//This is called by the TreeDrawCanvas after an initial
	//traversal of the tree. After determining the dimentions
	//of the tree, this is called to update the maximum values of
	//the horizontal and vertical scroll bars.
	//skipCall is used here to prevent unwanted recursion. The
	//problem is that each time the scroll bars are resized, the
	//frame and the tree are automatically repainted. A repaint
	//of the tree causes the scroll bars to be updated, and they,
	//in turn, force another repaint, and around and around we go.
	//skipCall set to 1 simply tells the TreeDrawCanvas to not do
	//anything, if called.
    public void setScrolls(int x, int y)
    {
        skipCall=1;
        
        if(x<gfx.getWidth())
        {
            hScroll.setMaximum(1);
            hScroll.setValue(0);
        } else
            hScroll.setMaximum(x-gfx.getWidth());
        
        //the 20 compensates for some font height related stuff that I never really unserstand
        if(y+20<gfx.getHeight())
        {
            vScroll.setMaximum(1);
            vScroll.setValue(0);
        } else
            vScroll.setMaximum(y+20-gfx.getHeight());
        skipCall=0;       
    }
    
    
    //This function forces the tree to be repainted.
    //You need to call this if, say, a node was added or removed to
    //update the picture.
    public void repaint()
    {
        gfx.repaint();
    }
    
    
    //This function should be used to set the root of the tree
    //that is to be drawn.
    public void setTree(TreeNode what)
    {
        tree=what;
		gfx.repaint();        
    }
    
    
    //This function is used by TreeDrawCanvas to set the caption
    //at the bottom of the screen about any errors that
    //occured, or to say that no tree exists, etc.
    public void setStatusComment(String what)
    {
        lblStatus.setText(what);
    }
    
    //This finds the depth of the node passed in
    //It is necessary to know how far up or down the node must be drawn
	public int getDepth(TreeNode what)
	{
		int d=0;
		while(what!=null)
		{
			what=findParent(what);
			d++;
		}
		return d;
	}
	
    //This finds the height of the tree
    //It is necessary to know how tall the whole tree will be
	public static int getHeight(TreeNode what)
	{
			if(what==null)
				return 0;

			return Math.max(getHeight(what.getLeft()),  getHeight(what.getRight()))+1;
	}	
    
    
    TreeNode parentSearchTemp = null;
	public TreeNode findParent(TreeNode ofWhat)
	{
		parentSearchTemp = ofWhat;
		return findParentHelper(tree);
	}
	
	public TreeNode findParentHelper(TreeNode subTree)
	{
		if(subTree!=null)
		{
			if(subTree.getLeft()==parentSearchTemp || subTree.getRight()==parentSearchTemp)
			{
				return subTree;
			}
			
			TreeNode lp = findParentHelper(subTree.getLeft());
			TreeNode rp = findParentHelper(subTree.getRight());
			
			if(lp!=null)
				return lp;
			
			if(rp!=null)
				return rp;
		}
		
		return null;
	}


    private javax.swing.JButton btnRedraw;
    private java.awt.Canvas gfx;
    private javax.swing.JSlider hScroll;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel paintingPanel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JSlider vScroll;

    
}
