package brogrammers.tutoring_room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomView extends Pane{
	
	//For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	
	//Section for the app title
	private Label title;
	
	//one button for signing in, one button for creating account
	private Button roomButton;
	
	// HBoxes
	private HBox roomButtonRow;
	
	//and a VBox to contain them all
	private VBox roomRows;
	
	public RoomView(Stage stage)
	{
		this.stage = stage;
		this.switcher = new SceneSwitcher(stage);

		initializeVariables();
		stylizeElements();
		
		assignSetOnActions();
		populateChildren();
	}
	
	public void initializeVariables()
	{
		title = new Label("Room");
		
		roomButton = new Button("Go back to Sign in");
		
		roomButtonRow = new HBox();
		
		roomRows = new VBox();
	}
	
	public void stylizeElements()
	{
		title.setAlignment(Pos.CENTER);
		title.setMinWidth(500);
		HBox.setMargin(title, new Insets(40, 0, 40, 0));
		
		roomButtonRow.setAlignment(Pos.CENTER);
		HBox.setMargin(roomButton, new Insets(10, 0, 10, 0));
	}
	
	public void assignSetOnActions()
	{
		roomButton.setOnAction(e -> stage.setScene(switcher.LoginScene()));
	}
	
	public void populateChildren() 
	{
		roomButtonRow.getChildren().add(roomButton);
		
		roomRows.getChildren().addAll(title, roomButtonRow);
		
		this.getChildren().add(roomRows);
	}
}