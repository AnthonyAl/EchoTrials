package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;
import com.unipi.alexandris.game.echotrials.base.physics.ParticleCreator;
import com.unipi.alexandris.game.echotrials.base.physics.PhysicsPlatformer;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * Hazardous spike trap that damages the player on contact.
 * Features state management for visibility and orientation.
 * Can be triggered to appear/disappear and supports different spike types.
 */
public class SpikeBlock extends GameObject {

	/** Game object handler for collision detection */
	private final Handler handler;
	
	/** Array of spike state images */
	private final BufferedImage[] image;
	
	/** Flag indicating if spike is visible */
	private boolean arisen = false;
	
	/** Flag indicating if spike is pointing downward */
	private boolean inverted = false;
	
	/** Current spike image index */
	private int i = 0;
	
	/**
	 * Creates a new spike block.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param image Array of spike state images
	 * @param handler Game object handler
	 */
	public SpikeBlock(int x, int y, ID id, BufferedImage[] image, Handler handler) {
		super(x, y, id);
		setCancelUP(true);
		size = 48;
		this.handler = handler;
		this.image = image;
		ParticleCreator pc = new ParticleCreator(handler);
	}

	/**
	 * Updates spike state and checks for player collision.
	 * Handles player damage and physics interactions.
	 */
	@Override
	public void tick() {
		if(!isCancelUP()) {
			PhysicsPlatformer pp = new PhysicsPlatformer(0, 500, 10);

			obstructions.add(Game.player.obstructions);
			double[] coords = pp.movementPhysics(obstructions, size, size, x, y, getPressUP(), getPressDOWN(), getPressLEFT(), getPressRIGHT());
			y = coords[1];
			coords = pp.swimmingPhysics(obstructions, Game.water, size, size, x, y, getPressUP(), getPressDOWN(), getPressLEFT(), getPressRIGHT());
			y = coords[1];
		}
		for(GameObject tempObject : handler.getObject())
			if(tempObject instanceof Player player) {
				if(getArea().intersects(player.getBounds())) {
					player.death = true;
					
				}
			}
	}

	/**
	 * Renders the spike block if visible.
	 * Handles orientation-based rendering.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
		if(arisen) {
			if(inverted) g.drawImage(image[i], (int) x, (int) y + size, size, -size, null);
			else g.drawImage(image[i], (int) x, (int) y, null);
		}
	}

	/**
	 * Switches to the next spike image.
	 * Used for spike type variations.
	 */
	public void switchImage() {
		if(++i > 1) i = 0;
	}

	/**
	 * Makes the spike block visible.
	 */
	public void arise() {
		arisen = true;
	}

	/**
	 * Makes the spike block invisible.
	 */
	public void hide() {
		arisen = false;
	}

	/**
	 * Inverts the spike's orientation.
	 */
	public void invert() {
		inverted = true;
	}

	/**
	 * Checks if spike is currently hidden.
	 * @return true if spike is hidden, false if visible
	 */
	public boolean isHidden() {
		return !arisen;
	}

	/**
	 * Gets spike's collision bounds.
	 * @return Rectangle for collision detection, or empty rectangle if hidden
	 */
	@Override
	public Rectangle getBounds() {
		if(arisen) return new Rectangle((int) x, (int) y + 20, size, size - 20);
		else return new Rectangle(0, 0, 0, 0);
	}
	
	/**
	 * Gets expanded collision bounds.
	 * @param a Expansion amount
	 * @return Expanded rectangle bounds, or empty rectangle if hidden
	 */
	public Rectangle getBounds(int a) {
		if(arisen) return new Rectangle((int) x - a, (int) y + 20 - a, size + a*2, (size-20) + a*2);
		else return new Rectangle(0, 0, 0, 0);
	}

	/**
	 * Gets spike's precise collision area.
	 * @return Elliptical area for collision detection, or empty area if hidden
	 */
	@Override
	public Area getArea() {
		if(arisen) return new Area(new Ellipse2D.Double((int) x, (int) y + 20, size, size - 20));
		else return new Area(new Rectangle(0, 0, 0, 0));
	}

}
