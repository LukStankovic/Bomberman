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
	
	private Map map;
	
	public Player(double positionX, double positionY, Map map) {
		super(positionX, positionY);
		this.map = map;
	}

	@Override
	public void updatePosition() {
		//System.out.println(getPositionX() + " - " + getPositionY() + "bl: " + map.getAtPosition(positionOnBoardY, positionOnBoardX));
		
		if (moveTop) {
			/*int positionOnBoardX = (int)(getCenterPositionX()) / (700/map.getSizeOfMap());
			int positionOnBoardY = (int)(getCenterPositionY() - speed) / (700/map.getSizeOfMap());
			*/
			if (!map.isColliding(this, 1)) {
				positionY -= speed;
				angle = 0;
			}
		} else if (moveBottom) {
			int positionOnBoardX = (int)(getCenterPositionX()) / (700/map.getSizeOfMap());
			int positionOnBoardY = (int)(getCenterPositionY() + speed) / (700/map.getSizeOfMap());
			
			if (map.getAtPosition(positionOnBoardY, positionOnBoardX) == Block.GRASS) {
			
			//if (!map.isColliding(this, 2)) {
				positionY += speed;
				angle = 180;
			}
			
		} else if (moveLeft) {
			int positionOnBoardX = (int)(getCenterPositionX() - speed) / (700/map.getSizeOfMap());
			int positionOnBoardY = (int)(getCenterPositionY()) / (700/map.getSizeOfMap());
			
			if (map.getAtPosition(positionOnBoardY, positionOnBoardX) == Block.GRASS) {
				positionX -= speed;
				angle = 270;
			}
		} else if (moveRight) {
			int positionOnBoardX = (int)(getCenterPositionX() + speed) / (700/map.getSizeOfMap());
			int positionOnBoardY = (int)(getCenterPositionY()) / (700/map.getSizeOfMap());
			
			if (map.getAtPosition(positionOnBoardY, positionOnBoardX) == Block.GRASS) {
				positionX += speed;
				angle = 90;
			}
			
		}
	}
	
		@Override
	public void handle(KeyEvent t) {
		if (t.getEventType() == KeyEvent.KEY_PRESSED) {
		/*	
			System.out.println(
					getPositionX() + " - " + getPositionY() + ", p: " + positionOnBoardX + " - " + positionOnBoardY 
					+ map.getAtPosition(positionOnBoardX, positionOnBoardY)
			);
		*/	
			int positionOnBoardX;
			int positionOnBoardY;
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
/*

int positionOnBoardX = (int)(getPositionX()-5) / (700/map.getSizeOfMap());
			int positionOnBoardY = (int)(getPositionY()) / (700/map.getSizeOfMap());

*/
