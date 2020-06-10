package assignment.two;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import assignment.two.Node;

public class Table {
	private Node root= null;
	private Node node;
	int count;
	/**
	 * Inserts a new Node into the table. If the provided key already exists, no entry will be created. Otherwise, the new entry is
	 * added to the table. Serves as a wrapper for insertNode()/ @param key/ @param value
	 * @return True if the new node was inserted successfully and false if the provided key already exists in the table.
	 */
	public boolean insert(String key, String value) {
		Node node = new Node(key, value);

		//		looks up the key and if it done not already exist, the key and the value are entered as the new node
		if(this.lookUp(key)==null) {
			root = insertNode(root, node);
			return true;
		}
		//		returns false if the key already exists
		else {
			return false;
		}
	}
	/**
	 * Inserts a new node into a binary search tree. If the new node key is not unique, the new node will not be added.
	 * @param parent Root node of the tree/ @param newNode Node to add/ @return Root node of the altered tree
	 */
	private Node insertNode(Node parent, Node newNode) {
		//		checking if the patent node is null
		if(parent==null) {
			parent = newNode;
			return parent;			
		}
		//		searching through the binary tree; if the key is smaller than the parent key, the search traverses toward the left side of the tree
		if((newNode.getKey().compareToIgnoreCase(parent.getKey()))<0) {
			parent.setLeft(insertNode(parent.getLeft(), newNode));
		}
		//		if the key is greater than the parent key, the search traverses toward the right side of the tree
		else if((newNode.getKey().compareToIgnoreCase(parent.getKey()))>0) {
			parent.setRight(insertNode(parent.getRight(), newNode));
		}		
		return parent;
	}
	/**
	 * Looks up the table entry with the provided key.
	 * @param key/ @return The value of the entry with the provided key. Null if no entry with the key can be found.
	 */
	public String lookUp(String key) {
		Node node = this.lookUpNode(root, key);
		if(node==null)return null;
		else return (node.getValue());
	}

	/**
	 * Looks up the node in the binary search tree.
	 * @param parent Root node of the tree/ @param key Key of the node to search for/ @return The Node with the provided key.
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
	 * Deletes the table entry with the given key. @param key/ @return True if the entry was successfully deleted.
	 * False if no entry with the given key was found.
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
	 * @param parent The root of the tree containing the node to delete/ @param key The key of the node to delete
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
			parent.setKey((findLargestNode(parent.getRight())).getKey());
			parent.setValue((findLargestNode(parent.getRight())).getValue());
			// Delete the inorder successor
			parent.setRight(deleteNode(parent.getRight(), parent.getKey()));
			parent.setRight(deleteNode(parent.getRight(), parent.getValue()));
		}
		return parent;
	}

	/**
	 * Finds the largest node of the provided tree
	 * @param parent The root of the tree/ @return The largest node in the provided tree
	 */
	private Node findLargestNode(Node parent) {
		if(parent==null)return null;

		//		traversing through only the left nodes of the node you wish to delete; because we are looking to find the next largest node to the deleting node
		if(parent.getLeft()!=null){
			if ((parent.getKey().compareToIgnoreCase(parent.getLeft().getKey()))>0) {
//				parent.setLeft(findLargestNode(parent.getLeft()));
//				corrected on 4/17/2020:
				parent = findLargestNode(parent.getLeft());
			}
		}

		return parent;
	}

	//	update method to update the address value of the specified key
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
	} //end of update method

	/**
	 * Saves the table to a text file
	 * @param filename Name of the file to contain the table
	 * @throws IOException 
	 */
	public void save(String filePath) throws IOException {

		//		BufferedWriter and FileWriter class to write the data into a file
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));;
		writeNode(writer, root);

		writer.close();
	}
	/**
	 * Writes a tree to a file using pre-order traversal
	 * (parent, left, right)
	 * @param writer Writer to the file
	 * @param node Root node of the tree to write
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
		System.out.println("the total number of items (each item represents a pair of name and address) is "+displayNode(root)+"\n");
		return count;
	}
	/**
	 * Displays all nodes in a (sub)tree using in-order traversal
	 * (left, parent, right)
	 * @param node The root node of the tree to display
	 * @return The number of nodes in the tree
	 */
	private int displayNode(Node node) {
		if(node == null) {
			return count;	
		}
		//		In-order traversal of the binary search tree
		displayNode(node.getLeft());

		count++;
		System.out.println(node.getKey()+"\n"+node.getValue()+"\n");

		displayNode(node.getRight());

		return count;
	}

}
// end of table class