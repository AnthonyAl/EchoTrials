package com.unipi.alexandris.game.echotrials.base.sensors;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

/**
 * The MouseMotionSensor class tracks mouse movement across the game window.
 * It is responsible for:
 * <ul>
 *   <li>Updating cursor position coordinates</li>
 *   <li>Providing real-time mouse position tracking</li>
 *   <li>Supporting hover detection for GUI elements</li>
 * </ul>
 * This class extends MouseMotionAdapter to receive and process mouse motion events.
 */
public class MouseMotionSensor extends MouseMotionAdapter {
	
	/** Handler for managing game objects */
	Handler handler;
	
	/**
	 * Constructs a new MouseMotionSensor.
	 *
	 * @param handler The game object handler
	 */
	public MouseMotionSensor(Handler handler) {
		this.handler = handler;
	}
	
	/**
	 * Updates the game's cursor position when the mouse moves.
	 * Stores the current mouse coordinates in the Game class for global access.
	 *
	 * @param e The mouse motion event containing new cursor coordinates
	 */
	public void mouseMoved(MouseEvent e) {
		Game.cursorX = e.getX();
		Game.cursorY = e.getY();
	}
}
