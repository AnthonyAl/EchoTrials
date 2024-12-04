package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;

import java.awt.image.BufferedImage;

public class BackButton extends GUIButton {
    public BackButton(double x, double y, ID id, BufferedImage image) {
        super(x, y, id, image);
    }

    @Override
    public void onClick() {
        if(Game.currentLevel == LevelID.LEVEL_SELECTOR) Game.exit();
        else Game.restart();
    }
}
