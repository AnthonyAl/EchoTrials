package com.unipi.alexandris.game.echotrials.base.physics;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.loaders.SoundFXLoader;

import java.awt.*;
import java.awt.geom.Area;

/**
 * The PhysicsPlatformer class handles physics simulation for platforming mechanics.
 * It manages:
 * <ul>
 *   <li>Movement physics with collision detection</li>
 *   <li>Jumping and wall-jumping mechanics</li>
 *   <li>Swimming and water physics</li>
 *   <li>Surface-specific behavior (ice, blocks)</li>
 *   <li>Sound effects for movement</li>
 * </ul>
 * This class is central to the game's platforming mechanics and player movement.
 */
@SuppressWarnings(value = "unused")
public class PhysicsPlatformer {
	
	/** Wall-jump flags for left and right walls. */
	private boolean wjl = true, wjr = true;
	
	/** Current velocity components. */
	private double velx = 0, vely = 0;
	
	/** Return array for movement physics calculations. */
	private final double[] ret = new double[4];
	
	/** Return array for swimming physics calculations. */
	private final double[] ret2 = new double[5];
	
	/** Horizontal movement speed. */
	private double speedX = 5;
	
	/** Vertical movement speed (jump height). */
	private double speedY = 5;
	
	/** Gravity strength and direction. */
	private double gravity = 1;
	
	/** Flag indicating if last jump was from ice surface. */
	private boolean jumpedFromIce = false;
	
	/** Sound effect loader for movement audio. */
	private SoundFXLoader soundFXLoader;
	
	/** Resource path for jump sound effect. */
	private String jumpSoundPath;

	/**
	 * Constructs a PhysicsPlatformer with basic movement parameters.
	 *
	 * @param speedX Horizontal movement speed
	 * @param speedY Vertical movement speed
	 * @param gravity Gravity strength and direction
	 */
	public PhysicsPlatformer(double speedX, double speedY, double gravity) {
		this.speedX = speedX;
		this.speedY = speedY;
		this.gravity = gravity;
	}

	/**
	 * Constructs a PhysicsPlatformer with movement parameters and sound effects.
	 *
	 * @param speedX Horizontal movement speed
	 * @param speedY Vertical movement speed
	 * @param gravity Gravity strength and direction
	 * @param soundFXLoader Sound effect loader
	 * @param jumpSoundPath Path to jump sound resource
	 */
	public PhysicsPlatformer(double speedX, double speedY, double gravity, SoundFXLoader soundFXLoader, String jumpSoundPath) {
		this.speedX = speedX;
		this.speedY = speedY;
		this.gravity = gravity;
		this.soundFXLoader = soundFXLoader;
		this.jumpSoundPath = jumpSoundPath;
	}

	/**
	 * Default constructor with standard physics values.
	 */
	public PhysicsPlatformer() {}
	
	/**
	 * Calculates movement physics for platforming.
	 * Handles collision detection, wall-jumping, and surface effects.
	 *
	 * @param block Collision area for solid blocks
	 * @param width Object width
	 * @param height Object height
	 * @param x Current X position
	 * @param y Current Y position
	 * @param w Up/jump input
	 * @param s Down input
	 * @param a Left input
	 * @param d Right input
	 * @return Array containing [new X, new Y, X velocity, Y velocity]
	 */
	public double[] movementPhysics(Area block, double width, double height, double x, double y, boolean w, boolean s, boolean a, boolean d) {
		
		if(width < 5) width = 5;
		if(height < 5) height = 5;
		Area up = new Area(getRectangle(x - 3, y - 8 + vely, width + 6, 5 - vely));
		Area down = new Area(getRectangle(x - 3, y + height - 1, width + 6, 2 + vely));
		Area left = new Area(getRectangle(x - 8, y + 2, 5, height - 4));
		Area right = new Area(getRectangle(x + width + 3, y + 2, 5, height - 4));
		
		if(gravity >= 0) {
			up.intersect(block);
			if (!up.isEmpty()) {
				vely = 2;
			}

			assertGravity(block, w, down, up);
		}
		else {
			down.intersect(block);
			if (!down.isEmpty()) {
				vely = -2;
			}

			assertGravity(block, w, up, down);
		}

		if(speedX >= 0) {
			walk(block, w, d, a, up, down, left, right, -speedX, speedX);
		}
		else {
			walk(block, w, a, d, up, down, left, right, speedX, -speedX);
		}
		
		ret[0] = x + velx;
		ret[1] = y + vely / 2;
		ret[2] = velx;
		ret[3] = vely;
		
		return ret;
	}

