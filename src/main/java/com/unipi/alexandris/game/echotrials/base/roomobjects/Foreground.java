package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Foreground extends GameObject {
	
	BufferedImage foreground;
	ArrayList<char[][]> maps;
	ArrayList<BufferedImage> images;

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

	@Override
	public void tick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(foreground, 0, 0, null);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
	}

}
