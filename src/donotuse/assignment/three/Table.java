package donotuse.assignment.three;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** This class implements an AVL tree of key/value pairs **/
public class Table {
	/** Root node in the tree */
	public Node root;
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
			Node n = root;
			insertNode(n, node);
			return true;					
		}
		//		returns false if the key already exists
		else {
			return false;
		}
	}
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
			//			root.setParent(null);
			updateAVL(root);
			return root;
		}
		//		searching through the binary tree; if the key is smaller than the parent key, the search traverses toward the left side of the tree
		if((newNode.getKey().compareToIgnoreCase(root.getKey()))<0) {
			root.setLeft(insertNode(root.getLeft(), newNode));
			root.getLeft().setParent(root);
		}
		//		if the key is greater than the parent key, the search traverses toward the right side of the tree
		else if((newNode.getKey().compareToIgnoreCase(root.getKey()))>0) {
			root.setRight(insertNode(root.getRight(), newNode));
			root.getRight().setParent(root);
		}
		//			updates and re-balances the AVL tree (if not-in-balance)
		updateAVL(root);
		if(root.getBalance()==2 || root.getBalance()==-2) {
			root = rebalance(root);
		}
		return root;
	}

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
	/**
	 * Deletes the table entry with the given key.
	 * @param key
	 * @return True if the entry was successfully deleted. False if
	 * no entry with the given key was found.
	 */
	public boolean delete(String key) {
		if(this.lookUp(key)!=null) {
			root= deleteNode(root, key);
			return true;
		}
		else {
			System.out.println("No such item found in the address book.\n");
			return false;
		}
	}
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
			rebalance(parent);
		}
		return parent;
	}
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
				parent.setLeft(findNextLargestNode(parent.getLeft()));
			}
		}

		return parent;
	}
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
	/**
	 * Saves the table to a text file
	 * @param filename Name of the file to contain the table
	 * @throws IOException 
	 */
	public void save(String filePath) throws IOException {
		//			BufferedWriter and FileWriter class to write the data into a file
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));;
		writeNode(writer, root);
		writer.close();
	}
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

		count++;
		System.out.println(node.getKey()+"\n"+node.getValue());
		System.out.println("Balance factor = "+node.getBalance()+"\nHeight =  "+node.getHeight()+"\n");

		displayNode(node.getRight());

		return count;
	}
	/**
	 * Re-balances an AVL tree at the provided node.
	 * Note that the height and balance factor of the node
	 * will also be updated.
	 * @param n - Root of the (sub)tree to balance
	 * @return The root of the newly balanced (sub)tree
	 */
	private Node rebalance(Node n) {
		//			Imbalance on left side;
		if (n.getBalance()== -2) {

			//			if((n.getLeft().getLeft().getHeight())>= (n.getLeft().getRight().getHeight()))
			//					return rotateRight(n);
			//			else return rotateLeftThenRight(n);

			if(n.getLeft().getBalance()== -1) {
				n= rotateLeft(n);
			}
			else if (n.getLeft().getBalance() == 1){
				n= rotateLeftThenRight(n);
			}				
		}
		//			Imbalance on right side;
		else if(n.getBalance()==2) {

			//			if ((n.getRight().getRight().getHeight())>= (n.getRight().getLeft().getHeight()))
			//				return rotateLeft(n);
			//			else rotateRightThenLeft(n);

			if(n.getRight().getBalance()== -1) {
				n= rotateRightThenLeft(n);
			}
			else if (n.getRight().getBalance()== 1) {
				n= rotateRight(n);
			}
		}
		if(n.getParent()!=null) {
			rebalance(n.getParent());
		}
		else {
			this.root = n;
		}
		return n;
	}

	/**
	 * Performs a left-left single rotation for an
	 * unbalanced AVL tree.
	 * @param root - The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateLeft(Node n) {
		Node temp = n.getLeft();
		n.setLeft(temp.getRight());		
		temp.setRight(n);
		temp.setParent(n.getParent());
		n.setParent(temp);
		if(temp.getParent()!=null)	{
			if(temp.getParent().getRight().equals(n)) {
				temp.getParent().setRight(temp);
			}
			else {
				temp.getParent().setLeft(temp);
			}
		}
		updateAVL(n);
		updateAVL(temp);
		return temp;
	}
	/**
	 * Performs a right-right single rotation for an
	 * unbalanced AVL tree.
	 * @param root - The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateRight(Node n) {
		Node temp = n.getRight();
		n.setRight(temp.getLeft());
		temp.setLeft(n);
		temp.setParent(n.getParent());
		n.setParent(temp);
		if(temp.getParent()!=null)	{
			if(temp.getParent().getRight().equals(n)) {
				temp.getParent().setRight(temp);
			}
			else {
				temp.getParent().setLeft(temp);
			}
		}
		updateAVL(n);
		updateAVL(temp);
		return temp;
	}
	/**
	 * Performs a left-right double rotation for an
	 * unbalanced AVL tree.
	 * @param root The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateLeftThenRight(Node n) {
		n.setLeft(rotateLeft(n.getLeft()));
		return rotateRight(n);

	}
	/** Performs a right-left double rotation for an
	 * unbalanced AVL tree.
	 * @param root - The root of the unbalanced tree
	 * @return The root of the newly balanced tree
	 */
	private Node rotateRightThenLeft(Node n) {
		n.setRight(rotateRight(n.getRight()));
		return rotateLeft(n);
	}
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
		else if(n.getRight() == null) {
			n.setBalance(-(n.getHeight()));
		}
		else if(n.getLeft() == null) {
			n.setBalance(n.getHeight());
		}
		else {
			n.setBalance((n.getRight().getHeight())-(n.getLeft().getHeight()));
		}
	}
}
