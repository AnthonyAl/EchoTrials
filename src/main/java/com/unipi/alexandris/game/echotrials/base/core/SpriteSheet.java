package com.unipi.alexandris.game.echotrials.base.core;

import java.awt.image.BufferedImage;

/**
 * The SpriteSheet class handles the extraction of individual sprites from a sprite sheet image.
 * A sprite sheet is a single image containing multiple sprites arranged in a grid pattern.
 * This class provides functionality to extract specific sprites based on their position in the grid.
 */
public class SpriteSheet {

	/** The source sprite sheet image containing all sprites. */
	private final BufferedImage image;
	
	/**
	 * Constructs a new SpriteSheet with the specified source image.
	 *
	 * @param image The BufferedImage containing the sprite sheet
	 */
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	/**
	 * Extracts a specific sprite from the sprite sheet based on its grid position.
	 * The grid is assumed to have 32x32 pixel cells, with (1,1) being the top-left cell.
	 *
	 * @param col The column number of the sprite (1-based indexing)
	 * @param row The row number of the sprite (1-based indexing)
	 * @param width The width of the sprite to extract in pixels
	 * @param height The height of the sprite to extract in pixels
	 * @return A BufferedImage containing the extracted sprite
	 */
	public BufferedImage grabImage(int col, int row, int width, int height) {
		return image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
	}
	
	
}
