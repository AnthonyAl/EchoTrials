package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;
import com.unipi.alexandris.game.echotrials.base.loaders.SoundFXLoader;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PortalBlock extends GameObject {

	private final BufferedImage[] images;
	private int portalDelayCounter = 0;
	private boolean locked = true;
	private String title = "";
	private LevelID destination;
	SoundFXLoader sound = new SoundFXLoader();

	public PortalBlock(int x, int y, ID id, BufferedImage[] images) {
		super(x, y, id);
		size = 48;
		this.images = images;
	}

	@Override
	public void tick() {
		if(Game.player == null) return;
		if(locked) {
			if (Game.currentLevel == LevelID.LEVEL_SELECTOR || Game.currentLevel == LevelID.LEVEL_HIGHSCORE) {
				this.setCancelUP(true);
				if (getArea().intersects(Game.player.getBounds())) {
					this.setCancelUP(false);
					if(pressUP) {
						sound.playSound("/sounds/Jump.mp3");
						this.setCancelUP(true);
						this.pressUP = false;
					}
				}
			}
			return;
		}
		if(Game.currentLevel == LevelID.LEVEL_SELECTOR || Game.currentLevel == LevelID.LEVEL_HIGHSCORE) {
			this.setCancelUP(true);
			if(getArea().intersects(Game.player.getBounds())) {
				this.setCancelUP(false);
				if(pressUP) {
					if(Objects.equals(title, "SPEEDRUN")) {
						if(portalDelayCounter++ == 0) {
							Game.player.HEIGHT = 1.3;
							Game.player.WIDTH = 1;
							sound.playSound("/sounds/Pass Portal.mp3");
						}
						Game.player.setCancelLEFT(true);
						Game.player.setCancelRIGHT(true);
						Game.player.setCancelDOWN(true);
						Game.player.setPressLEFT(false);
						Game.player.setPressRIGHT(false);
						Game.player.setPressDOWN(false);
						Game.player.setX(x + (double)size/2 - 15*Game.player.WIDTH);
						Game.player.setY(y+size-Game.player.HEIGHT*30);
						Game.player.WIDTH -= Game.player.WIDTH*0.015;
						Game.player.HEIGHT -= Game.player.HEIGHT*0.015;
						if(portalDelayCounter++ > 180) {
							portalDelayCounter = 0;
							Game.loadLevel(destination);
							Game.SPEEDRUN = true;
						}
						return;
					}
					if(Objects.equals(title, "EXIT GAME")) {
						if(portalDelayCounter++ == 0) {
							Game.player.HEIGHT = 1.3;
							Game.player.WIDTH = 1;
							sound.playSound("/sounds/Exit Game.mp3");
						}
						Game.player.setCancelLEFT(true);
						Game.player.setCancelRIGHT(true);
						Game.player.setCancelDOWN(true);
						Game.player.setPressLEFT(false);
						Game.player.setPressRIGHT(false);
						Game.player.setPressDOWN(false);
						Game.player.setX(x + (double)size/2 - 15*Game.player.WIDTH);
						Game.player.setY(y+size-Game.player.HEIGHT*30);
						Game.player.WIDTH -= Game.player.WIDTH*0.015;
						Game.player.HEIGHT -= Game.player.HEIGHT*0.015;
						if(portalDelayCounter++ > 250) {
							portalDelayCounter = 0;
							Game.exit();
						}
						return;
					}

					if(portalDelayCounter++ == 0) {
						Game.player.HEIGHT = 1.3;
						Game.player.WIDTH = 1;
						sound.playSound("/sounds/Pass Portal.mp3");
					}
					Game.player.setCancelLEFT(true);
					Game.player.setCancelRIGHT(true);
					Game.player.setCancelDOWN(true);
					Game.player.setPressLEFT(false);
					Game.player.setPressRIGHT(false);
					Game.player.setPressDOWN(false);
					Game.player.setX(x + (double)size/2 - 15*Game.player.WIDTH);
					Game.player.setY(y+size-Game.player.HEIGHT*30);
					Game.player.WIDTH -= Game.player.WIDTH*0.015;
					Game.player.HEIGHT -= Game.player.HEIGHT*0.015;
					if(portalDelayCounter++ > 180) {
						portalDelayCounter = 0;
						Game.loadLevel(destination);
					}
				}
			}

			return;
		}
		if(getArea().intersects(Game.player.getBounds())) {
			if(portalDelayCounter++ == 0) {
				Game.player.HEIGHT = 1.3;
				Game.player.WIDTH = 1;
				sound.playSound("/sounds/Pass Portal.mp3");
			}
			Game.player.setCancelLEFT(true);
			Game.player.setCancelRIGHT(true);
			Game.player.setCancelUP(true);
			Game.player.setCancelDOWN(true);
			Game.player.setPressUP(false);
			Game.player.setPressLEFT(false);
			Game.player.setPressRIGHT(false);
			Game.player.setPressDOWN(false);
			Game.player.setX(x + (double)size/2 - 15*Game.player.WIDTH);
			Game.player.setY(y+size-Game.player.HEIGHT*30);
			Game.player.WIDTH -= Game.player.WIDTH*0.015;
			Game.player.HEIGHT -= Game.player.HEIGHT*0.015;
			if(portalDelayCounter++ > 180) {
				portalDelayCounter = 0;
				Game.loadNext();
			}
		}
	}

	public void setDestination(String text, LevelID levelID) {
		this.title = text;
		this.destination = levelID;
	}

	public void unlock() {
		locked = false;
	}

	public void lock() {
		locked = true;
	}

	public String getTitle() {
		return title;
	}

	public LevelID getDestination() {
		return destination;
	}

	@Override
	public void render(Graphics g) {

		// Find the size of string s in font f in the current Graphics context g.
		g.setFont(new Font("VERDANA", Font.BOLD, 15));

		drawString(g, title, (int) x, (int) (y - 20));  // Draw the string.

		if(!locked) g.drawImage(images[0], (int) x, (int) y - 8, size, size + 8, null);
		else g.drawImage(images[1], (int) x, (int) y - 8, size, size + 8, null);
	}

	private void drawString(Graphics g, String text, int x, int y) {
		FontMetrics fm   = g.getFontMetrics();
		y -= text.split("\n").length * fm.getHeight();
		for (String line : text.split("\n")) {
			java.awt.geom.Rectangle2D rect = fm.getStringBounds(line, g);
			int textWidth  = (int)(rect.getWidth());

			// Center text horizontally and vertically
			int centeredX = (size  - textWidth)  / 2;

			g.drawString(line, x + centeredX, y += fm.getHeight());
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y - 8, size, size + 8);
	}

	public Rectangle getBounds(int a) {
		return new Rectangle((int) x - a, (int) y - 8 - a, size + a*2, size + 8 + a*2);
	}

	@Override
	public Area getArea() {
		return new Area(new Rectangle2D.Double(x, y - 8, size, size + 8));
	}
}
