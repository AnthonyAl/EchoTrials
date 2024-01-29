package com.unipi.alexandris.game.MASHLE.core;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

public abstract class GameObject {
	
	public double x, y;
	public int size = 0;
	public ID id;
	public boolean w, s, a, d, mouse_pressed, mouse_clicked, mouse_moved;

	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
		
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract Area getArea();
	
	
	public void setY(double y) {
		this.y = y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public double getX() {
		return x;
	}
	public void setld(ID id) {
		this.id = id;
	}
	public ID getId() {
		return id;
	}

	public boolean getW() {
		return w;
	}

	public void setW(boolean w) {
		this.w = w;
	}

	public boolean getS() {
		return s;
	}

	public void setS(boolean s) {
		this.s = s;
	}

	public boolean getA() {
		return a;
	}

	public void setA(boolean a) {
		this.a = a;
	}

	public boolean getD() {
		return d;
	}

	public void setD(boolean d) {
		this.d = d;
	}

	public boolean getMouse_pressed() {
		return mouse_pressed;
	}

	public void setMouse_pressed(boolean mouse_pressed) {
		this.mouse_pressed = mouse_pressed;
	}

	public boolean getMouse_clicked() {
		return mouse_clicked;
	}

	public void setMouse_clicked(boolean mouse_clicked) {
		this.mouse_clicked = mouse_clicked;
	}

	public boolean getMouse_moved() {
		return mouse_moved;
	}

	public void setMouse_moved(boolean mouse_moved) {
		this.mouse_moved = mouse_moved;
	}
	
}
