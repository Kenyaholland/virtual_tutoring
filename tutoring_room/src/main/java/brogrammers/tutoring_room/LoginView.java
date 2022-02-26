package brogrammers.tutoring_room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	private HBox titleBox;
	
	//and a VBox to contain them all
	private VBox loginBox;
	
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
		
		titleBox = new HBox();
		
		loginBox = new VBox();
	}
	
	public void stylizeElements()
	{
		title.setAlignment(Pos.CENTER);
		title.setMinWidth(300);
		HBox.setMargin(title, new Insets(200, 0, 10, 0));
		
		//titleBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		//signInButtonRow.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		
		titleBox.setAlignment(Pos.CENTER);
		signInButtonRow.setAlignment(Pos.CENTER);
		HBox.setMargin(signInButton, new Insets(10, 0, 10, 0));
		
		loginBox.setAlignment(Pos.CENTER);
	}
	
	public void assignSetOnActions()
	{
		signInButton.setOnAction(e -> stage.setScene(switcher.DirectoryScene()));
	}
	
	public void populateChildren() 
	{
		signInButtonRow.getChildren().add(signInButton);
		
		titleBox.getChildren().add(title);
		
		loginBox.getChildren().addAll(titleBox, signInButtonRow);
		
		this.getChildren().addAll(loginBox);
	}
}
