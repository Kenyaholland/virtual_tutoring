package brogrammers.tutoring_room.views;

import org.controlsfx.control.CheckComboBox;
import brogrammers.tutoring_room.SceneSwitcher;
import brogrammers.tutoring_room.data_access.UserDAO;
import brogrammers.tutoring_room.reglogin.Registration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Purpose: GUI for tutor registrations
 */
public class TutorRegView 
{
	private Stage stage;
	private SceneSwitcher switcher;
	private Registration registration = new Registration();
	
	private Scene scene;
	private VBox root = new VBox();
	private HBox name = new HBox();
	private HBox buttons = new HBox();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CheckComboBox courses = new CheckComboBox(FXCollections.observableArrayList(SceneSwitcher.course_dao.getCourses()));
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	@SuppressWarnings("rawtypes")
	private ObservableList tutoringCourses;
	
	int success = -1;
	
	public TutorRegView(Stage stage, SceneSwitcher switcher)
	{
		this.stage = stage;
		this.switcher = switcher;
		
		init();
	}
    
	public void init()
    {
    	root.setAlignment(Pos.CENTER);
    	buttons.setAlignment(Pos.CENTER);
    	name.setAlignment(Pos.CENTER);
    	
    	root.setSpacing(16);
    	name.setSpacing(16);
    	
    	Text title = new Text(".:Tutor Registration:.");
    	title.setFont(Font.font("Verdana", FontWeight.BOLD, 32));
    	
    	Hyperlink login = new Hyperlink("Return to Login");
    	
    	TextField firstName_field = new TextField("");
    	TextField lastName_field = new TextField("");
    	TextField username_field = new TextField("");
    	PasswordField password_field = new PasswordField();
    	TextField email_field = new TextField("");
    	firstName_field.setPromptText("First Name");
    	lastName_field.setPromptText("Last Name");
    	username_field.setPromptText("Username");
    	password_field.setPromptText("Password");
    	email_field.setPromptText("Email Address");
    	courses.setTitle("Select Courses");
    	
    	firstName_field.setMaxWidth(256);
    	firstName_field.setMaxHeight(128);
    	
    	lastName_field.setMaxWidth(256);
    	lastName_field.setMaxHeight(128);
    	
    	username_field.setMaxWidth(256);
    	username_field.setMaxHeight(128);
    	
    	password_field.setMaxWidth(256);
    	password_field.setMaxHeight(128);
    	
    	email_field.setMaxWidth(256);
    	email_field.setMaxHeight(128);
    	
    	courses.setMaxWidth(256);
    	courses.setMaxHeight(128);
    		
    	Button register = new Button("Register");
    	
    	buttons.getChildren().add(register);
    	
    	name.getChildren().add(firstName_field);
    	name.getChildren().add(lastName_field);
    	
    	root.getChildren().add(title);
    	root.getChildren().add(name);
    	root.getChildren().add(username_field);
    	root.getChildren().add(password_field);
    	root.getChildren().add(email_field);
    	root.getChildren().add(courses);
    	root.getChildren().add(buttons);
    	root.getChildren().add(login);
    	
    	register.setOnAction(e -> getCredentials(courses, firstName_field, lastName_field, username_field, password_field, email_field));
    	login.setOnAction(e -> stage.setScene(switcher.LoginCredentialsScene(false)));
    	
    	scene = new Scene(root, 1000, 500);
    }
    
    boolean check_username;
	boolean check_email;
    
	public void getCredentials(@SuppressWarnings("rawtypes") CheckComboBox courses, TextField firstName_field, TextField lastName_field, TextField username_field, TextField password_field, TextField email_field)
    {		
    	firstName = firstName_field.getText();
    	lastName = lastName_field.getText();
    	username = username_field.getText();
    	password = password_field.getText();
    	email = email_field.getText();
    	
    	try
    	{
    		tutoringCourses = courses.getCheckModel().getCheckedItems();
    		
    		if(tutoringCourses.size() == 0)
    		{
    			tutoringCourses = null;
    		}
    	}
    	catch(Exception error)
    	{
    		tutoringCourses = null;
    	}
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Registration Alert");
    	alert.setHeaderText("Information about your registration attempt");
    	
    	boolean check_email_validity = registration.isValidEmail(email);
    	
    	Thread thread = new Thread(new Runnable()
		{
			public void run()
			{
				UserDAO temp = new UserDAO();
				temp.connectToDatabase();
				check_username = temp.checkExistenceOfUserName(username);
				check_email = temp.checkExistenceOfEmail(email);
				temp.closeConnection();
			}
		});
		
		thread.start();	
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty() || tutoringCourses == null)
    	{
    		alert.setContentText("You have left one or more fields blank. Please make sure you have filled out all fields to complete your registration.");
    		alert.showAndWait();
    		return;
    	}
    	else if(check_username)
    	{
    		alert.setContentText("The username you have entered is unavailable.");
    		alert.showAndWait();
    	}
    	else if(check_email || !check_email_validity)
    	{
    		alert.setContentText("The email address you have entered is either unavailable or is invalid. Make sure that you have entered a valid .edu email address to complete your registration.");
    		alert.showAndWait();
    	}
    	else if(!(registration.isValidPassword(password)))
    	{
    		alert.setContentText("Invalid password. Passwords must be at-least 8 characters in length, "
    				+ "contain a mix of upper and lower case characters, at-least one digit (0-9), and at-least one of the following special characters !@#?]");
    		
    		alert.showAndWait();
    	}
    	else
    	{
    		TextInputDialog code_field = new TextInputDialog();
    		
    		code_field.setTitle("Registration Alert");
    		code_field.setHeaderText("Information about your registration attempt");
    		code_field.setContentText("A verification code has been sent to the email provided, enter it in the provided field to complete your registration.");
    		
    		Thread thread2 = new Thread(new Runnable()
    		{
    			public void run()
    			{
    				registration.sendVerificationCode(email, username);
    			}
    		});
    		
    		thread2.start();
    		
    		code_field.showAndWait();
    		
    		String code = code_field.getEditor().getText();
    		
    		if(registration.isValidCode(code))
    		{
    			registerForApp(registration, tutoringCourses, firstName, lastName, username, password, email, code);
    		}
    		else
    		{
    			alert.setContentText("You have entered an invalid code. Try again later.");
    			alert.showAndWait();
    		}
    	}
    }
    
	public void registerForApp(Registration registration, @SuppressWarnings("rawtypes") ObservableList tutoringCourses, String firstName, String lastName, String username, String password, String email, String code)
    {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Registration Alert");
    	alert.setHeaderText("Information about your registration attempt");
    	
    	if(registration.registerTutor(tutoringCourses, firstName, lastName, username, password, email, code) != null)
    	{
    		alert.setContentText("You have been successfully registered. You may now login.");
    	}
    	else
    	{
    		alert.setContentText("An unexpected error occurred during your registration. Please try again later");
    	}
    	alert.showAndWait();
    }
    
    public Scene getScene()
    {
    	return this.scene;
    }
}