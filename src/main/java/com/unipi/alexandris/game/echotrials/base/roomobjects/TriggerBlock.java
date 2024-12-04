package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.TriggerHandler;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

@SuppressWarnings(value = "unused")
public class TriggerBlock extends GameObject {

	private final TriggerHandler handler;
	private final int delay; // The amount of ticks it takes for this Trigger's Action to initiate.
	private final int speed; // The amount of ticks it takes for this Trigger's Action to repeat.
	private final int iterations; // The amount of times this Trigger's Action will be repeated. (-1 for infinite)
	private final int WR1;
	private final int WR2;
	private final int HR1;
	private final int HR2;

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

	@Override
	public void tick() {
		if(Game.player == null) return;
		if(getArea(WR1, HR1, WR2, HR2).intersects(Game.player.getBounds())) {
			handler.scheduleTriggerTask(this);
		}
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}
	
	public Rectangle getBounds(int a) {
		a = a * Game.multiplier;
		return new Rectangle((int) x - a, (int) y - a, size + a*2, size + a*2);
	}

	@Override
	public Area getArea() {
		return new Area(new Ellipse2D.Double((int) x, (int) y, size, size));
	}

	public Area getArea(int w1, int h1, int w2, int h2) {
		w1 = w1 * Game.multiplier;
		h1 = h1 * Game.multiplier;
		w2 = w2 * Game.multiplier;
		h2 = h2 * Game.multiplier;
		return new Area(new Ellipse2D.Double((int) x - w1, (int) y - h1, size + w2*2, size + h2*2));
	}

	public int getDelay() {
		return delay;
	}

	public int getSpeed() {
		return speed;
	}

	public int getIterations() {
		return iterations;
	}

}
