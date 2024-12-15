package com.unipi.alexandris.game.echotrials.base.loaders;

import com.unipi.alexandris.game.echotrials.base.Game;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;
import java.util.Random;

/**
 * The SoundFXLoader class manages sound effect playback in the game.
 * It provides functionality for:
 * <ul>
 *   <li>Loading and playing individual sound effects</li>
 *   <li>Random selection from multiple sound variants</li>
 *   <li>Asynchronous sound playback using JavaFX</li>
 *   <li>Error handling for sound loading failures</li>
 * </ul>
 * This class ensures smooth audio feedback during gameplay.
 */
public class SoundFXLoader {

	/**
	 * Shared MediaPlayer instance for sound playback.
	 * Stored in an array to allow modification from lambda expressions.
	 */
	static final MediaPlayer[] mediaPlayer = {null};

	/**
	 * Constructs a new SoundFXLoader.
	 * Initializes a loader ready to handle sound playback requests.
	 */
	public SoundFXLoader() {

	}

	/**
	 * Plays a sound effect from the specified resource path.
	 * Uses JavaFX Platform.runLater for asynchronous playback.
	 * Handles potential errors during sound loading and playback.
	 *
	 * @param path The resource path to the sound file
	 */
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

	/**
	 * Plays a randomly selected sound from multiple options.
	 * Useful for adding variety to repeated sound effects.
	 * Uses JavaFX Platform.runLater for asynchronous playback.
	 *
	 * @param paths Variable number of resource paths to choose from
	 */
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
