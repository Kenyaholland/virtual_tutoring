package brogrammers.tutoring_room.views;

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
	
	// Text
	private Text coursesText;
	
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
		titleLabel = new Label("Tutoring Room " + this.roomNum);
		refreshButton = new Button("Refresh");
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
		titleLabel.setFont(new Font("Arial", 14));
		titleLabel.setTranslateX(10);
				
		// Stylize buttons
		refreshButton.setPrefSize(80, 10);
		refreshButton.setFont(new Font("Arial", 10));
		
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
		headerButtonBox.setTranslateX(470);
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
		
		messageField.setEditable(true);
		messageField.setPrefWidth(525);
		displayChats.setPrefHeight(250);
		displayChats.setEditable(false);
		chatWindow.setContent(displayChats);
		chatWindow.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        displayChats.setPrefWidth(525);

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
	
	private VBox buildBreakoutGroupBox(int groupNum)
	{
		VBox box = new VBox();
		
		Label breakoutRoomLabel = new Label("Group " + groupNum);
//		ImageView handIV = new ImageView(blankIcon);
//		breakoutRoomLabel.setGraphic(handIV);
		breakoutRoomLabel.setPrefSize(220, 30);
		breakoutRoomLabel.setFont(new Font("Arial", 12));
		//breakoutRoomLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		breakoutRoomLabel.setPadding(new Insets(0, 10, 0, 10));
		
		Button joinGroupButton = new Button("Join Breakout Room");
		joinGroupButton.setPrefSize(130, 20);
		joinGroupButton.setFont(new Font("Arial", 12));
		joinGroupButton.setTranslateX(35);
		
		joinGroupButton.setOnAction(e -> {
			
			// generate client identifier
			HttpClient client = HttpClient.newHttpClient();
			String state = Base64.getEncoder().encodeToString(("kmh99" + ":" + new Random().nextInt(999999)).getBytes());

			try {
				// send client to redirect URL
				switcher.getAuthClient().GenerateCode(client, state);
				
				// this is for testing
				// we can check if there is a generated code by calling RequestCode()
		        TimeUnit.SECONDS.sleep(5);
		        
		        // request access code
		        String code = switcher.getAuthClient().RequestCode(client, state);
		        
		        // get access token
		        String token = switcher.getAuthClient().RequestToken(client, state, code);
				
				switcher.getAuthClient().CreateMeeting(client, new JSONObject(token).get("access_token").toString());
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		joinGroupButtons.add(joinGroupButton);
		
		
		String groupMemberList = "Students:\n";
		groupMemberList += roomCtrl.getGroupMembers(groupNum);
		Text studentsText = new Text(groupMemberList);
		
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
			
		box.getChildren().addAll(breakoutRoomLabel, joinGroupButton, studentsTextBox);
		return box;
	}
}
