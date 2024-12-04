package com.unipi.alexandris.game.echotrials.base.sensors;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.gui.GUIButton;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

public class MouseInput extends MouseAdapter {
	
	private final Handler handler;
	private final Game game;

	
	public MouseInput(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i = 0; i < game.getGUIElements().size(); i++) {
			try {
				GUIButton tempElement = game.getGUIElements().get(i);
				if (tempElement.getId() == ID.Button) {
					if(tempElement.isMouse_hover()) {
						tempElement.setMouse_clicked(true);
						handler.runLater(() -> tempElement.setMouse_clicked(false), 1);
					}
				}
			}
			catch(NullPointerException p) {
				//To do;
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Nothing to do.
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// Nothing to do.
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// Nothing to do.
	}

}
