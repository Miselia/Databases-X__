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
			      "Where num = " + courseNum;
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}
	
}// Utilities class
