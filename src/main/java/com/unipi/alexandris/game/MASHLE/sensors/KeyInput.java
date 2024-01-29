package com.unipi.alexandris.game.MASHLE.sensors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.unipi.alexandris.game.MASHLE.core.*;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private GameObject tempObject;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();	
		for(int i = 0; i < handler.object.size(); i++) {
			try {
				tempObject = handler.object.get(i);
				if(tempObject.getId() == ID.Player) {
					if(key == KeyEvent.VK_A) tempObject.setA(true);
					if(key == KeyEvent.VK_D) tempObject.setD(true);
					if(key == KeyEvent.VK_W) tempObject.setW(true);
					if(key == KeyEvent.VK_S) tempObject.setS(true);
				}
			}
			catch(NullPointerException p) {
				//To do;
			}
		}
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();	
		for(int i = 0; i < handler.object.size(); i++) {
			try {
				tempObject = handler.object.get(i);
				if(tempObject.getId() == ID.Player) {
					if(key == KeyEvent.VK_A) tempObject.setA(false);
					if(key == KeyEvent.VK_D) tempObject.setD(false);
					if(key == KeyEvent.VK_W) tempObject.setW(false);
					if(key == KeyEvent.VK_S) tempObject.setS(false);
				}
		}
		catch(NullPointerException p) {
			//To do;
		}
		}
	}
}
