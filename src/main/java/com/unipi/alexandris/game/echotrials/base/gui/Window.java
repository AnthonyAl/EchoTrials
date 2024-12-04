package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.loaders.BufferedImageLoader;

import java.awt.*;
import java.io.Serial;

import javax.swing.JFrame;

public class Window extends Canvas {
	@Serial
	private static final long serialVersionUID = 2184430560234321567L;

	Game game;
	
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
