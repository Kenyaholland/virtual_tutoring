package brogrammers.tutoring_room.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesDAO 
{
    Connection connection;
    
    ArrayList<String> courses = new ArrayList<String>();
	
	public CoursesDAO() 
	{
		connectToDatabase();
		courses = getCourses();
		closeConnection();
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
	
	public ArrayList<String> getCourses()
	{
		try
		{
			String query = "SELECT * FROM courses";
			
			PreparedStatement get;
			get = connection.prepareStatement(query);
			
			ResultSet rs = get.executeQuery();
		
			while(rs.next()) 
			{ 
				String course = rs.getString("crn");
				this.courses.add(course);
			}
		}
		catch(Exception e)
		{
			
		}
		
		return this.courses;
	}
}
