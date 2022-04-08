package brogrammers.tutoring_room.reglogin;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import brogrammers.tutoring_room.data_access.UserDAO;

public class ChangePassword 
{
	private PasswordValidator pv = new PasswordValidator();
	private SecurityUtils su = new SecurityUtils();
	private String code;
	
	public ChangePassword(){}
	
	/**
	 * Checks if the newly entered passwords match
	 * @param newPassword This is the new password
	 * @param confirmNewPassword This is the new password confirmation
	 * @return
	 */
	public boolean doPasswordsMatch(String newPassword, String confirmNewPassword)
	{
		if(newPassword.contentEquals(confirmNewPassword))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the new password is valid
	 * @param newPassword This is the new password
	 * @return
	 */
	public boolean isPasswordValid(String newPassword)
	{
		if(pv.isPasswordValid(newPassword))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a provided username and email exist
	 * @param username This is the username
	 * @param email This is the email address
	 * @return
	 */
	public boolean doesUserExist(String username, String email)
	{
		UserDAO ud = new UserDAO();
		ud.connectToDatabase();
		if(ud.doesUserExist(username, email))
		{
			ud.closeConnection();
			return true;
		}
		ud.closeConnection();
		
		return false;
	}
	
	/**
	 * Sends a verification code to the user
	 * @param emailAddress This is the email address
	 * @param username This is the username
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
	    
	    this.code = su.getRandomVerificationCode();
	    
	    String msg = "Hey, " + username + "!\n\nWe have recieved a password change request for your account. In order to complete your password change, please enter the"
	    		+ " verification code below:\n\n" + code + "\n\nIf you were not the one who requested a password change for your account then you can just"
	    				+ " ignore this email. Otherwise, we look forward to seeing you soon. Happy learning!";
	      
	    try
	    {
	    	MimeMessage message = new MimeMessage(session);
	 
	        message.setFrom(new InternetAddress(sender));
	 
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
	 
	        message.setSubject("Security Notice - Password Change for Virtual Tutoring");
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
	 * Changes a users password
	 * @param username This is the username
	 * @param email This is the email
	 * @param newPassword This is the new password
	 * @return
	 */
	public boolean changePassword(String username, String email, String newPassword)
	{
		String hash = su.hashPassword(newPassword);
		String salt = su.generateSalt();
		
		String pass = hash.concat(salt);
		
		UserDAO ud = new UserDAO();
		ud.connectToDatabase();
		
		boolean result = ud.updatePassword(username, email, pass, salt);
		
		ud.closeConnection();
		
		return result;
	}
	
	/**
	 * Checks if the entered code is valid
	 * @param enteredCode This is the entered code
	 * @return
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
