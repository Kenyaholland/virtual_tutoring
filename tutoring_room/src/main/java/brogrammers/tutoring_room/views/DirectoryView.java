package brogrammers.tutoring_room.views;

import brogrammers.tutoring_room.SceneSwitcher;
import brogrammers.tutoring_room.controllers.DirectoryController;
import brogrammers.tutoring_room.data_access.UserDAO;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DirectoryView extends Pane{
	
	// For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	private String sessionId;
	private DirectoryController dirCtrl;
	
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
	
	
	public DirectoryView(Stage stage, SceneSwitcher switcher, String sessionId)
	{
		this.stage = stage;
		this.switcher = switcher;
		this.sessionId = sessionId;
		this.dirCtrl = new DirectoryController(this.sessionId);

		this.roomVBoxes = new ArrayList<VBox>();
		
		initializeVariables();
		stylizeElements();
		assignSetOnActions();
		populateChildren();	
	}
	
	public void initializeVariables()
	{
		// Labels
		titleLabel = new Label("Room Directory");
		
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
		refreshButton.setOnAction(e -> {
			Thread thread = new Thread(new Runnable()
			{
				public void run()
				{
					stage.setScene(switcher.DirectoryScene());
				}
			});
			
			thread.start();
			//stage.setScene(switcher.DirectoryScene());
		}); // refresh page
		
		logoutButton.setOnAction(e -> {
			boolean onLogout = true;
			stage.setScene(switcher.LoginScene(onLogout));
		});
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
		titleLabel.setTranslateX(10);
		titleLabel.setStyle("-fx-font-family: Helvetica; -fx-text-fill: #7cafc2; -fx-font-size: 20px; -fx-font-weight: BOLD;");
				
		// Style buttons
		refreshButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");
		logoutButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");
				
		// Style header boxes
		titleBox.setPrefWidth(200);
		titleBox.setAlignment(Pos.CENTER_LEFT);
		titleBox.setTranslateX(10);
				
		headerButtonBox.setAlignment(Pos.CENTER_RIGHT);
		headerButtonBox.setSpacing(15);
		headerButtonBox.setTranslateX(550);
				
		// Style header
		headerRow.setPrefSize(900, 50);
		headerRow.setAlignment(Pos.CENTER_LEFT);
		headerRow.setSpacing(10);
		headerRow.setStyle("-fx-background-color: #383838;");
	}
	
	private void stylizeRoomGrid()
	{
		roomsGrid.setTranslateX(20);
		roomsGrid.setHgap(15);
		roomsGrid.setVgap(15);
		roomsGrid.setPrefSize(900, 570);
	}
	
	private void buildRoom(int roomNum) {
		VBox roomBox = new VBox();
		roomBox.setPrefSize(275, 225);
		roomBox.setSpacing(10);
		roomBox.setStyle("-fx-background-color: #484848; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #a1b56c; -fx-border-width: 5;");
		
		// Make room title and join button
		Label roomLabel = new Label("Room #" + roomNum);
		roomLabel.setStyle("-fx-font-family: Helvetica; -fx-text-fill: #a1b56c; -fx-font-size: 16px; -fx-font-weight: BOLD;"
				+ "text-decoration: underline; text-decoration-color: #a1b56c;");
		HBox.setMargin(roomLabel, new Insets(10, 120, 10, 20));
		
		Button roomButton = new Button("Join");
		roomButton.setStyle("-fx-background-radius: 4; -fx-background-color: #c8c8c8; -fx-font-family: Helvetica;"
				+ "-fx-font-size: 12px; -fx-text-fill: #181818;");
		HBox.setMargin(roomButton, new Insets(10, 10, 10, 0));
		
		// TO-DO: Switch to unique room scenes
		roomButton.setOnAction(e -> {
			dirCtrl.addUserToRoom(roomNum);
			stage.setScene(switcher.RoomScene(roomNum));
		});
		
		HBox roomHeaderBox = new HBox();
		roomHeaderBox.setPrefSize(275, 20);
		roomHeaderBox.getChildren().addAll(roomLabel, roomButton);
		
		//String tutorName = dirCtrl.getTutorNameForRoom(roomNum);
		
		// Make status row
		Label statusLabel = new Label("Status: ");
		statusLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-font-weight: BOLD; -fx-text-fill: #e8e8e8;");
		HBox.setMargin(statusLabel, new Insets(0, 10, 0, 20));
		
		Label status = new Label("Closed");
		//When directory loads, do label.setText("In-Progress") in setOnAction for login if a tutor is present
		status.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-text-fill: #e8e8e8;");
		
		HBox statusRow = new HBox();
		statusRow.setPrefSize(275, 20);
		statusRow.getChildren().addAll(statusLabel, status);
		
		// Make Tutor row
		Label tutorLabel = new Label("Tutor: ");
		tutorLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-font-weight: BOLD; -fx-text-fill: #e8e8e8;");
		HBox.setMargin(tutorLabel, new Insets(0, 10, 0, 20));
		
		Label tutorName = new Label("None");
		tutorName.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-text-fill: #e8e8e8;");
		
		HBox tutorRow = new HBox();
		tutorRow.setPrefSize(275, 20);
		tutorRow.getChildren().addAll(tutorLabel, tutorName);
		
		// Make Courses row
		Label courseLabel = new Label("Courses: ");
		courseLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-font-weight: BOLD; -fx-text-fill: #e8e8e8;");
		HBox.setMargin(courseLabel, new Insets(0, 10, 0, 20));
		
		Label courses = new Label("None");
		courses.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-text-fill: #e8e8e8;");
		
		HBox coursesRow = new HBox();
		coursesRow.setPrefSize(275, 40);
		coursesRow.getChildren().addAll(courseLabel, courses);
		
		// Make Students Row
		Label studentsLabel = new Label("Number of students: ");
		studentsLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-font-weight: BOLD; -fx-text-fill: #e8e8e8;");
		HBox.setMargin(studentsLabel, new Insets(0, 10, 0, 20));
		
		int numStudents = dirCtrl.getNumStudentsInRoom(roomNum);
		String _numStudents;
		
		if (numStudents == 0) {
			_numStudents = "0";
		}
		else {
			_numStudents = String.valueOf(numStudents);
		}
		
		Label numOfStudents = new Label(_numStudents);
		numOfStudents.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-text-fill: #e8e8e8;");
		
		HBox studentsRow = new HBox();
		studentsRow.setPrefSize(275, 20);
		studentsRow.getChildren().addAll(studentsLabel, numOfStudents);
		
		roomBox.getChildren().addAll(roomHeaderBox, statusRow, studentsRow, tutorRow, coursesRow);
		
		roomVBoxes.add(roomBox);
	}
}
