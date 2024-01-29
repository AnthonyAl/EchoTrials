package com.unipi.alexandris.game.MASHLE.sensors;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.unipi.alexandris.game.MASHLE.core.Handler;
import  com.unipi.alexandris.game.MASHLE.Game;

public class MouseMotionSensor extends MouseMotionAdapter {
	
	Handler handler;
	
	public MouseMotionSensor(Handler handler) {
		this.handler = handler;
	}
	
	public void mouseMoved(MouseEvent e) {
		Game.coordX = e.getX();
		Game.coordY = e.getY();
	}
}
