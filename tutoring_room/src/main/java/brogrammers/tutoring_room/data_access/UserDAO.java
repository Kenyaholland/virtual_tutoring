package brogrammers.tutoring_room.data_access;

import brogrammers.tutoring_room.reglogin.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Contains methods that allow database access for users table
 */
public class UserDAO
{
	Connection connection;
	
	public UserDAO()
	{
		
	}
	
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
	
	public boolean doesUserExist(String username, String email)
	{
		try
		{
			String query = "SELECT * FROM users WHERE username=? AND email=?";
			
			PreparedStatement select;
			select = connection.prepareStatement(query);
			
			select.setString(1, username);
			select.setString(2, email);
			
			ResultSet rs = select.executeQuery();
			
			return rs.next();
		}
		catch(Exception error){}
		
		return false;
	}
	
	/**
	 * Inserts a user into the database
	 * @param user This is the user to be inserted
	 * @return
	 */
	public boolean insertUser(User user, String password)
	{
		try 
		{
			String query = "INSERT INTO users " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement insert;
			insert = connection.prepareStatement(query);
			
			insert.setString(1, user.getUserName());
			insert.setString(2, password);
			insert.setString(3, user.getSalt());
			insert.setString(4, user.getEmailAddress());
			insert.setString(5, user.getRole());
			insert.setString(6, user.getFirstName());
			insert.setString(7, user.getLastName());
			insert.setInt(8, 0);
			insert.setInt(9, 0);
			
			insert.executeUpdate();
			
			return true;
		}
		catch(Exception error)
		{
			error.printStackTrace();
			return false;
		}
	}
	
	public boolean insertTutor(String username, String tutoringCourse)
	{
		try 
		{
			String query = "INSERT INTO tutors " + "VALUES (?, ?)";
			
			PreparedStatement insert;
			insert = connection.prepareStatement(query);
			
			insert.setString(1, username);
			insert.setString(2, tutoringCourse);
			
			insert.executeUpdate();
			
			return true;
		}
		catch(Exception error)
		{
			error.printStackTrace();
			return false;
		}
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
	public boolean updatePassword(String username, String email, String password, String salt)
	{
		try 
		{
			String query = "UPDATE users SET password=?, salt=? WHERE username=? AND email=?";
			
			PreparedStatement update;
			update = connection.prepareStatement(query);
			
			update.setString(1, password);
			update.setString(2, salt);
			update.setString(3, username);
			update.setString(4, email);
			
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
	
	public String getEmail(String userName)
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
				String email = rs.getString("email");
				return email;
			}
		}
		catch(Exception e)
		{
			
		}
		
		return null;
	}
	
	public String getRole(String userName)
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
				String role = rs.getString("role");
				return role;
			}
		}
		catch(Exception e)
		{
			
		}
		
		return null;
	}
	
	public boolean setRoomNum(String username, int roomNum)
	{
		try
		{
			String query = "UPDATE users SET roomNum=? WHERE username=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setInt(1, roomNum);
			get.setString(2, username);			
			
			get.executeUpdate();
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int getRoomNum(String username)
	{
		try
		{
			String query = "SELECT * FROM users WHERE username=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setString(1, username);			
			
			ResultSet rs = get.executeQuery();
			
			if (rs.next()) {
				int roomNum = rs.getInt("roomNum");
				return roomNum;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int getNumUsersInRoom(int roomNum)
	{
		try
		{
			String query = "SELECT COUNT(username) FROM users WHERE roomNum=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setInt(1, roomNum);
			
			ResultSet rs = get.executeQuery();
		
			if (rs.next()) 
			{ 
				int count = rs.getInt("count");
				return count;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public boolean setGroupNum(String username, int groupNum)
	{
		try
		{
			String query = "UPDATE users SET groupNum=? WHERE username=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setInt(1, groupNum);
			get.setString(2, username);			
			
			get.executeUpdate();
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int getGroupNum(String username)
	{
		try
		{
			String query = "SELECT * FROM users WHERE username=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setString(1, username);			
			
			ResultSet rs = get.executeQuery();
			
			if (rs.next()) {
				int groupNum = rs.getInt("groupNum");
				return groupNum;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public List<String> getGroupUsernames(int roomNum, int groupNum)
	{
		try
		{
			String query = "SELECT * FROM users WHERE roomNum=? AND groupNum=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setInt(1, roomNum);
			get.setInt(2, groupNum);
			
			ResultSet rs = get.executeQuery();
		
			List<String> usernames = new ArrayList<String>();
			while (rs.next()) 
			{ 
				String username = rs.getString("username");
				usernames.add(username);
			}
			
			return usernames;
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
