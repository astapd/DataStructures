package assignment.four;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AddressBook{

	public static void main(String[] args) throws IOException {
		//creating a variable of table class and instantiating the table class
		Table table = new Table();

		//declaring variables for name, address, new address, file path, FileReader class and BufferedReader class
		String key = null;
		String value = null;
		String newValue = null;
		String filePath= null;
		FileReader file = null;
		BufferedReader bf= null;
		String st;

		//creating and instantiating scanner class
		Scanner sc = new Scanner(System.in);

		//		Prompting to choose if the user wishes to open the file containing address book.
		System.out.println("Do you want to open a file? Type 'y' for Yes and 'n' for No:");
		String open = sc.nextLine();

		// 		Prompting to enter the file path and then opening and displaying the contents of the file (i.e. if the user first chose to open it)
		if(open.equalsIgnoreCase("y")) {
			System.out.println("Please enter the path of the file you wish to open\n(You can either create your own text file (with NO empty line in between two pairs of entries) or use the one I created named 'AssignmentFourFile')");
			System.out.println("For example. My file path was : C:\\INFS 519 workspace\\INFS_519_Assignments\\src\\assignment\\four\\AssignmentFourFile.txt\n");
			filePath = sc.nextLine();				
			System.out.println("Displaying the contents of the file (if there is any):\n");
			file = new FileReader(filePath);
			bf = new BufferedReader(file);
			while((st= bf.readLine())!=null) {
				System.out.println(st);
			}
		}else {
			System.out.println("The user did not wish to open the file.\n");
		}

		//Instruction statements for what the user options are
		System.out.println("\nEnter what you wish to do in the address book. Your options are:");
		System.out.println("Add a name (n)");
		System.out.println("Look up a name (l)");
		System.out.println("Update address of the name (u)");
		System.out.println("Delete an entry (d)");
		System.out.println("Display all entries (a)");
		System.out.println("Save all entries (s)");
		System.out.println("Quit (q)");

		//calling for input in console
		String selection = sc.nextLine();

		//while loop-- so that the user may continue until he/she hits q, for quit
		while(!(selection.equals("q") )) {

			//switch cases to navigate to the correct command
			switch(selection){

			//n- for adding a name and address in the address book
			case "n":
				//				if the user had chosen to open the file he/she is given option to either used the items in the file as entries or to manually enter their own items
				if(open.equalsIgnoreCase("y")) {
					System.out.println("Do you want to add the items (name and address pairs) from the pre existing file as new entries in the address book? ('y' for Yes & 'n' or any other key for No)");
					String option = sc.nextLine();
					//				In case the user chose to input the items in the file as entries
					if(option.equalsIgnoreCase("y")) {
						file = new FileReader(filePath);
						bf = new BufferedReader(file);
						String st2;
						while((st2= bf.readLine())!=null) {
							key = st2;
							value = bf.readLine();
							table.insert(key, value);
						}
						System.out.println("The items from the file has been entered in the address book.\n");
					}
					//				In case the user chose not to input the items form the file as entries, he/she needs to enter their own items
					else {
						System.out.println("The user had chosen to open the file but has chosen to manualy input new entries.");
						System.out.println("Enter the name to be added");
						key = sc.nextLine();
						if(table.lookUp(key)==null) {
							System.out.println("Enter the address");
							value = sc.nextLine();
							table.insert(key, value);
						}
						else {
							System.out.println("The name already exists in the address book.\n");
						}
					}
				}
				//				If the user had chosen not to open the file, he/she needs to enter their own item(s)
				else {
					System.out.println("The user has chosen not to open the file, thus has to manualy input new entries.");
					System.out.println("Enter the name to be added");
					key = sc.nextLine();
					if(table.lookUp(key)==null) {
						System.out.println("Enter the address");
						value = sc.nextLine();
						table.insert(key, value);
					}
					else {
						System.out.println("The name already exists in the address book.\n");
					}
				}
				break;

				//l- for looking up a name
			case "l":
				System.out.println("Enter the name to look up");
				key = sc.nextLine();
				if(!(table.lookUp(key)==null)) {
					System.out.println("the name is "+key+" and the address is "+table.lookUp(key)+"\n");
				}else {
					System.out.println("The name does not exist in the file.\n");
				}
				break;

				//u- for updating the address of a name
			case "u":
				System.out.println("Enter the name whose address you intend to update");
				key = sc.nextLine();
				if(table.lookUp(key)==null) {
					System.out.println("No such name found in the address book. No update done.\n");
				}
				else {
					System.out.println("Enter the new address you want it updated to");
					newValue = sc.nextLine();
					table.update(key, newValue);
				}
				break;

				//d- for deleting a name/item form the list
			case "d":
				System.out.println("Enter the name to delete");
				key = sc.nextLine();
				table.delete(key);
				break;

				//a- for displaying all the items and giving us the total number of items
			case "a":
				System.out.println("Displaying all the items in the address book\n" );
				table.displayAll();
				break;

				//s- opens file and writes all the items to the address file and saves it
			case "s":
				System.out.println("Please give the path of the file you wish to save the address book in: ");
				System.out.println(" For example. My file path was : C:\\INFS 519 workspace\\INFS_519_Assignments\\src\\assignment\\four\\AssignmentFourFile.txt\n");
				filePath = sc.nextLine();
				table.save(filePath);
				System.out.println("Please see the file  for the saved address book.\n");
				break;

				//default in case the user chooses an invalid instruction
			default:
				System.out.println("Option not valid, choose a valid option for command\n");
			}

			//repeating the instruction menu
			System.out.println("Enter what you wish to do in the address book. Your options are:");
			System.out.println("Add a name (n)");
			System.out.println("Look up a name (l)");
			System.out.println("Update address of the name (u)");
			System.out.println("Delete an entry (d)");
			System.out.println("Display all entries (a)");
			System.out.println("Save all entries (s)");
			System.out.println("Quit (q)");

			selection = sc.nextLine();
		}	

		//		Print statement for when the user chooses to quit
		System.out.println("User chose to quit.\nEnd of program.");

		//		closing the scanner class to free up the resources
		sc.close();
	}
	//	end of main method

} 
//end of class AddressBook
