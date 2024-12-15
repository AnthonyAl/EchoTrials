package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The HighScoreButton class represents a GUI button that navigates to the high score screen.
 * This button is only visible and interactive from the level selector screen.
 * When clicked, it loads the high score level where players can view their best times.
 */
public class HighScoreButton extends GUIButton {
    
    /**
     * Constructs a new HighScoreButton with specified position and properties.
     *
     * @param x The X coordinate for the button
     * @param y The Y coordinate for the button
     * @param id The button's identifier type
     * @param image The image to display for the button
     */
    public HighScoreButton(double x, double y, ID id, BufferedImage image) {
        super(x, y, id, image);
    }

    /**
     * Handles the button click event.
     * If clicked from the level selector, loads the high score screen.
     * Has no effect when clicked from other screens.
     */
    @Override
    public void onClick() {
        if(Game.currentLevel == LevelID.LEVEL_SELECTOR)
            Game.loadLevel(LevelID.LEVEL_HIGHSCORE);
    }

    /**
     * Renders the button on the screen.
     * The button is only rendered when the player is in the level selector screen.
     *
     * @param g The Graphics context to render to
     */
    @Override
    public void render(Graphics g) {
        if(Game.currentLevel == LevelID.LEVEL_SELECTOR)
            g.drawImage(image, (int)x, (int)y, size, size, null);
    }
}
