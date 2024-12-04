package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HighScoreButton extends GUIButton {
    public HighScoreButton(double x, double y, ID id, BufferedImage image) {
        super(x, y, id, image);
    }

    @Override
    public void onClick() {
        if(Game.currentLevel == LevelID.LEVEL_SELECTOR)
            Game.loadLevel(LevelID.LEVEL_HIGHSCORE);
    }

    @Override
    public void render(Graphics g) {
        if(Game.currentLevel == LevelID.LEVEL_SELECTOR)
            g.drawImage(image, (int)x, (int)y, size, size, null);
    }

}