	/**
	 * Helper method for handling walking and wall-jumping mechanics.
	 * Manages movement on different surfaces and wall interactions.
	 *
	 * @param block Collision area for solid blocks
	 * @param w Up/jump input
	 * @param a Left input
	 * @param d Right input
	 * @param up Upper collision area
	 * @param down Lower collision area
	 * @param left Left collision area
	 * @param right Right collision area
	 * @param speedX Movement speed in X direction
	 * @param v Velocity value
	 */
	private void walk(Area block, boolean w, boolean a, boolean d, Area up, Area down, Area left, Area right, double speedX, double v) {
		Area iceDown = new Area(down);
		Area iceLeft = new Area(left);
		iceLeft.intersect(Game.ice);
		Area iceRight = new Area(right);
		iceRight.intersect(Game.ice);
		Area rockDown = new Area(down);
		rockDown.intersect(Game.block);
		iceDown.intersect(Game.ice);
		if (d) {
			left.intersect(block);
			if (!left.isEmpty()) {
				velx = 0;

				if(gravity > 0) {
					up.intersect(block);
					if (w && up.isEmpty() && wjl && iceLeft.isEmpty()) {
						vely = -(speedY - 0.1 * speedY);
						wjl = false;
					}
				}
				else {
					down.intersect(block);
					if (w && down.isEmpty() && wjl && iceLeft.isEmpty()) {
						vely = -(speedY - 0.1 * speedY);
						wjl = false;
					}
				}

			} else {
				velx = speedX;
			}
		} else if (a) {
			right.intersect(block);
			if (!right.isEmpty()) {
				velx = 0;

				if(gravity > 0) {
					up.intersect(block);
					if (w && up.isEmpty() && wjr && iceRight.isEmpty()) {
						vely = -(speedY - 0.1 * speedY);
						wjr = false;
					}
				}
				else {
					down.intersect(block);
					if (w && down.isEmpty() && wjr && iceRight.isEmpty()) {
						vely = -(speedY - 0.1 * speedY);
						wjr = false;
					}
				}

			} else {
				velx = v;
			}
		} else {
			left.intersect(block);
			right.intersect(block);
			if (Math.abs(velx) > 0.25 && left.isEmpty() && right.isEmpty()) {
				if (!iceDown.isEmpty()) {
					velx += 0.0085 * velx;
					jumpedFromIce = true;
				}
				if(!rockDown.isEmpty()) jumpedFromIce = false;
				if(jumpedFromIce) {
					velx -= 0.01 * velx;
				}
				else {
					velx -= 0.25 * velx;
                }
			}
			else velx = 0;
		}
	}

	/**
	 * Helper method for handling gravity and jumping mechanics.
	 * Manages vertical movement and jump sound effects.
	 *
	 * @param block Collision area for solid blocks
	 * @param w Up/jump input
	 * @param a First collision area to check
	 * @param b Second collision area to check
	 */
	private void assertGravity(Area block, boolean w, Area a, Area b) {
		a.intersect(block);
		if (a.isEmpty()) {
			vely += gravity;
		} else {
			wjl = true;
			wjr = true;
			vely = 0;
			b.intersect(block);
			if (w && b.isEmpty()) {
				vely = -speedY;
				if(soundFXLoader != null) {
					soundFXLoader.playSound(jumpSoundPath);
				}
			}
		}
	}

	/**
	 * Calculates physics for swimming movement.
	 * Handles buoyancy, water resistance, and underwater controls.
	 *
	 * @param block Collision area for solid blocks
	 * @param water Collision area for water
	 * @param width Object width
	 * @param height Object height
	 * @param x Current X position
	 * @param y Current Y position
	 * @param w Up input
	 * @param s Down input
	 * @param a Left input
	 * @param d Right input
	 * @return Array containing [new X, new Y, X velocity, Y velocity]
	 */
	public double[] swimmingPhysics(Area block, Area water, double width, double height, double x, double y, boolean w, boolean s, boolean a, boolean d) {

		if(width < 2) width = 2;
		if(height < 4) height = 4;
		Area player = new Area(getRectangle((int) x, (int) y, width + 2, height + 2));
		Area down = new Area(getRectangle(x - 2, y + height - 1, width + 4, 2 + vely));
		Area left = new Area(getRectangle(x - 8, y + 2, 5, height - 4));
		Area right = new Area(getRectangle(x + width + 3, y + 2, 5, height - 4));
		
		player.intersect(water);
		down.intersect(block);
		left.intersect(block);
		right.intersect(block);
		
		
		if(!player.isEmpty()) {
			
			wjl = true;
			wjr = true;
			
			if(!left.isEmpty() && w && a) y += 4;
			if(!right.isEmpty() && w && d) y += 4;
			
			if(w) vely = -5;
			else vely = 1;
			
			if(a) velx = 1;
			else if(d) velx = -1;
			
			if(s && down.isEmpty()) vely = 5;
			
			
			ret[0] = x + velx;
			ret[1] = y + vely / 3;
			ret[2] = -velx;
			ret[3] = -vely;
		}
		
		return ret;
	}

	/**
	 * Calculates a point on a line using linear interpolation.
	 *
	 * @param x X coordinate to calculate for
	 * @param x1 First point X coordinate
	 * @param y1 First point Y coordinate
	 * @param x2 Second point X coordinate
	 * @param y2 Second point Y coordinate
	 * @return Y coordinate at the given X position
	 */
	public static double line(double x, double x1, double y1, double x2, double y2) {
		double l = (y2 - y1) / (x2 - x1);
		double b = y2 - l * x2;
		return l * x + b;
	}
	
	/**
	 * Creates a Rectangle with the specified dimensions.
	 * Helper method for collision area creation.
	 *
	 * @param a X coordinate
	 * @param b Y coordinate
	 * @param c Width
	 * @param d Height
	 * @return Rectangle with the specified dimensions
	 */
	public Rectangle getRectangle(double a, double b, double c, double d) {
		return new Rectangle((int) a,(int) b, (int) c, (int) d);
	}
	
}
