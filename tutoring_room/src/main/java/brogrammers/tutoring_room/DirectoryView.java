package brogrammers.tutoring_room;

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
	
	// Labels
	private Label titleLabel;
	private Label room1Label;
	private Label room2Label;
	private Label room3Label;
	private Label room4Label;
	private Label room5Label;
	private Label room6Label;
	
	// Buttons
	//private Button directoryButton;
	private Button logoutButton;
	private Button roomButton1;
	private Button roomButton2;
	private Button roomButton3;
	private Button roomButton4;
	private Button roomButton5;
	private Button roomButton6;
	
	// HBoxes
	private HBox headerRow;
	private HBox titleBox;
	private HBox headerButtonBox;
	private HBox roomHeaderBox1;
	private HBox roomHeaderBox2;
	private HBox roomHeaderBox3;
	private HBox roomHeaderBox4;
	private HBox roomHeaderBox5;
	private HBox roomHeaderBox6;
	//private HBox directoryButtonRow;
	
	// GridPane
	private GridPane roomsGrid;
	
	// VBoxes
	private VBox roomBox1;
	private VBox roomBox2;
	private VBox roomBox3;
	private VBox roomBox4;
	private VBox roomBox5;
	private VBox roomBox6;
	private VBox directoryBox;
	
	
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
		// Labels
		titleLabel = new Label("Virtual Tutoring Directory");
		room1Label = new Label("Room 1");
		room2Label = new Label("Room 2");
		room3Label = new Label("Room 3");
		room4Label = new Label("Room 4");
		room5Label = new Label("Room 5");
		room6Label = new Label("Room 6");
		
		// Buttons
		//directoryButton1 = new Button("Go to Room");
		logoutButton = new Button("Logout");
		roomButton1 = new Button("Join");
		roomButton2 = new Button("Join");
		roomButton3 = new Button("Join");
		roomButton4 = new Button("Join");
		roomButton5 = new Button("Join");
		roomButton6 = new Button("Join");
		
		// HBoxes
		headerRow = new HBox();
		titleBox = new HBox();
		headerButtonBox = new HBox();
		roomHeaderBox1 = new HBox();
		roomHeaderBox2 = new HBox();
		roomHeaderBox3 = new HBox();
		roomHeaderBox4 = new HBox();
		roomHeaderBox5 = new HBox();
		roomHeaderBox6 = new HBox();
		//directoryButtonRow = new HBox();
		
		// GridPanes
		roomsGrid = new GridPane();
		
		// VBoxes
		roomBox1 = new VBox();
		roomBox2 = new VBox();
		roomBox3 = new VBox();
		roomBox4 = new VBox();
		roomBox5 = new VBox();
		roomBox6 = new VBox();
		directoryBox = new VBox();
	}
	
	public void stylizeElements()
	{	
		stylizeHeaderBox();
		stylizeRoomGrid();
		stylizeRooms();
		
		directoryBox.setAlignment(Pos.CENTER);
		directoryBox.setSpacing(25);
	}
	
	public void assignSetOnActions()
	{
		logoutButton.setOnAction(e -> stage.setScene(switcher.LoginScene()));
		
		// TO-DO: Switch to unique room scenes
		roomButton1.setOnAction(e -> stage.setScene(switcher.RoomScene()));
		roomButton2.setOnAction(e -> stage.setScene(switcher.RoomScene()));
		roomButton3.setOnAction(e -> stage.setScene(switcher.RoomScene()));
		roomButton4.setOnAction(e -> stage.setScene(switcher.RoomScene()));
		roomButton5.setOnAction(e -> stage.setScene(switcher.RoomScene()));
		roomButton6.setOnAction(e -> stage.setScene(switcher.RoomScene()));
	}
	
	public void populateChildren() 
	{
		titleBox.getChildren().add(titleLabel);
		headerButtonBox.getChildren().add(logoutButton);
		
		headerRow.getChildren().addAll(titleBox, headerButtonBox);
		
		//directoryButtonRow.getChildren().add(directoryButton);
		
		roomHeaderBox1.getChildren().addAll(room1Label, roomButton1);
		roomHeaderBox2.getChildren().addAll(room2Label, roomButton2);
		roomHeaderBox3.getChildren().addAll(room3Label, roomButton3);
		roomHeaderBox4.getChildren().addAll(room4Label, roomButton4);
		roomHeaderBox5.getChildren().addAll(room5Label, roomButton5);
		roomHeaderBox6.getChildren().addAll(room6Label, roomButton6);
		
		roomBox1.getChildren().addAll(roomHeaderBox1);
		roomBox2.getChildren().addAll(roomHeaderBox2);
		roomBox3.getChildren().addAll(roomHeaderBox3);
		roomBox4.getChildren().addAll(roomHeaderBox4);
		roomBox5.getChildren().addAll(roomHeaderBox5);
		roomBox6.getChildren().addAll(roomHeaderBox6);
		
		roomsGrid.add(roomBox1, 0, 0);
		roomsGrid.add(roomBox2, 1, 0);
		roomsGrid.add(roomBox3, 2, 0);
		roomsGrid.add(roomBox4, 0, 1);
		roomsGrid.add(roomBox5, 1, 1);
		roomsGrid.add(roomBox6, 2, 1);
		
		directoryBox.getChildren().addAll(headerRow, roomsGrid);
		
		this.getChildren().addAll(directoryBox);
	}
	
	private void stylizeHeaderBox()
	{
		// Style title
		titleLabel.setFont(new Font("Arial", 14));
		titleLabel.setTranslateX(10);
				
		// Style buttons
		logoutButton.setPrefSize(80, 10);
		logoutButton.setFont(new Font("Arial", 10));
		
		//directoryButton.setPrefSize(130, 10);
		//directoryButton.setFont(new Font("Arial", 10));
				
		// Style header boxes
		titleBox.setPrefWidth(200);
		titleBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		titleBox.setAlignment(Pos.CENTER_LEFT);
		titleBox.setTranslateX(10);
				
		headerButtonBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		headerButtonBox.setAlignment(Pos.CENTER_RIGHT);
		headerButtonBox.setTranslateX(700);
				
		// Style header
		headerRow.setPrefSize(1000, 30);
		headerRow.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		headerRow.setAlignment(Pos.CENTER_LEFT);
		headerRow.setSpacing(10);
	}
	
	private void stylizeRoomGrid()
	{
		roomsGrid.setPrefSize(1000, 570);
		roomsGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
	}
	
	private void stylizeRooms()
	{
		room1Label.setFont(new Font("Arial", 16));
		room2Label.setFont(new Font("Arial", 16));
		room3Label.setFont(new Font("Arial", 16));
		room4Label.setFont(new Font("Arial", 16));
		room5Label.setFont(new Font("Arial", 16));
		room6Label.setFont(new Font("Arial", 16));
		
		HBox.setMargin(room1Label, new Insets(10, 200, 10, 10));
		HBox.setMargin(room2Label, new Insets(10, 200, 10, 10));
		HBox.setMargin(room3Label, new Insets(10, 200, 10, 10));
		HBox.setMargin(room4Label, new Insets(10, 200, 10, 10));
		HBox.setMargin(room5Label, new Insets(10, 200, 10, 10));
		HBox.setMargin(room6Label, new Insets(10, 200, 10, 10));
		
		HBox.setMargin(roomButton1, new Insets(10, 10, 10, 10));
		HBox.setMargin(roomButton2, new Insets(10, 10, 10, 10));
		HBox.setMargin(roomButton3, new Insets(10, 10, 10, 10));
		HBox.setMargin(roomButton4, new Insets(10, 10, 10, 10));
		HBox.setMargin(roomButton5, new Insets(10, 10, 10, 10));
		HBox.setMargin(roomButton6, new Insets(10, 10, 10, 10));
		
		roomHeaderBox1.setPrefSize(333.3, 20);
		roomHeaderBox2.setPrefSize(333.3, 20);
		roomHeaderBox3.setPrefSize(333.3, 20);
		roomHeaderBox4.setPrefSize(333.3, 20);
		roomHeaderBox5.setPrefSize(333.3, 20);
		roomHeaderBox6.setPrefSize(333.3, 20);
		
		roomBox1.setPrefSize(333.3, 260);
		roomBox2.setPrefSize(333.3, 260);
		roomBox3.setPrefSize(333.3, 260);
		roomBox4.setPrefSize(333.3, 260);
		roomBox5.setPrefSize(333.3, 260);
		roomBox6.setPrefSize(333.3, 260);
		
		roomBox1.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		roomBox2.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		roomBox3.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		roomBox4.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		roomBox5.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		roomBox6.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
	}
}
