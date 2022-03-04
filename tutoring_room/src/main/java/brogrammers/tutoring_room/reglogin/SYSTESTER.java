package brogrammers.tutoring_room.reglogin;

import java.util.Scanner;

/**
 * Demonstrates functionality of login/registration system through command line interface
 */
public class SYSTESTER 
{
	private Scanner in = new Scanner(System.in);
	private Login login = new Login();
	private Registration register = new Registration();
	private PasswordValidator pv = new PasswordValidator();
	
	public SYSTESTER(){}
	
	public void run()
	{
		int option = 0;
		
		while(option != 3)
		{
			System.out.println(".:Menu:.");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			
			option = in.nextInt();
			
			if(option == 1)
			{
				login();
			}
			else if(option == 2)
			{
				register();
			}
			else if(option == 3)
			{
				System.exit(0);
			}
			else
			{
				System.out.println("Invalid option");
			}
		}
		in.close();
	}
	
	public void login()
	{
		System.out.print("Enter your username: ");
		String username = in.next();
		System.out.print("Enter your password: ");
		String password = in.next();
		
		if(login.login(username, password))
		{
			System.out.println("Successfully authenticated, logging in...");
		}
		else
		{
			System.out.println("Invalid credentials...");
		}
	}
	
	public void register()
	{
		System.out.print("Enter a username: ");
		String username = in.next();
		
		if(register.doesUserNameAlreadyExist(username))
		{
			System.out.println("Invalid username.");
			return;
		}
		
		register.setUserName(username);
		
		System.out.println("Password requirements: At-least 8 characters, a mix of upper and lower case, at-least one number, and at-least one of the following special characters: !@#?]");
		System.out.print("Enter a password: ");
		String password = in.next();
		
		if(!pv.isPasswordValid(password))
		{
			System.out.println("Invalid password.");
			return;
		}
		
		System.out.print("Enter an email address: ");
		String email = in.next();
		
		if(register.doesEmailAlreadyExist(email) || !register.isEmailValid(email))
		{
			System.out.println("Invalid email");
			return;
		}
		
		register.sendVerificationCode(email, username);
		
		System.out.println("A verification code has been sent to your email. Enter the code below:");
		String code = in.next();
		
		if(!register.isValidCode(code))
		{
			return;
		}
		register.register(username, password, email, code);
		System.out.println("Successfully registered");
	}
}
