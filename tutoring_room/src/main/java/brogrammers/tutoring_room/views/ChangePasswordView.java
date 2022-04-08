package brogrammers.tutoring_room.views;

import brogrammers.tutoring_room.SceneSwitcher;
import brogrammers.tutoring_room.reglogin.ChangePassword;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangePasswordView 
{
	private SceneSwitcher switcher;
	private Stage stage;
	
	private Scene scene;
	private VBox root = new VBox();
	private HBox buttons = new HBox();
	
	private ChangePassword pwChanger = new ChangePassword();
	
	private String username;
	private String email;
	private String newPassword;
	private String confirmNewPassword;
	
	public ChangePasswordView(Stage stage, SceneSwitcher switcher)
	{
		this.stage = stage;
		this.switcher = switcher;
		
		init();
	}
    
    public void init()
    {
    	root.setAlignment(Pos.CENTER);
    	buttons.setAlignment(Pos.CENTER);
    	
    	root.setSpacing(16);
    	
    	Text title = new Text(".:Change Password:.");
    	title.setFont(Font.font("Verdana", FontWeight.BOLD, 32));
    	
    	Hyperlink login = new Hyperlink("Have an Account? Click Here to Login");
    	Hyperlink register = new Hyperlink("No Account? Click Here to Register");
    	
    	TextField username_field = new TextField("");
    	TextField email_field = new TextField("");
    	PasswordField newPassword_field = new PasswordField();
    	PasswordField confirmNewPassword_field = new PasswordField();
    	username_field.setPromptText("Username");
    	email_field.setPromptText("Email Address");
    	newPassword_field.setPromptText("New Password");
    	confirmNewPassword_field.setPromptText("Confirm New Password");
    	
    	username_field.setMaxWidth(256);
    	username_field.setMaxHeight(128);
    	
    	email_field.setMaxWidth(256);
    	email_field.setMaxHeight(128);
    	
    	newPassword_field.setMaxWidth(256);
    	newPassword_field.setMaxHeight(128);
    	
    	confirmNewPassword_field.setMaxWidth(256);
    	confirmNewPassword_field.setMaxHeight(128);
    	
    	Button request = new Button("Request Password Change");
    	
    	buttons.getChildren().add(request);
    	
    	root.getChildren().add(title);
    	root.getChildren().add(username_field);
    	root.getChildren().add(email_field);
    	root.getChildren().add(newPassword_field);
    	root.getChildren().add(confirmNewPassword_field);
    	root.getChildren().add(buttons);
    	root.getChildren().add(login);
    	root.getChildren().add(register);
    	
    	request.setOnAction(e -> getCredentials(username_field, email_field, newPassword_field, confirmNewPassword_field));
    	
    	login.setOnAction(e -> stage.setScene(switcher.LoginCredentialsScene(false)));
    	register.setOnAction(e -> stage.setScene(switcher.RegistrationScene()));
    	
    	scene = new Scene(root, 1000, 500);
    }
    
    private boolean checkExistence = false;
    
    public void getCredentials(TextField username_field, TextField email_field, PasswordField newPassword_field, PasswordField confirmNewPassword_field)
    {
    	username = username_field.getText();
    	email = email_field.getText();
    	newPassword = newPassword_field.getText();
    	confirmNewPassword = confirmNewPassword_field.getText();
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Security Alert");
    	alert.setHeaderText("Information about your password change attempt");
    	
    	if(username.trim().isEmpty() || email.trim().isEmpty() || newPassword.trim().isEmpty() || confirmNewPassword.trim().isEmpty())
    	{
    		alert.setContentText("You have left one or more fields blank. Please make sure you have filled out all fields to complete your password change request.");
    		alert.showAndWait();
    		return;
    	}
    	
    	if(!(pwChanger.doPasswordsMatch(newPassword, confirmNewPassword)))
    	{
        	alert.setContentText("The passwords you have entered do not match.");
        	
        	alert.showAndWait();
        	
    		return;
    	}
    	
    	if(!(pwChanger.isPasswordValid(newPassword)))
    	{
    		alert.setContentText("Invalid password. Passwords must be at-least 8 characters in length, "
    				+ "contain a mix of upper and lower case characters, at-least one digit (0-9), and at-least one of the following special characters !@#?]");
    		
    		alert.showAndWait();
    		
    		return;
    	}
    	
    	TextInputDialog code_field = new TextInputDialog();
    	
    	Thread thread = new Thread(new Runnable()
		{
			public void run()
			{
				checkExistence = pwChanger.doesUserExist(username, email);
			}
		});
    	
    	thread.start();
    	try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(checkExistence)
		{

    		code_field.setTitle("Security Alert");
    		code_field.setHeaderText("Information about your password change attempt");
    		code_field.setContentText("A verification code has been sent to the email provided, enter it in the provided field to complete your password change.");
    		
    		pwChanger.sendVerificationCode(email, username);
    		
    		code_field.showAndWait();
    		
    		String code = code_field.getEditor().getText();
    		boolean updated;
    		
    		if(pwChanger.isValidCode(code))
    		{
    			System.out.println("Valid code");
    			updated = pwChanger.changePassword(username, email, newPassword);
    			
    			if(updated)
    			{
    				alert.setContentText("Your password has been updated. You may now login with your new credentials.");
    				alert.showAndWait();
    			}
    		}
    		else
    		{
    			alert.setContentText("You have entered an invalid code. Try again later.");
    			alert.showAndWait();
    		}
		}
		else
		{
			alert.setContentText("An error occurred while processing your request. Please try again later.");
			alert.showAndWait();
		}
    }
    
    public Scene getScene()
    {
    	return this.scene;
    }
}
