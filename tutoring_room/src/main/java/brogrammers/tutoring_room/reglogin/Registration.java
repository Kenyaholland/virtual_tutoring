package brogrammers.tutoring_room.reglogin;

import java.util.*;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.*;
import brogrammers.tutoring_room.data_access.UserDAO;
import javafx.collections.ObservableList;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Purpose: Handles registration of new user accounts. 
 */
public class Registration
{
	private final int MIN_USERNAME_LENGTH = 3;
	private final int MAX_USERNAME_LENGTH = 32;
	
	private SecurityUtils sh;
	private UserDAO dbo;
	private PasswordValidator pv;
	private String code;
	
	public Registration()
	{
		sh = new SecurityUtils();
		dbo = new UserDAO();
//		Thread thread = new Thread(new Runnable()
//		{
//			public void run()
//			{
//				dbo = new UserDAO();
//			}
//		});
//		
//		thread.start();
		
		pv = new PasswordValidator();
	}
	
	
	/**
	 * Registers a student user into the database
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param userName student's username
	 * @param password student's password
	 * @param emailAddress student's email
	 * @return a User if student is successfully registered, null if unsuccessful
	 */
	public User register(String firstName, String lastName, String userName, String password, String emailAddress)
	{
		String hash = sh.hashPassword(password);
		String salt = sh.generateSalt();
			
		String pass = hash.concat(salt);
			
		User user = new User(firstName, lastName, userName, salt, emailAddress, "Student");
			
		dbo.connectToDatabase();
			
		dbo.insertUser(user, pass);
			
		dbo.closeConnection();
			
		return user;
	}
	
	/**
	 * Registers a tutor user into the database
	 * @param tutoringCourses list of CRNs that the tutor helps with
	 * @param firstName tutor's first name
	 * @param lastName tutor's last name
	 * @param userName tutor's username
	 * @param password tutor's password
	 * @param emailAddress tutor's email
	 * @param enteredCode user-entered verification code
	 * @return a User if tutor is successfully registered, null if unsuccessful
	 */
	public User registerTutor(@SuppressWarnings("rawtypes") ObservableList tutoringCourses, String firstName, String lastName, String userName, String password, String emailAddress, String enteredCode)
	{
		if(isValidCode(enteredCode))
		{
			String hash = sh.hashPassword(password);
			String salt = sh.generateSalt();
			
			String pass = hash.concat(salt);
			
			User user = new User(firstName, lastName, userName, salt, emailAddress, "Tutor");
			
			dbo.connectToDatabase();
			
			dbo.insertUser(user, pass);
			
			for(Object c : tutoringCourses)
			{
				dbo.insertTutor(userName, c.toString());
			}
			
			dbo.closeConnection();
			
			return user;
		}
		return null;
	}
	
	/**
	 * Checks validity of user-entered username
	 * @param userName username entered
	 * @return true if username is valid, false if not
	 */
	public boolean isValidUsername(String userName)
	{
		if(userName.length() < MIN_USERNAME_LENGTH || userName.length() > MAX_USERNAME_LENGTH)
		{
			return false;
		}
		
		if(doesUserNameAlreadyExist(userName))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if user with given username exists in database
	 * @param userName username to look for
	 * @return true if user with given username exists already, false if not
	 */
	public boolean doesUserNameAlreadyExist(String userName)
	{
		dbo.connectToDatabase();
		
		if(dbo.checkExistenceOfUserName(userName))
		{
			dbo.closeConnection();
			
			return true;
		}
		dbo.closeConnection();
		
		return false;
	}
	
	/**
	 * Checks if email is valid format
	 * @param emailAddress email address to validate
	 * @return true if email is valid, false if not
	 */
	public boolean isValidEmail(String emailAddress)
	{	
		if(!Pattern.matches(".*@.*.edu", emailAddress))
		{
			return false;
		}
		
		if (doesEmailAlreadyExist(emailAddress))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if a user with the given email exists in the database
	 * @param emailAddress email address
	 * @return true if email exists, false if not
	 */
	public boolean doesEmailAlreadyExist(String emailAddress)
	{
		dbo.connectToDatabase();
		
		if(dbo.checkExistenceOfEmail(emailAddress))
		{
			dbo.closeConnection();
			
			return true;
		}
		dbo.closeConnection();
		
		return false;
	}
	
	/**
	 * Checks validity of user-entered password
	 * @param password password entered
	 * @return true if password is valid, false if not
	 */
	public boolean isValidPassword(String password)
	{
		if(pv.isPasswordValid(password))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks that user-entered registration info is valid
	 * @param userName username entered
	 * @param password password entered
	 * @param emailAddress email entered
	 * @return true if all fields are valid, false if at least one field is invalid
	 */
	public boolean isInformationGood(String userName, String password, String emailAddress)
	{
		if(!doesUserNameAlreadyExist(userName))
		{
			if(!doesEmailAlreadyExist(emailAddress) && isValidEmail(emailAddress)) 
			{
				if(pv.isPasswordValid(password))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Uses smtp to send an account verification code to user's email
	 * @param emailAddress user's email address (destination)
	 * @param username user's username
	 */
	public void sendVerificationCode(String emailAddress, String username)
	{
		String sender = "TheBrogrammers.VirtualTutoring@gmail.com";
		String host = "smtp.gmail.com";
		 
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		 
	    properties.setProperty("mail.smtp.host", host);
	 
	    Session session = Session.getInstance(properties, new javax.mail.Authenticator() 
	    {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
               return new PasswordAuthentication(sender, "cis4592capstone522");
            }
	    });
	    
	    this.code = sh.getRandomVerificationCode();
	    
	    String msg = "Hey, " + username + "!\n\nWelcome to Virtual Tutoring by The Brogrammers. In order to verify your new account, please enter the"
	    		+ " verification code below:\n\n" + code + "\n\nIf you were not the one who registered for an account then you can just"
	    				+ " ignore this email. Otherwise, we look forward to seeing you soon. Happy learning!";
	      
	    try
	    {
	    	MimeMessage message = new MimeMessage(session);
	 
	        message.setFrom(new InternetAddress(sender));
	 
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
	 
	        message.setSubject("Security Notice - Verification Code for Virtual Tutoring");
	        message.setText(msg);
	 
	        Transport.send(message);
	        System.out.println("Mail successfully sent");
        }
	    catch(Exception mex)
	    {
	        mex.printStackTrace();
	    }
	}
	
	/**
	 * Checks that verification code entered by user matches the code sent
	 * @param enteredCode code entered by user
	 * @return true if codes match, false if not
	 */
	public boolean isValidCode(String enteredCode)
	{
		if(enteredCode.contentEquals(code))
		{
			return true;
		}
		return false;
	}
}