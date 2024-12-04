package com.unipi.alexandris.game.echotrials.base.sensors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;

public class KeyInput extends KeyAdapter {
	
	private final Handler handler;
	private final Game game;
	private GameObject tempObject;
	
	public KeyInput(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}

	
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
