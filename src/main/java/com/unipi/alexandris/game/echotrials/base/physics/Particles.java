package com.unipi.alexandris.game.echotrials.base.physics;

import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Random;

public class Particles extends GameObject {
	
	private final Color color;
	private final Handler handler;
	private final Random random = new Random();
	private final int size;
    private final int offset;
    private final int spawn_offsetX;
    private final int spawn_offsetY;
    private final int size_offset;
    private int counter;
	private double gravityX = 0, gravityY = 0, velX = 0, velY = 0;
	private final char shape;
	private final double time;
	private final Area obstructions;
	
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

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}

	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
	}

}
