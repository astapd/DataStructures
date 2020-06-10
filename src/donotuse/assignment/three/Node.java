package donotuse.assignment.three;

/**
 * This class is a single entry in an AVL tree.
 * It stores a key/value pair of strings, as well as
 * references to the left and right child Nodes within the tree.
 */
public class Node implements Comparable<Node> {
	/* Node key and value*/
	private String key, value;
	/* Child Nodes in the tree */
	private Node left, right, parent;
	/* Node balance and height */
	private int balance, height;
	/** Creates a new Node.
	 * @param key
	 * @param value
	 */
	public Node(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return The Node parent
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param parent – Set the Node parent
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * @return The Node key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key – Set the Node key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return The Node value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value – Set the Node value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return The left child Node
	 */
	public Node getLeft() {
		return left;
	}
	/**
	 * @param left – Set the left child Node
	 */
	public void setLeft(Node left) {
		this.left = left;
	}
	/**
	 * @return The right child Node
	 */
	public Node getRight() {
		return right;
	}
	/**
	 * @param right – Set the right child Node
	 */
	public void setRight(Node right) {
		this.right = right;
	}
	/**
	 * @return The balance of this node in the AVL tree.
	 * (Left subtree height - Right subtree height)
	 */
	public int getBalance() {
		return balance;
	}
	/**
	 * @param balance - Set the balance of this node in the AVL tree
	 * (Left subtree height - Right subtree height)
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	/**
	 * @return The height of this node in the AVL tree
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height – Set the height of this node in the AVL tree
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return String.format("%s%n%s%n", this.key, this.value);
	}

	@Override
	public int compareTo(Node that) {
		return this.key.compareTo(that.key);
	}
}