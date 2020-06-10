package chaininghashtablestructure;

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

	//	head (first) node of the respective linked list in a particular array index
	private Node head;

	//	default constructor-/ to create new reference node for array
	public Node() {
	}

	//	 Parameterized constructor- to create a new Node for linked list
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

	//	@return the first/head Node of each array's (which is first node in the linked list reference)
	public Node getHead() {
		return head;
	}

	//	Set the first/head Node in the linked list reference of each array
	public void setHead(Node head) {
		this.head = head;
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
