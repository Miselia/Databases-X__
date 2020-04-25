package dbUtil;
/**
 * This program is used to test the Utilities class
 */
 
// You need to import the java.sql package to use JDBC
import java.sql.*; 
 
import java.util.Scanner;

/**
 * @author Dr. Blaha
 * @coauthor Devin Ober
 * 
 */
public class TestUtilities {

	// Global variables
	static Utilities testObj = new Utilities(); 		// Utilities object for testing
	static Scanner keyboard = new Scanner(System.in); 	// standard input

	public static void main(String[] args) throws SQLException {

		// variables needed for menu
		int choice;
		boolean done = false;

		while (!done) {
			System.out.println();
			displaymenu();
			choice = getChoice();
			switch (choice) {
			case 1: {
				openDefault();
				break;
			}
			case 2: {
				callGetNameSalary();
				break;
			}
			case 3: {
				callMatchName();
				break;
			}
			case 4: {
				callEmployeeByDNO();
				break;
			}
			
			case 5: {
				callOpenDB();
				break;
			}
			case 6: {
				callGetNameHours();
				break;
			}
			case 7: {
				callGetAverageHours();
				break;
			}
			case 8: {
				callGetSalaryFromEmployee();
				break;
			}
		
			case 9: {
				testObj.closeDB(); //close the DB connection 
				break;
			}
			case 10: {
				done = true;
				System.out.println("Good bye");
				break;
			}
			case 11: {
				callGetNotWorking();
				break;
			}
			case 12: {
				callInsertWorksOnList();
				break;
			}
			case 13: {
				callUpdateWorksOn();
				break;
			}
			case 14: {
				callDeleteWorksOn();
				break;
			}
			case 15: {
				callInsertDepartment();
				break;
			}
			}// switch
		}

	}// main

	static void displaymenu() {
		System.out.println("1)  open default DB");
		System.out.println("2)  call getNameSalary()");
		System.out.println("3)  call matchLastName(String)");
		System.out.println("4)  call employeeByDNO()");
		System.out.println("5)  open with user name and password");
		System.out.println("6)  call getNameHours()");
		System.out.println("7)  call getAverageHours()");
		System.out.println("8)  call getSalaryFromEmployee()");
		System.out.println("9)  close the DB");
		System.out.println("10) quit");
		System.out.println("11) call getNotWorking()");
		System.out.println("12) call insertWorksOnList()");
		System.out.println("13) call updateWorksOn()");
		System.out.println("14) call deleteWorksOn()");
		System.out.println("15) call insertDepartment()");
	}

