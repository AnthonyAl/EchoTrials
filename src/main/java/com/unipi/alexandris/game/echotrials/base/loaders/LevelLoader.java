package com.unipi.alexandris.game.echotrials.base.loaders;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.GameLevel;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;
import com.unipi.alexandris.game.echotrials.base.handlers.TriggerHandler;
import com.unipi.alexandris.game.echotrials.base.roomobjects.*;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;

import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * The LevelLoader class handles loading and initialization of game levels.
 * It manages:
 * <ul>
 *   <li>Level data deserialization from files</li>
 *   <li>Game object initialization and placement</li>
 *   <li>Background and foreground layer setup</li>
 *   <li>Collision area configuration</li>
 *   <li>Player and portal placement</li>
 * </ul>
 * This class is crucial for transitioning between different game levels.
 */
public class LevelLoader {
	/** The main game object handler. */
	private final Handler handler;
	
	/** Handler for level-specific triggers and interactions. */
	private final TriggerHandler triggerHandler;
	
	/** Collection of images used for level backgrounds. */
	private final ArrayList<BufferedImage> backgroundImages = new ArrayList<>();

	/**
	 * Constructs a new LevelLoader with a reference to the game's object handler.
	 * Initializes a new TriggerHandler for managing level-specific interactions.
	 *
	 * @param handler The main game object handler
	 */
	public LevelLoader(Handler handler) {
		this.handler = handler;
		triggerHandler = new TriggerHandler(handler);
	}
	
	/**
	 * Loads and initializes a game level from its serialized data file.
	 * This method:
	 * <ul>
	 *   <li>Deserializes level data from resources</li>
	 *   <li>Sets up collision areas and spawn points</li>
	 *   <li>Creates background and foreground layers</li>
	 *   <li>Places the player and portal objects</li>
	 *   <li>Configures level-specific triggers</li>
	 * </ul>
	 *
	 * @param levelID The identifier of the level to load
	 * @return The loaded and initialized GameLevel object
	 */
	public GameLevel load(LevelID levelID) {
		GameLevel gameLevel = null;

		// Deserialization of the level
		try
		{
			InputStream stream = Game.class.getResourceAsStream("/levels/data/" + levelID.name() + ".data");
			assert stream != null;

			ObjectInputStream in = new ObjectInputStream(stream);
			Object o = in.readObject();
			System.out.println(o.getClass());

			// Method for deserialization of object
			gameLevel = (GameLevel) o;

			in.close();
            stream.close();

			System.out.println(gameLevel.levelID().name() + " has been deserialized.");
		}
		catch(IOException ex)
		{
			System.out.println(levelID + " deserialization has produced an exception.");
		}
		catch(ClassNotFoundException | NullPointerException ex)
		{
			System.out.println(levelID + " data was not found.");
		}
        if(gameLevel == null) {
			System.out.println(Game.class.getResource("/levels/data/" + levelID.name() + ".data"));
			System.out.println(" ");
			System.exit(1);
		}

		// Initialize Game level constants
		Game.block = new Area(gameLevel.blockArea());
		Game.ice = new Area(gameLevel.iceArea());
		Game.water = new Area(gameLevel.waterArea());
		Game.i_p_x = gameLevel.playerCoords()[0];
		Game.i_p_y = gameLevel.playerCoords()[1];

		// Initialize Base Game Objects (Level, Player, etc.)
		backgroundImages.add(Game.gameImages.brickImage());
		backgroundImages.add(Game.gameImages.iceImage());
		backgroundImages.add(Game.gameImages.waterImage());
		backgroundImages.add(Game.gameImages.backgroundImage());
		handler.addObject(new Background(0, 0, ID.Background, backgroundImages,
				new ArrayList<>(Collections.singleton(gameLevel.backgroundMap())), gameLevel.multiplier(), gameLevel.mapWidth(), gameLevel.mapHeight()));

		ArrayList<GameObject> portalBlocks = new ArrayList<>();
		for(Integer[] goalCoords : gameLevel.goalCoords()) {
			PortalBlock portalBlock = new PortalBlock(goalCoords[0], goalCoords[1], ID.Goal, Game.gameImages.portalImages());
			portalBlock.unlock();
			portalBlocks.add(portalBlock);
			handler.addObject(portalBlock); // Also initializes the goal in the Game constants.
		}

		Player player = new Player(Game.i_p_x + 30, Game.i_p_y + 30, ID.Player, handler);
		player.addExtraObstructions(Game.ice);
		handler.addObject(player); // Also initializes the player in the Game constants.

		triggerHandler.buildLevel(portalBlocks, gameLevel);

		handler.addObject(new Foreground(0, 0, ID.Foreground, backgroundImages,
				new ArrayList<>(Collections.singleton(gameLevel.backgroundMap())), gameLevel.multiplier(), gameLevel.mapWidth(), gameLevel.mapHeight()));


		return gameLevel;
	}
	
}
