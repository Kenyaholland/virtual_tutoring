package brogrammers.tutoring_room;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DirectoryView extends Pane{
	
	// For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	
	// ArrayList for storing and accessing room VBoxes
	private ArrayList<VBox> roomVBoxes;
	
	// Labels
	private Label titleLabel;
	
	// Buttons
	private Button refreshButton;
	private Button logoutButton;
	
	// HBoxes
	private HBox headerRow;
	private HBox titleBox;
	private HBox headerButtonBox;
	
	// GridPane
	private GridPane roomsGrid;
	
	// VBoxes
	private VBox directoryBox;
	
	
	public DirectoryView(Stage stage, SceneSwitcher switcher)
	{
		this.stage = stage;
		this.switcher = switcher;

		this.roomVBoxes = new ArrayList<VBox>();
		
		initializeVariables();
		stylizeElements();
		
		assignSetOnActions();
		populateChildren();
	}
	
	public void initializeVariables()
	{
		// Labels
		titleLabel = new Label("Virtual Tutoring Directory");
		
		// Buttons
		refreshButton = new Button("Refresh");
		logoutButton = new Button("Logout");
		
		// HBoxes
		headerRow = new HBox();
		titleBox = new HBox();
		headerButtonBox = new HBox();
		
		// GridPanes
		roomsGrid = new GridPane();
		
		// VBoxes
		directoryBox = new VBox();
	}
	
	public void stylizeElements()
	{	
		stylizeHeaderBox();
		stylizeRoomGrid();
		
		directoryBox.setAlignment(Pos.CENTER);
		directoryBox.setSpacing(25);
	}
	
	public void assignSetOnActions()
	{
		logoutButton.setOnAction(e -> stage.setScene(switcher.LoginScene()));
	}
	
	public void populateChildren() 
	{
		titleBox.getChildren().add(titleLabel);
		headerButtonBox.getChildren().addAll(refreshButton, logoutButton);
		
		headerRow.getChildren().addAll(titleBox, headerButtonBox);
		
		for(int i = 1; i <= 6; i++) {
			buildRoom(i);
		}
		
		roomsGrid.add(roomVBoxes.get(0), 0, 0);
		roomsGrid.add(roomVBoxes.get(1), 1, 0);
		roomsGrid.add(roomVBoxes.get(2), 2, 0);
		roomsGrid.add(roomVBoxes.get(3), 0, 1);
		roomsGrid.add(roomVBoxes.get(4), 1, 1);
		roomsGrid.add(roomVBoxes.get(5), 2, 1);
		
		directoryBox.getChildren().addAll(headerRow, roomsGrid);
		
		this.getChildren().addAll(directoryBox);
	}
	
	private void stylizeHeaderBox()
	{
		// Style title
		titleLabel.setFont(new Font("Arial", 14));
		titleLabel.setTranslateX(10);
				
		// Style buttons
		refreshButton.setPrefSize(80, 10);
		refreshButton.setFont(new Font("Arial", 10));
		
		logoutButton.setPrefSize(80, 10);
		logoutButton.setFont(new Font("Arial", 10));
				
		// Style header boxes
		titleBox.setPrefWidth(200);
		titleBox.setAlignment(Pos.CENTER_LEFT);
		titleBox.setTranslateX(10);
				
		headerButtonBox.setAlignment(Pos.CENTER_RIGHT);
		headerButtonBox.setSpacing(10);
		headerButtonBox.setTranslateX(600);
				
		// Style header
		headerRow.setPrefSize(1000, 30);
		headerRow.setAlignment(Pos.CENTER_LEFT);
		headerRow.setSpacing(10);
	}
	
	private void stylizeRoomGrid()
	{
		roomsGrid.setPrefSize(1000, 570);
	}
	
	private void buildRoom(int roomNum) {
		VBox roomBox = new VBox();
		
		// Make room title and join button
		Label roomLabel = new Label("Room " + roomNum);
		roomLabel.setFont(new Font("Arial", 16));
		HBox.setMargin(roomLabel, new Insets(10, 150, 10, 20));
		
		Button roomButton = new Button("Join");
		HBox.setMargin(roomButton, new Insets(10, 10, 10, 0));
		// TO-DO: Switch to unique room scenes
		roomButton.setOnAction(e -> stage.setScene(switcher.RoomScene(roomNum)));
		
		HBox roomHeaderBox = new HBox();
		roomHeaderBox.setPrefSize(333.3, 20);
		roomHeaderBox.getChildren().addAll(roomLabel, roomButton);
		
		// Make status row
		Label statusLabel = new Label("Status:");
		statusLabel.setFont(new Font("Arial", 12));
		HBox.setMargin(statusLabel, new Insets(0, 10, 0, 20));
		
		Label status = new Label("Closed");
		//When directory loads, do label.setText("In-Progress") in setOnAction for login if a tutor is present
		status.setFont(new Font("Arial", 12));
		
		HBox statusRow = new HBox();
		statusRow.setPrefSize(333.3, 20);
		statusRow.getChildren().addAll(statusLabel, status);
		
		// Make Tutor row
		Label tutorLabel = new Label("Tutor:");
		tutorLabel.setFont(new Font("Arial", 12));
		HBox.setMargin(tutorLabel, new Insets(0, 10, 0, 20));
		
		Label tutorName = new Label("None");
		tutorName.setFont(new Font("Arial", 12));
		
		HBox tutorRow = new HBox();
		tutorRow.setPrefSize(333.3, 20);
		tutorRow.getChildren().addAll(tutorLabel, tutorName);
		
		// Make Courses row
		Label courseLabel = new Label("Courses:");
		courseLabel.setFont(new Font("Arial", 12));
		HBox.setMargin(courseLabel, new Insets(0, 10, 0, 20));
		
		Label courses = new Label("None");
		courses.setFont(new Font("Arial", 12));
		
		HBox coursesRow = new HBox();
		coursesRow.setPrefSize(333.3, 20);
		coursesRow.getChildren().addAll(courseLabel, courses);
		
		// Make Students Row
		Label studentsLabel = new Label("Students");
		studentsLabel.setFont(new Font("Arial", 12));
		HBox.setMargin(studentsLabel, new Insets(0, 10, 0, 20));
		
		Label numOfStudents = new Label("(0)");
		numOfStudents.setFont(new Font("Arial", 12));
		
		HBox studentsRow = new HBox();
		studentsRow.setPrefSize(333.3, 20);
		studentsRow.getChildren().addAll(studentsLabel, numOfStudents);
		
		roomBox.setPrefSize(333.3, 250);
		roomBox.setSpacing(10);
		
		roomBox.getChildren().addAll(roomHeaderBox, statusRow, tutorRow, coursesRow, studentsRow);
		
		roomVBoxes.add(roomBox);
	}
}
