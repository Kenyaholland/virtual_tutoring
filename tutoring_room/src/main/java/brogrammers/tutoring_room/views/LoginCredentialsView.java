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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;

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
    	
    	Label title = new Label(".:Login:.");
    	title.setStyle("-fx-font-family: Helvetica; -fx-font-size: 28px; -fx-font-weight: BOLD; -fx-text-fill: #7cafc2");
    	
    	Hyperlink forgot_password = new Hyperlink("Forgot Password");
    	Hyperlink register = new Hyperlink("No Account? Click Here to Register as a Student");
    	Hyperlink tutor_reg = new Hyperlink("No Account? Click Here to Register as a Tutor");

    	forgot_password.setStyle("-fx-font-family: Helvetica");
    	register.setStyle("-fx-font-family: Helvetica");
    	tutor_reg.setStyle("-fx-font-family: Helvetica");
    	
    	TextField username_field = new TextField("");
    	username_field.setPromptText("Username");
    	username_field.setStyle("-fx-background-radius: 4; -fx-background-color: #585858; -fx-font-family: Helvetica;"
    			+ "-fx-prompt-text-fill: #888888; -fx-text-fill: #e8e8e8");
    	username_field.setMaxWidth(256);
    	username_field.setMaxHeight(128);
    	
    	PasswordField password_field = new PasswordField();
    	password_field.setPromptText("Password");
    	password_field.setStyle("-fx-background-radius: 4; -fx-background-color: #585858; -fx-prompt-text-fill: #888888;"
    			+ "-fx-text-fill: #282828");
    	password_field.setMaxWidth(256);
    	password_field.setMaxHeight(128);
    	
    	Button loginBtn = new Button("Login");
		loginBtn.setStyle("-fx-background-radius: 4; -fx-background-color: #e8e8e8; -fx-font-family: Helvetica;"
				+ "-fx-font-size: 12px; -fx-text-fill: #181818;");
    	
    	buttons.getChildren().add(loginBtn);
    	
    	root.setBackground(new Background(new BackgroundFill(Color.web("181818"), null, null)));
    	root.getChildren().add(title);
    	root.getChildren().add(username_field);
    	root.getChildren().add(password_field);
    	root.getChildren().add(buttons);
    	root.getChildren().add(forgot_password);
    	root.getChildren().add(register);
    	root.getChildren().add(tutor_reg);
    	
    	loginBtn.setOnAction(e -> getCredentials(username_field, password_field));
    	
    	forgot_password.setOnAction(e -> stage.setScene(switcher.ChangePasswordScene()));
    	
    	register.setOnAction(e -> stage.setScene(switcher.RegistrationScene()));
    	tutor_reg.setOnAction(e -> stage.setScene(switcher.TutorRegistrationScene()));
    	
    	scene = new Scene(root, 600, 500);
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
    	alert.setX(250);
    	alert.setY(200);
    	alert.getDialogPane().setBackground(new Background(new BackgroundFill(Color.web("E8E8E8"), null, null)));
    	
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

