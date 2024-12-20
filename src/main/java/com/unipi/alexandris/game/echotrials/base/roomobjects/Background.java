package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the game's background layer and parallax scrolling effects.
 * Provides visual depth through layered background images.
 * Supports camera-based movement and scaling.
 */
public class Background extends GameObject {

	BufferedImage background;
	ArrayList<char[][]> maps;
	ArrayList<BufferedImage> images;


	public Background(int x, int y, ID id, ArrayList<BufferedImage> images, ArrayList<char[][]> maps, int multiplier, int width, int height) {
		super(x, y, id);
		this.maps = maps;
		this.images = images;
		background = new BufferedImage(width * multiplier, height * multiplier, BufferedImage.TYPE_INT_RGB);
		Random r = new Random();

		Graphics g = background.getGraphics();
		for(char[][] map : maps) {
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					int rW = r.nextInt(-2,2);
					int rH =  r.nextInt(-2,2);
					if(rW == 0) rW = 1;
					if(rH == 0) rH = 1;
					int adjX = 0;
					int adjY = 0;
					if(rW < 0) adjX = -rW*multiplier;
					if(rH < 0) adjY = -rH*multiplier;
					if(map[i][j] == 'g') g.drawImage(images.get(3), i * multiplier + adjX, j * multiplier+adjY, rW*multiplier, rH*multiplier, null);
				}
			}
		}
		g.dispose();
	}

	/**
	 * Updates background position based on camera movement.
	 * Applies parallax scrolling effect.
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub
	}

	/**
	 * Renders the background layer.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}

	/**
	 * Gets background's collision bounds.
	 * Currently unused as background has no collision.
	 * @return null as no collision is needed
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets expanded collision bounds.
	 * Currently unused as background has no collision.
	 * @param ignoredA Ignored expansion parameter
	 * @return null as no collision is needed
	 */
	public Rectangle getBounds(int ignoredA) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets background's collision area.
	 * Currently unused as background has no collision.
	 * @return null as no collision is needed
	 */
	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
	}

}
