package assignment.one;

/** This class implements a linked list of key/value pairs of strings
 * */
public class Table{

//	creating a node reference "mark" as a pointer
	private Node mark=null;

//	 First node in the list
	private Node first=null;

//	insert method to insert a new unique key. it returns false if the key already exists and shows appropriate message
	public boolean insert(String key, String value) {
		Node node = new Node(key, value);
		if(this.lookUp(key)== null) {
			if(this.first==null) {
				this.first = node;	
			} // end of if 2
			else {
				node.setNext(this.first.getNext());
				this.first.setNext(node);
			} // end of else 2
			return true;
		}//end of if 1
		else {
			return false;			
		} //end of else 1
	} //end of insert method

//	lookUp method to look up the key by traversing through the list. Returns null if it does not find the key
	public String lookUp(String key) {
		if(this.markToStart()!=false) {
			do {
				if(this.keyAtMark().equals(key))
					return this.valueAtMark();
				} //end of (inner) if 2
			while(this.advanceMark()!= false) ;

		} //end of if 1
		return null;
	} //end of lookUp method

//	delete method deletes the key. It points the previous item to the item next to key and thus drops/deletes the key item
	public boolean delete(String key) {
		Node prev= null;
		Node temp = null;
		if(this.lookUp(key)!= null) {
			if(this.mark==this.first) {
				this.first = this.first.getNext();
			} //end of if 2
			else {
				temp = this.first;
				while(!(temp.equals(this.mark))) {
					prev = temp;
					temp =temp.getNext();
				} // end of while
			}  //end of else 2
			prev.setNext(mark.getNext());
		} // end of if 1
		return true;

	} //end of delete method

	
//	update method to update the address value of the specified key
	public boolean update(String key, String newValue) {
		if (this.lookUp(key) != null) {
			mark.setValue(newValue);
			return true;
		} // end of if
		else {
			return false;
		} //end of else
	} //end of update method
	
//	markToStart method to make the first node in the list
	public boolean markToStart() {
		if (this.first == null) return false;
		else{
			this.mark = this.first;
			return true;
		} //end of else
	} //end of markToStart method

//	advanceMark method to traverse through the list
	public boolean advanceMark() {
		if(mark.getNext()==null) {
			return false;
		} //end of if
		else {
			this.mark = mark.getNext();
			return true;
		} //end of else
	} //end of advanceMark method

//	KeyAtMark method to return the key that is marked
	public String keyAtMark() {
		return mark.getKey();
	} //end of keyAtMark method

//	ValueAtMark method to return the value that is marked
	public String valueAtMark() {
		return mark.getValue();
	} //end of valueAtMark method

//	displayAll method displays all the items in the list and also returns the total count of items in the list
	public int displayAll() {
		int count = 0;
		if(markToStart()==true) {
			String name = mark.getKey();
			System.out.println("The user name is: "+name);
			String address = mark.getValue();
			System.out.println("The address is: "+address);
			count++;
			while(this.advanceMark() == true) {
				name = mark.getKey();
				System.out.println("The user name is: "+name);
				address = mark.getValue();
				System.out.println("The address is: "+address);
				count++;
			} // end of while
		} //end of if
		System.out.println("The total number of items is: "+count);
		return count;
	} //end of displayAll method

}
