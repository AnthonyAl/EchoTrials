package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.loaders.SoundFXLoader;
import com.unipi.alexandris.game.echotrials.base.physics.ParticleCreator;
import com.unipi.alexandris.game.echotrials.base.physics.PhysicsPlatformer;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;
import com.unipi.alexandris.game.echotrials.base.loaders.BufferedImageLoader;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Player extends GameObject {
	private final Handler handler;
	private final ParticleCreator pc;
	private PhysicsPlatformer pp;
	BufferedImageLoader loader = new BufferedImageLoader();
	SoundFXLoader sound = new SoundFXLoader();
	private int breath_timer = 0;
	boolean direction, in_water;
	private final Color[] color = {new Color(0, 164, 254, 100), new Color(0, 231, 254, 100), new Color(0, 205, 255, 100)};
	private BufferedImage sprite;
	private double width = size;
	private double height = size*2;
	public double WIDTH = 1;
	public double HEIGHT = 1.3;
	public double SPEED_X = 5;
	public double SPEED_Y = 15;
	public double GRAV = 0.8;
	public boolean death = false;
	private int animationCounter = 0;
	private int deathDelayCounter = 0;
	private final Random r = new Random();
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		pc = new ParticleCreator(handler);
		sprite = loader.loadImage("/textures/player/Idle.png");
		size = 1;
		obstructions.add(Game.block);
		pp = new PhysicsPlatformer(SPEED_X, SPEED_Y, GRAV, sound, "/sounds/Jump.mp3");
	}

	public void resetPhysics(boolean playSound) {
		if(playSound) pp = new PhysicsPlatformer(SPEED_X, SPEED_Y, GRAV, sound, "/sounds/Jump.mp3");
		else pp = new PhysicsPlatformer(SPEED_X, SPEED_Y, GRAV);
	}

	
	public void tick() {
		int particleSize = (int) width;

		if(height < width) particleSize = (int)height;
		
		for(int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			if(tempObject.getId() == ID.Enemy_Projectile) {
				if(getArea().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					Game.health_counter--;
				}
			}
		}
		if(size<=0) {
			size = 1;
		}
		
		if(size <= 30) {
			size += 1;
		}
		width = size*WIDTH;
		height = size*HEIGHT;
		
//		/*
		Area playerW = getArea();
		playerW.intersect(Game.water);
		Area playerG = getArea();
		playerG.intersect(obstructions);
		if(playerW.isEmpty()) {
			in_water = false;
			Game.bubble_flag = false;
			breath_timer = 0;
			Game.bubble_counter = 10;
			if(!playerG.isEmpty() && getPressRIGHT())
				for(int i = 0; i < 5; i++) pc.spawn(x + 10, y + height - 5, Color.BLACK, 4, particleSize * 25 / 100, 0.6, 'c');
			else if(!playerG.isEmpty() && getPressLEFT())
				for(int i = 0; i < 5; i++) pc.spawn(x + width - 10, y + height - 5, Color.BLACK, 4, particleSize * 25 / 100, 0.6, 'c');
		}
		else {
			in_water = true;
			Game.bubble_flag = true;
			breath_timer++;
			if(size < 5) pc.spawn(10, x + width / 10, y + height / 10, color, 5, particleSize, size * 80 / 100 + 1, size * 80 / 100 + 1, 1, 1, 0, 0, 's');
			else pc.spawn(5, x + width / 10, y + height / 10, color, size * 80 / 100, particleSize, size * 80 / 100, size * 80 / 100, 1, 1, 0, 0, 'c');
		}
		
		if(breath_timer >= 200) {
			Game.bubble_counter--;
			if(Game.bubble_counter < 0) Game.health_counter--;
			breath_timer = 0;
		}

		if(y > Game.camera.getHeight() || y < Game.camera.getY()) {
			if(deathDelayCounter++ == 0) sound.playSound("/sounds/AAAAAAAAAaa.mp3", "/sounds/Falling.mp3");
			setPressLEFT(false);
			setPressRIGHT(false);
			if(deathDelayCounter++ > 180) {
				Game.health_counter--;
				deathDelayCounter = 0;
				death = false;
				Game.reload();
			}
		}
		if(death) {
			resetPhysics(false);
			if(deathDelayCounter++ == 0) {
				for(int i = 0; i < 10; i++) {
					double velX = r.nextDouble(10, 30);
					if(r.nextBoolean()) velX = -velX;
					double velY = r.nextDouble(-20, -15);
					pc.spawn(2, x + width / 2, y - 2, new Color[] {Color.BLACK}, 10, 6, 15, 2, 5, 15, -1, 3, velX, velY, obstructions, 's');
				}
				for(int i = 0; i < 10; i++) {
					double velX = r.nextDouble(1, 10);
					if(r.nextBoolean()) velX = -velX;
					double velY = r.nextDouble(-30, -20);
					pc.spawn(3, x + width / 2, y - 2, new Color[] {Color.BLACK}, 20, 6, 15, 2, 1, 15, 1, 4, velX, velY, obstructions, 's');
				}
				double velY = r.nextDouble(-10, 0);
				pc.spawn(2, x + width / 2, y - 2, new Color[] {Color.BLACK}, 25, 6, 15, 2, 10, 15, 0, 3, 0, velY, obstructions, 's');
				sound.playSound("/sounds/Explosion.mp3");
			}
			setPressLEFT(false);
			setPressRIGHT(false);
			size = 0;
			if(deathDelayCounter++ > 180) {
				Game.health_counter--;
				deathDelayCounter = 0;
				death = false;
				Game.reload();
			}
		}
		
		if(Game.health_counter <= 0) {
			// TODO: Add a game-over screen and effects
			Game.restart();
		}

		// MOVEMENT PHYSICS
		double[] coords = pp.movementPhysics(obstructions, width, height, x, y, getPressUP(), getPressDOWN(), getPressLEFT(), getPressRIGHT());
		x = coords[0];
		y = coords[1];
		coords = pp.swimmingPhysics(obstructions, Game.water, width, height, x, y, getPressUP(), getPressDOWN(), getPressLEFT(), getPressRIGHT());
		x = coords[0];
		y = coords[1];
		double vely = coords[3];

        if(SPEED_X > 0) direction = getPressLEFT() && !getPressRIGHT();
		else direction = !getPressLEFT() && getPressRIGHT();

		animationCounter++;
		// Animation Logic
		if(vely >= -1 && vely <= 1) {
			if (!getPressLEFT() && !getPressRIGHT()) sprite = loader.loadImage("/textures/player/Idle.png");
			else {
				if (animationCounter > 8) {
					sprite = loader.loadImage("/textures/player/Move.png");
				} else {
					sprite = loader.loadImage("/textures/player/Jump.png");
				}
				if (animationCounter > 16) animationCounter = 0;
				if (getPressUP()) sprite = loader.loadImage("/textures/player/Jump.png");
			}
		}
	}
	
	public void render(Graphics g) {
		if (size <= 0 || width <= 0 || height <= 0) {
			return;
		}

		Graphics2D g2d;
		g2d = (Graphics2D) g;

		try {
			if(GRAV >= 0)
				if(direction) g2d.drawImage(loader.resizeImage(sprite, width, height), (int) x, (int) y, null);
				else g2d.drawImage(loader.resizeImage(sprite, width, height), (int) (x + width), (int) y, (int) -width, (int) height, null);
			else {
				// Flip the image vertically
				if(direction) g2d.drawImage(loader.resizeImage(sprite, width, height), (int) x, (int) (y + height), (int) width, (int) -height, null);
				else g2d.drawImage(loader.resizeImage(sprite, width, height), (int) (x + width), (int) (y + height), (int) -width, (int) -height, null);
			}
		}
		catch (IOException ignored) {}
	}

	public Rectangle getBounds(int x1, int x2, int y1, int y2) {
		return new Rectangle((int) x - x1,(int) y - x2, (int) (size*WIDTH) + y1, (int) (size*HEIGHT) + y2);
	}


	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return  new Rectangle((int) x,(int) y, (int)(size*WIDTH), (int)(size*HEIGHT));
	}


	@Override
	public Area getArea() {
		return new Area(new Rectangle2D.Double((int) x - 3, (int) y - 3, size*WIDTH + 6, size*HEIGHT + 6));
	}

	public void removeObstructions(Area area) {
		obstructions.subtract(area);
		obstructions.add(Game.block);
	}
	public void addExtraObstructions(Area area) {
		obstructions.add(area);
	}
	
}
