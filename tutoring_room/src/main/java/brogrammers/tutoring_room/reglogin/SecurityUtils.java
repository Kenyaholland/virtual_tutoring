package brogrammers.tutoring_room.reglogin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Purpose: Contains methods related to security 
 */
public class SecurityUtils
{
	public SecurityUtils(){}
	
	/**
	 * Hashes a password using a SHA3-256 algorithm
	 * @param password This is the password to be hashed
	 * @return sha3Hex This is the hash in hexadecimal
	 */
	public String hashPassword(String password)
	{		
		try
		{
			final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
			final byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			
			String sha3Hex = byteArrayToHex(hashbytes);
			
			return sha3Hex;
		}
		catch(Exception error){}
		
		return null;
	}
	
	/**
	 * Generates a random 32 character salt
	 * @return salt This is the salt
	 */
	public String generateSalt()
	{
		String salt = RandomStringUtils.random(32, 0, 0, true, true, null, new SecureRandom());
		
		return salt;
	}
	
	/**
	 * Converts a byte array to a hex string
	 * @param arr This is the byte array
	 * @return The converted hex string
	 */
	public static String byteArrayToHex(byte[] arr) 
	{
		StringBuilder sb = new StringBuilder(arr.length * 2);
		
		for(byte b : arr)
		{
		      sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	/**
	 * Returns a six digit verification code
	 * @return number This is the six digit verification code
	 */
	public String getRandomVerificationCode() 
	{
	    Random rand = new Random();
	    int number = rand.nextInt(999999);
	    
	    return String.format("%06d", number);
	}
}