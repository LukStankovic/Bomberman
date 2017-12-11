/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.moveableObjects;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author lukstankovic
 */
public class Player extends MovableObject implements EventHandler<KeyEvent>{

	private boolean moveTop = false;
	private boolean moveBottom = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	
	public Player(double positionX, double positionY) {
		super(positionX, positionY);
	}

	@Override
	public void updatePosition() {
		if (moveTop) {
			positionY -= speed;
			angle = 0;
		} else if (moveBottom) {
			positionY += speed;
			angle = 0;
		} else if (moveLeft) {
			positionX -= speed;
			angle = 0;
		} else if (moveRight) {
			positionX += speed;
			angle = 0;
		}
	}
	
		@Override
	public void handle(KeyEvent t) {
		if (t.getEventType() == KeyEvent.KEY_PRESSED) {
			switch (t.getCode()) {
				case UP: moveTop = true; break;
				case DOWN: moveBottom = true; break;
				case LEFT: moveLeft = true; break;
				case RIGHT: moveRight = true; break;
			}
		} else if (t.getEventType() == KeyEvent.KEY_RELEASED) {
			switch (t.getCode()) {
				case UP: moveTop = false; break;
				case DOWN: moveBottom = false; break;
				case LEFT: moveLeft = false; break;
				case RIGHT: moveRight = false; break;
			}
		}
	}
}
