package brogrammers.tutoring_room;

import brogrammers.tutoring_room.controllers.ClientController;
import brogrammers.tutoring_room.controllers.SessionController;
import brogrammers.tutoring_room.data_access.CoursesDAO;
import brogrammers.tutoring_room.data_access.UserDAO;
import brogrammers.tutoring_room.views.ChangePasswordView;
import brogrammers.tutoring_room.views.DirectoryView;
import brogrammers.tutoring_room.views.LoginCredentialsView;
import brogrammers.tutoring_room.views.LoginView;
import brogrammers.tutoring_room.views.RegistrationView;
import brogrammers.tutoring_room.views.RoomView;
import brogrammers.tutoring_room.views.TutorRegView;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneSwitcher extends Pane{
	private Stage stage;
	private SessionController sessionCtrl;
	private ClientController clientCtrl;
	private ArrayList<RoomView> rooms;
	
	public static CoursesDAO course_dao = new CoursesDAO();
	
	public static HashSet<String> invalidUsernames;
	public static HashSet<String> invalidEmails;
	
	public SceneSwitcher(Stage stage) 
	{
		this.stage = stage;
		this.sessionCtrl = null;
		rooms = new ArrayList<RoomView>();
		
		for (int i = 0; i < 6; i++) {
			rooms.add(null);
		}
	}
	
	public void makeRoom(int roomNum, String sessionId) 
	{
		RoomView roomView = new RoomView(stage, this, clientCtrl, roomNum, sessionId);
		rooms.add(roomNum-1, roomView);
	}
	
	public Scene LoginScene(boolean onLogout)
	{
		HBox loginBox = new HBox();
    	LoginView loginView = new LoginView(this.stage, this);
    	stage.setTitle("Sign in");
    	
    	loginBox.setAlignment(Pos.CENTER);
    	loginBox.getChildren().add(loginView);
        Scene loginScene = new Scene(loginBox, 1000, 500);
        
      //loginScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        if (onLogout) {
    		System.out.println("LoginScene() - on logout");
        	sessionCtrl.closeSession();
        }
        
        return loginScene;
	}
	
	public Scene LoginCredentialsScene(boolean tutor) 
	{
    	LoginCredentialsView loginCredView = new LoginCredentialsView(stage, this);
    	stage.setTitle("Sign in");
 
        Scene loginCredScene = loginCredView.getScene();
        
        //loginCredScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());

        return loginCredScene;
	}
	
	public Scene ChangePasswordScene()
	{
		ChangePasswordView changePasswordView = new ChangePasswordView(stage, this);
		stage.setTitle("Forgot Password");
		
		Scene changepwScene = changePasswordView.getScene();
		
		return changepwScene;
	}
	
	public Scene RegistrationScene() 
	{
    	RegistrationView regView = new RegistrationView(stage, this);
    	stage.setTitle("Register A Student Account");
 
        Scene regScene = regView.getScene();
        
        //loginCredScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());

        return regScene;
	}
	
	public Scene TutorRegistrationScene() 
	{
		TutorRegView regView = new TutorRegView(stage, this);
    	stage.setTitle("Register A Tutor Account");
 
        Scene regScene = regView.getScene();
        
        //loginCredScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());

        return regScene;
	}

	public Scene DirectoryScene() 
	{
		VBox directoryVBox = new VBox();
		DirectoryView directoryView = new DirectoryView(this.stage, this, sessionCtrl.getSessionId());

		directoryVBox.setAlignment(Pos.TOP_LEFT);
		directoryVBox.getChildren().add(directoryView);
        Scene directoryScene = new Scene(directoryVBox, 1000, 600);
        stage.setTitle("Virtual Tutoring");
        
        //directoryViewScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        
        return directoryScene;
	}
	
	public Scene RoomScene(int roomNum)
	{
		makeRoom(roomNum, sessionCtrl.getSessionId());  //rebuild rooms here so data can be updated
		System.out.println("Switching to room " + roomNum);
		VBox roomVBox = new VBox();

		roomVBox.setAlignment(Pos.TOP_LEFT);
		roomVBox.getChildren().add(rooms.get(roomNum - 1));
        Scene roomScene = new Scene(roomVBox, 1000, 600);
        stage.setTitle("Virtual Tutoring");
        
        clientCtrl.joinRoom(String.valueOf(roomNum));
        
        //directoryViewScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        
        return roomScene;
	}
	
	public void openSession(String username)
	{
		this.sessionCtrl = new SessionController(username);
		sessionCtrl.openSession();
		clientCtrl = new ClientController("localhost", 55555);
		clientCtrl.sendUsername(username);
	}
	
	public void closeSession()
	{
		sessionCtrl.closeSession();
		clientCtrl.disconnect();
	}
	
	public boolean checkActiveSession()
	{
		if (sessionCtrl == null) {
			return false;
		}
		else {
			return sessionCtrl.getActiveSession();
		}
	}
}