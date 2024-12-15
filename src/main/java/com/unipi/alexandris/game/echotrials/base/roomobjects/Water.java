package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * Represents a water body in the game environment.
 * Provides swimming physics and visual effects for submerged objects.
 * Affects player movement and physics when in contact.
 */
public class Water extends GameObject {
	
	/** Water texture */
	private final BufferedImage image;
	
	/**
	 * Creates a new water area.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param image Water texture
	 */
	public Water(int x, int y, ID id, BufferedImage image) {
		super(x, y, id);
		this.image = image;
	}
	
	/**
	 * Updates water state.
	 * Currently no update behavior implemented.
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Renders the water area with transparency.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);
	}
	
	/**
	 * Gets water's collision bounds.
	 * @return Rectangle for basic collision
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, image.getWidth(), image.getHeight());
	}
	
	/**
	 * Gets expanded collision bounds.
	 * @param a Expansion amount
	 * @return Expanded rectangle bounds
	 */
	public Rectangle getBounds(int a) {
		return new Rectangle((int) x - a, (int) y - a, image.getWidth() + 2 * a, image.getHeight() + 2 * a);
	}
	
	/**
	 * Gets water's precise collision area.
	 * @return Area for collision detection
	 */
	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
