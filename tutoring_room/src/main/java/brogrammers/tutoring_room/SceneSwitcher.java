package brogrammers.tutoring_room;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneSwitcher extends Pane{
	private Stage stage;
	
	public ArrayList<RoomView> rooms;
	
	public SceneSwitcher(Stage stage) {
		this.stage = stage;
		rooms = new ArrayList<RoomView>();
	}
	
	public void makeRooms() {
		for(int i = 0; i < 6; i++) {
			RoomView roomView = new RoomView(stage, this);
			rooms.add(roomView);
		}
	}
	
	public Scene LoginScene() {
		HBox loginBox = new HBox();
    	LoginView loginView = new LoginView(this.stage, this);
    	stage.setTitle("Sign in");
    	
    	loginBox.setAlignment(Pos.CENTER);
    	loginBox.getChildren().add(loginView);
        Scene loginScene = new Scene(loginBox, 400, 500);
        
      //loginScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());

        return loginScene;
	}

	public Scene DirectoryScene() {
		VBox directoryVBox = new VBox();
		DirectoryView directoryView = new DirectoryView(this.stage, this);

		directoryVBox.setAlignment(Pos.TOP_LEFT);
		directoryVBox.getChildren().add(directoryView);
        Scene directoryScene = new Scene(directoryVBox, 1000, 600);
        stage.setTitle("Virtual Tutoring");
        
        //directoryViewScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        
        return directoryScene;
	}
	
	public Scene RoomScene(int roomNum) {
		System.out.println("Switching to room " + roomNum);
		VBox roomVBox = new VBox();

		roomVBox.setAlignment(Pos.TOP_LEFT);
		roomVBox.getChildren().add(rooms.get(roomNum - 1));
        Scene roomScene = new Scene(roomVBox, 1000, 600);
        stage.setTitle("Virtual Tutoring");
        
        //directoryViewScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        
        return roomScene;
	}
}
