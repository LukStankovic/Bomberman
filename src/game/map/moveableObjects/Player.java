/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.moveableObjects;

import game.map.Map;
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

		if (map.isCollidingWithEnemy(positionX, positionY)) {
			isAlive = false;
			return;
		}
		
		if (map.isCollidingWithExplosion(positionX, positionY, this)) {
			isAlive = false;
			return;
		}
		
		if (moveTop && !map.isCollidingWithBlock(this, Direction.UP)) {
			positionY -= speed;
			angle = 0;
		} else if (moveBottom && !map.isCollidingWithBlock(this, Direction.DOWN)) {
			positionY += speed;
			angle = 180;
		} else if (moveLeft && !map.isCollidingWithBlock(this, Direction.LEFT)) {
			positionX -= speed;
			angle = 270;
		} else if (moveRight && !map.isCollidingWithBlock(this, Direction.RIGHT)) {
			positionX += speed;
			angle = 90;
		} 
		
		if (placeBomb && !map.isIsBombPlaced()) {
			map.placeBomb(this);
		}
	}
	
		@Override
	public void handle(KeyEvent t) {
		if (t.getEventType() == KeyEvent.KEY_PRESSED) {
			switch (t.getCode()) {
				case UP: 
					moveTop = true; 
					direction = Direction.UP; 
					break;
				case DOWN: 
					moveBottom = true; 
					direction = Direction.DOWN;
					break;
				case LEFT: 
					moveLeft = true; 
					direction = Direction.LEFT;
					break;
				case RIGHT: 
					moveRight = true;
					direction = Direction.RIGHT;
					break;
				case SPACE: 
					placeBomb = true; 
					break;
			}
		} else if (t.getEventType() == KeyEvent.KEY_RELEASED) {
			switch (t.getCode()) {
				case UP: moveTop = false;break;
				case DOWN: moveBottom = false; break;
				case LEFT: moveLeft = false; break;
				case RIGHT: moveRight = false; break;
				case SPACE: placeBomb = false; break;
			}
			direction = Direction.NONE;
		}
	}

}