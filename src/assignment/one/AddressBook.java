package assignment.one;

import java.util.Scanner;

public class AddressBook{

	public static void main(String[] args) {

//		creating a variable of table class and instantiating the table class
		Table table = new Table();

//		declaring variables for name, value and new address
		String key = null;
		String value = null;
		String newValue = null;
		
//		Instruction statements for what the user wants to do
		System.out.println("Enter what you wish to do in the list:");
		System.out.println("Add a name (n)");
		System.out.println("Look up a name (l)");
		System.out.println("Update address name (u)");
		System.out.println("delete an entry (d)");
		System.out.println("Display all entries (a)");
		System.out.println("Quit (q)");

//		creating and instantiating scanner class
		Scanner sc = new Scanner(System.in);
		
//		calling for input in console
		String operator = sc.nextLine();

//		while loop-- so that the user may continue until he/she hits q, for quit
		while(!(operator.equals("q") )) {
			
//			switch cases to navigate to the correct command
			switch(operator){
			
//			n- for adding a name
			case "n":
				System.out.println("Enter the name to be added");
				key = sc.nextLine();
				if(table.lookUp(key)==null) {
					System.out.println("Enter the address");
					value = sc.nextLine();
					table.insert(key, value);
					}
				else {
					System.out.println("The name already exists in the address book");
				}
				break;
				
//				l- for looking up a name
			case "l":
				System.out.println("Enter the name to look up");
				key = sc.nextLine();
				table.lookUp(key);
				System.out.println("the name is "+table.keyAtMark()+" and the address is "+table.valueAtMark());
				break;
				
//				u- for updating the address of a name
			case "u":
				System.out.println("Enter the name whose address you intend to update");
				key = sc.nextLine();
				System.out.println("Enter the new address you want it updated to");
				newValue = sc.nextLine();
				table.update(key, newValue);
				break;
				
//				d- for deleting a name/item form the list
			case "d":
				System.out.println("Enter the name to delete");
				key = sc.nextLine();
				table.delete(key);
				break;
				
//				a- for displaying all the items and giving us the total number of items
			case "a":
				table.displayAll();
				break;
				
//				default in case the user chooses an invalid instruction
			default:
				System.out.println("Option not valid, choose a valid option for command\n");
			}
			
//			repeating the instruction menu
			System.out.println("\nEnter what you wish to do next in the list: ");
			System.out.println("Add a name (n");
			System.out.println("Look up a name (l)");
			System.out.println("Update address name (u)");
			System.out.println("delete an entry (d)");
			System.out.println("Display all entries (a)");
			System.out.println("Quit (q)");
			operator = sc.nextLine();
		}
		
//		Specifying that the user has chosen to quit and thus the program has ended
		System.out.println("End of program");

//		closing scanner to release resources
		sc.close();
	} //end of main method

} //end of class AddressBook
