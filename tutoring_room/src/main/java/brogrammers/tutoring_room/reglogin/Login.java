package brogrammers.tutoring_room.reglogin;

/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Purpose: Handles user logins. 
 */
public class Login
{
	private SecurityUtils sh;
	private DAO dao;
	
	public Login()
	{
		sh = new SecurityUtils();
		dao = new DAO();
	}
	
	public boolean login(String userName, String password)
	{
		//connect to database here
		dao.connectToDatabase();
		
		boolean success = false;
		
		//Check for existance of username in database
		if(!(dao.checkExistenceOfUserName(userName)))
		{
			return false;
		}
		
		//Salt and salted hash from database
		String salt = dao.getSalt(userName);
		String storedPassword = dao.getSaltedHash(userName);
		
		//Recalculated hash from provided password
		String hash = sh.hashPassword(password);
		String saltedHash = hash.concat(salt);
		
		//If recalculated hash + salt equals the stored password from database then the entered password is correct
		if(isMatchingPassword(saltedHash, storedPassword))
		{
			success = true;
		}
		
		//close database connection
		dao.closeConnection();
		
		if(success)
		{
			return true;
		}
		return false;
	}
	
	public boolean isMatchingPassword(String password, String storedPassword)
	{
		if(password.contentEquals(storedPassword))
		{
			return true;
		}
		return false;
	}
}
