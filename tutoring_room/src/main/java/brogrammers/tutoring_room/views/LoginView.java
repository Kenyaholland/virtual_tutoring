package brogrammers.tutoring_room.views;

import brogrammers.tutoring_room.SceneSwitcher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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
		title = new Label("Virtual Tutoring");
		schoolSelection = new Label("Please select your school to sign in");
		
		// Buttons
		signInButton = new Button("Sign in");
		signInButton.setId("sign-in-button");
		
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
		title.setStyle("-fx-text-fill: #7cafc2; -fx-font-family: Helvetica; -fx-font-size: 32px; -fx-font-weight: BOLD");
		HBox.setMargin(title, new Insets(125, 0, 10, 0));
		
		schoolSelection.setAlignment(Pos.CENTER);
		schoolSelection.setStyle("-fx-text-fill: #E8E8E8; -fx-font-family: Helvetica; -fx-font-size: 12px");
		HBox.setMargin(schoolSelection, new Insets(0, 0, 0, 50));
		schoolSelection.setPadding(new Insets(0, 0, 5, 0));
		
		schoolDropDown.setStyle("-fx-background-radius: 4; -fx-background-color: #B8B8B8; -fx-font-family: Helvetica;"
				+ "-fx-font-size: 12px;");
		
		// CheckBox
		isTutor.setAlignment(Pos.CENTER);
		isTutor.setStyle("-fx-text-fill: #E8E8E8; -fx-font-family: Helvetica; -fx-font-size: 12px");
		HBox.setMargin(isTutor, new Insets(20, 0, 0, 150));
		
		signInButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");
		
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
		signInButton.setOnAction(e -> { 
			if(schoolDropDown.getValue() ==  "Select") {
				Alert alert = new Alert(AlertType.ERROR);
		    	alert.setTitle("Login Alert");
		    	alert.setHeaderText("Login Error");
		    	alert.setContentText("Must select a University from the drop-down menu.");
		    	alert.setX(250);
		    	alert.setY(200);
		    	alert.getDialogPane().setBackground(new Background(new BackgroundFill(Color.web("E8E8E8"), null, null)));
		    	alert.showAndWait();
			}
			else 
			{
				//System.out.println("Test: " + App.tutor);
				stage.setScene(switcher.LoginCredentialsScene(false));
			}
		});
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
		
		loginBox.getChildren().addAll(titleBox, selectionBox, tutorBox, signInButtonRow);
		
		this.getChildren().addAll(loginBox);
	}
}