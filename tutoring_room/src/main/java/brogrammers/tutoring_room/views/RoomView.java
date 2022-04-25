package brogrammers.tutoring_room.views;

import java.awt.Desktop;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import brogrammers.tutoring_room.SceneSwitcher;
import brogrammers.tutoring_room.controllers.ClientController;
import brogrammers.tutoring_room.controllers.DirectoryController;
import brogrammers.tutoring_room.controllers.RoomController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RoomView extends Pane{
	
	// For switching scenes
	private Stage stage;
	private SceneSwitcher switcher;
	private int roomNum;
	private String sessionId;
	private RoomController roomCtrl; 
	private DirectoryController dirCtrl;
	private ClientController clientCtrl;
	
	// Labels
	private Label titleLabel;
	private Label tutorBoxLabel;
	private Label tutorNameLabel;
	private Label statusLabel;
	private Label coursesLabel;
	
	// TextField
	private TextField messageField;

	// Text Area
	private TextArea displayChats;
	
	// Buttons
	private Button refreshButton;
	private Button directoryButton;
	private List<Button> joinGroupButtons;
	private Button logoutButton;
	private Button tutorZoomButton;
	private Button sendChatButton;
	
	// ScrollPane
	private ScrollPane chatWindow;
	
	// Boxes
	private HBox headerBox;
	private HBox titleBox;
	private HBox headerButtonBox;
	private VBox tutorInfoBox;
	private HBox coursesTextBox;
	private VBox chatBox;
	private VBox createMessageBox;
	private VBox sendMessageBox;
	private GridPane chatPane;
	private HBox middleBox;
	private HBox breakoutRoomsBox;
	private VBox tutorRoomBox;
	
	//Image blankIcon = new Image(getClass().getResourceAsStream("/main/java/brogrammers/tutoring_room/white_square.png"));
	//Image raiseHandIcon = new Image(getClass().getResourceAsStream("/main/java/brogrammers/tutoring_room/raisedHand.jpg"));
	
	public RoomView(Stage stage, SceneSwitcher switcher, ClientController clientCtrl, int roomNum, String sessionId)
	{
		this.stage = stage;
		this.switcher = switcher;
		this.roomNum = roomNum;
		this.sessionId = sessionId;
		this.clientCtrl = clientCtrl;
		roomCtrl = new RoomController(roomNum, this.sessionId);
		dirCtrl = new DirectoryController(this.sessionId);

		initializeVariables();
		stylizeElements();

		populateChildren();
		assignSetOnActions();
	}
	
	public void initializeVariables()
	{
		// Header components
		titleLabel = new Label("Tutor Room #" + this.roomNum);
		refreshButton = new Button("Refresh");
		directoryButton = new Button("Back to Directory");
		titleBox = new HBox();
		headerButtonBox = new HBox();
		logoutButton = new Button("Logout");	
		headerBox = new HBox();
		
		// Middle components
		tutorBoxLabel = new Label("Tutor Information");
		tutorZoomButton = new Button("Enter Zoom Room");
		tutorNameLabel = new Label();
		statusLabel = new Label();
		coursesLabel = new Label();
		coursesTextBox = new HBox();
		tutorInfoBox = new VBox();
		messageField = new TextField();
		sendChatButton = new Button("Send");
		displayChats = new TextArea();
		chatWindow = new ScrollPane();
		chatBox = new VBox();
		createMessageBox = new VBox();
		sendMessageBox = new VBox();
		chatPane = new GridPane();
		middleBox = new HBox();
		
		// Break-out rooms components
		//raiseHandIcon = new Image(getClass().getResourceAsStream("raisedHand.jpg"));
		breakoutRoomsBox = new HBox();
		joinGroupButtons = new ArrayList<Button>();
		
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
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						String message = clientCtrl.getMessage();
						displayChats.appendText(message + '\n');
					} catch (Exception e) {
						break;
					}
				}
			}
		}).start();
		
		messageField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (!messageField.getText().equals("")) {
					clientCtrl.sendMessage(messageField.getText());
					messageField.setText("");
				}
			}
		});
		
		sendChatButton.setOnAction(e -> {
			if (!messageField.getText().equals("")) {
				clientCtrl.sendMessage(messageField.getText());
				messageField.setText("");
			}
		});
		
		refreshButton.setOnAction(e -> {
			stage.setScene(switcher.RoomScene(roomNum));
		});
		
		directoryButton.setOnAction(e -> {
			roomCtrl.removeFromBreakoutGroup();
			dirCtrl.removeFromRoom();
			clientCtrl.leaveRoom();
			stage.setScene(switcher.DirectoryScene());
		});
		
		logoutButton.setOnAction(e -> {
			roomCtrl.removeFromBreakoutGroup();
			dirCtrl.removeFromRoom();
			clientCtrl.disconnect();
			boolean logout = true;
			stage.setScene(switcher.LoginScene(logout));
		});
		
		for (int i = 1; i <= 4; i++) {
			final int groupNum = i;
			joinGroupButtons.get(i-1).setOnAction(e -> {
				//produce zoom link
				
				// generate client identifier
				HttpClient client = HttpClient.newHttpClient();

				try {
					
					String url = switcher.getAuthClient().CreateMeeting(client, new JSONObject(switcher.getToken()).get("access_token").toString());
					
					Desktop.getDesktop().browse(new URL(url).toURI());
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				roomCtrl.joinBreakoutGroup(groupNum);
			});
		}
	}
	
	public void populateChildren() 
	{
		titleBox.getChildren().add(titleLabel);
		headerButtonBox.getChildren().addAll(refreshButton, directoryButton, logoutButton);
		headerBox.getChildren().addAll(titleBox, headerButtonBox);
		
		middleBox.getChildren().addAll(tutorInfoBox, chatPane);
		
		for (int i = 1; i <= 4; i++) {
			breakoutRoomsBox.getChildren().add(buildBreakoutGroupBox(i));
		}
		
		tutorRoomBox.getChildren().addAll(headerBox, middleBox, breakoutRoomsBox);
		
		this.getChildren().add(tutorRoomBox);
	}
	
	private void stylizeHeaderBox()
	{
		// Stylize title
		titleLabel.setTranslateX(10);
		titleLabel.setStyle("-fx-font-family: Helvetica; -fx-text-fill: #7cafc2; -fx-font-size: 20px; -fx-font-weight: BOLD;");
				
		// Stylize buttons
		refreshButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");		
		directoryButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");
		logoutButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");
				
		// Stylize header boxes
		titleBox.setPrefWidth(200);
		titleBox.setAlignment(Pos.CENTER_LEFT);
		titleBox.setTranslateX(10);
				
		headerButtonBox.setAlignment(Pos.CENTER_RIGHT);
		headerButtonBox.setTranslateX(525);
		headerButtonBox.setSpacing(10);
				
		// Stylize header
		headerBox.setPrefSize(1000, 50);
		headerBox.setStyle("-fx-background-color: #383838;");
		headerBox.setAlignment(Pos.CENTER_LEFT);
		headerBox.setSpacing(10);
	}
	
	private void stylizeMiddleBox()
	{
		tutorBoxLabel.setPrefSize(270, 30);
		tutorBoxLabel.setAlignment(Pos.CENTER);
		tutorBoxLabel.setTranslateY(10);
		tutorBoxLabel.setStyle("-fx-font-family: Helvetica; -fx-text-fill: #f7ca88; -fx-font-size: 16px; -fx-font-weight: BOLD;"
				+ "text-decoration: underline; text-decoration-color: #f7ca88;");
		
		//tutorNameLabel.setText(get tutor name for this room);
		tutorNameLabel.setText("Name: ");
		tutorNameLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-font-weight: BOLD; -fx-text-fill: #e8e8e8;");
		tutorNameLabel.setTranslateX(20);
		tutorNameLabel.setAlignment(Pos.BASELINE_LEFT);
		
		tutorZoomButton.setAlignment(Pos.BASELINE_CENTER);
		tutorZoomButton.setTranslateY(30);
		tutorZoomButton.setTranslateX(75);
		tutorZoomButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");
		
		coursesLabel.setText("Courses: ");
		coursesLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-text-fill: #e8e8e8;");
		coursesLabel.setTranslateX(20);
		coursesLabel.setAlignment(Pos.BASELINE_LEFT);
		
		statusLabel.setText("Status: ");
		statusLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 12px; -fx-text-fill: #e8e8e8;");
		statusLabel.setTranslateX(20);
		statusLabel.setTranslateY(20);
		statusLabel.setAlignment(Pos.BASELINE_LEFT);
		
		tutorInfoBox.setPrefSize(270, 200);
		tutorInfoBox.setTranslateX(25);
		//tutorInfoBox.setAlignment(Pos.TOP_CENTER);
		tutorInfoBox.setSpacing(15);
		tutorInfoBox.setStyle("-fx-background-color: #484848; -fx-background-radius: 10; -fx-border-width: 4; "
				+ "-fx-border-radius: 10; -fx-border-color: #f7ca88;");
		tutorInfoBox.getChildren().addAll(tutorBoxLabel, tutorNameLabel, coursesLabel, statusLabel, tutorZoomButton);
		
		messageField.setEditable(true);
		messageField.setPrefWidth(550);
		displayChats.setPrefHeight(250);
		displayChats.setEditable(false);
		chatWindow.setContent(displayChats);
		chatWindow.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        displayChats.setPrefWidth(550);
        displayChats.setStyle("-fx-background-color: #d8d8d8; -fx-text-fill: #181818");
        
        sendChatButton.setStyle("-fx-background-radius: 4; -fx-background-color: #e8e8e8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");

        chatBox.setPadding(new Insets(10, 10, 10, 10));
        chatBox.getChildren().add(displayChats);
		createMessageBox.setPadding(new Insets(10, 10, 10, 10));
		createMessageBox.getChildren().add(messageField);
		sendMessageBox.setPadding(new Insets(10, 10, 10, 10));
		sendMessageBox.getChildren().add(sendChatButton);
		
		chatPane.setPrefSize(650, 300);
		chatPane.setTranslateX(30);
		chatPane.add(chatBox, 0, 0);
		chatPane.add(createMessageBox, 0, 1);
		chatPane.add(sendMessageBox, 1, 1);
		chatPane.setStyle("-fx-background-color: #484848; -fx-background-radius: 10; -fx-border-width: 4; "
				+ "-fx-border-radius: 10; -fx-border-color: #dc9656;");
		
		middleBox.setPrefSize(1000, 300);
		middleBox.setAlignment(Pos.TOP_LEFT);
		middleBox.setSpacing(25);
	}
	
	private void stylizeBreakoutRoomsBox()
	{
		breakoutRoomsBox.setPrefSize(1000, 185);
		breakoutRoomsBox.setAlignment(Pos.CENTER_LEFT);
		breakoutRoomsBox.setSpacing(25);
		breakoutRoomsBox.setPadding(new Insets(0, 25, 0, 25));
	}
	
	private VBox buildBreakoutGroupBox(int groupNum)
	{
		VBox box = new VBox();
		
		Label breakoutRoomLabel = new Label("Group #" + groupNum);
		breakoutRoomLabel.setPrefSize(220, 30);
		breakoutRoomLabel.setPadding(new Insets(0, 10, 0, 10));
		breakoutRoomLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 14px; -fx-font-weight: BOLD; -fx-text-fill: #dc9656;");
		
		Button joinGroupButton = new Button("Join Breakout Room");
		sendChatButton.setStyle("-fx-background-radius: 4; -fx-background-color: #E8E8E8; -fx-text-fill: #181818;"
				+ "-fx-font-family: Helvetica; -fx-font-size: 12px;");
		joinGroupButton.setTranslateX(35);
		joinGroupButtons.add(joinGroupButton);
		
		
		String groupMemberList = "Students:\n";
		groupMemberList += roomCtrl.getGroupMembers(groupNum);
		Text studentsText = new Text(groupMemberList);
		studentsText.setFill(Color.web("e8e8e8"));
		studentsText.setFont(new Font("Helvetica", 12));
		
		HBox studentsTextBox = new HBox();
		studentsTextBox.setPrefSize(160, 90);
		studentsTextBox.setAlignment(Pos.TOP_LEFT);
		studentsTextBox.setPadding(new Insets(0, 10, 0, 10));
		studentsTextBox.setStyle("-fx-background-color: #484848;");
		
		studentsTextBox.getChildren().add(studentsText);
		
		box.setPrefSize(220, 185);
		//box.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		box.setAlignment(Pos.TOP_LEFT);
		box.setSpacing(15);
		box.setStyle("-fx-background-color: #484848; -fx-background-radius: 10; -fx-border-width: 4; "
				+ "-fx-border-radius: 10; -fx-border-color: #dc9656;");
		
		//breakoutRoom.setGraphic(new ImageView(blank white image initially))
		//studentsText.setText -> String of names from ResultSet of query for students in room [i]
		//studentsText.setWrappingWidth(150)
			
		box.getChildren().addAll(breakoutRoomLabel, joinGroupButton, studentsTextBox);
		return box;
	}
}
