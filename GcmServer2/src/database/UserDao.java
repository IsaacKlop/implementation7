package database;

import java.sql.*;
import java.util.UUID;


public class UserDao {
	// Database credentials
	final String DB_URL = "jdbc:mysql://localhost/TEST";
	final String USERNAME = "root";
	final String PASS = "password";

	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	public String[] getUserInfo(String username) {

		String[] returnString = { "", "" };
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASS);

			// Execute SQL query
			String sql = "SELECT regid, uuid FROM users WHERE username=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			rs = stmt.executeQuery();

			String regid = null;
			String uuid = null;

			// Extract data from result set
			while (rs.next()) {
				regid = rs.getString("regid");
				uuid = rs.getString("uuid");
			}
			returnString[0] = regid;
			returnString[1] = uuid;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se2) {
			}
		} // end try
		return returnString;
	}

	public boolean setUuid(String uuid, String username) {
		boolean successful = false;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASS);

			// Execute SQL query
			String sql = "UPDATE users SET uuid=? WHERE username=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uuid);
			stmt.setString(2, username);
			rs = stmt.executeQuery();
			successful = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se2) {
			}
		}
		return successful;
	}
	
	public void createUuid(String username) {
		//Generate UUID
		Long uuidGet = UUID.randomUUID().getMostSignificantBits();
		
		String uuid = Long.toString(uuidGet);
		
		UserDao userDao = new UserDao();
		userDao.setUuid(uuid, username);
	}
	
	public boolean checkInfo(String uuid, String username) {
		try {
		// Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");
		
		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASS);
		
		
		// Execute SQL query
		String sql = "SELECT COUNT(*) FROM users WHERE uuid=? AND username=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, uuid);
		stmt.setString(2, username);
		rs = stmt.executeQuery();
		
		//Empty variable
		int counter;
		
		//Read count
		while(rs.next()){
		    counter = rs.getInt("total");
		    } 
		
		if (counter <= 0) {
			return false;
		}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return true;
	}
}