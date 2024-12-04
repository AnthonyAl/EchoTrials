package com.unipi.alexandris.game.echotrials.base.sensors;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

public class MouseMotionSensor extends MouseMotionAdapter {
	
	Handler handler;
	
	public MouseMotionSensor(Handler handler) {
		this.handler = handler;
	}
	
	public void mouseMoved(MouseEvent e) {
		Game.cursorX = e.getX();
		Game.cursorY = e.getY();
	}
}
