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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RoomView extends Pane{
	
	// For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	private int roomNum;
	
	// Labels
	private Label titleLabel;
	private Label tutorBoxLabel;
	private Label tutorNameLabel;
	private Label statusLabel;
	
	// Text
	private Text coursesText;
	
	// Buttons
	private Button directoryButton;
	private Button logoutButton;
	private Button tutorZoomButton;
	
	// Boxes
	private HBox headerBox;
	private HBox titleBox;
	private HBox headerButtonBox;
	private VBox tutorInfoBox;
	private HBox coursesTextBox;
	private Pane chatPane;
	private HBox middleBox;
	private HBox breakoutRoomsBox;
	private VBox tutorRoomBox;
	
	//Image blankIcon = new Image(getClass().getResourceAsStream("/main/java/brogrammers/tutoring_room/white_square.png"));
	//Image raiseHandIcon = new Image(getClass().getResourceAsStream("/main/java/brogrammers/tutoring_room/raisedHand.jpg"));
	
	public RoomView(Stage stage, SceneSwitcher switcher, int roomNum)
	{
		this.stage = stage;
		this.switcher = switcher;
		this.roomNum = roomNum;

		initializeVariables();
		stylizeElements();
		
		assignSetOnActions();
		populateChildren();
	}
	
	public void initializeVariables()
	{
		// Header components
		titleLabel = new Label("Tutoring Room " + this.roomNum);
		directoryButton = new Button("Return to Directory");
		titleBox = new HBox();
		headerButtonBox = new HBox();
		logoutButton = new Button("Logout");	
		headerBox = new HBox();
		
		// Middle components
		tutorBoxLabel = new Label("Tutor Information");
		tutorZoomButton = new Button("Enter Zoom Room");
		tutorNameLabel = new Label();
		statusLabel = new Label();
		coursesText = new Text();
		coursesTextBox = new HBox();
		tutorInfoBox = new VBox();
		chatPane = new Pane();
		middleBox = new HBox();
		
		// Break-out rooms components
		//raiseHandIcon = new Image(getClass().getResourceAsStream("raisedHand.jpg"));
		breakoutRoomsBox = new HBox();
		
		tutorRoomBox = new VBox();
	}
	
	public void stylizeElements()
	{		
		stylizeHeaderBox();
		stylizeMiddleBox();
		stylizeBreakoutRoomsBox();
		
		// Stylize entire room VBox
		tutorRoomBox.setAlignment(Pos.TOP_LEFT);
		tutorRoomBox.setSpacing(25);
	}
	
	public void assignSetOnActions()
	{
		directoryButton.setOnAction(e -> stage.setScene(switcher.DirectoryScene()));
		logoutButton.setOnAction(e -> stage.setScene(switcher.LoginScene()));
		//zoomButton.setOnAction(e -> {});
	}
	
	public void populateChildren() 
	{
		titleBox.getChildren().add(titleLabel);
		headerButtonBox.getChildren().addAll(directoryButton, logoutButton);
		headerBox.getChildren().addAll(titleBox, headerButtonBox);
		
		middleBox.getChildren().addAll(tutorInfoBox, chatPane);
		
		for (int i = 1; i <= 4; i++) {
			breakoutRoomsBox.getChildren().add(buildBreakoutRoomBox(i));
		}
		
		tutorRoomBox.getChildren().addAll(headerBox, middleBox, breakoutRoomsBox);
		
		this.getChildren().add(tutorRoomBox);
	}
	
	private void stylizeHeaderBox()
	{
		// Stylize title
		titleLabel.setFont(new Font("Arial", 14));
		titleLabel.setTranslateX(10);
				
		// Stylize buttons
		directoryButton.setPrefSize(130, 10);
		directoryButton.setFont(new Font("Arial", 10));
				
		logoutButton.setPrefSize(80, 10);
		logoutButton.setFont(new Font("Arial", 10));
				
		// Stylize header boxes
		titleBox.setPrefWidth(200);
		//titleBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		titleBox.setAlignment(Pos.CENTER_LEFT);
		titleBox.setTranslateX(10);
				
		//headerButtonBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		headerButtonBox.setAlignment(Pos.CENTER_RIGHT);
		headerButtonBox.setTranslateX(550);
		headerButtonBox.setSpacing(10);
				
		// Stylize header
		headerBox.setPrefSize(1000, 30);
		headerBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		headerBox.setAlignment(Pos.CENTER_LEFT);
		headerBox.setSpacing(10);
	}
	
	private void stylizeMiddleBox()
	{
		tutorBoxLabel.setPrefSize(270, 30);
		//tutorBoxLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		tutorBoxLabel.setFont(new Font("Arial", 14));
		tutorBoxLabel.setPadding(new Insets(0, 10, 0, 10));
		
		//tutorNameLabel.setText(get tutor name for this room);
		tutorNameLabel.setText("Tutor Name");
		tutorNameLabel.setPrefSize(150, 20);
		tutorNameLabel.setFont(new Font("Arial", 12));
		//tutorNameLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		tutorNameLabel.setAlignment(Pos.CENTER);
		
		tutorZoomButton.setPrefSize(130, 20);
		tutorZoomButton.setFont(new Font("Arial", 12));
		
		coursesText.setText("Courses: <list>");
		coursesText.setFont(new Font("Arial", 12));
		
		coursesTextBox.setPrefSize(240, 50);
		//coursesTextBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		coursesTextBox.setAlignment(Pos.TOP_LEFT);
		coursesTextBox.setPadding(new Insets(0, 20, 0, 20));
		coursesTextBox.getChildren().add(coursesText);
		
		statusLabel.setText("Status: <status>");
		statusLabel.setPrefSize(200, 20);
		statusLabel.setFont(new Font("Arial", 12));
		//statusLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		statusLabel.setAlignment(Pos.CENTER);
		
		tutorInfoBox.setPrefSize(270, 300);
		//tutorInfoBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		tutorInfoBox.setTranslateX(25);
		tutorInfoBox.setAlignment(Pos.TOP_CENTER);
		tutorInfoBox.setSpacing(25);
		tutorInfoBox.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" + 
                "-fx-border-radius: 2;" + 
                "-fx-border-color: black;");
		
		tutorInfoBox.getChildren().addAll(tutorBoxLabel, tutorNameLabel, coursesTextBox, statusLabel, tutorZoomButton);
		
		chatPane.setPrefSize(650, 300);
		chatPane.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		chatPane.setTranslateX(30);
		chatPane.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" + 
                "-fx-border-radius: 2;" + 
                "-fx-border-color: black;");
		
		middleBox.setPrefSize(1000, 300);
		//middleBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		middleBox.setAlignment(Pos.TOP_LEFT);
		middleBox.setSpacing(25);
		//middleBox.setPadding(new Insets(0, 25, 0, 25));
	}
	
	private void stylizeBreakoutRoomsBox()
	{
		breakoutRoomsBox.setPrefSize(1000, 185);
		//breakoutRoomsBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		breakoutRoomsBox.setAlignment(Pos.CENTER_LEFT);
		breakoutRoomsBox.setSpacing(25);
		breakoutRoomsBox.setPadding(new Insets(0, 25, 0, 25));
	}
	
	private VBox buildBreakoutRoomBox(int index)
	{
		VBox box = new VBox();
		
		Label breakoutRoomLabel = new Label("Group " + index);
//		ImageView handIV = new ImageView(blankIcon);
//		breakoutRoomLabel.setGraphic(handIV);
		breakoutRoomLabel.setPrefSize(220, 30);
		breakoutRoomLabel.setFont(new Font("Arial", 12));
		//breakoutRoomLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		breakoutRoomLabel.setPadding(new Insets(0, 10, 0, 10));
		
		Button zoomButton = new Button("Enter Zoom Room");
		zoomButton.setPrefSize(130, 20);
		zoomButton.setFont(new Font("Arial", 12));
		zoomButton.setTranslateX(35);
		
		Text studentsText = new Text("Students: <list>");
		
		HBox studentsTextBox = new HBox();
		studentsTextBox.setPrefSize(160, 90);
		//studentsTextBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		studentsTextBox.setAlignment(Pos.TOP_LEFT);
		studentsTextBox.setPadding(new Insets(0, 10, 0, 10));
		
		studentsTextBox.getChildren().add(studentsText);
		
		box.setPrefSize(220, 185);
		//box.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		box.setAlignment(Pos.TOP_LEFT);
		box.setSpacing(15);
		box.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" + 
                "-fx-border-radius: 2;" + 
                "-fx-border-color: black;");
		
		//breakoutRoom.setGraphic(new ImageView(blank white image initially))
		//studentsText.setText -> String of names from ResultSet of query for students in room [i]
		//studentsText.setWrappingWidth(150)
			
		box.getChildren().addAll(breakoutRoomLabel, zoomButton, studentsTextBox);
		return box;
	}
}
