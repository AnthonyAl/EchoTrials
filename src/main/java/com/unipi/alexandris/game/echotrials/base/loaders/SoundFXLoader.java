package com.unipi.alexandris.game.echotrials.base.loaders;

import com.unipi.alexandris.game.echotrials.base.Game;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;
import java.util.Random;

public class SoundFXLoader {


	static final MediaPlayer[] mediaPlayer = {null};

	public SoundFXLoader() {

	}

	public void playSound(String path) {
		try {
			Platform.runLater(() -> {
                Media sound = new Media(Objects.requireNonNull(Game.class.getResource(path)).toString());
                mediaPlayer[0] = new MediaPlayer(sound);
                mediaPlayer[0].play();
            });
		} catch (Exception e) {
			System.out.println("[SEVERE]: There has been an error attempting to load and play a requested sound.");
		}
	}

	public void playSound(String... paths) {
		try {
			Platform.runLater(() -> {
				Random random = new Random();
				Media sound = new Media(Objects.requireNonNull(Game.class.getResource(paths[random.nextInt(0, paths.length)])).toString());
				mediaPlayer[0] = new MediaPlayer(sound);
				mediaPlayer[0].play();
			});
		} catch (Exception e) {
			System.out.println("[SEVERE]: There has been an error attempting to load and play a requested sound.");
		}
	}

}
