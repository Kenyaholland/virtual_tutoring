package brogrammers.tutoring_room.views;

import brogrammers.tutoring_room.SceneSwitcher;
import brogrammers.tutoring_room.reglogin.Login;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
 * Purpose: GUI for entering login credentials
 */
public class LoginCredentialsView
{
	private SceneSwitcher switcher;
	private Stage stage;
	
	private Scene scene;
	private VBox root = new VBox();
	private HBox buttons = new HBox();
	
	private String username;
	private String password;
	
	int success = -1;
	
	public LoginCredentialsView(Stage stage, SceneSwitcher switcher)
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
    	
    	Text title = new Text(".:Login:.");
    	title.setFont(Font.font("Verdana", FontWeight.BOLD, 32));
    	
    	Hyperlink forgot_password = new Hyperlink("Forgot Password");
    	Hyperlink register = new Hyperlink("No Account? Click Here to Register");
    	Hyperlink tutor_reg = new Hyperlink("I'm a tutor");
    	
    	TextField username_field = new TextField("");
    	PasswordField password_field = new PasswordField();
    	username_field.setPromptText("Username");
    	password_field.setPromptText("Password");
    	
    	username_field.setMaxWidth(256);
    	username_field.setMaxHeight(128);
    	
    	password_field.setMaxWidth(256);
    	password_field.setMaxHeight(128);
    	
    	Button login = new Button("Login");
    	
    	buttons.getChildren().add(login);
    	
    	root.getChildren().add(title);
    	root.getChildren().add(username_field);
    	root.getChildren().add(password_field);
    	root.getChildren().add(buttons);
    	root.getChildren().add(forgot_password);
    	root.getChildren().add(register);
    	root.getChildren().add(tutor_reg);
    	
    	login.setOnAction(e -> getCredentials(username_field, password_field));
    	
    	forgot_password.setOnAction(e -> stage.setScene(switcher.ChangePasswordScene()));
    	
    	register.setOnAction(e -> stage.setScene(switcher.RegistrationScene()));
    	tutor_reg.setOnAction(e -> stage.setScene(switcher.TutorRegistrationScene()));
    	
    	scene = new Scene(root, 1000, 500);
    }
    
    public void getCredentials(TextField username_field, TextField password_field)
    {
    	username = username_field.getText();
    	password = password_field.getText();
    	
    	loginToApp(username, password);
    }
    
    private boolean checklogin = false;
    
    public void loginToApp(String username, String password)
    {	
    	Login login = new Login();
    	
    	Thread thread = new Thread(new Runnable()
		{
			public void run()
			{
				checklogin = login.login(username, password);
			}
		});
		
		thread.start();	
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Login Alert");
    	alert.setHeaderText("Information about your login attempt");
    	
    	if(checklogin)
    	{
    		alert.setContentText("You have been successfully authenticated.");
    		switcher.openSession(username);
    		stage.setScene(switcher.DirectoryScene());
    	}
    	else
    	{
    		alert.setContentText("The credentials you have entered are invalid.");
    	}
    	
    	alert.showAndWait();
	}
    
    public Scene getScene()
    {
    	return this.scene;
    }
}

