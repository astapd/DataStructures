package assignment.four;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/** This class implements a HashTable utilizing Open Hashing with Chaining and also consists of
 * linked list of key/value pairs of strings, which is used as entries in the hash-array
 * */
public class Table{

	//	creating a node reference "mark" as a pointer
	private Node mark=null;

	//	initializing hash table size
	private int tableSize = 10;

	//	creating hashTable array
	private Node[] hashTable = new Node[tableSize];

	//	creating reference node in the hashtable array indexes using Table Constructor
	public Table() {
		for(int i=0;i<tableSize; i++) {
			hashTable[i] = new Node();
		}
	}

	//	insert method to insert a new unique key. it returns false if the key already exists and shows appropriate message
	public boolean insert(String key, String value) {
		Node node = new Node(key, value);
		int hashIndex = hash(key,tableSize);
		if(this.lookUp(key)== null) {
			//			if the linked list in this particular hash index is empty, then the new node is set as first or head of the index's linked list
			if(hashTable[hashIndex].getHead()==null) {
				hashTable[hashIndex].setHead(node);	
			}
			//			if the head/ first is not null, then the new node(s) is added to the back of the last node in the list
			else {
				Node temp = hashTable[hashIndex].getHead();
				while(temp.getNext()!=null) {
					temp = temp.getNext();
				}
				temp.setNext(node);
			} 
			return true;
		}
		else {
			return false;			
		}
	} 
	//end of insert method

	//	lookUp method to look up the key by traversing through the list of each array. Returns null if it does not find the key
	public String lookUp(String key) {
		//		using hash function to find the hash index to lookup the node with the specified key in
		int hashIndex = hash(key,tableSize);
		//traversing and looking through the appropriate hash index's linked list
		if(this.markToStart(hashTable[hashIndex])!=false) {
			do {
				if(this.keyAtMark().equals(key))
					return this.valueAtMark();
			} 
			while(this.advanceMark()!= false);
		} 
		return null;
	} 
	//end of lookUp method

	//	delete method deletes the node with the specified key. It points the previous item to the item next to key and thus drops/deletes the key item
	public boolean delete(String key) {
		if(this.lookUp(key)!= null) {
			int hashIndex = hash(key,tableSize);

			//			traversing and looking through the appropriate hash index's linked list and deleting the node containing the specified key
			if(this.markToStart(hashTable[hashIndex])!=false) {
				Node temp = mark;
				if(temp.getKey().equals(key)) {
					hashTable[hashIndex].setHead(hashTable[hashIndex].getHead().getNext());
				}
				else {
					while(!(temp.getNext().getKey()).equals(key)) {
						temp = temp.getNext();
					}
					temp.setNext(temp.getNext().getNext());
				}
			}
			return true;
		}
		else {
			System.out.println("No such name in the address book\n");
			return false;
		}
	}
	//end of delete method

	//	update method to update the address value of the specified key
	public boolean update(String key, String newValue) {
		if (this.lookUp(key) != null) {
			int hashIndex = hash(key,tableSize);
			if(markToStart(hashTable[hashIndex])!=false) {
				while(!(mark.getKey()).equals(key)) {
					advanceMark();	
				}
				mark.setValue(newValue);
			}
			return true;
		}
		else {
			return false;
		} 
	} 
	//end of update method

	//	markToStart method to make the first node in the list
	public boolean markToStart( Node node) {
		if((node.getHead())==null) return false;
		else{
			this.mark = node.getHead();
			return true;
		} 
	} 
	//end of markToStart method

	//	advanceMark method to traverse through the list
	public boolean advanceMark() {
		if(mark.getNext()==null) {
			return false;
		}
		else {
			this.mark = mark.getNext();
			return true;
		} 
	} 
	//end of advanceMark method

	//	KeyAtMark method to return the key that is marked
	public String keyAtMark() {
		return mark.getKey();
	} 
	//end of keyAtMark method

	//	ValueAtMark method to return the value that is marked
	public String valueAtMark() {
		return mark.getValue();
	} 
	//end of valueAtMark method

	//	hash method to calculate the hash value using the hash function
	public static int hash(String key, int tableSize){
		int hashVal = 0;
		for ( int i = 0; i < key.length(); i++ )
			hashVal = 37 * hashVal + key.charAt(i);
		hashVal %= tableSize;
		if( hashVal < 0 ) //overflow case
			hashVal += tableSize;
		return hashVal;
	}
	//	end of hash value method

	//	displayAll method displays all the items in the list and returns the total count of pairs of items and also 
	//	the average probe length of items in the list
	//	by incrementing the probeCount each time the array's referenced linked list is probed
	public double displayAll() {
		int entryCount= 0;
		int probeCount = 0;
		double avgProbeLngth = 0;
		for(int i=0;i<tableSize; i++) {
			probeCount++;
			if(markToStart(hashTable[i])==true) {
				System.out.println(mark.getKey()+"\n"+mark.getValue()+"\n");
				entryCount++;

				probeCount++;
				while(this.advanceMark() == true) {
					System.out.println(mark.getKey()+"\n"+mark.getValue()+"\n");
					entryCount++;
					probeCount++;
				}
			}
		}
		System.out.println("The total number of key/value pair entries is "+entryCount);
		avgProbeLngth = (double) probeCount/tableSize;
		System.out.println("And the average probe length is "+avgProbeLngth+"\n");
		return avgProbeLngth;
	}
	//end of displayAll method


	/**
	 * Saves the table to a text file
	 * @param filepath path of the file to contain the table
	 * @throws IOException 
	 */
	public void save(String filePath) throws IOException {
		//		BufferedWriter and FileWriter class to write the data into a file
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		for(int i=0;i<tableSize; i++) {
			if(markToStart(hashTable[i])==true) {
				writer.write(mark.getKey()+"\n"+mark.getValue()+"\n");

				while(this.advanceMark() == true) {
					writer.write(mark.getKey()+"\n"+mark.getValue()+"\n");
				}
			}
		}
		writer.close();
	}
	//	end of save method
}
//end of table class