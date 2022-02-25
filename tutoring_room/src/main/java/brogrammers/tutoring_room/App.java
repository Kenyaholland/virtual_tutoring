package brogrammers.tutoring_room;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

    	SceneSwitcher switcher = new SceneSwitcher(stage);
   
    	stage.setScene(switcher.LoginScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}