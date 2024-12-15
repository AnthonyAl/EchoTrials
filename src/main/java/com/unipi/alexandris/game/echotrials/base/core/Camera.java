package com.unipi.alexandris.game.echotrials.base.core;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;

/**
 * The Camera class handles the viewport movement and positioning in the game.
 * It follows the player (or other game objects) smoothly and ensures the view stays within
 * the level boundaries. The camera implements a smooth follow mechanism with interpolation
 * for fluid movement.
 */
public class Camera {
	
	/** The camera's X coordinate in the game world. */
	private double x;
	
	/** The camera's Y coordinate in the game world. */
	private double y;
	
	/** The width of the camera's view area in game units. */
	private int width = 0;
	
	/** The height of the camera's view area in game units. */
	private int height = 0;
	
	/** The size multiplier for converting game units to screen pixels. */
	private int multiplier = 1;

	/**
	 * Constructs a new Camera with specified position and dimensions.
	 *
	 * @param x The initial X coordinate of the camera
	 * @param y The initial Y coordinate of the camera
	 * @param width The width of the camera's view area
	 * @param height The height of the camera's view area
	 * @param multiplier The size multiplier for converting game units to screen pixels (must be > 1)
	 */
	public Camera(double x, double y, int width, int height, int multiplier) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if(multiplier > 1) this.multiplier = multiplier;
	}

	/**
	 * Updates the camera position to follow a game object.
	 * Uses smooth interpolation to create fluid camera movement and includes boundary checking
	 * to prevent the camera from showing areas outside the level.
	 *
	 * @param object The GameObject to follow (typically the player)
	 */
	public void tick(GameObject object) {
		// Calculate smooth camera movement with 5% interpolation
		x += ((object.getX() + (double) object.getSize() / 2 - x) - (double) Game.WIDTH / 2) * 0.05f;
		y += ((object.getY() + (double) object.getSize() / 2 - y) - (double) Game.HEIGHT / 2) * 0.05f;
		
		// Enforce camera boundaries
		if(x <= 0) x = 0;
		if(x >= width * multiplier - Game.WIDTH + (double) object.getSize() / 2 - 10) x = width * multiplier - Game.WIDTH + (double) object.getSize() / 2 - 10;
		if(y <= 0) y = 0;
		if(y >= height * multiplier - Game.HEIGHT + (double) object.getSize() / 2 + 10) y = height * multiplier - Game.HEIGHT + (double) object.getSize() / 2 + 10;
	}

	/**
	 * Gets the camera's current X coordinate.
	 *
	 * @return The X coordinate in game world units
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the camera's X coordinate.
	 *
	 * @param x The new X coordinate in game world units
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Gets the camera's current Y coordinate.
	 *
	 * @return The Y coordinate in game world units
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the camera's Y coordinate.
	 *
	 * @param y The new Y coordinate in game world units
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Gets the camera's view width in screen pixels.
	 *
	 * @return The width multiplied by the size multiplier
	 */
	public int getWidth() {
		return width*multiplier;
	}

	/**
	 * Gets the camera's view height in screen pixels.
	 *
	 * @return The height multiplied by the size multiplier
	 */
	public int getHeight() {
		return height*multiplier;
	}
}
