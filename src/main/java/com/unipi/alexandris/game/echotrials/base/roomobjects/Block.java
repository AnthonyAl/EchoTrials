package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;

/**
 * Basic solid block class that forms the static geometry of game levels.
 * Provides collision and rendering for standard rectangular blocks.
 */
public class Block extends GameObject {
	
	/** Size of the block in pixels, must be divisible by 4 */
	private int pixels = 32;

	/**
	 * Constructs a new block with specified position and size.
	 * @param x The X coordinate of the block
	 * @param y The Y coordinate of the block
	 * @param id The block's identifier type
	 * @param pixels The size of the block (must be positive and divisible by 4)
	 */
	public Block(int x, int y, ID id, int pixels) {
		super(x, y, id);
		if(pixels > 0 && pixels % 4 == 0) this.pixels = pixels;
	}

	/**
	 * Updates the block's state each game tick.
	 * Blocks are static and do not need updating.
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Renders the block on the screen.
	 * Draws a filled blue rectangle with a 1-pixel offset.
	 * @param g The graphics context to render to
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int) x + 1, (int) y + 1, pixels - 1, pixels - 1);
		//g.setColor(Color.black);
		//g.drawRect((int) x, (int) y, pixels, pixels);
		//g.drawLine((int) x + pixels, (int) y,  (int) x + pixels, (int) y + pixels);
	}

	/**
	 * Gets the block's rectangular bounds for collision detection.
	 * @return Rectangle representing the block's bounds
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, pixels, pixels);
	}

	/**
	 * Gets the block's precise collision area.
	 * Currently returns null as blocks use simple rectangular collision.
	 * @return null as blocks use getBounds() for collision
	 */
	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
	}

}
