package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;

public class Block extends GameObject {
	
	private int pixels = 32;

	public Block(int x, int y, ID id, int pixels) {
		super(x, y, id);
		if(pixels > 0 && pixels % 4 == 0) this.pixels = pixels;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int) x + 1, (int) y + 1, pixels - 1, pixels - 1);
		//g.setColor(Color.black);
		//g.drawRect((int) x, (int) y, pixels, pixels);
		//g.drawLine((int) x + pixels, (int) y,  (int) x + pixels, (int) y + pixels);
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
