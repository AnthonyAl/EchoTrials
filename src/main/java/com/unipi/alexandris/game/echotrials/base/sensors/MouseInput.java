package com.unipi.alexandris.game.echotrials.base.sensors;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.gui.GUIButton;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

/**
 * The MouseInput class handles mouse input events for GUI interactions.
 * It manages:
 * <ul>
 *   <li>Button click detection and processing</li>
 *   <li>Mouse hover state tracking</li>
 *   <li>GUI element interaction states</li>
 * </ul>
 * This class extends MouseAdapter to receive and process mouse events.
 */
public class MouseInput extends MouseAdapter {
	
	/** Handler for managing game objects */
	private final Handler handler;
	
	/** Reference to the main game instance */
	private final Game game;
	
	/**
	 * Constructs a new MouseInput handler.
	 *
	 * @param game The main game instance
	 * @param handler The game object handler
	 */
	public MouseInput(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}

	/**
	 * Handles mouse click events on GUI elements.
	 * Processes button clicks and updates their states:
	 * <ul>
	 *   <li>Detects clicks on hovering buttons</li>
	 *   <li>Updates button click states</li>
	 *   <li>Schedules click state reset</li>
	 * </ul>
	 *
	 * @param e The mouse event to process
	 */
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

	/**
	 * Handles mouse enter events.
	 * Currently not implemented.
	 *
	 * @param e The mouse event to process
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// Nothing to do.
	}
	
	/**
	 * Handles mouse exit events.
	 * Currently not implemented.
	 *
	 * @param e The mouse event to process
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// Nothing to do.
	}
	
	/**
	 * Handles mouse press events.
	 * Currently not implemented.
	 *
	 * @param e The mouse event to process
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Nothing to do.
	}
}
