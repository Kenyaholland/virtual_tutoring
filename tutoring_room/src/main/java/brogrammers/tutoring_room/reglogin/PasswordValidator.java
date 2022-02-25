package brogrammers.tutoring_room.reglogin;

/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Purpose: Ensures that user passwords follow a set of common rules to make them more secure:
 * length of 8 or more characters, a mix of upper and lower case characters, a mix of letters and numbers, 
 * and at-least one special character -> !@#?]
 */
public class PasswordValidator 
{
	private final int MIN_PASS_LENGTH = 8;
	
	public PasswordValidator(){}
	
	/**
	 * Checks if a password complies with all security rules
	 * @param password This is the password to be checked
	 * @return This is the state of compliance, true or false
	 */
	public boolean isPasswordValid(String password)
	{
		if(password.length() >= MIN_PASS_LENGTH && containsUpperCase(password) 
				&& containsLowerCase(password) && containsNumber(password) 
				&& containsLetter(password) && containsSpecialCharacter(password))
		{
			return true;
		}
		return false;                                                                                                                                          
	}
	
	/**
	 * Checks if a password contains an upper case character
	 * @param password This is the password to be checked
	 * @return This is the state of compliance, true or false
	 */
	public boolean containsUpperCase(String password)
	{
		for(int index = 0; index < password.length(); index++)
		{
			char ch = password.charAt(index);
			
			if(Character.isUpperCase(ch))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a password contains a lower case character
	 * @param password This is the password to be checked
	 * @return This is the state of compliance, true or false
	 */
	public boolean containsLowerCase(String password)
	{
		for(int index = 0; index < password.length(); index++)
		{
			char ch = password.charAt(index);
			
			if(Character.isLowerCase(ch))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a password contains a number
	 * @param password This is the password to be checked
	 * @return This is the state of compliance, true or false
	 */
	public boolean containsNumber(String password)
	{
		for(int index = 0; index < password.length(); index++)
		{
			char ch = password.charAt(index);
			
			if(Character.isDigit(ch))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a password contains a letter
	 * @param password This is the password to be checked
	 * @return This is the state of compliance, true or false
	 */
	public boolean containsLetter(String password)
	{
		for(int index = 0; index < password.length(); index++)
		{
			char ch = password.charAt(index);
			
			if(Character.isAlphabetic(ch))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a password contains a special character
	 * @param password This is the password to be checked
	 * @return This is the state of compliance, true or false
	 */
	public boolean containsSpecialCharacter(String password)
	{
		for(int index = 0; index < password.length(); index++)
		{
			char ch = password.charAt(index);
			
			if(ch == '!' || ch == '@' || ch == '#' || ch == '?' || ch == ']')
			{
				return true;
			}
		}
		return false;
	}
}
