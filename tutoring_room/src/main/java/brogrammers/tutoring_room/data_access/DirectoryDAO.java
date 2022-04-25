package brogrammers.tutoring_room.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectoryDAO {

	Connection connection;
	
	public DirectoryDAO() {}
	
	
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
	
	public int getNumStudentsInRoom(int roomNum)
	{
		try
		{
			String query = "SELECT numStudents FROM room_directory WHERE id=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setInt(1, roomNum);
			
			ResultSet rs = get.executeQuery();
			
			int numStudents = 0;
			if (rs.next()) {
				numStudents = rs.getInt("numStudents");
				return numStudents;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public boolean incrementNumStudentsInRoom(int roomNum)
	{
		try
		{
			String query = "SELECT numStudents FROM room_directory WHERE id=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setInt(1, roomNum);
			
			ResultSet rs = get.executeQuery();
			
			int numStudents = 0;
			if (rs.next()) {
				numStudents = rs.getInt("numStudents");
			}
			
			query = "UPDATE room_directory SET numStudents=? WHERE id=?";
			
			get = connection.prepareStatement(query);
			
			get.setInt(1, numStudents+1);
			get.setInt(2, roomNum);
			
			get.executeUpdate();
			System.out.println("Incremented room count for room " + roomNum);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean decrementNumStudentsInRoom(int roomNum)
	{
		try
		{
			String query = "SELECT numStudents FROM room_directory WHERE id=?";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			get.setInt(1, roomNum);
			
			ResultSet rs = get.executeQuery();
			
			int numStudents = 0;
			if (rs.next()) {
				numStudents = rs.getInt("numStudents");
			}
			
			query = "UPDATE room_directory SET numStudents=? WHERE id=?";
			
			get = connection.prepareStatement(query);
			
			numStudents--;
			get.setInt(1, numStudents);
			get.setInt(2, roomNum);
			
			get.executeUpdate();
			System.out.println("Decremented room count for room " + roomNum);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean hasOpenConnection()
	{
		boolean openConn = false;
		try {
			openConn = connection.isValid(100);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return openConn;
	}

}
