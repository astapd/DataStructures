package assignment.three;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import donotuse.assignment.three.Node;

/** This class implements an AVL tree of key/value pairs **/
public class Table {

	/** Root node in the tree */
	public Node root;
	// declaring count variable
	private int count;

	/**
	 * Inserts a new Node into the table. If the provided key already
	 * exists, no entry will be created. Otherwise, the new entry is
	 * added to the table.
	 * @param key
	 * @param value
	 * @return True if the new node was inserted successfully.
	 * False if the provided key already exists in the table.
	 */
	public boolean insert(String key, String value) {
		Node node = new Node(key, value);
		if(root==null) {
			root = insertNode(root, node);
			return true;
		}
		//		looks up the key and if it done not already exist, the key and the value are entered as the new node
		else if(this.lookUp(key)==null) {
			root = insertNode(root, node);
			return true;					
		}
		//		returns false if the key already exists
		else {
			return false;
		}
	}
	//	end of insert method

	/**
	 * Inserts a new node into an AVL tree.
	 * Note that if the new node key is not unique, the
	 * new node will not be added.
	 * @param root Root node of the tree
	 * @param newNode Node to add
	 * @return Root node of the altered tree
	 */
	private Node insertNode(Node root, Node newNode) {
		//			checking if the root node is null
		if(root==null) {
			root = newNode;
			updateAVL(root);
			return root;
		}
		//		searching through the binary tree; if the key is smaller than the parent key, the search traverses toward the left side of the tree
		if((newNode.getKey().compareToIgnoreCase(root.getKey()))<0) {
			root.setLeft(insertNode(root.getLeft(), newNode));
		}
		//		if the key is greater than the parent key, the search traverses toward the right side of the tree
		else if((newNode.getKey().compareToIgnoreCase(root.getKey()))>0) {
			root.setRight(insertNode(root.getRight(), newNode));
		}
		//			updates and re-balances the AVL tree (if not-in-balance)
		updateAVL(root);
		if(root.getBalance()>=2 || root.getBalance()<=-2) {
			root = rebalance(root);
		}
		return root;
	}
	//	end of insertNode method

	/**
	 * Looks up the table entry with the provided key.
	 * @param key
	 * @return The value of the entry with the provided key. Null if
	 * no entry with the key can be found.
	 */
	public String lookUp(String key) {
		Node node = this.lookUpNode(root, key);
		if(node==null)return null;
		else return (node.getValue());
	}
	//	end of lookUp method

	/**
	 * Looks up the node in the AVL tree.
	 * @param parent - Root node of the tree
	 * @param key - Key of the node to search for
	 * @return The Node with the provided key.
	 * Null if no entry with the key can be found.
	 */
	private Node lookUpNode(Node parent, String key) {
		if(parent==null) return null;
		//		recursively finding the node that has the specified key; traversing left or right as appropriate
		while(parent!= null) {
			if(parent.getKey().equalsIgnoreCase(key)) {
				return parent;
			}
			else if((parent.getKey().compareToIgnoreCase(key))<0) {
				return lookUpNode(parent.getRight(), key);
			}
			else return lookUpNode(parent.getLeft(), key);
		}
		return null;
	}
	//	end of lookUpNode method

	/**
	 * Deletes the table entry with the given key.
	 * @param key
	 * @return True if the entry was successfully deleted. False if
	 * no entry with the given key was found.
	 */
	public boolean delete(String key) {
		//		looks up the node and deletes it if found
		if(this.lookUp(key)!=null) {
			root= deleteNode(root, key);
			return true;
		}
		//		if the node with the key is not found, then a statement saying such is printed out
		else {
			System.out.println("No such item found in the address book.\n");
			return false;
		}
	}
	//	end of delete method

