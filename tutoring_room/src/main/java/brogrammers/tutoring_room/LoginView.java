package brogrammers.tutoring_room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends Pane{
	
	//For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	
	//Section for the app title
	private Label title;
	
	//one button for signing in, one button for creating account
	private Button signInButton;
	
	// HBoxes
	private HBox signInButtonRow;
	
	//and a VBox to contain them all
	private VBox loginRows;
	
	public LoginView(Stage stage)
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
		title = new Label("Sign in Page");
		
		signInButton = new Button("Sign in");
		
		signInButtonRow = new HBox();
		
		loginRows = new VBox();
	}
	
	public void stylizeElements()
	{
		title.setAlignment(Pos.CENTER);
		title.setMinWidth(500);
		HBox.setMargin(title, new Insets(40, 0, 40, 0));
		
		signInButtonRow.setAlignment(Pos.CENTER);
		HBox.setMargin(signInButton, new Insets(10, 0, 10, 0));
	}
	
	public void assignSetOnActions()
	{
		signInButton.setOnAction(e -> stage.setScene(switcher.DirectoryScene()));
	}
	
	public void populateChildren() 
	{
		signInButtonRow.getChildren().add(signInButton);
		
		loginRows.getChildren().addAll(title, signInButtonRow);
		
		this.getChildren().add(loginRows);
	}
}