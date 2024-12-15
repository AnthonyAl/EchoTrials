package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.TriggerHandler;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * Interactive button block that triggers game mechanics when touched by the player.
 * Can modify player speed, size, and other properties through different button types.
 */
public class ButtonBlock extends GameObject {
	/**
	 * Defines the different types of button effects.
	 */
	public enum ButtonType {
		/** Modifies player movement speed */
		SPEED,
		/** Modifies player size */
		SIZE
	}

	/** Handler for button trigger events */
	private final TriggerHandler handler;

	/** Array of button state images */
	private final BufferedImage[] image;

	/** Reference to the player that can interact with this button */
	private final Player targetedPlayer;

	/** Flag indicating if button is currently pressed */
	public boolean pressed = false;

	/** Type of effect this button applies */
	private final ButtonType type;

	/** Settings for movement modification */
	private TriggerHandler.ButtonMovementSetting buttonMovementSetting;

	/** Settings for size modification */
	private TriggerHandler.ButtonSizeSetting buttonSizeSetting;

	/**
	 * Constructs a movement-modifying button.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param handler Trigger event handler
	 * @param buttonMovementSetting Movement modification settings
	 */
	public ButtonBlock(int x, int y, ID id, TriggerHandler handler, TriggerHandler.ButtonMovementSetting buttonMovementSetting) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.targetedPlayer = Game.player;
		this.type = buttonMovementSetting.type();
		this.image = Game.gameImages.buttonBlockImages();
		this.buttonMovementSetting = buttonMovementSetting;
	}

	/**
	 * Constructs a size-modifying button.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param id Object identifier
	 * @param handler Trigger event handler
	 * @param buttonSizeSetting Size modification settings
	 */
	public ButtonBlock(int x, int y, ID id, TriggerHandler handler, TriggerHandler.ButtonSizeSetting buttonSizeSetting) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.targetedPlayer = Game.player;
		this.type = buttonSizeSetting.type();
		this.image = Game.gameImages.buttonBlockImages();
		this.buttonSizeSetting = buttonSizeSetting;
	}

	/**
	 * Updates button state and checks for player interaction.
	 */
	@Override
	public void tick() {
		if(getArea().intersects(targetedPlayer.getBounds())) {
			if(!pressed) {
				pressed = true;
				passEffect();
			}
		}
	}

	/**
	 * Applies the button's effect when activated.
	 */
	private void passEffect() {
		switch(type) {
			case SIZE -> handler.scheduleTriggerTask(() -> {
							targetedPlayer.setX(targetedPlayer.getX() - targetedPlayer.WIDTH*0.173);
							if(targetedPlayer.GRAV > 0) targetedPlayer.setY(targetedPlayer.getY() - buttonSizeSetting.height());
							targetedPlayer.WIDTH += buttonSizeSetting.width()*0.01;
							targetedPlayer.HEIGHT += buttonSizeSetting.height()*0.01;
							});
			case SPEED -> {
				targetedPlayer.SPEED_X = buttonMovementSetting.speed();
				targetedPlayer.SPEED_Y = buttonMovementSetting.jump();
				targetedPlayer.GRAV = buttonMovementSetting.gravity();
				targetedPlayer.resetPhysics(true);
			}
		}
	}

	/**
	 * Renders the button in its current state.
	 * @param g Graphics context
	 */
	@Override
	public void render(Graphics g) {
		if(!pressed) g.drawImage(image[0], (int) x, (int) y, null);
		else g.drawImage(image[1], (int) x, (int) y, null);
	}

	/**
	 * Gets the button's rectangular bounds.
	 * @return Rectangle for basic collision
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size/2);
	}
	
	/**
	 * Gets expanded bounds with specified padding.
	 * @param a Padding amount
	 * @return Padded rectangle bounds
	 */
	public Rectangle getBounds(int a) {
		return new Rectangle((int) x - a, (int) y - a, size + a*2, size + a*2);
	}

	/**
	 * Gets the button's elliptical collision area.
	 * @return Area for precise collision detection
	 */
	@Override
	public Area getArea() {
		return new Area(new Ellipse2D.Double((int) x, (int) y, size, size*0.5));
	}

}
