package com.unipi.alexandris.game.MASHLE.sensors;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.unipi.alexandris.game.MASHLE.core.*;

public class MouseWheelInput implements MouseWheelListener {
	
	Handler handler;
	GameObject tempObject;
	
	public MouseWheelInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// Nothing to do.
	}
}
