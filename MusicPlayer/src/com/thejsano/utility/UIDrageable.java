package com.thejsano.utility;

import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UIDrageable implements Initializable {
	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	private HBox root;
	private Stage stage;

	public UIDrageable(HBox root, Stage stage) {
		this.root = root;
		this.stage = stage;
	}

	public void makeStageDrageable() {
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

	@FXML
	private void close_app(MouseEvent event) {
		System.exit(0);
	}

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		makeStageDrageable();

	}

}
