package brogrammers.tutoring_room;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneSwitcher extends Pane{
	Stage stage;
	
	public SceneSwitcher(Stage stage) {
		this.stage = stage;
	}
	
	public Scene LoginScene() {
		VBox loginVBox = new VBox();
    	LoginView loginView = new LoginView(this.stage);
    	stage.setTitle("Sign in");
    	
    	loginVBox.getChildren().add(loginView);
        Scene loginScene = new Scene(loginVBox, 800, 500);
        
        loginVBox.setAlignment(Pos.CENTER);
        
      //loginScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());

        return loginScene;
	}

	public Scene DirectoryScene() {
		VBox directoryVBox = new VBox();
		DirectoryView directoryView = new DirectoryView(this.stage);

		directoryVBox.getChildren().add(directoryView);
        Scene directoryScene = new Scene(directoryVBox, 800, 500);
        stage.setTitle("Virtual Tutoring Room");
        
        directoryVBox.setAlignment(Pos.CENTER);
        
        //directoryViewScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        
        return directoryScene;
	}
	
	public Scene RoomScene() {
		VBox roomVBox = new VBox();
		RoomView roomView = new RoomView(this.stage);

		roomVBox.setAlignment(Pos.TOP_LEFT);
		roomVBox.getChildren().add(roomView);
        Scene roomScene = new Scene(roomVBox, 1000, 600);
        stage.setTitle("Virtual Tutoring Room");
        
        //directoryViewScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        
        return roomScene;
	}
}
