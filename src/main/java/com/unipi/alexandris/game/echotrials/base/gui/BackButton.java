package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;

import java.awt.image.BufferedImage;

/**
 * The BackButton class represents a GUI button that handles navigation back or game exit.
 * When clicked, it either returns to the level selector or exits the game depending on
 * the current game state. This button extends the base GUIButton class and implements
 * specific click behavior.
 */
public class BackButton extends GUIButton {
    
    /**
     * Constructs a new BackButton with specified position and properties.
     *
     * @param x The X coordinate for the button
     * @param y The Y coordinate for the button
     * @param id The button's identifier type
     * @param image The image to display for the button
     */
    public BackButton(double x, double y, ID id, BufferedImage image) {
        super(x, y, id, image);
    }

    /**
     * Handles the button click event.
     * If currently in the level selector, exits the game.
     * Otherwise, returns to the level selector.
     */
    @Override
    public void onClick() {
        if(Game.currentLevel == LevelID.LEVEL_SELECTOR) Game.exit();
        else Game.restart();
    }
}
