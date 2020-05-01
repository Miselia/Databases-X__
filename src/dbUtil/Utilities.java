package dbUtil;
/**
 * This class provides some basic methods for accessing a MariaDB database.
 * It uses Java JDBC and the MariaDB JDBC driver, mariadb-java-client-2.4.0.jar
 * to open an modify the DB.
 * 
 */

import java.security.SecureRandom;
// You need to import the java.sql package to use JDBC methods and classes
import java.sql.*;

/**
 * @coauthor Devin Ober
 * @coauthor Chris Caudill
 * @coauthor Nathan Hohnbaum
 * @coauthor Moses Mbugua
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
		String url = "jdbc:mysql://localhost:2000/X__367_2020?user=X__367&password=X__367";
		
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
		String url = "jdbc:mysql://localhost:2000/X__367_2020?user=" + user + "&password="+pass;
		
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
			sql = "SELECT c_dept, c_num, term, year " + 
				  "From GradPlan "+
			      "WHERE s_s_id = ? " +
				  "ORDER BY year,term";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setInt(1, id); // set the 1 parameter

			rset = pstmt.executeQuery();
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
	public ResultSet getWhenOffered(String courseNum) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement

			sql = "SELECT num, dept, term, altYear " +
			      "FROM Course " +
			      "Where num = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setString(1, courseNum); // set the 1 parameter

			rset = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}
	
	
	
	
	
	/**
	 * Generates a salt for passwords
	 * @return a salt for passwords
	 */
	public byte[] generateSalt()
	{
		SecureRandom sr = new SecureRandom();
		byte[] ret = new byte[256];
		sr.nextBytes(ret);
		return ret;
	}
	
	/**
	 * Attempts to log a user in
	 * @param username The username of the person attempting to log in
	 * @param password The password of the person attempting to log in
	 * @return A ResultSet containing the user's user id or an empty set if the login is unsuccessful
	 */
	public ResultSet doLogin(String username, String password)
	{
		ResultSet rset = null;
		String sql = null;
		try {
			
			sql = "SELECT id " + 
					"FROM User " + 
					"WHERE pw_hash = sha2(concat(sha2(?,256),pw_salt),256) AND id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			rset = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}
		return rset;
	}
	
	/**
	 * Changes the user's password
	 * @param username The username whose password to change
	 * @param oldPassword The user's old password
	 * @param newPassword The new password for the user
	 * @return True if the password was successfully changed, false otherwise
	 */
	public boolean changePassword(String username, String oldPassword, String newPassword)
	{
		String sql = null;
		try {
			ResultSet rset = null;
			sql = "SELECT id " + 
					"FROM User " + 
					"WHERE pw_hash = sha2(concat(sha2(?,256),pw_salt),256) AND id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setString(1, oldPassword);
			pstmt.setString(2, username);
			rset = pstmt.executeQuery();
			if (!rset.next()) {
				return false;
			}
			String uid = rset.getString(1);
			sql = "UPDATE User\n" + 
					"SET pw_hash = sha2(concat(sha2(?,256),?),256), pw_salt=?" + 
					"WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			byte[] salt = generateSalt();
			pstmt.clearParameters();
			pstmt.setString(1, newPassword);
			pstmt.setBytes(2, salt);
			pstmt.setBytes(3, salt);
			pstmt.setString(4, uid);
			int res = pstmt.executeUpdate();
			return res > 0;
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}
		return false;
	}
	
	
	
	/**
	 * check what 300+ electives are offered at a point
	 * 
	 * @param term, the Semester a class is offered
	 * @return ResultSet for list of electives courses
	 * 
	 */
	public ResultSet getElectiveOfferedCourses(String term, int id) {
		ResultSet rset = null;
		String sql = null;
		
		try {
			
			sql = "SELECT dept, title, num, altYear " + 
				   "FROM Required AS R, Course AS C, Student AS S " + 
				   "WHERE S.s_id = ? AND S.major = R.d_type AND C.term = ? AND elective = true AND c_num = num AND c_dept = dept;";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setInt(1,  id);
			pstmt.setString(2, term);
			
			rset = pstmt.executeQuery();
			
		}catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);

		}
		
		return rset;
	}
	
	
	/**
	 * Get major requirements for a student depending on their major
	 * 
	 * @param id - student id
	 * @return ResultSet for list of all required courses
	 * 
	 */
	public ResultSet getRequired(int id) {
		ResultSet rset = null;
		String sql = null;
		
		try {
			
			sql = "SELECT * " + 
				   "FROM Required" + 
				   "WHERE dtype = major IN (SELECT major FROM student WHERE s_id = ?);";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setInt(1,  id);
			
			rset = pstmt.executeQuery();
			
		}catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);

		}
		
		return rset;
	}
	
	
	
	
}// Utilities class
