package brogrammers.tutoring_room;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

/**
 * JavaFX App
 */
public class App extends Application 
{
    @Override
    public void start(Stage stage) 
    {
    	SceneSwitcher switcher = new SceneSwitcher(stage);
    	   
    	boolean onLogout = false;
    	stage.setScene(switcher.LoginScene(onLogout));
    	stage.setX(200);
    	stage.setY(100);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	if (switcher.checkActiveSession()) {
            		switcher.closeSession();
            		System.out.println("Closing session.");
            	}
            	else {
            		System.out.println("Closing window, no session started.");
            	}
                
            }
        });
    }

    public static void main(String[] args) 
    {
    	launch(args);
    	//SYSTESTER tester = new SYSTESTER();
    	//tester.run();
    }
}