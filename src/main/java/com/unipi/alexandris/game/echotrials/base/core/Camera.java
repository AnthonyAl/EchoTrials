package com.unipi.alexandris.game.echotrials.base.core;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;

public class Camera {
	
	private double x, y;
	private int width = 0, height = 0, multiplier = 1;

	public Camera(double x, double y, int width, int height, int multiplier) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if(multiplier > 1) this.multiplier = multiplier;
		// TODO Auto-generated constructor stub
	}

	public void tick(GameObject object) {
		
		x += ((object.getX() + (double) object.getSize() / 2 - x) - (double) Game.WIDTH / 2) * 0.05f;
		y += ((object.getY() + (double) object.getSize() / 2 - y) - (double) Game.HEIGHT / 2) * 0.05f;
		
		if(x <= 0) x = 0;
		if(x >= width * multiplier - Game.WIDTH + (double) object.getSize() / 2 - 10) x = width * multiplier - Game.WIDTH + (double) object.getSize() / 2 - 10;
		if(y <= 0) y = 0;
		if(y >= height * multiplier - Game.HEIGHT + (double) object.getSize() / 2 + 10) y = height * multiplier - Game.HEIGHT + (double) object.getSize() / 2 + 10;
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width*multiplier;
	}

	public int getHeight() {
		return height*multiplier;
	}
	
}
