package com.unipi.alexandris.game.echotrials.base.handlers;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.roomobjects.GameObject;
import com.unipi.alexandris.game.echotrials.base.roomobjects.Player;
import com.unipi.alexandris.game.echotrials.base.roomobjects.PortalBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

@SuppressWarnings(value = "unused")
public class Handler {

	protected static class Clock {
		private final int period;
		private final int iterations;
		private int clock;
		private int cycles = 0;
		public Clock(int delay, int period) {
			this.period = period;
			this.iterations = -1;
			this.clock = -delay;
		}
		public Clock(int delay, int period, int iterations) {
			this.period = period;
			this.iterations = iterations;
			this.clock = -delay;
		}
	}
	
	protected LinkedList<GameObject> object = new LinkedList<>();
	protected HashMap<Runnable, Clock> scheduledTasks = new HashMap<>();
	
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
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	public void clear() {
		this.scheduledTasks.clear();
		this.object.clear();
	}

	public LinkedList<GameObject> getObject() {
		return object;
	}

	public void runLater(Runnable runnable, int ticks) {
		scheduledTasks.put(runnable, new Clock(ticks, 0, 1));
	}
}
