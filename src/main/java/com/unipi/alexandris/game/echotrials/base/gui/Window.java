package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.loaders.BufferedImageLoader;

import java.awt.*;
import java.io.Serial;

import javax.swing.JFrame;

/**
 * The Window class manages the game's main window and display settings.
 * It extends Canvas and handles window creation, sizing, and fullscreen functionality.
 * This class serves as the container for the game's visual components and manages
 * the game window's properties such as size, title, and display mode.
 */
public class Window extends Canvas {
	/** Serialization version UID for the Window class. */
	@Serial
	private static final long serialVersionUID = 2184430560234321567L;

	/** Reference to the main game instance. */
	Game game;

	/**
	 * Constructs a new game window with specified dimensions and properties.
	 * Sets up the JFrame with appropriate size constraints, icon, and display settings.
	 * Also initializes and starts the game within the window.
	 *
	 * @param width The width of the window in pixels
	 * @param height The height of the window in pixels
	 * @param title The title to display in the window's title bar
	 * @param game Reference to the main Game instance
	 */
	public Window(int width, int height, String title, Game game) {
		this.game = game;
		BufferedImageLoader loader = new BufferedImageLoader();
		Image icon = loader.loadImage("/textures/Terraformar.png");

        JFrame frame = new JFrame(title);

		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		Toolkit tk = Toolkit.getDefaultToolkit();
		frame.setMaximizedBounds(new Rectangle(tk.getScreenSize().width+10, tk.getScreenSize().height+40));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setIconImage(icon);
		frame.setVisible(true);
		game.frame = frame;
		game.start();
		frame.toFront();
		frame.requestFocus();
	}

	/**
	 * Toggles fullscreen mode for the game window.
	 * When activated, the window will occupy the entire screen and be set as always-on-top.
	 * When deactivated, the window returns to its normal windowed state.
	 */
	public void fullscreen() {
		GraphicsDevice fullscreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (game.frame.isAlwaysOnTop()) {
			game.frame.setAlwaysOnTop(false);
			fullscreenDevice.setFullScreenWindow(null);
		} else {
			game.frame.setAlwaysOnTop(true);
			fullscreenDevice.setFullScreenWindow(game.frame);
		}
	}

}
