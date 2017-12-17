/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.map.moveableObjects.MovableObject;
import game.map.moveableObjects.MovableObjects;
import game.map.undestroyableBlock.Block;
import game.map.undestroyableBlock.UndestroyableBlocks;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author lukstankovic
 */
public class Map {
	
	private int sizeOfMap;
	
	private UndestroyableBlocks undestroyableBlocks;
	
	private MovableObjects movableObjects;
		
	private int maxBricks = 30;
	
	private int sizeOfCanvas = 700;
	
	public Map(int sizeOfMap) {
		this.sizeOfMap = sizeOfMap;
		undestroyableBlocks = new UndestroyableBlocks(sizeOfMap);
		movableObjects = new MovableObjects();
	}

	public Map() {
		this(25);
	}

	public int getSizeOfMap() {
		return sizeOfMap;
	}
	
	public void addBlockIntoMap(int x, int y, int type) {
		switch (type) {
			case 0: undestroyableBlocks.setBlockAtPosition(x, y, Block.GRASS);break;
			case 1: undestroyableBlocks.setBlockAtPosition(x, y, Block.WALL);break;
			case 2: undestroyableBlocks.setBlockAtPosition(x, y, Block.BRICK);break;
		}
	}
	
	public void addMovableObject(MovableObject movableObject) {
		movableObjects.addMoveableObject(movableObject);
	}
	
	public ArrayList<MovableObject> getMovableObjects() {
		return movableObjects.getObjects();
	}
	
	public void updateMap() {
		movableObjects.updatePositionOfAllObjects(this);
	}
	
	
	public Block[][] getUndestroyableBlocks() {
		return undestroyableBlocks.getBlocks();
	}

	public Block getAtPosition(int x, int y) {
		return undestroyableBlocks.getBlockAtPosition(x, y);
	}
	
	public boolean isColliding(MovableObject mo, int direction) {
		double sizeOfObject = (double)(sizeOfCanvas / sizeOfMap) - 8;
		if (direction == 1) {// UP
			int positionOnBoardXLeft = (int)(mo.getPositionX()) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardYLeft = (int)(mo.getPositionY() - 2) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardXRight = (int)(mo.getPositionX() + sizeOfObject) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardYRight = (int)(mo.getPositionY() - 2) / (sizeOfCanvas / getSizeOfMap());
			return (getAtPosition(positionOnBoardYLeft, positionOnBoardXLeft) != Block.GRASS || getAtPosition(positionOnBoardYRight, positionOnBoardXRight) != Block.GRASS);
		} else if (direction == 2) { // DOWN
			int positionOnBoardXLeft = (int)(mo.getPositionX()) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardYLeft = (int)(mo.getPositionY() - sizeOfObject) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardXRight = (int)(mo.getPositionX() + sizeOfObject) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardYRight = (int)(mo.getPositionY() - sizeOfObject) / (sizeOfCanvas / getSizeOfMap());

			return (getAtPosition(positionOnBoardYLeft, positionOnBoardXLeft) != Block.GRASS || getAtPosition(positionOnBoardYRight, positionOnBoardXRight) != Block.GRASS);
		}
		
		return false;
	}
	
	public void generateBricks() {
		int bricksCount = 0;
		Random rand = new Random();
		while (bricksCount <= maxBricks) {
			for (int i = 1; i < getSizeOfMap(); i++) {
				for (int j = 1; j < getSizeOfMap(); j++) {
					if (getAtPosition(i, j) == Block.GRASS) {
						if (rand.nextInt(5) == 2) {
							addBlockIntoMap(i, j, 2);
							bricksCount++;
						}
					}
				}
			}
		}
		for (int i = 0; i < getSizeOfMap(); i++) {
			for (int j = 0; j < getSizeOfMap(); j++) {
				if (null != getAtPosition(i, j)) switch (getAtPosition(i, j)) {
					case GRASS:
						System.out.print("-");
						break;
					case WALL:
						System.out.print("X");
						break;
					case BRICK:
						System.out.print("!");
						break;
					default:
						break;
				}
			} System.out.println("");
		}
	}
}
