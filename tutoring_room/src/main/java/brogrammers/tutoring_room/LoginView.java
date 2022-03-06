package brogrammers.tutoring_room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView extends Pane{
	
	//For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	
	// Labels
	private Label title;
	private Label schoolSelection;
	
	// Buttons
	private Button signInButton;
	
	// DropDown
	private ComboBox<String> schoolDropDown;
	
	// Checkbox
	private CheckBox isTutor;
	
	// HBoxes
	private HBox signInButtonRow;
	private HBox titleBox;
	private HBox tutorBox;
	
	// VBoxes
	private VBox selectionBox;
	private VBox loginBox;
	
	public LoginView(Stage stage, SceneSwitcher switcher)
	{
		this.stage = stage;
		this.switcher = switcher;

		initializeVariables();
		stylizeElements();
		
		assignSetOnActions();
		populateChildren();
	}
	
	public void initializeVariables()
	{	
		// Labels
		title = new Label("Virtual Tutoring Room");
		schoolSelection = new Label("Please select your school to sign in");
		
		// Buttons
		signInButton = new Button("Sign in");
		
		// DropDown
		schoolDropDown = new ComboBox<String>();
		
		// CheckBox
		isTutor = new CheckBox("I am a tutor");
		
		// HBox
		signInButtonRow = new HBox();
		titleBox = new HBox();
		tutorBox = new HBox();
		
		// VBox
		selectionBox = new VBox();
		loginBox = new VBox();
	}
	
	public void stylizeElements()
	{
		// Labels
		title.setAlignment(Pos.CENTER);
		title.setFont(new Font("Arial", 16));
		HBox.setMargin(title, new Insets(150, 0, 10, 0));
		
		schoolSelection.setAlignment(Pos.CENTER);
		schoolSelection.setFont(new Font("Arial", 12));
		HBox.setMargin(schoolSelection, new Insets(0, 0, 0, 50));
		
		// CheckBox
		isTutor.setAlignment(Pos.CENTER);
		isTutor.setFont(new Font("Arial", 12));
		HBox.setMargin(isTutor, new Insets(0, 0, 0, 50));
		
		// HBoxes
		signInButtonRow.setAlignment(Pos.CENTER);
		HBox.setMargin(signInButton, new Insets(10, 0, 10, 0));
		
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setPrefSize(400, 30);
		
		tutorBox.setAlignment(Pos.CENTER_LEFT);
		tutorBox.setPrefSize(400, 20);
		
		// VBoxes
		selectionBox.setAlignment(Pos.CENTER);
		
		loginBox.setAlignment(Pos.CENTER);
		loginBox.setSpacing(10);
	}
	
	public void assignSetOnActions()
	{
		signInButton.setOnAction(e -> stage.setScene(switcher.LoginCredentialsScene()));
		// rooms array from SceneSwitcher is public to be used here to set the label texts
	}
	
	public void populateChildren() 
	{
		signInButtonRow.getChildren().add(signInButton);
		
		schoolDropDown.getItems().addAll(
				"Select",
				"University of West Florida"
				);
		schoolDropDown.getSelectionModel().selectFirst();
		// Should use ComboBox schoolDropDown = new ComboBox(FXCollections.observableArrayList(AllSchools)) if we figure out how to use all universities
		
		
		titleBox.getChildren().add(title);
		
		tutorBox.getChildren().add(isTutor);
		
		selectionBox.getChildren().addAll(schoolSelection, schoolDropDown);
		
		loginBox.getChildren().addAll(titleBox, tutorBox, selectionBox, signInButtonRow);
		
		this.getChildren().addAll(loginBox);
	}
}