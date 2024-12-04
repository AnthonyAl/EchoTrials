package com.unipi.alexandris.game.echotrials.base.physics;

import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

import java.awt.*;
import java.awt.geom.Area;

public class ParticleCreator {
	
	Handler handler;

	public ParticleCreator(Handler handler) {
		this.handler = handler;
	}
	
	public void spawn(int num, double x, double y, Color[] color, int size, int offset, int spawn_offsetX, int spawn_offsetY, int size_offset, double time, double gravityX, double gravityY, char shape) {
		for(int i = 0; i < num; i++) {
			handler.addObject(new Particles(x, y, ID.Particles, handler, color, size, offset, spawn_offsetX, spawn_offsetY, size_offset, time, gravityX, gravityY, 0, 0, new Area(), shape));
		}
	}

	public void spawn(int num, double x, double y, Color[] color, int size, int offset, int spawn_offsetX, int spawn_offsetY, int size_offset, double time, double gravityX, double gravityY, double velX, double velY, Area obstructions, char shape) {
		for(int i = 0; i < num; i++) {
			handler.addObject(new Particles(x, y, ID.Particles, handler, color, size, offset, spawn_offsetX, spawn_offsetY, size_offset, time, gravityX, gravityY, velX, velY, obstructions, shape));
		}
	}

	public void spawn(double x, double y, Color color, int offset, int size, double time, char shape) {
		Color[] c = {color};
		for(int i = 0; i < 1; i++) {
			handler.addObject(new Particles(x, y, ID.Particles, handler, c, size, offset, 1, 1, 1, time, 0, 0, 0, 0, new Area(), shape));
		}
	}
	
	
}
