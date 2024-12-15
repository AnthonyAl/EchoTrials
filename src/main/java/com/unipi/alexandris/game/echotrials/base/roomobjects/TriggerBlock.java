package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.TriggerHandler;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * A trigger zone that activates game events when the player enters its area.
 * Supports delayed activation, repeated triggering, and customizable detection ranges.
 * Used to create interactive level mechanics and synchronized events.
 */
@SuppressWarnings(value = "unused")
public class TriggerBlock extends GameObject {

	/** Handler for trigger events */
	private final TriggerHandler handler;

	/** Delay in ticks before trigger activates */
	private final int delay;

	/** Ticks between repeated trigger activations */
	private final int speed;

	/** Number of times trigger can activate (-1 for infinite) */
	private final int iterations;

	/** Left range multiplier for trigger area */
	private final int WR1;

	/** Right range multiplier for trigger area */
	private final int WR2;

	/** Top range multiplier for trigger area */
	private final int HR1;

	/** Bottom range multiplier for trigger area */
	private final int HR2;

	/**
	 * Creates a trigger with default range.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param delay Activation delay
	 * @param speed Repeat interval
	 * @param iterations Maximum activations
	 * @param handler Event handler
	 */
	public TriggerBlock(int x, int y, ID id, int delay, int speed, int iterations, TriggerHandler handler) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.delay = delay;
		this.speed = speed;
		this.iterations = iterations;
		WR1 = 0;
		WR2 = 0;
		HR1 = 0;
		HR2 = 0;
	}

	/**
	 * Creates a trigger with uniform range.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param delay Activation delay
	 * @param speed Repeat interval
	 * @param handler Event handler
	 * @param RANGE Detection range in all directions
	 */
	public TriggerBlock(int x, int y, ID id, int delay, int speed, TriggerHandler handler, int RANGE) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.delay = delay;
		this.speed = speed;
		this.iterations = -1;
		WR1 = RANGE;
		WR2 = RANGE;
		HR1 = RANGE;
		HR2 = RANGE;
	}

	/**
	 * Creates a trigger with custom ranges.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param delay Activation delay
	 * @param speed Repeat interval
	 * @param handler Event handler
	 * @param WR1 Left range
	 * @param WR2 Right range
	 * @param HR1 Top range
	 * @param HR2 Bottom range
	 */
	public TriggerBlock(int x, int y, ID id, int delay, int speed, TriggerHandler handler, int WR1, int WR2, int HR1, int HR2) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.delay = delay;
		this.speed = speed;
		this.iterations = -1;
		this.WR1 = WR1;
		this.WR2 = WR2;
		this.HR1 = HR1;
		this.HR2 = HR2;
	}

	/**
	 * Creates a trigger with uniform range and iteration limit.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param delay Activation delay
	 * @param speed Repeat interval
	 * @param iterations Maximum activations
	 * @param handler Event handler
	 * @param RANGE Detection range in all directions
	 */
	public TriggerBlock(int x, int y, ID id, int delay, int speed, int iterations, TriggerHandler handler, int RANGE) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.delay = delay;
		this.speed = speed;
		this.iterations = iterations;
		WR1 = RANGE;
		WR2 = RANGE;
		HR1 = RANGE;
		HR2 = RANGE;
	}

	/**
	 * Creates a trigger with custom ranges and iteration limit.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param delay Activation delay
	 * @param speed Repeat interval
	 * @param iterations Maximum activations
	 * @param handler Event handler
	 * @param WR1 Left range
	 * @param WR2 Right range
	 * @param HR1 Top range
	 * @param HR2 Bottom range
	 */
	public TriggerBlock(int x, int y, ID id, int delay, int speed, int iterations, TriggerHandler handler, int WR1, int WR2, int HR1, int HR2) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.delay = delay;
		this.speed = speed;
		this.iterations = iterations;
		this.WR1 = WR1;
		this.WR2 = WR2;
		this.HR1 = HR1;
		this.HR2 = HR2;
	}

	/**
	 * Checks for player collision and activates trigger.
	 */
	@Override
	public void tick() {
		if(Game.player == null) return;
		if(getArea(WR1, HR1, WR2, HR2).intersects(Game.player.getBounds())) {
			handler.scheduleTriggerTask(this);
		}
	}

	/**
	 * Renders the trigger block.
	 * Trigger zones are invisible by default.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
	}

	/**
	 * Gets trigger's basic collision bounds.
	 * @return Rectangle representing trigger zone
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}
	
	/**
	 * Gets expanded collision bounds.
	 * @param a Expansion multiplier
	 * @return Expanded rectangle bounds
	 */
	public Rectangle getBounds(int a) {
		a = a * Game.multiplier;
		return new Rectangle((int) x - a, (int) y - a, size + a*2, size + a*2);
	}

	/**
	 * Gets trigger's precise collision area.
	 * @return Elliptical area for basic detection
	 */
	@Override
	public Area getArea() {
		return new Area(new Ellipse2D.Double((int) x, (int) y, size, size));
	}

	/**
	 * Gets trigger's detection area with custom ranges.
	 * @param w1 Left range
	 * @param h1 Top range
	 * @param w2 Right range
	 * @param h2 Bottom range
	 * @return Elliptical area with specified ranges
	 */
	public Area getArea(int w1, int h1, int w2, int h2) {
		w1 = w1 * Game.multiplier;
		h1 = h1 * Game.multiplier;
		w2 = w2 * Game.multiplier;
		h2 = h2 * Game.multiplier;
		return new Area(new Ellipse2D.Double((int) x - w1, (int) y - h1, size + w2*2, size + h2*2));
	}

	/**
	 * Gets trigger activation delay.
	 * @return Delay in ticks
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Gets trigger repeat interval.
	 * @return Speed in ticks
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Gets maximum number of trigger activations.
	 * @return Iteration limit (-1 for infinite)
	 */
	public int getIterations() {
		return iterations;
	}

}
