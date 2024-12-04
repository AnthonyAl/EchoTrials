package com.unipi.alexandris.game.echotrials.base.loaders;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import com.unipi.alexandris.game.echotrials.base.Game;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image;
	
	public BufferedImageLoader() {
		
	}
	
	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(Objects.requireNonNull(Game.class.getResource(path)));
		} catch (IOException ignored) {
			System.out.println("[SEVERE]: There has been an error attempting to load an image with the BufferedImageLoader Class.");
		}
		return image;
	}

	public BufferedImage resizeImage(BufferedImage originalImage, double targetWidth, double targetHeight) throws IOException {
		BufferedImage resizedImage = new BufferedImage((int)targetWidth,(int)targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, (int)targetWidth, (int)targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}
	
}
