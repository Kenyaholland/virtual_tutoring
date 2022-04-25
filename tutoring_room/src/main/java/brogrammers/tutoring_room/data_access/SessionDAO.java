package brogrammers.tutoring_room.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Contains methods that allow database access for active_sessions table
 */
public class SessionDAO {

	Connection connection;
	
	public SessionDAO(){}
	
	/**
	 * Establishes a connection to the mySQL database
	 * @return
	 */
	public boolean connectToDatabase()
	{
		String url = "jdbc:mysql://virtual-tutoring-db.cb2bnbp0fzgx.us-east-1.rds.amazonaws.com:3306/tutoringdb";
		String username = "admin";
		String password = "Capstone22!";
    	
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		} 
		catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			connection = DriverManager.getConnection(url, username, password);
			
			if(connection.isValid(100))
			{
				return true;
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		} 
		return false;
	}
	
	
	public void addSession(String id, String username)
	{
		try
		{
			String query = "INSERT INTO active_sessions VALUES (?,?)";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setString(1, id);
			get.setString(2, username);
			get.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void removeSession(String sessionId)
	{
		try
		{			
			PreparedStatement get;
			
			String query = "SELECT username FROM active_sessions WHERE id=?";
			get = connection.prepareStatement(query);
			get.setString(1, sessionId);
			
			ResultSet rs = get.executeQuery();
			
			String username = "";
			if(rs.next()) 
			{ 
				username = rs.getString("username");
			}
			
			query = "DELETE FROM active_sessions WHERE id=?";
			get = connection.prepareStatement(query);
			get.setString(1, sessionId);
			get.execute();
			
			query = "SELECT roomNum FROM users WHERE username=?";
			get = connection.prepareStatement(query);
			get.setString(1, username);
			rs = get.executeQuery();
			
			int roomNum = 0;
			if(rs.next()) 
			{ 
				roomNum = rs.getInt("roomNum");
			}
			
			if (roomNum != 0) {
				query = "UPDATE users SET roomNum=?, groupNum=? WHERE username=?";
				get = connection.prepareStatement(query);
				get.setInt(1, 0);
				get.setInt(2, 0);
				get.setString(3, username);
				get.execute();
				System.out.println("Set room and group number to 0 on user exit");
				
				query = "SELECT numStudents FROM room_directory WHERE id=?";
				get = connection.prepareStatement(query);
				get.setInt(1, roomNum);
				rs = get.executeQuery();
				
				int numStudents = 0;
				if (rs.next()) {
					numStudents = rs.getInt("numStudents");
				}
				
				query = "UPDATE room_directory SET numStudents=? WHERE id=?";
				get = connection.prepareStatement(query);
				numStudents--;
				get.setInt(1, numStudents);
				get.setInt(2, roomNum);
				get.execute();
				System.out.println("Decremented room count for room " + roomNum);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getActiveUserName(String sessionId)
	{
		try
		{
			String query = "SELECT username FROM active_sessions WHERE id=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setString(1, sessionId);
			
			ResultSet rs = get.executeQuery();
			
			if(rs.next()) 
			{ 
				String username = rs.getString("username");
				return username;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Closes the database connection
	 */
	public void closeConnection()
	{
		try 
		{
			connection.close();
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
