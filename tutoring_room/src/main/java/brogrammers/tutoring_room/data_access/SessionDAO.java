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
			String query = "DELETE FROM active_sessions WHERE id=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setString(1, sessionId);
			get.execute();
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
