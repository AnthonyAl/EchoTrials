package com.unipi.alexandris.game.MASHLE.core;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Player extends GameObject {
	//private Handler handler;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		//this.handler = handler;
	}

	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	
	public Area getArc(int x, int y, int w, int h, int ang1, int ang2) {
		return new Area(new Arc2D.Double(x, y, w, h, ang1, ang2, Arc2D.PIE));
	}

	
	public Rectangle getBounds(int x1, int x2, int y1, int y2) {
		return new Rectangle((int) this.x - x1,(int) y - x2, size + y1, size + y2);
	}


	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return  new Rectangle((int) x,(int) y, size, size);
	}


	@Override
	public Area getArea() {
		return new Area(new Ellipse2D.Double((int) x - 3, (int) y - 3, size + 6, size + 6));
	}
	
}
