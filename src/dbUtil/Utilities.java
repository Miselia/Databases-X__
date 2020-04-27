package dbUtil;
/**
 * This class provides some basic methods for accessing a MariaDB database.
 * It uses Java JDBC and the MariaDB JDBC driver, mariadb-java-client-2.4.0.jar
 * to open an modify the DB.
 * 
 */

// You need to import the java.sql package to use JDBC methods and classes
import java.sql.*;

/**
 * @coauthor Devin Ober
 */
public class Utilities {

	private Connection conn = null; // Connection object
	 
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * Open a MariaDB DB connection where user name and password are predefined
	 * (hardwired) within the url.
	 */
	public void openDB() {

		// Connect to the database
		String url = "jdbc:mysql://localhost:2000/company367_2020?user=X__367&password=X__367";
		
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println("using url:"+url);
			System.out.println("problem connecting to MariaDB: "+ e.getMessage());			
			//e.printStackTrace();
		}

	}// openDB

	/**
	 * Close the connection to the DB
	 */
	public void closeDB() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			System.err.println("Failed to close database connection: " + e);
		}
	}// closeDB

	/**
	 * This method creates an SQL statement to list fname, lname, salary of all
	 * employees that work in the department with dname='Research'
	 * 
	 * @return ResultSet that contains three columns lname, fname, salary of all
	 *         employees that work in the research department
	 */
	public ResultSet getNameSalary() {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT lname, fname, salary " +
			      "FROM employee, department " + 
				  "WHERE dno=dnumber and dname='Research' " + 
			      "ORDER BY lname, fname";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}// getNameSalary

	/**
	 * This method creates an SQL statement to list fname, lname, and department
	 * number of all employees that have a last name that starts with the String
	 * target - 
	 * THIS IS AN EXAMPLE OF WHAT NOT TO DO!!!!
	 * THIS SHOULD REALLY BE DONE WITH A PREPARED STATMENT
	 * 
	 * @param target the string used to match beginning of employee's last name
	 * @return ResultSet that contains lname, fname, and department number of
	 *         all employees that have a first name that starts with target.
	 */
	public ResultSet matchLastName(String target) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT dno, lname, fname " + 
			      "FROM employee " + 
				  "WHERE lname like '" + target + "%' " + 
			      "ORDER BY dno";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;

	}// matchLastName

	/**
	 * This method creates an SQL statement to list fname, lname, and department
	 * number of all employees that work in the department with number dno
	 * 
	 * @param dno the department number
	 * @return ResultSet that contains lname, fname, and department number of
	 *         all employees that work in the department number dno
	 */
	// EXAMPLE OF USING A PreparedStatement AND SETTING Parameters
	public ResultSet employeeByDNO(int dno) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			sql = "SELECT dno, lname, fname FROM employee " + 
			      "WHERE dno = ? " + 
				  "ORDER BY dno";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setInt(1, dno); // set the 1 parameter

			rset = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}// employeeByDNO

	/**  
	 * 1 Write and Test
	 * Overload the open method that opens a MariaDB DB with the user name 
	 * and password given as input.
	 * 
	 * @param username is a String that is the DB account username
	 * @param password is a String that is the password the account
	 */
	public void openDB(String user, String pass) {

		// Connect to the database
		String url = "jdbc:mysql://localhost:2000/company367_2020?user=" + user + "&password="+pass;
		
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println("using url:"+url);
			System.out.println("problem connecting to MariaDB: "+ e.getMessage());			
			//e.printStackTrace();
		}

	}// openDB

	
	/**
	 * getGradPlan
	 * 
	 * @param id The student's id number
	 * @return rset All grad plan entries associated with the student's id.
	 */
	public ResultSet getGradPlan(int id) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT c_dept, c_num, term, year " + 
				  "From GradPlan "+
			      "WHERE s_s_id = " + id + " "
				  "ORDER BY year,term";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}
	/**
	 * getWhenOffered()
	 * 
	 * @param courseNum The student's id number
	 * @return ResultSet that has the information on the selected course
	 */
	public ResultSet getWhenOffered(int courseNum) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT num, dept, term, altYear " +
			      "FROM Course " +
			      "Where num = " courseNum;
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}

	/**
	 * 4 Write and Test
	 * Write a method that returns fname, lname, salary, and dno for each employee 
	 * that works on a project with the employee specified by input values empFname, empLname
	 * 
	 * @param empFname is the first name of the employee
	 * @param empLname is the last name of the employee
	 * @return ResultSet that has fname, lname, salary, and dno for each
	 *         employee that works on a project with the employee empFname,
	 *         empLname
	 */
	public ResultSet getSalaryFromEmployee(String empFname, String empLname) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT lname, fname, salary" +
			      "FROM employee " + 
				  "WHERE lname = " + empLname + " AND fname = " + empFname +
			      " ORDER BY lname";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}
	/**
	 * 5 Write and Test
	 * Retrieve the names of employees who do not work on any project and their 
	 * salary Names must be in the format "lname, fname" i.e., the last name and 
	 * first name must be concatenated.
	 * 
	 * @return ResultSet that has employee name and salary of all employees that
	 *         do not work on any project.
	 */
	public ResultSet getNotWorking() {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT CONCAT(lName, ', ', fName) " +
			      "FROM employee, works_on "  + 
				  "EXCEPT (Select ssn "+
				   "FROM employee, works_on " +
				   "WHERE essn = ssn) "; //+
				  //"ORDER BY lname";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}
	/**
	 * 6 Write and Test   ==> YOU MUST USE A PreparedStatement <==
	 * This method will use a PreparedStatement and the
	 * information in data to update the works_on table. Each row of the 2-dim
	 * array, data, contains the 3 attributes for one tuple in the works_on
	 * table. The 2-dim array is a nx3 array and the column format is 
	 * (essn, pno, hours) The method returns the number of tuples successfully
	 * inserted.
	 * 
	 * @param data is a nx3 table of Strings where each row has the format 
	 *        (essn, pno, hours)
	 * @return number of tuples successfully inserted into works_on
	 */
	public int insertWorksOnList(int[][] inputs) {
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			sql = "Insert Into works_on (essn,pno,hours) " + 
			      "Values(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < inputs.length; i++) {
				pstmt.clearParameters();
				for(int j = 0; j < 3; j++) {
					
					pstmt.setInt(j+1,inputs[i][j] ); // set the 1 parameter
				}
				pstmt.executeQuery();
			}

			
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return inputs.length;
	}
	
	/**
	 * Modify Utilities.java and TestUtilities.java to update the hours of a Works_On tuple.
	 * You should prompt the user for the essn, pno, hours. Add the hours submitted to the
	 * existing hours
	 * @param empEssn
	 * @param empPno
	 * @param empHours
	 * @return whether or not it succeeded
	 */
	public boolean updateWorksOn(int empEssn, int empPno, int empHours) {
		
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			sql = "UPDATE works_on " + 
				  "SET hours=" + empHours + " " +
			      "WHERE essn=" + empEssn + " essn=" + empPno;
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);

		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return true;
	}
	
	/**
	 * Modify Utilities.java and TestUtilities.java to delete a works_on tuple. You
	 * should prompt the user for the essn and pno.
	 * @param empEssn
	 * @param empPno
	 * @return whether or not it succeded
	 */
	public boolean deleteWorksOn(int empEssn, int empPno) {
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			sql = "Delete works_on " + 
				  "Where essn=" + empEssn +" pno=" + empPno; 
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);

		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return true;
	}
	
	/**
	 * Modify Utilities.java and TestUtilities.java to add a dept_locations tuple. You
	 * should prompt the user for the dnumber and dlocation.
	 * @param depDnumber
	 * @param depDlocation
	 * @return number of tuples successfully inserted into works_on
	 */
	public boolean insertDepartment(int depDnumber, String depDlocation) {
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			sql = "Insert Into works_on (dnumber,dlocation) " + 
				  "Values (?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setInt(1,depDnumber);
			pstmt.setString(1,depDlocation);
			
			pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return true;
	}
	
}// Utilities class
