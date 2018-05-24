package com.thejsano.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thejsano.player.UserInterface;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Model {
	UserInterface ui;

	FileChooser fileChooser;
	MediaPlayer media;
	List<File> selectedFile = new ArrayList<File>();

	SimpleDoubleProperty time = new SimpleDoubleProperty();
	ObservableList<String> songList = FXCollections.observableArrayList();

	int nbPlayClick = 0;
	int index = 0;
	int song = -1;
	int nextClick = 0;

	public Model(UserInterface ui) {
		this.ui = ui;
	}

	public void choser(Stage stage) {
		// File chooser
		fileChooser = new FileChooser();
		fileChooser.setTitle("Select song");

		// Default directory
		fileChooser.setInitialDirectory(new File("C:\\Users"));

		// Limits to audio
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
		List<File> files = fileChooser.showOpenMultipleDialog(stage);

		for (File file : files) {
			if (!selectedFile.contains(file)) {
				selectedFile.add(file);
				addToList();
			}
		}

	}

	// Select the song to play
	public void selectSong() {
		// If is the same song then return
		if (song == ui.listView.getSelectionModel().getSelectedIndex())
			return;
		if (media != null) {
			media.stop();
		}

		nbPlayClick = 0;
		song = ui.listView.getSelectionModel().getSelectedIndex();

		try {
			media = new MediaPlayer(new Media(selectedFile.get(song).toURI().toString()));
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	// Add song to the list
	private void addToList() {
		songList.add(index, selectedFile.get(index).getName());
		ui.listView.setItems(songList);
		index++;

	}

	public void play() {
		// If we press play with no song selected it will play the first
		if (media == null) {
			if (selectedFile.isEmpty())
				return;
			song++;
			media = new MediaPlayer(new Media(selectedFile.get(song).toURI().toString()));
		}

		nbPlayClick++;
		// Pause if the song is running
		if (nbPlayClick % 2 == 0) {
			media.pause();
			return;
		}
		// Play song
		media.play();

		// Set the song title
		ui.songTitle.setText(selectedFile.get(song).getName());

		// Setting the slider bar time
		ui.bar.setMax(getSongLength());
		media.currentTimeProperty().addListener((obsVal, oldVal, newVal) -> {
			update();
		});

	}

	// Play next song
	public void next() {
		nextClick++;
		System.out.println(nextClick);
		if (nextClick % index == 0)
			song = -1;

		nbPlayClick = 0;
		media.stop();
		song++;
		media = new MediaPlayer(new Media(selectedFile.get(song).toURI().toString()));
		play();

	}

	private void updateTimeLabel() {
		ui.lblTime.setText(formatTime(getTime()).concat("/" + formatTime(getSongLength())));
	}

	private String formatTime(double time) {
		int min = (int) time / 60;
		int sec = (int) (time - min * 60);

		String m = String.valueOf(min);
		String s = String.valueOf(sec);

		if (min < 10)
			m = "0" + min;
		if (sec < 10)
			s = "0" + sec;

		return new String(m + ":" + s);
	}

	private double getSongLength() {
		return media.getStopTime().toSeconds();
	}

	public double getTime() {
		return media.getCurrentTime().toSeconds();

	}

	private void update() {
		time.set(getTime());
		updateTimeLabel();
		ui.bar.valueProperty().bind(time);
	}

}
