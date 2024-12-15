package com.unipi.alexandris.game.echotrials.base.core;

import java.io.Serializable;

/**
 * The LevelID enum represents unique identifiers for all levels in Echo Trials.
 * Each level is identified by a group (e.g., "A", "B", "C") and a part (e.g., "I", "II", "III").
 * The enum implements Serializable to support level data persistence.
 * 
 * Level organization:
 * <ul>
 *   <li>TEST group: Test and development levels</li>
 *   <li>MAIN group: Core game system levels (selector, highscore)</li>
 *   <li>A-E groups: Main game progression levels, each with 5 parts (I-V)</li>
 * </ul>
 */
public enum LevelID implements Serializable {
    /** Test level, part 1 */
    LEVEL_TEST_I("TEST", "I"),
    /** Test level, part 2 */
    LEVEL_TEST_II("TEST", "II"),
    /** Rock mechanics test level */
    LEVEL_ROCKS("TEST", "III"),
    /** Level selection menu */
    LEVEL_SELECTOR("MAIN", "I"),
    /** High score display level */
    LEVEL_HIGHSCORE("MAIN", "II"),
    
    /** Group A, Level 1 */
    LEVEL_A_I("A", "I"),
    /** Group A, Level 2 */
    LEVEL_A_II("A", "II"),
    /** Group A, Level 3 */
    LEVEL_A_III("A", "III"),
    /** Group A, Level 4 */
    LEVEL_A_IV("A", "IV"),
    /** Group A, Level 5 */
    LEVEL_A_V("A", "V"),
    
    /** Group B, Level 1 */
    LEVEL_B_I("B", "I"),
    /** Group B, Level 2 */
    LEVEL_B_II("B", "II"),
    /** Group B, Level 3 */
    LEVEL_B_III("B", "III"),
    /** Group B, Level 4 */
    LEVEL_B_IV("B", "IV"),
    /** Group B, Level 5 */
    LEVEL_B_V("B", "V"),
    
    /** Group C, Level 1 */
    LEVEL_C_I("C", "I"),
    /** Group C, Level 2 */
    LEVEL_C_II("C", "II"),
    /** Group C, Level 3 */
    LEVEL_C_III("C", "III"),
    /** Group C, Level 4 */
    LEVEL_C_IV("C", "IV"),
    /** Group C, Level 5 */
    LEVEL_C_V("C", "V"),
    
    /** Group D, Level 1 */
    LEVEL_D_I("D", "I"),
    /** Group D, Level 2 */
    LEVEL_D_II("D", "II"),
    /** Group D, Level 3 */
    LEVEL_D_III("D", "III"),
    /** Group D, Level 4 */
    LEVEL_D_IV("D", "IV"),
    /** Group D, Level 5 */
    LEVEL_D_V("D", "V"),
    
    /** Group E, Level 1 */
    LEVEL_E_I("E", "I"),
    /** Group E, Level 2 */
    LEVEL_E_II("E", "II"),
    /** Group E, Level 3 */
    LEVEL_E_III("E", "III"),
    /** Group E, Level 4 */
    LEVEL_E_IV("E", "IV"),
    /** Group E, Level 5 */
    LEVEL_E_V("E", "V");

    /** The group this level belongs to (e.g., "A", "B", "TEST", "MAIN") */
    public final String group;
    
    /** The part number of this level within its group (e.g., "I", "II", "III") */
    public final String part;

    /**
     * Constructs a new LevelID with the specified group and part.
     *
     * @param group The group identifier for this level
     * @param part The part number within the group
     */
    LevelID(String group, String part) {
        this.group = group;
        this.part = part;
    }

    /**
     * Retrieves a LevelID by its exact name.
     *
     * @param name The exact name of the level (e.g., "LEVEL_A_I")
     * @return The matching LevelID, or null if no match is found
     */
    public static LevelID getByName(String name) {
        for(LevelID levelID : LevelID.values()) if(levelID.name().equals(name)) return levelID;
        return null;
    }
}
