package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;
public abstract class GameObject {
	protected double x;
	protected double y;
	protected int size = 0;
	protected ID id;
	protected final Area obstructions = new Area();
	protected boolean pressUP;
	protected boolean pressDOWN;
	protected boolean pressLEFT;
	protected boolean pressRIGHT;
	protected boolean cancelUP = false;
	protected boolean cancelDOWN = false;
	protected boolean cancelLEFT = false;
	protected boolean cancelRIGHT = false;
	protected boolean shoot;
	protected boolean mouse_pressed;
	protected boolean mouse_clicked;
	protected boolean mouse_moved;

	public GameObject(double x, double y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public abstract void tick();

	public abstract void render(Graphics var1);

	public abstract Rectangle getBounds();

	public abstract Area getArea();

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}


	public void moveY(double y) {
		this.y += y;
	}

	public void moveX(double x) {
		this.x += x;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}

	public void setld(ID id) {
		this.id = id;
	}

	public ID getId() {
		return this.id;
	}

	public boolean getPressUP() {
		return this.pressUP;
	}

	public void setPressUP(boolean pressUP) {
		this.pressUP = pressUP;
	}

	public boolean getPressDOWN() {
		return this.pressDOWN;
	}

	public void setPressDOWN(boolean pressDOWN) {
		this.pressDOWN = pressDOWN;
	}

	public boolean getPressLEFT() {
		return this.pressLEFT;
	}

	public void setPressLEFT(boolean pressLEFT) {
		this.pressLEFT = pressLEFT;
	}

	public boolean getPressRIGHT() {
		return this.pressRIGHT;
	}

	public void setPressRIGHT(boolean pressRIGHT) {
		this.pressRIGHT = pressRIGHT;
	}

	public boolean getShoot() {
		return this.shoot;
	}

	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

	public boolean getMouse_pressed() {
		return this.mouse_pressed;
	}

	public void setMouse_pressed(boolean mouse_pressed) {
		this.mouse_pressed = mouse_pressed;
	}

	public boolean getMouse_clicked() {
		return this.mouse_clicked;
	}

	public void setMouse_clicked(boolean mouse_clicked) {
		this.mouse_clicked = mouse_clicked;
	}

	public boolean getMouse_moved() {
		return this.mouse_moved;
	}

	public void setMouse_moved(boolean mouse_moved) {
		this.mouse_moved = mouse_moved;
	}

	public boolean isCancelUP() {
		return cancelUP;
	}

	public void setCancelUP(boolean cancelUP) {
		this.cancelUP = cancelUP;
	}

	public boolean isCancelDOWN() {
		return cancelDOWN;
	}

	public void setCancelDOWN(boolean cancelDOWN) {
		this.cancelDOWN = cancelDOWN;
	}

	public boolean isCancelLEFT() {
		return cancelLEFT;
	}

	public void setCancelLEFT(boolean cancelLEFT) {
		this.cancelLEFT = cancelLEFT;
	}

	public boolean isCancelRIGHT() {
		return cancelRIGHT;
	}

	public void setCancelRIGHT(boolean cancelRIGHT) {
		this.cancelRIGHT = cancelRIGHT;
	}

	public int getSize() {
		return size;
	}
}