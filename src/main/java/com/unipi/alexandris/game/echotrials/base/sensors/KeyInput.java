package com.unipi.alexandris.game.echotrials.base.sensors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;

/**
 * The KeyInput class handles keyboard input events for the game.
 * It processes:
 * <ul>
 *   <li>Player movement controls (WASD and arrow keys)</li>
 *   <li>Game control shortcuts (ESC, F11, H)</li>
 *   <li>Object-specific interactions</li>
 * </ul>
 * This class extends KeyAdapter to receive and process keyboard events.
 */
public class KeyInput extends KeyAdapter {
	
	/** Handler for managing game objects */
	private final Handler handler;
	
	/** Reference to the main game instance */
	private final Game game;
	
	/** Temporary object reference for input processing */
	private GameObject tempObject;
	
	/**
	 * Constructs a new KeyInput handler.
	 *
	 * @param game The main game instance
	 * @param handler The game object handler
	 */
	public KeyInput(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}

	/**
	 * Handles key press events.
	 * Processes:
	 * <ul>
	 *   <li>Movement controls for Player objects</li>
	 *   <li>Interaction controls for Goal and Spike objects</li>
	 *   <li>Game control shortcuts:
	 *     <ul>
	 *       <li>ESC - Exit or restart level</li>
	 *       <li>F11 - Toggle fullscreen</li>
	 *       <li>H - View high scores</li>
	 *     </ul>
	 *   </li>
	 * </ul>
	 *
	 * @param e The key event to process
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();	
		for(int i = 0; i < handler.getObject().size(); i++) {
			try {
				tempObject = handler.getObject().get(i);
				if(tempObject.getId() == ID.Player) {
					if(key == KeyEvent.VK_A ^ key == KeyEvent.VK_LEFT)
						if(!tempObject.isCancelLEFT()) tempObject.setPressLEFT(true);
					if(key == KeyEvent.VK_D ^ key == KeyEvent.VK_RIGHT)
						if(!tempObject.isCancelRIGHT()) tempObject.setPressRIGHT(true);
					if(key == KeyEvent.VK_W ^ key == KeyEvent.VK_UP ^ key == KeyEvent.VK_SPACE)
						if(!tempObject.isCancelUP()) tempObject.setPressUP(true);
					if(key == KeyEvent.VK_S ^ key == KeyEvent.VK_DOWN)
						if(!tempObject.isCancelDOWN()) tempObject.setPressDOWN(true);
				}
				if(tempObject.getId() == ID.Goal) {
					if(key == KeyEvent.VK_W ^ key == KeyEvent.VK_UP ^ key == KeyEvent.VK_SPACE)
						if(!tempObject.isCancelUP()) tempObject.setPressUP(true);
				}
				if(tempObject.getId() == ID.Spike) {
					if(key == KeyEvent.VK_W ^ key == KeyEvent.VK_UP ^ key == KeyEvent.VK_SPACE)
						if(!tempObject.isCancelUP()) tempObject.setPressUP(true);
				}
			}
			catch(NullPointerException p) {
				//To do;
			}
		}
		if(key == KeyEvent.VK_ESCAPE) {
			if(Game.currentLevel == LevelID.LEVEL_SELECTOR) Game.exit();
			Game.restart();
		}
		if(key == KeyEvent.VK_F11) game.window.fullscreen();
		if(key == KeyEvent.VK_H)
			if(Game.currentLevel == LevelID.LEVEL_SELECTOR) Game.getHighScores();
	}
	
	/**
	 * Handles key release events.
	 * Stops movement or interactions when control keys are released.
	 * Affects:
	 * <ul>
	 *   <li>Player movement controls</li>
	 *   <li>Spike object interactions</li>
	 * </ul>
	 *
	 * @param e The key event to process
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();	
		for(int i = 0; i < handler.getObject().size(); i++) {
			try {
				tempObject = handler.getObject().get(i);
				if(tempObject.getId() == ID.Player) {
					if(key == KeyEvent.VK_A ^ key == KeyEvent.VK_LEFT)
						if(!tempObject.isCancelLEFT()) tempObject.setPressLEFT(false);
					if(key == KeyEvent.VK_D ^ key == KeyEvent.VK_RIGHT)
						if(!tempObject.isCancelRIGHT()) tempObject.setPressRIGHT(false);
					if(key == KeyEvent.VK_W ^ key == KeyEvent.VK_UP ^ key == KeyEvent.VK_SPACE)
						if(!tempObject.isCancelUP()) tempObject.setPressUP(false);
					if(key == KeyEvent.VK_S ^ key == KeyEvent.VK_DOWN)
						if(!tempObject.isCancelDOWN()) tempObject.setPressDOWN(false);
				}
				if(tempObject.getId() == ID.Spike) {
					if(key == KeyEvent.VK_W ^ key == KeyEvent.VK_UP ^ key == KeyEvent.VK_SPACE)
						if(!tempObject.isCancelUP()) tempObject.setPressUP(false);
				}
		}
		catch(NullPointerException p) {
			//To do;
		}
		}
	}
}
