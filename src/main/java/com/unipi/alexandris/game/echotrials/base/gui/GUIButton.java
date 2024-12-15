package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * Abstract base class for all GUI button elements in the game.
 * Provides common functionality for button positioning, rendering, and mouse interaction.
 * Subclasses must implement specific button behavior by overriding the onClick method.
 */
public abstract class GUIButton {
    /** X coordinate of the button in screen space. */
    protected double x;
    
    /** Y coordinate of the button in screen space. */
    protected double y;
    
    /** Size of the button in pixels (both width and height). */
    protected int size = 64;
    
    /** Identifier for the type of button. */
    protected ID id;
    
    /** Cooldown timer to prevent rapid button clicks (in game ticks). */
    private int cooldown = 1;
    
    /** Flag indicating if the mouse button is currently pressed. */
    protected boolean mouse_pressed;
    
    /** Flag indicating if the mouse button was clicked. */
    protected boolean mouse_clicked;
    
    /** Flag indicating if the mouse has moved. */
    protected boolean mouse_moved;
    
    /** Flag indicating if the mouse is hovering over the button. */
    protected boolean mouse_hover;

    /** The image to be displayed for this button. */
    protected final Image image;

    /**
     * Constructs a new GUI button with specified position and properties.
     *
     * @param x The X coordinate for the button
     * @param y The Y coordinate for the button
     * @param id The button's identifier type
     * @param image The image to display for the button
     */
    public GUIButton(double x, double y, ID id, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.image = image;
    }

    /**
     * Updates the button's state each game tick.
     * Handles mouse interaction and click cooldown timing.
     */
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

    /**
     * Called when the button is clicked.
     * Subclasses should override this method to implement specific button behavior.
     */
    public void onClick() {}

    /**
     * Renders the button on the screen.
     *
     * @param g The Graphics context to render to
     */
    public void render(Graphics g) {
        g.drawImage(image, (int)x, (int)y, size, size, null);
    }

    /**
     * Gets the button's collision area for mouse interaction.
     *
     * @return An Area object representing the button's bounds
     */
    public Area getArea() {
        return new Area(getBounds());
    }

    /**
     * Gets the button's rectangular bounds for collision detection.
     *
     * @return A Rectangle representing the button's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }

    /**
     * Gets the button's X coordinate.
     *
     * @return The X coordinate in screen space
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the button's X coordinate.
     *
     * @param x The new X coordinate in screen space
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the button's Y coordinate.
     *
     * @return The Y coordinate in screen space
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the button's Y coordinate.
     *
     * @param y The new Y coordinate in screen space
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the button's size (width and height are equal).
     *
     * @return The size in pixels
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the button's identifier type.
     *
     * @return The button's ID
     */
    public ID getId() {
        return id;
    }

    /**
     * Sets the button's identifier type.
     *
     * @param id The new ID for the button
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * Checks if the mouse is hovering over the button.
     *
     * @return true if the mouse is hovering, false otherwise
     */
    public boolean isMouse_hover() {
        return mouse_hover;
    }

    /**
     * Checks if the mouse button is pressed.
     *
     * @return true if the mouse button is pressed, false otherwise
     */
    public boolean isMouse_pressed() {
        return mouse_pressed;
    }

    /**
     * Sets the mouse pressed state.
     *
     * @param mouse_pressed The new mouse pressed state
     */
    public void setMouse_pressed(boolean mouse_pressed) {
        this.mouse_pressed = mouse_pressed;
    }

    /**
     * Checks if the mouse was clicked.
     *
     * @return true if the mouse was clicked, false otherwise
     */
    public boolean isMouse_clicked() {
        return mouse_clicked;
    }

    /**
     * Sets the mouse clicked state.
     *
     * @param mouse_clicked The new mouse clicked state
     */
    public void setMouse_clicked(boolean mouse_clicked) {
        this.mouse_clicked = mouse_clicked;
    }

    /**
     * Checks if the mouse has moved.
     *
     * @return true if the mouse has moved, false otherwise
     */
    public boolean isMouse_moved() {
        return mouse_moved;
    }

    /**
     * Sets the mouse moved state.
     *
     * @param mouse_moved The new mouse moved state
     */
    public void setMouse_moved(boolean mouse_moved) {
        this.mouse_moved = mouse_moved;
    }
}
