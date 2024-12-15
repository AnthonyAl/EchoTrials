package com.unipi.alexandris.game.echotrials.base.physics;

import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

import java.awt.*;
import java.awt.geom.Area;

/**
 * The ParticleCreator class manages the creation and spawning of particle effects.
 * It provides functionality for:
 * <ul>
 *   <li>Creating particle systems with various properties</li>
 *   <li>Spawning particles with custom colors, sizes, and behaviors</li>
 *   <li>Managing particle physics and collision areas</li>
 *   <li>Supporting different particle shapes and patterns</li>
 * </ul>
 * This class is essential for creating visual effects like dust, water splashes, and explosions.
 */
public class ParticleCreator {
	
	/** The game object handler for managing particle instances. */
	Handler handler;

	/**
	 * Constructs a new ParticleCreator with a reference to the game's object handler.
	 *
	 * @param handler The handler for managing game objects and particles
	 */
	public ParticleCreator(Handler handler) {
		this.handler = handler;
	}
	
	/**
	 * Spawns a complex particle system with customizable properties.
	 * Creates multiple particles with specified behavior and appearance.
	 *
	 * @param num Number of particles to spawn
	 * @param x X coordinate of spawn point
	 * @param y Y coordinate of spawn point
	 * @param color Array of colors for particles
	 * @param size Base size of particles
	 * @param offset Random offset for particle positions
	 * @param spawn_offsetX X-axis spawn area width
	 * @param spawn_offsetY Y-axis spawn area height
	 * @param size_offset Random variation in particle size
	 * @param time Particle lifetime in seconds
	 * @param gravityX Horizontal gravity effect
	 * @param gravityY Vertical gravity effect
	 * @param shape Particle shape ('c' for circle, 's' for square)
	 */
	public void spawn(int num, double x, double y, Color[] color, int size, int offset, int spawn_offsetX, int spawn_offsetY, int size_offset, double time, double gravityX, double gravityY, char shape) {
		for(int i = 0; i < num; i++) {
			handler.addObject(new Particles(x, y, ID.Particles, handler, color, size, offset, spawn_offsetX, spawn_offsetY, size_offset, time, gravityX, gravityY, 0, 0, new Area(), shape));
		}
	}

	/**
	 * Spawns a physics-based particle system with velocity and collision.
	 * Creates particles that interact with obstacles and have initial velocity.
	 *
	 * @param num Number of particles to spawn
	 * @param x X coordinate of spawn point
	 * @param y Y coordinate of spawn point
	 * @param color Array of colors for particles
	 * @param size Base size of particles
	 * @param offset Random offset for particle positions
	 * @param spawn_offsetX X-axis spawn area width
	 * @param spawn_offsetY Y-axis spawn area height
	 * @param size_offset Random variation in particle size
	 * @param time Particle lifetime in seconds
	 * @param gravityX Horizontal gravity effect
	 * @param gravityY Vertical gravity effect
	 * @param velX Initial X velocity
	 * @param velY Initial Y velocity
	 * @param obstructions Collision areas for particles
	 * @param shape Particle shape ('c' for circle, 's' for square)
	 */
	public void spawn(int num, double x, double y, Color[] color, int size, int offset, int spawn_offsetX, int spawn_offsetY, int size_offset, double time, double gravityX, double gravityY, double velX, double velY, Area obstructions, char shape) {
		for(int i = 0; i < num; i++) {
			handler.addObject(new Particles(x, y, ID.Particles, handler, color, size, offset, spawn_offsetX, spawn_offsetY, size_offset, time, gravityX, gravityY, velX, velY, obstructions, shape));
		}
	}

	/**
	 * Spawns a simple single-color particle effect.
	 * Simplified method for basic particle effects with minimal parameters.
	 *
	 * @param x X coordinate of spawn point
	 * @param y Y coordinate of spawn point
	 * @param color Color of the particle
	 * @param offset Random offset for particle position
	 * @param size Size of the particle
	 * @param time Particle lifetime in seconds
	 * @param shape Particle shape ('c' for circle, 's' for square)
	 */
	public void spawn(double x, double y, Color color, int offset, int size, double time, char shape) {
		Color[] c = {color};
		for(int i = 0; i < 1; i++) {
			handler.addObject(new Particles(x, y, ID.Particles, handler, c, size, offset, 1, 1, 1, time, 0, 0, 0, 0, new Area(), shape));
		}
	}
	
	
}
