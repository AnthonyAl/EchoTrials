package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the foreground layer of game levels.
 * Renders decorative elements and visual effects that appear in front of other game objects.
 * Uses a tile-based system with support for multiple texture types and random variations.
 */
public class Foreground extends GameObject {
	
	/** Composite image containing all foreground elements */
	BufferedImage foreground;
	
	/** Collection of tile maps defining foreground layout */
	ArrayList<char[][]> maps;
	
	/** Collection of textures used for different tile types */
	ArrayList<BufferedImage> images;

	/**
	 * Creates a new foreground layer with specified properties.
	 * @param x Starting X coordinate
	 * @param y Starting Y coordinate
	 * @param id Object identifier
	 * @param images Collection of tile textures
	 * @param maps Collection of tile maps
	 * @param multiplier Size multiplier for tiles
	 * @param width Level width in tiles
	 * @param height Level height in tiles
	 */
	public Foreground(int x, int y, ID id, ArrayList<BufferedImage> images, ArrayList<char[][]> maps, int multiplier, int width, int height) {
		super(x, y, id);
		this.maps = maps;
		this.images = images;

		foreground = new BufferedImage(width * multiplier, height * multiplier, BufferedImage.TYPE_INT_ARGB);
		Random r = new Random();

		Graphics g = foreground.getGraphics();
		g.setColor(new Color(255,0,0, 0));
		for(char[][] map : maps) {
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					int rW = r.nextInt(-1,1);
					int rH =  r.nextInt(-1,1);
					if(rW == 0) rW = 1;
					if(rH == 0) rH = 1;
					int adjX = 0;
					int adjY = 0;
					if(rW < 0) adjX = -rW*multiplier;
					if(rH < 0) adjY = -rH*multiplier;
					if(map[i][j] == 'b') g.drawImage(images.get(0), i * multiplier+adjX, j * multiplier+adjY, rW*multiplier, rH*multiplier, null);
					if(map[i][j] == 'i') g.drawImage(images.get(1), i * multiplier, j * multiplier, null);
					if(map[i][j] == 'w') g.drawImage(images.get(2), i * multiplier, j * multiplier, null);
					if(map[i][j] == 'g') g.fillRect(i * multiplier, j * multiplier, multiplier, multiplier);
				}
			}
		}
		g.dispose();
	}

	/**
	 * Updates foreground state.
	 * Foreground is static and requires no updates.
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub
	}

	/**
	 * Renders the foreground layer.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(foreground, 0, 0, null);
	}

	/**
	 * Gets collision bounds.
	 * Foreground is decorative and has no collision.
	 * @return null as foreground has no collision
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets collision area.
	 * Foreground is decorative and has no collision.
	 * @return null as foreground has no collision
	 */
	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
	}

}
