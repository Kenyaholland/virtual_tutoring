package brogrammers.tutoring_room.reglogin;

import java.util.*;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.*;

import brogrammers.tutoring_room.data_access.UserDAO;

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
	
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String emailAddress;
	
	private String enteredCode;
	private String code;
	
	public Registration()
	{
		sh = new SecurityUtils();
		dbo = new UserDAO();
		pv = new PasswordValidator();
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public void registerUser()
	{
		if(isInformationGood(userName, password, emailAddress))
		{
			register(firstName, lastName, userName, password, emailAddress, enteredCode);
		}
	}
	
	public User register(String firstName, String lastName, String userName, String password, String emailAddress, String enteredCode)
	{
		if(isValidCode(enteredCode))
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
		return null;
	}
	
	public User registerTutor(String tutoringCourse, String firstName, String lastName, String userName, String password, String emailAddress, String enteredCode)
	{
		if(isValidCode(enteredCode))
		{
			String hash = sh.hashPassword(password);
			String salt = sh.generateSalt();
			
			String pass = hash.concat(salt);
			
			User user = new User(firstName, lastName, userName, salt, emailAddress, "Tutor");
			
			dbo.connectToDatabase();
			
			dbo.insertUser(user, pass);
			dbo.insertTutor(userName, tutoringCourse);
			
			dbo.closeConnection();
			
			return user;
		}
		return null;
	}
	
	public boolean isUserNameValid(String userName)
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
	
	public boolean isInformationGood(String userName, String password, String emailAddress)
	{
		if(!doesUserNameAlreadyExist(userName))
		{
			if(!doesEmailAlreadyExist(emailAddress))
			{
				if(pv.isPasswordValid(password))
				{
					return true;
				}
			}
		}
		return false;
	}
	
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
	
	public boolean isEmailValid(String emailAddress)
	{	
		if(Pattern.matches(".*@.*.edu", emailAddress))
		{
			return true;
		}
		
		return false;
	}
	
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
	
	public boolean isValidCode(String enteredCode)
	{
		if(enteredCode.contentEquals(code))
		{
			return true;
		}
		return false;
	}
}