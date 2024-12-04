package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TrapBlock extends GameObject {

	private final BufferedImage image;

	public boolean visible = true;

	public TrapBlock(int x, int y, ID id, BufferedImage image) {
		super(x, y, id);
		size = 48;
		this.image = image;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		if(visible) g.drawImage(image, (int) x, (int) y, null);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
	
	public Rectangle getBounds(int ignoredA) {
		return null;
	}

	@Override
	public Area getArea() {
		return new Area(new Rectangle2D.Double(x, y, size, size));
	}

}
