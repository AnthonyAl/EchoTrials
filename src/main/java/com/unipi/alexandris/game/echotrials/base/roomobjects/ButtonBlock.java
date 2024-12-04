package com.unipi.alexandris.game.echotrials.base.roomobjects;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.handlers.TriggerHandler;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class ButtonBlock extends GameObject {
	public enum ButtonType {
		SPEED,
		SIZE
	}
	private final TriggerHandler handler;
	private final BufferedImage[] image;
	private final Player targetedPlayer;
	public boolean pressed = false;
	private final ButtonType type;
	private TriggerHandler.ButtonMovementSetting buttonMovementSetting;
	private TriggerHandler.ButtonSizeSetting buttonSizeSetting;

	public ButtonBlock(int x, int y, ID id, TriggerHandler handler, TriggerHandler.ButtonMovementSetting buttonMovementSetting) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.targetedPlayer = Game.player;
		this.type = buttonMovementSetting.type();
		this.image = Game.gameImages.buttonBlockImages();
		this.buttonMovementSetting = buttonMovementSetting;
	}

	public ButtonBlock(int x, int y, ID id, TriggerHandler handler, TriggerHandler.ButtonSizeSetting buttonSizeSetting) {
		super(x, y, id);
		size = 48;
		this.handler = handler;
		this.targetedPlayer = Game.player;
		this.type = buttonSizeSetting.type();
		this.image = Game.gameImages.buttonBlockImages();
		this.buttonSizeSetting = buttonSizeSetting;
	}

	@Override
		public void tick() {
			if(getArea().intersects(targetedPlayer.getBounds())) {
				if(!pressed) {
					pressed = true;
					passEffect();
				}
			}
	}

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

	@Override
	public void render(Graphics g) {
		if(!pressed) g.drawImage(image[0], (int) x, (int) y, null);
		else g.drawImage(image[1], (int) x, (int) y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size/2);
	}
	
	public Rectangle getBounds(int a) {
		return new Rectangle((int) x - a, (int) y - a, size + a*2, size + a*2);
	}

	@Override
	public Area getArea() {
		return new Area(new Ellipse2D.Double((int) x, (int) y, size, size*0.5));
	}

}