	/**
	 * Deletes the node with the provided key from the given tree
	 * @param root - The root of the tree containing the node to delete
	 * @param key - The key of the node to delete
	 * @return The root node of the altered tree.
	 */
	private Node deleteNode(Node parent, String key) {

		if (parent == null) return parent;

		//		recursively finding the node that has the specified key; traversing left or right as appropriate
		if ((parent.getKey().compareToIgnoreCase(key))<0) {
			parent.setRight(deleteNode(parent.getRight(), key));
		}
		else if ((parent.getKey().compareToIgnoreCase(key))>0) {
			parent.setLeft(deleteNode(parent.getLeft(), key));
		}
		else{
			// node with only one child or no child
			if (parent.getLeft()== null)
				return parent.getRight();
			else if (parent.getRight() == null)
				return parent.getLeft();

			// node with two children: Get the inorder successor (smallest in the right subtree)
			parent.setKey((findNextLargestNode(parent.getRight())).getKey());
			parent.setValue((findNextLargestNode(parent.getRight())).getValue());

			// Delete the inorder successor
			parent.setRight(deleteNode(parent.getRight(), parent.getKey()));
			parent.setRight(deleteNode(parent.getRight(), parent.getValue()));
		}
		updateAVL(parent);
		if(parent.getBalance()>=2 || parent.getBalance()<=-2) {
			parent = rebalance(parent);
		}
		return parent;
	}
	//	end of deleteNode method
	/**
	 * Finds the largest node of the provided tree
	 * @param parent - The root of the tree
	 * @return The largest node in the provided tree
	 */
	private Node findNextLargestNode(Node parent) {
		if(parent==null)return null;

		//		traversing through only the left nodes of the node you wish to delete; because we are looking to find the next largest node to the deleting node
		if(parent.getLeft()!=null){
			if ((parent.getKey().compareToIgnoreCase(parent.getLeft().getKey()))>0) {
				parent = (findNextLargestNode(parent.getLeft()));
			}
		}
		return parent;
	}
	//	 end of findNextLargestNode method

	/**
	 * Replaces the old value associated with the given key
	 * with the newValue string.
	 * @param key
	 * @param newValue
	 * @return True if the node value was updated successfully.
	 * False if the provided key was not found.
	 */
	public boolean update(String key, String newValue) {
		//		looks up the key and returns the node of the key
		if (this.lookUp(key) != null) {
			Node node= lookUpNode(root, key);
			//			updating the value of that particular node as the newValue
			node.setValue(newValue);
			return true;
		} // end of if
		else {
			return false;
		} //end of else
	}
	//	end of update method

	/**
	 * Saves the table to a text file
	 * @param filename Name of the file to contain the table
	 * @throws IOException 
	 */
	public void save(String filePath) throws IOException {
		//			BufferedWriter and FileWriter class to write the data into a file
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		writeNode(writer, root);
		writer.close();
	}
	//	end of save method

	/**
	 * Writes a tree to a file using pre-order traversal
	 * (parent, left, right)
	 * @param writer Writer to the file
	 * @param node - Root node of the tree to write
	 * @throws IOException
	 */
	private void writeNode(BufferedWriter writer, Node node) throws IOException {
		if(node==null) {
			return;}
		//		writing the node (key and value) in the specified file
		writer.write(node.getKey()+"\n"+node.getValue()+"\n");
		writeNode(writer, node.getLeft());
		writeNode(writer, node.getRight());
	}
	//	end of writeNode method

	/**
	 * Displays all nodes in the table.
	 * @return The number of nodes in the table.
	 */
	public int displayAll() {
		int count = 0;
		this.count=count;
		System.out.println("The total number of items (each item represents a pair of name and address) is "+displayNode(root)+"\n");
		return count;
	}
	//	end of displayAll method

	/**
	 * Displays all nodes in a (sub)tree using in-order traversal
	 * (left, parent, right)
	 * @param node - The root node of the tree to display
	 * @return The number of nodes in the tree
	 */
	private int displayNode(Node node) {

		if(node == null) {
			return count;
		}
		//		In-order traversal of the binary search tree
		displayNode(node.getLeft());

		//		incrementing count, printing the key and value and also the height and the balance factor of the node.
		count++;
		System.out.println(node.getKey()+"\n"+node.getValue());
		System.out.println("Balance factor = "+node.getBalance()+"\nHeight =  "+node.getHeight()+"\n");

		displayNode(node.getRight());

		return count;
	}
	//	end of displayNode method

