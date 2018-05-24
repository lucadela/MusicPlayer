package com.thejsano.utility;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MusicPlayerController {

	@FXML
	ImageView close;

	@FXML
	private void close() {
		MusicPlayer.close();
	}
}