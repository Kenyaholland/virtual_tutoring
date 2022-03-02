package brogrammers.tutoring_room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Class for database actions
public class DAO {

	Connection conn = null;
	private static final String URL = "jdbc:mysql://virtual-tutoring-db.cb2bnbp0fzgx.us-east-1.rds.amazonaws.com:3306/tutoringdb";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Capstone22!";
    
    public DAO()
    {
    	try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			if (conn != null) {
				System.out.println("Successfully connected to database");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
