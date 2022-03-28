package brogrammers.tutoring_room.reglogin;

/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Purpose: Representation of a system user
 */
public class User 
{
	private String userName;
	private String password;
	private String salt;
	private String emailAddress;
	private String role;
	private String fName;
	private String lName;
	
	/**
	 * Parameterized constructor for creating a user
	 * @param userName This is the userName
	 * @param password This is the HASHED password
	 * @param emailAddress This is the email address
	 * @param role This is the user role
	 */
	public User(String userName, String salt, String emailAddress, String role)
	{
		setUserName(userName);
		setSalt(salt);
		setEmailAddress(emailAddress);
		setRole(role);
	}
	
	/**
	 * Sets the userName
	 * @param userName This is the userName
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	/**
	 * Returns the userName
	 * @return userName This is the userName
	 */
	public String getUserName()
	{
		return this.userName;
	}
	
	/**
	 * Sets the HASHED password
	 * @param password This is the HASHED password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * Returns the HASHED password
	 * @return password This is the HASHED password
	 */
	public String getPassword()
	{
		return this.password;
	}
	
	/**
	 * Sets the password salt
	 * @param salt This is the password salt
	 */
	public void setSalt(String salt)
	{
		this.salt = salt;
	}
	
	/**
	 * Returns the password salt
	 * @return This is the password salt
	 */
	public String getSalt()
	{
		return this.salt;
	}
	
	/**
	 * Sets the email address
	 * @param emailAddress This is the email address
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	
	/**
	 * Returns the email address
	 * @return emailAddress This is the email address
	 */
	public String getEmailAddress()
	{
		return this.emailAddress;
	}
	
	/**
	 * Sets the users role
	 * @param role This is the users role
	 */
	public void setRole(String role)
	{
		this.role = role;
	}
	
	/**
	 * Returns the users role
	 * @return role This is the users role
	 */
	public String getRole()
	{
		return this.role;
	}

	public String getFName() 
	{
		return this.fName;
	}
	
	public String getLName()
	{
		return this.lName;
	}
}