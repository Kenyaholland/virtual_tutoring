package brogrammers.tutoring_room.reglogin;

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
 * Purpose: Performs database operations 
 */
public class DAO 
{
	Connection connection;
	
	public DAO(){}
	
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
	
	/**
	 * Inserts a user into the database
	 * @param user This is the user to be inserted
	 * @return
	 */
	public boolean insertUser(User user)
	{
		try 
		{
			String query = "INSERT INTO users " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement insert;
			insert = connection.prepareStatement(query);
			
			insert.setString(1, user.getUserName());
			insert.setString(2, user.getPassword());
			insert.setString(3, user.getSalt());
			insert.setString(4, user.getEmailAddress());
			insert.setString(5, user.getRole());
			insert.setString(6, null);
			insert.setString(7, null);
			insert.setString(8, null);
			
			insert.executeUpdate();
			
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Deletes a user from the database
	 * @param user This is the user to be deleted
	 * @return 
	 */
	public boolean deleteUser(User user)
	{
		try 
		{
			String query = "DELETE FROM users WHERE username=? AND email=?";
			
			PreparedStatement delete;
			delete = connection.prepareStatement(query);
			
			delete.setString(1, user.getUserName());
			delete.setString(2, user.getEmailAddress());
			
			delete.executeUpdate();
			
			return true;
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Updates a users password
	 * @param user This is the user to be updated
	 * @param newPassword This is the new password
	 * @return
	 */
	public boolean updatePassword(User user, String newPassword)
	{
		try 
		{
			String query = "UPDATE users SET password=? WHERE username=? AND email=?";
			
			PreparedStatement update;
			update = connection.prepareStatement(query);
			
			update.setString(1, newPassword);
			update.setString(2, user.getUserName());
			update.setString(3, user.getEmailAddress());
			
			update.executeUpdate();
			
			return true;
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Checks the database for the existence of a username
	 * @param userName This is the username to be checked
	 * @return
	 */
	public boolean checkExistenceOfUserName(String userName)
	{
		try 
		{
			String query = "SELECT * FROM users WHERE username=?";
			
			PreparedStatement check;
			check = connection.prepareStatement(query);
			
			check.setString(1, userName);
			
			ResultSet rs = check.executeQuery();
			
			if(rs.next())
			{				
				return true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks the database for the existence of an email address
	 * @param emailAddress This is the email to be checked
	 * @return
	 */
	public boolean checkExistenceOfEmail(String emailAddress)
	{
		try 
		{
			String query = "SELECT * FROM users WHERE email=?";
			
			PreparedStatement check;
			check = connection.prepareStatement(query);
			
			check.setString(1, emailAddress);
			
			ResultSet rs = check.executeQuery();
			
			if(rs.next())
			{				
				return true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Returns the salted hash for a user
	 * @param userName This is the username
	 * @return
	 */
	public String getSaltedHash(String userName)
	{
		try
		{
			String query = "SELECT * FROM users WHERE username=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setString(1, userName);
			
			ResultSet rs = get.executeQuery();
		
			if(rs.next()) 
			{ 
				String saltedHash = rs.getString("password");
				return saltedHash;
			}
		}
		catch(Exception e)
		{
			
		}
		
		return null;
	}
	
	/**
	 * Returns the salt for a user
	 * @param userName This is the username
	 * @return 
	 */
	public String getSalt(String userName)
	{
		try
		{
			String query = "SELECT * FROM users WHERE username=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setString(1, userName);
			
			ResultSet rs = get.executeQuery();
		
			if(rs.next()) 
			{ 
				String salt = rs.getString("salt");
				return salt;
			}
		}
		catch(Exception e)
		{
			
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
