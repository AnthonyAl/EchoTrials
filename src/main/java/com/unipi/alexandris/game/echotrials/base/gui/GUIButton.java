package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public abstract class GUIButton {
    protected double x;
    protected double y;
    protected int size = 64;
    protected ID id;
    private int cooldown = 1;
    protected boolean mouse_pressed;
    protected boolean mouse_clicked;
    protected boolean mouse_moved;
    protected boolean mouse_hover;

    protected final Image image;

    public GUIButton(double x, double y, ID id, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.image = image;
    }

    public void tick() {
        Point point = new Point(Game.cursorX, Game.cursorY);
        if(cooldown < 50) cooldown++;
        if(getBounds().contains(point)) {
            mouse_hover = true;
            if (mouse_clicked && cooldown == 50) {
                cooldown = 0;
                onClick();
                mouse_clicked = false;
            }
        }
        else {
            mouse_hover = false;
        }
    }

    public void onClick() {}

    public void render(Graphics g) {
        g.drawImage(image, (int)x, (int)y, size, size, null);
    }

    public Area getArea() {
        return new Area(getBounds());
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public boolean isMouse_hover() {
        return mouse_hover;
    }

    public boolean isMouse_pressed() {
        return mouse_pressed;
    }

    public void setMouse_pressed(boolean mouse_pressed) {
        this.mouse_pressed = mouse_pressed;
    }

    public boolean isMouse_clicked() {
        return mouse_clicked;
    }

    public void setMouse_clicked(boolean mouse_clicked) {
        this.mouse_clicked = mouse_clicked;
    }

    public boolean isMouse_moved() {
        return mouse_moved;
    }

    public void setMouse_moved(boolean mouse_moved) {
        this.mouse_moved = mouse_moved;
    }
}
