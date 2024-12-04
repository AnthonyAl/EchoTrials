package com.unipi.alexandris.game.echotrials.base.physics;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.loaders.SoundFXLoader;

import java.awt.*;
import java.awt.geom.Area;

@SuppressWarnings(value = "unused")
public class PhysicsPlatformer {
	
	private boolean wjl = true, wjr = true;
	private double velx = 0, vely = 0;
	private final double[] ret = new double[4];
	private final double[] ret2 = new double[5];
	private double speedX = 5;
	private double speedY = 5;
	private double gravity = 1;
	private boolean jumpedFromIce = false;
	private SoundFXLoader soundFXLoader;
	private String jumpSoundPath;

	public PhysicsPlatformer(double speedX, double speedY, double gravity) {
		this.speedX = speedX;
		this.speedY = speedY;
		this.gravity = gravity;
	}

	public PhysicsPlatformer(double speedX, double speedY, double gravity, SoundFXLoader soundFXLoader, String jumpSoundPath) {
		this.speedX = speedX;
		this.speedY = speedY;
		this.gravity = gravity;
		this.soundFXLoader = soundFXLoader;
		this.jumpSoundPath = jumpSoundPath;
	}

	public PhysicsPlatformer() {}
	
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

	public static double line(double x, double x1, double y1, double x2, double y2) {
		double l = (y2 - y1) / (x2 - x1);
		double b = y2 - l * x2;
		return l * x + b;
	}
	
	public Rectangle getRectangle(double a, double b, double c, double d) {
		return new Rectangle((int) a,(int) b, (int) c, (int) d);
	}
	
}
