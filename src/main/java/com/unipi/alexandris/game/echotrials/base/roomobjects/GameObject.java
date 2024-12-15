package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

/**
 * Abstract base class for all game objects in the Echo Trials game.
 * Provides common functionality for position, movement, collision detection,
 * and input handling that all game objects share.
 */
public abstract class GameObject {
	/** X-coordinate position of the game object */
	protected double x;
	
	/** Y-coordinate position of the game object */
	protected double y;
	
	/** Size of the game object in pixels */
	protected int size = 0;
	
	/** Object type identifier */
	protected ID id;
	
	/** Collision area for handling object interactions */
	protected final Area obstructions = new Area();
	
	/** Flag indicating if up movement is being pressed */
	protected boolean pressUP;
	
	/** Flag indicating if down movement is being pressed */
	protected boolean pressDOWN;
	
	/** Flag indicating if left movement is being pressed */
	protected boolean pressLEFT;
	
	/** Flag indicating if right movement is being pressed */
	protected boolean pressRIGHT;
	
	/** Flag indicating if upward movement is canceled */
	protected boolean cancelUP = false;
	
	/** Flag indicating if downward movement is canceled */
	protected boolean cancelDOWN = false;
	
	/** Flag indicating if leftward movement is canceled */
	protected boolean cancelLEFT = false;
	
	/** Flag indicating if rightward movement is canceled */
	protected boolean cancelRIGHT = false;
	
	/** Flag indicating if shooting action is active */
	protected boolean shoot;
	
	/** Flag indicating if mouse button is being held */
	protected boolean mouse_pressed;
	
	/** Flag indicating if mouse has been clicked */
	protected boolean mouse_clicked;
	
	/** Flag indicating if mouse has been moved */
	protected boolean mouse_moved;

