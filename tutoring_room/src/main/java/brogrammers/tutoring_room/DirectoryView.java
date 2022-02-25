package brogrammers.tutoring_room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectoryView extends Pane{
	
	//For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	
	//Section for the app title
	private Label title;
	
	//one button for signing in, one button for creating account
	private Button directoryButton;
	
	// HBoxes
	private HBox directoryButtonRow;
	
	//and a VBox to contain them all
	private VBox directoryRows;
	
	public DirectoryView(Stage stage)
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
		title = new Label("Directory");
		
		directoryButton = new Button("Go to Room");
		
		directoryButtonRow = new HBox();
		
		directoryRows = new VBox();
	}
	
	public void stylizeElements()
	{	
		title.setAlignment(Pos.CENTER);
		title.setMinWidth(500);
		HBox.setMargin(title, new Insets(40, 0, 40, 0));
		
		directoryButtonRow.setAlignment(Pos.CENTER);
		HBox.setMargin(directoryButton, new Insets(10, 0, 10, 0));
	}
	
	public void assignSetOnActions()
	{
		directoryButton.setOnAction(e -> stage.setScene(switcher.RoomScene()));
	}
	
	public void populateChildren() 
	{
		directoryButtonRow.getChildren().add(directoryButton);
		
		directoryRows.getChildren().addAll(title, directoryButtonRow);
		
		this.getChildren().add(directoryRows);
	}
}
