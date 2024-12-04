package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;

public class Water extends GameObject {
	
	private int pixels = 32;

	public Water(int x, int y, ID id, int pixels) {
		super(x, y, id);
		if(pixels > 0 && pixels % 4 == 0) this.pixels = pixels;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int) x, (int) y, pixels, pixels);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, pixels, pixels);
	}

	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
