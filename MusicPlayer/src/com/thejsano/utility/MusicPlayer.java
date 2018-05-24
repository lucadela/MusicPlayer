package com.thejsano.utility;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MusicPlayer extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = FXMLLoader.load(getClass().getResource("MusicPlayer.fxml"));
		root.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(new Scene(root));
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
	}

	protected static void close() {
		Platform.exit();
	}

	public static void main(String[] args) {
		launch(args);
	}
}