	static int getChoice() {
		String input;
		int i = 0;
		while (i < 1 || i > 15) {
			try {
				System.out.print("Please enter an integer between 1-15: ");
				input = keyboard.nextLine();
				i = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		return i;
	}

	// open the default database;
	static void openDefault() {
		testObj.openDB();
	}
	static void callOpenDB() {
		String user = "";
		String pass = "";
		System.out.print("Please enter your username:");
		user = keyboard.nextLine();
		System.out.print("Please enter your password:");
		pass = keyboard.nextLine();
		testObj.openDB(user,pass);
	}

	// test getNameSalary() method
	static void callGetNameSalary() throws SQLException {
		ResultSet rs;
		System.out.println("Research Department Employees");
		System.out.println("*****************************");
		System.out.printf("LastName, FirstName        Salary\n");
		rs = testObj.getNameSalary();
		while (rs.next()) {
			System.out.printf("%-26s %s \n", rs.getString(1) + ", " + rs.getString(2), 
					                         rs.getString(3));
		}
	}

	// test matchName() method
	static void callMatchName() throws SQLException {
		ResultSet rs;
		String target;
		target = "K";
		System.out.println("\nEmployees with name that starts with " + target);
		System.out.println("***************************************************");
		System.out.printf("%-12s  %s\n", "Dept Number",   "LastName, FirstName");
		rs = testObj.matchLastName(target);
		while (rs.next()) {
			System.out.printf("    %-8s    %s\n", rs.getString(1), 
					rs.getString(2) + ", " + rs.getString(3));
		}
	}
	
	 
	//test employeeByDNO() method 
	static void callEmployeeByDNO() throws SQLException {
		ResultSet rs;
		System.out.print("Please enter a department number: ");
		String input = keyboard.nextLine();
		int dno= Integer.parseInt(input); 
		System.out.println("\nEmployees that work in department " + dno); 
		System.out.println("*******************************************");
		System.out.printf("%-12s   %s\n", "Dept Number",   "LastName, FirstName");
		rs = testObj.employeeByDNO(dno); 
		while(rs.next()){ 
			System.out.printf("    %-8s     %s\n", rs.getString(1), 
					rs.getString(2) + ", " + rs.getString(3));
			}
		
	}
	//test callGetNameHours() method 
	static void callGetNameHours() throws SQLException {
		ResultSet rs;
		System.out.print("Please enter a department number: ");
		String input = keyboard.nextLine();
		int dno= Integer.parseInt(input); 
		System.out.println("\n Worker Hours In Department " + dno); 
		System.out.println("*******************************************");
		System.out.printf("%-12s   %s\n",   "LastName, FirstName", "Dept Number");
		rs = testObj.getNameHours(dno); 
		while(rs.next()){ 
			System.out.printf("    %-8s     %s\n", rs.getString(1)+ ", " + rs.getString(2), 
					  rs.getString(3));
			}
		
	}
	
	// test callGetAverageHours() method
	static void callGetAverageHours() throws SQLException {
		ResultSet rs;
		System.out.println("\nAverage Project Hours");
		System.out.println("***************************************************");
		System.out.printf("%-12s  %s\n", "Total Hours",   "Average hours", "# of Workers");
		rs = testObj.getAverageHours();
		while (rs.next()) {
			System.out.printf("    %-8s    %s\n", rs.getString(1), 
					rs.getString(2), rs.getString(3));
		}
	}

	//test callGetSalaryFromEmployee() method 
	static void callGetSalaryFromEmployee() throws SQLException {
		ResultSet rs;
		String first = "";
		String last = "";
		System.out.print("Please enter employee's first name:");
		first = keyboard.nextLine();
		System.out.print("Please enter employee's last name");
		last = keyboard.nextLine();
		System.out.println("\n Employee's Salary " + first + " " + last); 
		System.out.println("*******************************************");
		System.out.printf("%-12s   %s\n",   "LastName, FirstName", "Salary");
		rs = testObj.getSalaryFromEmployee(first, last); 
		while(rs.next()){ 
			System.out.printf("    %-8s     %s\n", rs.getString(1), 
					rs.getString(2) + ", " + rs.getString(3));
		}
			
	}
	
	// test callGetAverageHours() method
	static void callGetNotWorking() throws SQLException {
		ResultSet rs;
		System.out.println("\nEmployees with no work");
		System.out.println("***************************************************");
		System.out.println("Employee");
		rs = testObj.getNotWorking();
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}
	//test callGetSalaryFromEmployee() method 
	static void callInsertWorksOnList() throws SQLException {
		int entryCount = 0;
		String input = "";
		System.out.print("How many entries are being made?");
		while (entryCount < 1) {
			try {
				System.out.print("\nPlease enter a positive integer: ");
				input = keyboard.nextLine();
				entryCount = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		int[][] entries = new int[entryCount][3];
		
		for(int i = 0; i < entryCount; i++) {
			
			while (entries[i][0] <= 0) {
				try {
					System.out.print("Please enter a positive integer for SSN: ");
					input = keyboard.nextLine();
					entries[i][0] = Integer.parseInt(input);
					System.out.println();
				} catch (NumberFormatException e) {
					System.out.println("I SAID AN INTEGER!!!!");
				}
			}
			while (entries[i][1] <= 0) {
				try {
					System.out.print("Please enter a positive integer for Project#: ");
					input = keyboard.nextLine();
					entries[i][1] = Integer.parseInt(input);
					System.out.println();
				} catch (NumberFormatException e) {
					System.out.println("I SAID AN INTEGER!!!!");
				}
			}
			while (entries[i][2] <= 0) {
				try {
					System.out.print("Please enter a positive integer for Hours: ");
					input = keyboard.nextLine();
					entries[i][2] = Integer.parseInt(input);
					System.out.println();
				} catch (NumberFormatException e) {
					System.out.println("I SAID AN INTEGER!!!!");
				}
			}
		}

		
		System.out.println("\n Number of Entries: " +testObj.insertWorksOnList(entries)); 
		
			
	}
	// test callUpdateWorksOn() method
	static void callUpdateWorksOn() throws SQLException {
		int essn = 0;
		int pno = 0;
		int hours = 0;
		String input = "";
		
		while (essn < 0) {
			try {
				System.out.print("Please enter a positive integer for SSN: ");
				input = keyboard.nextLine();
				essn = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		while (pno < 0) {
			try {
				System.out.print("Please enter a positive integer for Project#: ");
				input = keyboard.nextLine();
				pno = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		while (hours < 0) {
			try {
				System.out.print("Please enter a positive integer for Hours: ");
				input = keyboard.nextLine();
				hours = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
	

		testObj.updateWorksOn(essn,pno,hours);
		System.out.println("\n Works On Updated");
	}
	
	static void callDeleteWorksOn() throws SQLException {
		int essn = 0;
		int pno = 0;
		String input = "";
		
		while (essn < 0) {
			try {
				System.out.print("Please enter a positive integer for SSN: ");
				input = keyboard.nextLine();
				essn = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		while (pno < 0) {
			try {
				System.out.print("Please enter a positive integer for Project#: ");
				input = keyboard.nextLine();
				pno = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
	

		testObj.deleteWorksOn(essn,pno);
		System.out.println("\n" + essn + " " + pno + "has been deleted");
	}
	
	static void callInsertDepartment() throws SQLException {
		int dNumber = 0;
		String dLocation = "";
		String input = "";
		
		while (dNumber < 0) {
			try {
				System.out.print("Please enter a positive integer for SSN: ");
				input = keyboard.nextLine();
				dNumber = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		while (dLocation.equals("")) {
			try {
				System.out.print("Please enter the Location: ");
				input = keyboard.nextLine();
				dLocation = input;
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("Please insert a string");
			}
		}
	

		testObj.insertDepartment(dNumber,dLocation);
		System.out.println("\n" + dNumber + " " + dLocation + "has been deleted");
	}

		
}//MyUtilitiesTest	
