package com.unipi.alexandris.game.echotrials.base.handlers;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;
import com.unipi.alexandris.game.echotrials.base.roomobjects.Player;
import com.unipi.alexandris.game.echotrials.base.roomobjects.PortalBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * The Handler class manages all game objects and scheduled tasks in Echo Trials.
 * It provides functionality for:
 * <ul>
 *   <li>Managing the game object lifecycle (adding, removing, updating)</li>
 *   <li>Scheduling and executing delayed or periodic tasks</li>
 *   <li>Rendering game objects in the correct order</li>
 *   <li>Handling special cases for Player and PortalBlock objects</li>
 * </ul>
 */
@SuppressWarnings(value = "unused")
public class Handler {

	/**
	 * The Clock class manages timing for scheduled tasks.
	 * It tracks periods, iterations, and delays for task execution.
	 */
	protected static class Clock {
		/** The period between task executions in game ticks. */
		private final int period;
		
		/** The number of times to execute the task (-1 for infinite). */
		private final int iterations;
		
		/** Current tick count since last execution. */
		private int clock;
		
		/** Number of completed execution cycles. */
		private int cycles = 0;

		/**
		 * Constructs a Clock for infinite task repetition.
		 *
		 * @param delay Initial delay before first execution
		 * @param period Time between executions
		 */
		public Clock(int delay, int period) {
			this.period = period;
			this.iterations = -1;
			this.clock = -delay;
		}

		/**
		 * Constructs a Clock with limited iterations.
		 *
		 * @param delay Initial delay before first execution
		 * @param period Time between executions
		 * @param iterations Number of times to execute (-1 for infinite)
		 */
		public Clock(int delay, int period, int iterations) {
			this.period = period;
			this.iterations = iterations;
			this.clock = -delay;
		}
	}
	
	/** List of all active game objects. */
	protected LinkedList<GameObject> object = new LinkedList<>();
	
	/** Map of scheduled tasks and their timing information. */
	protected HashMap<Runnable, Clock> scheduledTasks = new HashMap<>();
	
	/**
	 * Updates all game objects and executes scheduled tasks each game tick.
	 * Handles task scheduling, iteration counting, and safe object updates.
	 */
	public void tick() {
		try {
			for (Runnable task : new ArrayList<>(scheduledTasks.keySet())) {
					Clock clock = scheduledTasks.get(task);
					if (clock.clock++ >= clock.period) {
						if (clock.iterations > -1 && ++clock.cycles > clock.iterations) scheduledTasks.remove(task);
						if (scheduledTasks.containsKey(task)) {
							task.run();
							clock.clock = 0;
						}
					}
			}

			for (GameObject tempObject : new ArrayList<>(object)) {
				tempObject.tick();
			}
		}
		catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
			System.out.println("[WARNING] Fast level exit has caused a minor exception.");
		}
	}
	
	/**
	 * Renders all game objects in their current state.
	 * Uses a safe copy of the object list to prevent concurrent modification.
	 *
	 * @param g The Graphics context to render to
	 */
	public void render(Graphics g) {
		try {
			for (GameObject tempObject : new ArrayList<>(object)) {
				tempObject.render(g);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			System.out.println("[WARNING] Fast level exit has caused a minor exception.");
		}
	}
	
	/**
	 * Adds a new game object to the handler.
	 * Handles special cases for Player and PortalBlock objects,
	 * ensuring only one instance of each exists.
	 *
	 * @param object The GameObject to add
	 */
	public void addObject(GameObject object) {
		if(object instanceof Player player) {
			if(Game.player != null) return;
			Game.player = player;
		}
		if(object instanceof PortalBlock goal) {
			Game.goal = goal;
		}
		this.object.add(object);
	}
	
	/**
	 * Removes a game object from the handler.
	 *
	 * @param object The GameObject to remove
	 */
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	/**
	 * Clears all game objects and scheduled tasks.
	 * Used when resetting or changing levels.
	 */
	public void clear() {
		this.scheduledTasks.clear();
		this.object.clear();
	}

	/**
	 * Gets the list of all active game objects.
	 *
	 * @return LinkedList containing all game objects
	 */
	public LinkedList<GameObject> getObject() {
		return object;
	}

	/**
	 * Schedules a task to run after a specified delay.
	 * The task will execute exactly once after the delay.
	 *
	 * @param runnable The task to execute
	 * @param ticks Number of game ticks to wait before execution
	 */
	public void runLater(Runnable runnable, int ticks) {
		scheduledTasks.put(runnable, new Clock(ticks, 0, 1));
	}
}