	/**
	 * Re-balances an AVL tree at the provided node.
	 * Note that the height and balance factor of the node
	 * will also be updated.
	 * @param n - Root of the (sub)tree to balance
	 * @return The root of the newly balanced (sub)tree
	 */
	private Node rebalance(Node n) {
		if(n==null) {
			return null;
		}
		//			Imbalance on left side;
		if (n.getBalance()<= -2) {
			if(n.getLeft().getBalance()<= -1 || n.getLeft().getBalance() == 0) {
				n= rotateLeft(n);
			}
			else if (n.getLeft().getBalance() >= 1){
				n= rotateLeftThenRight(n);
			}				
		}
		//			Imbalance on right side;
		else if(n.getBalance()>=2) {
			if(n.getRight().getBalance()<= -1) {
				n= rotateRightThenLeft(n);
			}
			else if (n.getRight().getBalance()>= 1 || n.getRight().getBalance()== 0) {
				n= rotateRight(n);
			}
		}
		return n;
	}
	//	end of rebalance method

	/**
	 * Performs a left-left single rotation for an
	 * unbalanced AVL tree.
	 * @param root - The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateLeft(Node n) {
		Node temp = null;
		//		using the if block code if node n has a left node
		if(n.getLeft()!=null) {
			temp = n.getLeft();
			n.setLeft(temp.getRight());		
			temp.setRight(n);
		}
		//		if n node doesn't have a left node, the else block of code will be executed. Both if and else do the same thing i.e. rotate left
		else {
			temp = n.getRight();
			n.setRight(temp.getLeft());		
			temp.setLeft(n);
		}
		updateAVL(n);
		updateAVL(temp);
		return temp;
	}
	//	end of rotateLeft method

	/**
	 * Performs a right-right single rotation for an
	 * unbalanced AVL tree.
	 * @param root - The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateRight(Node n) {
		Node temp = null;
		//		using the if block code if node n has a right node
		if(n.getRight()!=null) {
			temp = n.getRight();
			n.setRight(temp.getLeft());
			temp.setLeft(n);
		}
		//		if n node does n't have a right node, the else block of code will be executed. Both if and else do the same thing i.e. rotate right
		else {
			temp = n.getLeft();
			n.setLeft(temp.getRight());
			temp.setRight(n);
		}
		updateAVL(n);
		updateAVL(temp);
		return temp;
	}
	//	end of rotateRight method

	/**
	 * Performs a left-right double rotation for an
	 * unbalanced AVL tree.
	 * @param root The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateLeftThenRight(Node n) {
		//		rotating n.left left and setting the rotated node as n.left
		n.setLeft(rotateLeft(n.getLeft()));
		//		then rotating node n right
		return rotateRight(n);
	}
	//	end of rotateLeftThenRight method

	/** Performs a right-left double rotation for an
	 * unbalanced AVL tree.
	 * @param root - The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateRightThenLeft(Node n) {
		//		rotating n.right right and setting the rotated node as n.right
		n.setRight(rotateRight(n.getRight()));
		//		then rotating node n left
		return rotateLeft(n);
	}
	//	end of rotateRightThenLeft method

	/**
	 * Updates the height and balance factor of a node.
	 * Note that this method assumes all child nodes
	 * have up-to-date height and balance factors.
	 */
	private void updateAVL(Node n) {
		if(n==null) {
			n.setHeight(-1);
		}
		//			First updating the height of the node;
		//			if node is a leaf;
		if(n.getLeft() == null && n.getRight() == null) {
			n.setHeight(0);
		}
		//			if there is no left child;
		else if (n.getLeft()==null) {
			n.setHeight(n.getRight().getHeight() + 1);
		}
		//			no right child
		else if(n.getRight()==null) {
			n.setHeight(n.getLeft().getHeight() + 1);
		}
		//			has both child
		else {
			if((n.getRight().getHeight()) > (n.getLeft().getHeight()) ) {
				n.setHeight((n.getRight().getHeight()) + 1);
			}
			else {
				n.setHeight((n.getLeft().getHeight()) + 1);
			}				
		}

		//			setting the balance factor
		if(n.getRight() == null && n.getLeft() == null) {
			n.setBalance(0);
		}
		//		no right child
		else if(n.getRight() == null) {
			n.setBalance(-(n.getHeight()));
		}
		//		if there is no left child;
		else if(n.getLeft() == null) {
			n.setBalance(n.getHeight());
		}
		//		has both child
		else {
			n.setBalance((n.getRight().getHeight())-(n.getLeft().getHeight()));
		}
	}
	//	end of updateAVL method
}
// end of TableClass