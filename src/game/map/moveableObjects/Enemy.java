/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.moveableObjects;

import game.map.Map;
import java.util.Random;

/**
 *
 * @author lukstankovic
 */
public class Enemy extends MovableObject {

	private Map map;
	
	private Direction direction;
	
	private boolean start;
	
	private Random rand = new Random();
	
	public Enemy(double positionX, double positionY, Map map) {
		super(positionX, positionY);
		this.map = map;
		this.direction = getDirectionFromInt(rand.nextInt(4) + 1);
		this.start = true;
	}

	@Override
	public void updatePosition() {
		if (map.isColidingWithExplosion(positionX, positionY)) {
			isAlive = false;
			return;
		}

		if (map.isColliding(this, direction)) {
			positionCorrection();
			direction = getDirectionFromInt(rand.nextInt(4) + 1);
			return;
		}

		switch (direction) {
			case UP: positionY -= speed;break;
			case DOWN: positionY += speed;break;
			case LEFT: positionX -= speed;break;
			case RIGHT: positionX += speed;break;
		}
	}
	
	private Direction getDirectionFromInt(int direction) {
		switch (direction) {
			case 1: return Direction.UP;
			case 2: return Direction.DOWN;
			case 3: return Direction.LEFT;
			case 4: return Direction.RIGHT;
			default: return Direction.NONE;
		}
	}
	
	private void positionCorrection() {
		switch (direction) {
			case UP: positionY += speed;break;
			case DOWN: positionY -= speed;break;
			case LEFT: positionX += speed;break;
			case RIGHT: positionX -= speed;break;
		}
	}
	
}
