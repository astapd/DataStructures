package assignment.one;

/**
 * This class is a single entry in a linked list.
 * It stores a key/value pair of strings and a next pointer */

public class Node {

	//	Node key
	private String key;

	//	Node value
	private String value;

	//	Next Node in the list 
	private Node next;

	//	 Creates a new Node
	public Node(String key, String value) {
		this.key = key;
		this.value = value;	
	}

	//	@return The Node key
	public String getKey() {
		return key;
	}

	//	 Set the Node key
	public void setKey(String key) {
		this.key = key;
	}

	//	@return The Node value
	public String getValue() {
		return value;
	}
	//	Set the Node value
	public void setValue(String value) {
		this.value = value;
	}

	//	@return The next Node in the list
	public Node getNext() {
		return next;
	}

	//	 Set the next Node in the list
	public void setNext(Node next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return value;
	}

}
