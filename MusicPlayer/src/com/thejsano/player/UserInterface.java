package com.thejsano.player;

import com.thejsano.utility.Model;
import com.thejsano.utility.UIDrageable;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserInterface extends Application {
	// Make UI Drageable
	UIDrageable drag = null;

	// Main container with player
	VBox root = new VBox(10);

	// Controller
	HBox controller = new HBox(10);

	// Main Scene
	Scene scene = null;

	// Main Stage
	Stage stage = null;

	// Control buttons
	public Button play = new Button("Play");
	Button next = new Button("Next");
	Button previous = new Button("Previous");

	// Open directory
	Button insert = new Button("Select");

	// SliderBar
	public Slider bar = new Slider(0, 10, 100);

	// Help moving variables
	private double xOffset = 0;
	private double yOffset = 0;

	// Label
	// Time label
	public Label lblTime = new Label("00:00/XX:XX");
	// Song title
	public Label songTitle = new Label("No song");
	
	//ListView
	public ListView<String> listView = new ListView<String>();

	// Model
	Model model;

	// ================================================================================================
	// Initialize
	@Override
	public void init() {
		makeStageDrageable();
	}

	// Start
	@Override
	public void start(Stage stage) throws Exception {
		createContentMain();
		modifyContent();
		model = new Model(this);
		this.stage = stage;
		this.stage.initStyle(StageStyle.TRANSPARENT);
		this.stage.setResizable(false);
		this.stage.setTitle("MusicPlayer");
		this.stage.setScene(scene);
		this.stage.show();
	}

	// ================================================================================================
	// Create the content of the main panel and populate it
	private void createContentMain() {
		controller.getChildren().addAll(previous, play, next, insert);
		root.getChildren().addAll(songTitle, controller, bar, lblTime, listView);

		scene = new Scene(root);
	}

	// Sett all the correct setting for the content
	private void modifyContent() {
		// Add a little bit of padding in the root
		root.setPadding(new Insets(10));

		// Select song
		insert.setOnAction(e -> model.choser(stage));

		// Play
		play.setOnAction(e -> model.play());
		
		//Next
		next.setOnAction(e -> model.next());
		
		//Song selection
		listView.setOnMouseClicked(e -> model.selectSong());

	}

	// ================================================================================================
	// Drag the application
	private void makeStageDrageable() {
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
				stage.setOpacity(0.7f);
			}
		});
		root.setOnDragDone((e) -> {
			stage.setOpacity(1.0f);
		});
		root.setOnMouseReleased((e) -> {
			stage.setOpacity(1.0f);
		});
	}
}
