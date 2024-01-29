package com.unipi.alexandris.game.MASHLE.sensors;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.unipi.alexandris.game.MASHLE.core.Handler;
import  com.unipi.alexandris.game.MASHLE.Game;

public class MouseInput extends MouseAdapter {
	
	Handler handler;
	Game tempObject;
	
	public MouseInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// Nothing to do.
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
