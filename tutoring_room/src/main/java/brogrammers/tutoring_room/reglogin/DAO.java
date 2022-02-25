package brogrammers.tutoring_room.reglogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public boolean insertUser(User user)
	{
		try 
		{
			Statement insert = (Statement) connection.createStatement();
			
			String query = "INSERT INTO users " + "VALUES ('" + user.getUserName() + "', '" + user.getPassword() + "', '"
					+ user.getSalt() + "', '" + user.getEmailAddress()
			+ "', '" + user.getRole() + "')";
			
			System.out.println(query);
			
			insert.executeUpdate(query);
			
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteUser(User user)
	{
		try 
		{
			Statement insert = (Statement) connection.createStatement();
			
			String query = "DELETE FROM users WHERE username='" + user.getUserName() + "' AND email='" + user.getEmailAddress() + "'";
			
			insert.executeUpdate(query);
			
			return true;
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updatePassword(User user, String newPassword)
	{
		try 
		{
			Statement update = (Statement) connection.createStatement();
			
			String query = "UPDATE users SET password='" + newPassword + "' WHERE username='" + user.getUserName() + "' AND email='" + user.getEmailAddress() + "'";
			
			update.executeUpdate(query);
			
			return true;
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean checkExistanceOfUserName(String userName)
	{
		try 
		{
			Statement check = (Statement) connection.createStatement();
			
			String query = "SELECT * FROM users WHERE username='" + userName + "'";
			
			ResultSet rs = check.executeQuery(query);
			
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
	
	public boolean checkExistanceOfEmail(String emailAddress)
	{
		try 
		{
			Statement check = (Statement) connection.createStatement();
			
			String query = "SELECT * FROM users WHERE email='" + emailAddress + "'";
			
			ResultSet rs = check.executeQuery(query);
			
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
	
	public String getSaltedHash(String userName)
	{
		try
		{
			Statement get = connection.createStatement();
			String query = ("SELECT * FROM users WHERE username='" + userName + "'");
			ResultSet rs = get.executeQuery(query);
		
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
	
	public String getSalt(String userName)
	{
		try
		{
			Statement get = connection.createStatement();
			String query = ("SELECT * FROM users WHERE username='" + userName + "'");
			ResultSet rs = get.executeQuery(query);
		
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
