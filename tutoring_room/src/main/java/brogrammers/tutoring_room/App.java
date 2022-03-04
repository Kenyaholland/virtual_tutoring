package brogrammers.tutoring_room;

//import brogrammers.tutoring_room.reglogin.SYSTESTER;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application 
{
    @Override
    public void start(Stage stage) 
    {
    	SceneSwitcher switcher = new SceneSwitcher(stage);
    	   
    	stage.setScene(switcher.LoginScene());
        stage.show();
    }

    public static void main(String[] args) 
    {
    	launch(args);
    	//SYSTESTER tester = new SYSTESTER();
    	//tester.run();
    }
}