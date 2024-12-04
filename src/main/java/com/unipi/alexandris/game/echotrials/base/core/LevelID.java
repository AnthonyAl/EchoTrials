package com.unipi.alexandris.game.echotrials.base.core;

import java.io.Serializable;

public enum LevelID implements Serializable {
    LEVEL_TEST_I("TEST", "I"),
    LEVEL_TEST_II("TEST", "II"),
    LEVEL_ROCKS("TEST", "III"),
    LEVEL_SELECTOR("MAIN", "I"),
    LEVEL_HIGHSCORE("MAIN", "II"),
    LEVEL_A_I("A", "I"),
    LEVEL_A_II("A", "II"),
    LEVEL_A_III("A", "III"),
    LEVEL_A_IV("A", "IV"),
    LEVEL_A_V("A", "V"),
    LEVEL_B_I("B", "I"),
    LEVEL_B_II("B", "II"),
    LEVEL_B_III("B", "III"),
    LEVEL_B_IV("B", "IV"),
    LEVEL_B_V("B", "V"),
    LEVEL_C_I("C", "I"),
    LEVEL_C_II("C", "II"),
    LEVEL_C_III("C", "III"),
    LEVEL_C_IV("C", "IV"),
    LEVEL_C_V("C", "V"),
    LEVEL_D_I("D", "I"),
    LEVEL_D_II("D", "II"),
    LEVEL_D_III("D", "III"),
    LEVEL_D_IV("D", "IV"),
    LEVEL_D_V("D", "V"),
    LEVEL_E_I("E", "I"),
    LEVEL_E_II("E", "II"),
    LEVEL_E_III("E", "III"),
    LEVEL_E_IV("E", "IV"),
    LEVEL_E_V("E", "V");

    public final String group;
    public final String part;
    LevelID(String group, String part){
        this.group = group;
        this.part = part;
    }

    public static LevelID getByName(String name) {
        for(LevelID levelID : LevelID.values()) if(levelID.name().equals(name)) return levelID;
        return null;
    }
}
