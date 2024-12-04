package com.unipi.alexandris.game.echotrials.base.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public record UserFiles(String uuid, String username, ArrayList<String> unlockedLevels, HashMap<LevelID, Double> highScore) implements Serializable {}