	/**
	 * Constructs a new game object at the specified position.
	 * @param x The initial X coordinate
	 * @param y The initial Y coordinate
	 * @param id The object's type identifier
	 */
	public GameObject(double x, double y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	/**
	 * Updates the game object's state each game tick.
	 * Must be implemented by concrete subclasses.
	 */
	public abstract void tick();

	/**
	 * Renders the game object to the screen.
	 * Must be implemented by concrete subclasses.
	 * @param var1 The graphics context to render to
	 */
	public abstract void render(Graphics var1);

	/**
	 * Gets the rectangular bounds for collision detection.
	 * Must be implemented by concrete subclasses.
	 * @return Rectangle representing object's collision bounds
	 */
	public abstract Rectangle getBounds();

	/**
	 * Gets the precise collision area for the object.
	 * Must be implemented by concrete subclasses.
	 * @return Area representing object's collision shape
	 */
	public abstract Area getArea();

	/**
	 * Sets the Y coordinate of the object.
	 * @param y New Y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Sets the X coordinate of the object.
	 * @param x New X coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Adjusts the Y coordinate by the specified amount.
	 * @param y Amount to move in Y direction
	 */
	public void moveY(double y) {
		this.y += y;
	}

	/**
	 * Adjusts the X coordinate by the specified amount.
	 * @param x Amount to move in X direction
	 */
	public void moveX(double x) {
		this.x += x;
	}

	/**
	 * Gets the current Y coordinate.
	 * @return Current Y position
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Gets the current X coordinate.
	 * @return Current X position
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Sets the object's type identifier.
	 * @param id New object ID
	 */
	public void setld(ID id) {
		this.id = id;
	}

	/**
	 * Gets the object's type identifier.
	 * @return Current object ID
	 */
	public ID getId() {
		return this.id;
	}

	/**
	 * Gets the flag indicating if up movement is being pressed.
	 * @return Flag indicating if up movement is being pressed
	 */
	public boolean getPressUP() {
		return this.pressUP;
	}

	/**
	 * Sets the flag indicating if up movement is being pressed.
	 * @param pressUP Flag indicating if up movement is being pressed
	 */
	public void setPressUP(boolean pressUP) {
		this.pressUP = pressUP;
	}

	/**
	 * Gets the flag indicating if down movement is being pressed.
	 * @return Flag indicating if down movement is being pressed
	 */
	public boolean getPressDOWN() {
		return this.pressDOWN;
	}

	/**
	 * Sets the flag indicating if down movement is being pressed.
	 * @param pressDOWN Flag indicating if down movement is being pressed
	 */
	public void setPressDOWN(boolean pressDOWN) {
		this.pressDOWN = pressDOWN;
	}

	/**
	 * Gets the flag indicating if left movement is being pressed.
	 * @return Flag indicating if left movement is being pressed
	 */
	public boolean getPressLEFT() {
		return this.pressLEFT;
	}

	/**
	 * Sets the flag indicating if left movement is being pressed.
	 * @param pressLEFT Flag indicating if left movement is being pressed
	 */
	public void setPressLEFT(boolean pressLEFT) {
		this.pressLEFT = pressLEFT;
	}

	/**
	 * Gets the flag indicating if right movement is being pressed.
	 * @return Flag indicating if right movement is being pressed
	 */
	public boolean getPressRIGHT() {
		return this.pressRIGHT;
	}

	/**
	 * Sets the flag indicating if right movement is being pressed.
	 * @param pressRIGHT Flag indicating if right movement is being pressed
	 */
	public void setPressRIGHT(boolean pressRIGHT) {
		this.pressRIGHT = pressRIGHT;
	}

	/**
	 * Gets the flag indicating if shooting action is active.
	 * @return Flag indicating if shooting action is active
	 */
	public boolean getShoot() {
		return this.shoot;
	}

	/**
	 * Sets the flag indicating if shooting action is active.
	 * @param shoot Flag indicating if shooting action is active
	 */
	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

	/**
	 * Gets the flag indicating if mouse button is being held.
	 * @return Flag indicating if mouse button is being held
	 */
	public boolean getMouse_pressed() {
		return this.mouse_pressed;
	}

	/**
	 * Sets the flag indicating if mouse button is being held.
	 * @param mouse_pressed Flag indicating if mouse button is being held
	 */
	public void setMouse_pressed(boolean mouse_pressed) {
		this.mouse_pressed = mouse_pressed;
	}

	/**
	 * Gets the flag indicating if mouse has been clicked.
	 * @return Flag indicating if mouse has been clicked
	 */
	public boolean getMouse_clicked() {
		return this.mouse_clicked;
	}

	/**
	 * Sets the flag indicating if mouse has been clicked.
	 * @param mouse_clicked Flag indicating if mouse has been clicked
	 */
	public void setMouse_clicked(boolean mouse_clicked) {
		this.mouse_clicked = mouse_clicked;
	}

	/**
	 * Gets the flag indicating if mouse has been moved.
	 * @return Flag indicating if mouse has been moved
	 */
	public boolean getMouse_moved() {
		return this.mouse_moved;
	}

	/**
	 * Sets the flag indicating if mouse has been moved.
	 * @param mouse_moved Flag indicating if mouse has been moved
	 */
	public void setMouse_moved(boolean mouse_moved) {
		this.mouse_moved = mouse_moved;
	}

	/**
	 * Gets the flag indicating if upward movement is canceled.
	 * @return Flag indicating if upward movement is canceled
	 */
	public boolean isCancelUP() {
		return cancelUP;
	}

	/**
	 * Sets the flag indicating if upward movement is canceled.
	 * @param cancelUP Flag indicating if upward movement is canceled
	 */
	public void setCancelUP(boolean cancelUP) {
		this.cancelUP = cancelUP;
	}

	/**
	 * Gets the flag indicating if downward movement is canceled.
	 * @return Flag indicating if downward movement is canceled
	 */
	public boolean isCancelDOWN() {
		return cancelDOWN;
	}

	/**
	 * Sets the flag indicating if downward movement is canceled.
	 * @param cancelDOWN Flag indicating if downward movement is canceled
	 */
	public void setCancelDOWN(boolean cancelDOWN) {
		this.cancelDOWN = cancelDOWN;
	}

	/**
	 * Gets the flag indicating if leftward movement is canceled.
	 * @return Flag indicating if leftward movement is canceled
	 */
	public boolean isCancelLEFT() {
		return cancelLEFT;
	}

	/**
	 * Sets the flag indicating if leftward movement is canceled.
	 * @param cancelLEFT Flag indicating if leftward movement is canceled
	 */
	public void setCancelLEFT(boolean cancelLEFT) {
		this.cancelLEFT = cancelLEFT;
	}

	/**
	 * Gets the flag indicating if rightward movement is canceled.
	 * @return Flag indicating if rightward movement is canceled
	 */
	public boolean isCancelRIGHT() {
		return cancelRIGHT;
	}

	/**
	 * Sets the flag indicating if rightward movement is canceled.
	 * @param cancelRIGHT Flag indicating if rightward movement is canceled
	 */
	public void setCancelRIGHT(boolean cancelRIGHT) {
		this.cancelRIGHT = cancelRIGHT;
	}

	/**
	 * Gets the size of the game object.
	 * @return Size of the game object in pixels
	 */
	public int getSize() {
		return size;
	}
}