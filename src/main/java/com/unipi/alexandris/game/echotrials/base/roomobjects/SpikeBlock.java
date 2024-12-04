package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;
import com.unipi.alexandris.game.echotrials.base.physics.ParticleCreator;
import com.unipi.alexandris.game.echotrials.base.physics.PhysicsPlatformer;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class SpikeBlock extends GameObject {

	private final Handler handler;
	private final BufferedImage[] image;
	private boolean arisen = false;
	private boolean inverted = false;
	private int i = 0;
	
	public SpikeBlock(int x, int y, ID id, BufferedImage[] image, Handler handler) {
		super(x, y, id);
		setCancelUP(true);
		size = 48;
		this.handler = handler;
		this.image = image;
		ParticleCreator pc = new ParticleCreator(handler);
	}

	@Override
	public void tick() {
		if(!isCancelUP()) {
			PhysicsPlatformer pp = new PhysicsPlatformer(0, 500, 10);

			obstructions.add(Game.player.obstructions);
			double[] coords = pp.movementPhysics(obstructions, size, size, x, y, getPressUP(), getPressDOWN(), getPressLEFT(), getPressRIGHT());
			y = coords[1];
			coords = pp.swimmingPhysics(obstructions, Game.water, size, size, x, y, getPressUP(), getPressDOWN(), getPressLEFT(), getPressRIGHT());
			y = coords[1];
		}
		for(GameObject tempObject : handler.getObject())
			if(tempObject instanceof Player player) {
				if(getArea().intersects(player.getBounds())) {
					player.death = true;
					
				}
			}
	}

	@Override
	public void render(Graphics g) {
		if(arisen) {
			if(inverted) g.drawImage(image[i], (int) x, (int) y + size, size, -size, null);
			else g.drawImage(image[i], (int) x, (int) y, null);
		}
	}

	public void switchImage() {
		if(++i > 1) i = 0;
	}

	public void arise() {
		arisen = true;
	}

	public void hide() {
		arisen = false;
	}

	public void invert() {
		inverted = true;
	}

	public boolean isHidden() {
		return !arisen;
	}

	@Override
	public Rectangle getBounds() {
		if(arisen) return new Rectangle((int) x, (int) y + 20, size, size - 20);
		else return new Rectangle(0, 0, 0, 0);
	}
	
	public Rectangle getBounds(int a) {
		if(arisen) return new Rectangle((int) x - a, (int) y + 20 - a, size + a*2, (size-20) + a*2);
		else return new Rectangle(0, 0, 0, 0);
	}

	@Override
	public Area getArea() {
		if(arisen) return new Area(new Ellipse2D.Double((int) x, (int) y + 20, size, size - 20));
		else return new Area(new Rectangle(0, 0, 0, 0));
	}

}
