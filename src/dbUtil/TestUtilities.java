package dbUtil;
/**
 * This program is used to test the Utilities class
 */
 
// You need to import the java.sql package to use JDBC
import java.sql.*; 
 
import java.util.Scanner;

/**
 * @coauthor Devin Ober
 * @coauthor Chris Caudill
 * @coauthor Nathan Hohnbaum
 * @coauthor Moses Mbugua
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
				callDoLogin();
				break;
			}
			case 3: {
				callChangePassword();
				break;
			}
			case 5: {
				callOpenDB();
				break;
			}
			case 6: {
				callGetGradPlan();
				break;
			}
			case 7: {
				callGetGradPlan();
				break;
			}
			case 8: {
				callgetElectiveOfferedCourses();
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
			}// switch
		}

	}// main

	static void displaymenu() {
	}

	static int getChoice() {
		String input;
		int i = 0;
		while (i < 1 || i > 15) {
			try {
				System.out.print("Please enter an integer between 1-*: ");
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

	//test callGetGradPlan() method 
	static void callGetGradPlan() throws SQLException {
		ResultSet rs;
		int id = 0;
		String input = "";
		while (id < 1) {
			try {
				System.out.print("\nPlease enter a valid student id: ");
				input = keyboard.nextLine();
				id = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("Please input a valid id");
			}
		}
		System.out.println("\n Student GradPlan for " + id); 
		System.out.println("*******************************************");
		System.out.printf("%-12s   %s\n",   "Department", "CRN","Term","Year");
		rs = testObj.getGradPlan(id); 
		while(rs.next()){ 
			System.out.printf("    %-8s     %s\n", rs.getString(1)+ ", " + rs.getString(2), 
					  rs.getString(3), rs.getString(4));
			}
		
	}
	
	// test callGetWhenOffered() method
	static void callGetWhenOffered() throws SQLException {
		ResultSet rs;
		String crn = "";
		while (crn.length() < 1) {
			try {
				System.out.print("\nPlease enter requested crn: ");
				crn = keyboard.nextLine();
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("please input a valid crn");
			}
		}
		System.out.println("\n When " +crn+ " is offered"); 
		System.out.println("*******************************************");
		System.out.printf("%-12s   %s\n",   "CRN", "Department","Term","Year");
		rs = testObj.getWhenOffered(crn); 
		while(rs.next()){ 
			System.out.printf("    %-8s     %s\n", rs.getString(1)+ ", " + rs.getString(2), 
					  rs.getString(3), rs.getString(4));
			}
	}

	static void callDoLogin()
	{
		System.out.println("Username: ");
		String username = keyboard.nextLine();
		System.out.println("Password: ");
		String password = keyboard.nextLine();
		ResultSet rset = testObj.doLogin(username, password);
		System.out.println("id");
		System.out.println("--------------");
		try {
			while (rset.next()) {
				System.out.println(rset.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void callChangePassword()
	{
		System.out.println("Username: ");
		String username = keyboard.nextLine();
		System.out.println("Old Password: ");
		String oldPassword = keyboard.nextLine();
		System.out.println("New Password: ");
		String newPassword = keyboard.nextLine();
		boolean succ = testObj.changePassword(username, oldPassword, newPassword);
		if (succ) {
			System.out.println("Password successfully changed");
		} else {
			System.out.println("Error changing password");
		}
	}
		
	//test callgetElectiveOfferedCourses()
		static void callgetElectiveOfferedCourses() throws SQLException {
			ResultSet rs;
			String term = "";
			String input = "";
			int id  = 0;
			while (term.length() < 1 && id < 1) {
				try {
					System.out.print("\nPlease enter requested semester (F, J, S) : ");
					term = keyboard.nextLine().toUpperCase();
					
					System.out.print("\nPlease enter requested student id: ");
					input = keyboard.nextLine();
					id = Integer.parseInt(input);
				} catch(NumberFormatException e) {
					System.out.println("Please input a valid student id");
				}
			
			}
			switch (term) {
				case "F": 
					System.out.println("\n When 300+ Elective courses for Fall term are offered"); 
					break;
				case "J":
					System.out.println("\n When 300+ Elective courses for J-term are offered"); 
					break;
				case "S":
					System.out.println("\n When 300+ Elective courses for Spring term are offered"); 
					break;
				default:
				break;
			}
			System.out.println("*******************************************");
			System.out.printf("%-12s   %-12S      %-12s  %s\n",   "Department", "Title","Course Number","altYear");
			rs = testObj.getElectiveOfferedCourses(term, id);
			while(rs.next()){ 
				System.out.printf("%-6s  %-18s  %-12s %s\n", rs.getString(1), rs.getString(2), 
						  rs.getString(3), rs.getString(4));
				}
		}



		
}//MyUtilitiesTest	
