/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.moveableObjects;

import game.map.Map;
import game.map.undestroyableBlock.Block;
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
	
	private boolean placeBomb = false;
	
	private boolean isAlive = true;
	
	private Map map;
	
	public Player(double positionX, double positionY, Map map) {
		super(positionX, positionY);
		this.map = map;
	}

	@Override
	public void updatePosition() {
		if (!isAlive) {
			return;
		}
		
		if (map.isColidingWithEnemy(positionX, positionY)) {
			isAlive = false;
			return;
		}
		
		if (map.isColidingWithExplosion(positionX, positionY)) {
			isAlive = false;
			return;
		}
		
		if (moveTop) {
			
			if (!map.isColliding(this, 1)) {
				positionY -= speed;
				angle = 0;
			}
		} else if (moveBottom) {
			if (!map.isColliding(this, 2)) {
				positionY += speed;
				angle = 180;
			}
		} else if (moveLeft) {
			if (!map.isColliding(this, 3)) {
				positionX -= speed;
				angle = 270;
			}
		} else if (moveRight) {
			if (!map.isColliding(this, 4)) {
				positionX += speed;
				angle = 90;
			}
		} else if (placeBomb && !map.isIsBombPlaced()) {
			map.placeBomb(this);
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
				case SPACE: placeBomb = true; break;
			}
		} else if (t.getEventType() == KeyEvent.KEY_RELEASED) {
			switch (t.getCode()) {
				case UP: moveTop = false; break;
				case DOWN: moveBottom = false; break;
				case LEFT: moveLeft = false; break;
				case RIGHT: moveRight = false; break;
				case SPACE: placeBomb = false; break;
			}
		}
	}
}