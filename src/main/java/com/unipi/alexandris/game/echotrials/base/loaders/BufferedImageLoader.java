package com.unipi.alexandris.game.echotrials.base.loaders;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import com.unipi.alexandris.game.echotrials.base.Game;

import javax.imageio.ImageIO;

/**
 * The BufferedImageLoader class handles loading and resizing of game images.
 * It provides functionality for:
 * <ul>
 *   <li>Loading images from resource files</li>
 *   <li>Resizing images to specified dimensions</li>
 *   <li>Error handling for image loading failures</li>
 * </ul>
 * This class is essential for managing game assets and sprites.
 */
public class BufferedImageLoader {

	/** The currently loaded image buffer. */
	private BufferedImage image;
	
	/**
	 * Constructs a new BufferedImageLoader.
	 * Initializes an empty loader ready to handle image loading requests.
	 */
	public BufferedImageLoader() {
		
	}
	
	/**
	 * Loads an image from the specified resource path.
	 * Uses the Game class's classloader to locate resources.
	 * Handles potential IO errors and provides error logging.
	 *
	 * @param path The resource path to the image file
	 * @return The loaded BufferedImage, or null if loading fails
	 */
	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(Objects.requireNonNull(Game.class.getResource(path)));
		} catch (IOException ignored) {
			System.out.println("[SEVERE]: There has been an error attempting to load an image with the BufferedImageLoader Class.");
		}
		return image;
	}

	/**
	 * Resizes an image to specified dimensions.
	 * Creates a new image with the target size and maintains transparency.
	 *
	 * @param originalImage The source image to resize
	 * @param targetWidth The desired width in pixels
	 * @param targetHeight The desired height in pixels
	 * @return A new BufferedImage with the specified dimensions
	 * @throws IOException If there's an error during image processing
	 */
	public BufferedImage resizeImage(BufferedImage originalImage, double targetWidth, double targetHeight) throws IOException {
		BufferedImage resizedImage = new BufferedImage((int)targetWidth,(int)targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, (int)targetWidth, (int)targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}
	
}
