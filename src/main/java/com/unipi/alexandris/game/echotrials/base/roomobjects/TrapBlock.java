package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * A trap block that can be triggered to appear or disappear.
 * Used to create dynamic hazards and obstacles in game levels.
 * Provides collision detection and visual state management.
 */
public class TrapBlock extends GameObject {

	/** Block texture */
	private final BufferedImage image;

	/** Flag indicating if trap is currently visible */
	public boolean visible = true;

	/**
	 * Creates a new trap block.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param image Block texture
	 */
	public TrapBlock(int x, int y, ID id, BufferedImage image) {
		super(x, y, id);
		size = 48;
		this.image = image;
	}

	/**
	 * Updates trap block state.
	 * Currently no update behavior implemented.
	 */
	@Override
	public void tick() {

	}

	/**
	 * Renders the trap block if visible.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
		if(visible) g.drawImage(image, (int) x, (int) y, null);
	}

	/**
	 * Gets basic collision bounds.
	 * Currently returns null as precise collision is handled by getArea().
	 * @return null as precise collision is used instead
	 */
	@Override
	public Rectangle getBounds() {
		return null;
	}
	
	/**
	 * Gets expanded collision bounds.
	 * Currently returns null as precise collision is handled by getArea().
	 * @param ignoredA Ignored padding parameter
	 * @return null as precise collision is used instead
	 */
	public Rectangle getBounds(int ignoredA) {
		return null;
	}

	/**
	 * Gets trap's precise collision area.
	 * @return Rectangular area for collision detection
	 */
	@Override
	public Area getArea() {
		return new Area(new Rectangle2D.Double(x, y, size, size));
	}

}
