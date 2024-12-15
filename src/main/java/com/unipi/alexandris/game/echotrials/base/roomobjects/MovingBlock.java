package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * A dynamic block that can move in response to triggers or player interaction.
 * Provides collision handling and movement mechanics for mobile platforms.
 * Supports player transport and synchronized movement with other game objects.
 */
public class MovingBlock extends GameObject {

	/** Block texture */
	private final BufferedImage image;

	/** Movement behavior callback */
	private Runnable movement;

	/** Previous X position for velocity calculation */
	private double prevX = x;

	/** Previous Y position for velocity calculation */
	private double prevY = y;

	/** Flag indicating player contact */
	private boolean touchedPlayer = false;

	/**
	 * Creates a new moving block.
	 * @param x Initial X position
	 * @param y Initial Y position
	 * @param id Object identifier
	 * @param image Block texture
	 */
	public MovingBlock(int x, int y, ID id, BufferedImage image) {
		super(x, y, id);
		size = 48;
		this.image = image;
	}

	/**
	 * Updates block position and handles collisions.
	 * Manages player interaction and platform movement.
	 */
	@Override
	public void tick() {
		if(Game.player == null) return;
		double vely = y - prevY;
		double velx = x - prevX;
		prevX = x;
		prevY = y;

		Area up = new Area(getRectangle(x - 2, y - 8, size + 4, 5));
		Area down = new Area(getRectangle(x - 2, y + size - 1, size + 4, 2));
		Area left = new Area(getRectangle(x - 8, y + 2, 5, size - 4));
		Area right = new Area(getRectangle(x + size + 3, y + 2, 5, size - 4));

		up.intersect(new Area(Game.player.getBounds()));
		down.intersect(new Area(Game.player.getBounds()));
		left.intersect(new Area(Game.player.getBounds()));
		right.intersect(new Area(Game.player.getBounds()));
		if(Math.abs(vely) > 0) {
			if(!up.isEmpty() && vely<0) {
				Game.player.setCancelUP(true);
				Game.player.setPressUP(false);
				Game.player.setY(Game.player.getY() + vely);
			}
			if(!down.isEmpty() && vely>0) {
				Game.player.setCancelUP(true);
				Game.player.setPressUP(false);
				Game.player.setY(Game.player.getY() + vely);
			}
		}
		else {
			if(!up.isEmpty() || !down.isEmpty()) Game.player.setCancelUP(false);
		}
		if(Math.abs(velx) > 0) {
			if(!left.isEmpty() && velx<0) {
				Game.player.setCancelRIGHT(true);
				Game.player.setPressRIGHT(false);
				Game.player.setX(Game.player.getX() + velx);
				touchedPlayer = true;
			}
			if(!right.isEmpty() && velx>0) {
				Game.player.setCancelLEFT(true);
				Game.player.setPressLEFT(false);
				Game.player.setX(Game.player.getX() + velx);
				touchedPlayer = true;
			}
		}
		else {
			if(touchedPlayer) {
				Game.player.setCancelRIGHT(false);
				Game.player.setCancelLEFT(false);
				touchedPlayer = false;
			}
		}

		up = new Area(getRectangle(x - 2, y - 8, size + 4, 5));
		down = new Area(getRectangle(x - 2, y + size - 1, size + 4, 2));
		left = new Area(getRectangle(x - 8, y + 2, 5, size - 4));
		right = new Area(getRectangle(x + size + 3, y + 2, 5, size - 4));

		up.intersect(new Area(Game.goal.getBounds()));
		down.intersect(new Area(Game.goal.getBounds()));
		left.intersect(new Area(Game.goal.getBounds()));
		right.intersect(new Area(Game.goal.getBounds()));
		if(Math.abs(vely) > 0) {
			if (!up.isEmpty() && vely<0) {
				Game.goal.setY(Game.goal.getY() + vely);
			}
			if (!down.isEmpty() && vely>0) {
				Game.goal.setY(Game.goal.getY() + vely);
			}
		}
		if(Math.abs(velx) > 0) {
			if(!right.isEmpty() && velx<0) {
				Game.goal.setX(Game.goal.getX() + velx);
			}
			if(!right.isEmpty() && velx>0) {
				Game.goal.setX(Game.goal.getX() + velx);
			}
		}
	}

	/**
	 * Renders the moving block.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);
	}

	/**
	 * Gets block's collision bounds.
	 * @return Rectangle representing block bounds
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}
	
	/**
	 * Gets expanded collision bounds.
	 * @param a Expansion amount
	 * @return Expanded rectangle bounds
	 */
	public Rectangle getBounds(int a) {
		return new Rectangle((int) x - a, (int) y - a, size + a*2, size + a*2);
	}

	/**
	 * Gets precise collision area.
	 * @return Area for collision detection
	 */
	@Override
	public Area getArea() {
		return new Area(new Rectangle2D.Double(x, y, size, size));
	}

	/**
	 * Sets the block's movement behavior.
	 * @param movement Runnable defining movement pattern
	 */
	public void setMovement(Runnable movement) {
		this.movement = movement;
	}

	/**
	 * Gets the current movement behavior.
	 * @return Movement callback
	 */
	public Runnable getMovement() {
		return movement;
	}

	/**
	 * Creates a rectangle for collision detection.
	 * @param a X coordinate
	 * @param b Y coordinate
	 * @param c Width
	 * @param d Height
	 * @return Rectangle with specified dimensions
	 */
	public Rectangle getRectangle(double a, double b, double c, double d) {
		return new Rectangle((int) a,(int) b, (int) c, (int) d);
	}

}
