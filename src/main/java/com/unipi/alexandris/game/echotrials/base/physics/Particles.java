package com.unipi.alexandris.game.echotrials.base.physics;

import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Random;

/**
 * The Particles class represents individual particle objects in the game's particle system.
 * Each particle has its own:
 * <ul>
 *   <li>Physical properties (position, velocity, gravity)</li>
 *   <li>Visual properties (color, size, shape)</li>
 *   <li>Behavior characteristics (lifetime, collision)</li>
 *   <li>Random variations for natural movement</li>
 * </ul>
 * This class extends GameObject to integrate with the game's object system.
 */
public class Particles extends GameObject {
	
	/** The color of this particle. */
	private final Color color;
	
	/** Reference to the game object handler. */
	private final Handler handler;
	
	/** Random number generator for particle variations. */
	private final Random random = new Random();
	
	/** Base size of the particle. */
	private final int size;
	
	/** Random position offset range. */
	private final int offset;
	
	/** Spawn area width. */
	private final int spawn_offsetX;
	
	/** Spawn area height. */
	private final int spawn_offsetY;
	
	/** Random size variation range. */
	private final int size_offset;
	
	/** Lifetime counter for the particle. */
	private int counter;
	
	/** Horizontal gravity and velocity components. */
	private double gravityX = 0, gravityY = 0, velX = 0, velY = 0;
	
	/** Shape of the particle ('c' for circle, 's' for square). */
	private final char shape;
	
	/** Maximum lifetime of the particle. */
	private final double time;
	
	/** Collision areas that affect particle movement. */
	private final Area obstructions;
	
	/**
	 * Constructs a new Particle with specified properties.
	 * Initializes the particle with random variations within given ranges.
	 *
	 * @param x Initial X coordinate
	 * @param y Initial Y coordinate
	 * @param id Object identifier
	 * @param handler Game object handler
	 * @param color Array of possible colors
	 * @param size Base particle size
	 * @param offset Position variation range
	 * @param spawn_offsetX Spawn area width
	 * @param spawn_offsetY Spawn area height
	 * @param size_offset Size variation range
	 * @param time Maximum lifetime
	 * @param gravityX Horizontal gravity
	 * @param gravityY Vertical gravity
	 * @param velX Initial X velocity
	 * @param velY Initial Y velocity
	 * @param obstructions Collision areas
	 * @param shape Particle shape ('c' or 's')
	 */
	public Particles(double x, double y, ID id, Handler handler, Color[] color, int size, int offset, int spawn_offsetX, int spawn_offsetY, int size_offset, double time, double gravityX, double gravityY, double velX, double velY, Area obstructions, char shape) {
		super(x, y, id);
		if(time < 0) time = 0;
		if(size < 0) size = 0;
		if(offset <= 0) offset = 1;
		if(spawn_offsetX <= 0) spawn_offsetX = 1;
		if(spawn_offsetY <= 0) spawn_offsetY = 1;
		int i = random.nextInt(color.length);
		this.color = color[i];
		this.size = size;
		this.offset = offset;
		this.spawn_offsetX = spawn_offsetX;
		this.spawn_offsetY = spawn_offsetY;
		this.size_offset = size_offset;
		this.counter = 0;
		this.time = random.nextInt(10) * time;
		this.handler = handler;
		this.gravityX = gravityX;
		this.gravityY = gravityY;
		this.velX = velX;
		this.velY = velY;
		this.obstructions = obstructions;
		this.shape = shape;
	}

	/**
	 * Updates the particle's state each game tick.
	 * Handles:
	 * <ul>
	 *   <li>Random movement variations</li>
	 *   <li>Collision detection and response</li>
	 *   <li>Velocity and gravity effects</li>
	 *   <li>Lifetime counting</li>
	 * </ul>
	 */
	@Override
	public void tick() {
		double i = random.nextInt(offset)  - ((double) offset / 2) + velX;
		double j = random.nextInt(offset) - ((double) offset / 2) + velY;

		if(obstructions.isEmpty()) {
			x += i;
			y += j;
		}
		else {
			Area up = new Area(getBounds());
			Area down = new Area(getBounds());
			Area left = new Area(getBounds());
			Area right = new Area(getBounds());

			up.intersect(obstructions);
			down.intersect(obstructions);
			left.intersect(obstructions);
			right.intersect(obstructions);
			if (j <= 0) {
				if(up.isEmpty()) y += j;
			}
			else {
				if(down.isEmpty()) y += j;
			}
			if (i <= 0) {
				if(left.isEmpty()) x += i;
			}
			else {
				if(right.isEmpty()) x += i;
			}
		}

		if(velX != gravityX) velX += gravityX;
		if(velY != gravityY) velY += gravityY;
		counter++;
	}

	/**
	 * Renders the particle on the screen.
	 * Handles:
	 * <ul>
	 *   <li>Shape drawing (circle or square)</li>
	 *   <li>Random position and size variations</li>
	 *   <li>Particle removal when lifetime expires</li>
	 * </ul>
	 *
	 * @param g The Graphics context to render to
	 */
	@Override
	public void render(Graphics g) {
		if(counter < time) {
			g.setColor(color);
			if(shape == 's') g.fillRect((int) x + random.nextInt(spawn_offsetX) - (int)(spawn_offsetX / 2), (int) y + random.nextInt(spawn_offsetY) - (int)(spawn_offsetY / 2), size + random.nextInt(size_offset) - (int)(size_offset / 2), size+ random.nextInt(size_offset) - (int)(size_offset / 2));
			else if(shape == 'c') g.fillOval((int) x + random.nextInt(spawn_offsetX) - (int)(spawn_offsetX / 2), (int) y + random.nextInt(spawn_offsetY) - (int)(spawn_offsetY / 2), size + random.nextInt(size_offset) - (int)(size_offset / 2), size+ random.nextInt(size_offset) - (int)(size_offset / 2));
		}
		else {
			handler.removeObject(this);
		}
	}

	/**
	 * Gets the particle's rectangular bounds for collision detection.
	 *
	 * @return Rectangle representing the particle's bounds
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}

	/**
	 * Gets the particle's precise collision area.
	 * Currently not implemented as particles use simple rectangular collision.
	 *
	 * @return null as particles use getBounds() for collision
	 */
	@Override
	public Area getArea() {
		return new Area(getBounds());
	}
}
