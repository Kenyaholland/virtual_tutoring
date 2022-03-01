package brogrammers.tutoring_room;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

    	SceneSwitcher switcher = new SceneSwitcher(stage);
    	switcher.makeRooms();
   
    	stage.setScene(switcher.LoginScene());
    	stage.centerOnScreen();
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

}