package com.unipi.alexandris.game.echotrials.base.core;

/**
 * The ID enum represents different types of game objects and elements in Echo Trials.
 * Each enum value corresponds to a specific type of entity that can exist in the game world.
 * This enum is used for object identification, collision detection, and game logic handling.
 */
public enum ID {
	/** The player character controlled by the user */
	Player,
	
	/** The goal/endpoint of a level */
	Goal,
	
	/** Projectiles fired by enemy entities */
	Enemy_Projectile,
	
	/** Projectiles fired by the player or neutral sources */
	Projectile,
	
	/** Spike obstacles that can damage the player */
	Spike,
	
	/** Air bubble power-up for underwater sections */
	AirBubble,
	
	/** Heart power-up for health restoration */
	Heart,
	
	/** Foreground layer game elements */
	Foreground,
	
	/** Background layer game elements */
	Background,
	
	/** Level geometry/collision polygons */
	LevelPoly,
	
	/** Solid blocks that form the level structure */
	Block,
	
	/** Blocks that act as traps, damaging the player on contact */
	TrapBlock,
	
	/** Blocks that trigger specific events when interacted with */
	TriggerBlock,
	
	/** Blocks that move along predefined paths */
	MovingBlock,
	
	/** Interactive buttons that can be pressed to trigger events */
	ButtonBlock,
	
	/** Water areas affecting player movement and mechanics */
	Water,
	
	/** Collectible orbs that react to player touch */
	TouchOrb,
	
	/** Visual particle effects */
	Particles,
	
	/** UI or interactive button elements */
	Button
}

