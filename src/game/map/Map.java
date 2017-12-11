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
	
	public Map(int sizeOfMap) {
		this.sizeOfMap = sizeOfMap;
		undestroyableBlocks = new UndestroyableBlocks(sizeOfMap);
		movableObjects = new MovableObjects();
	}

	public Map() {
		this(27);
	}

	public int getSizeOfMap() {
		return sizeOfMap;
	}
	
	public void addBlockIntoMap(int x, int y, int type) {
		switch (type) {
			case 0: undestroyableBlocks.setBlockAtPosition(x, y, Block.GRASS);break;
			case 1: undestroyableBlocks.setBlockAtPosition(x, y, Block.WALL);break;
		}
	}
	
	public void addMovableObject(MovableObject movableObject) {
		movableObjects.addMoveableObject(movableObject);
	}
	
	public ArrayList<MovableObject> getMovableObjects() {
		return movableObjects.getObjects();
	}
	
	public void updateMap() {
		movableObjects.updatePositionOfAllObjects();
	}
	
	
	public Block[][] getUndestroyableBlocks() {
		return undestroyableBlocks.getBlocks();
	}

	public Block getAtPosition(int x, int y) {
		return undestroyableBlocks.getBlockAtPosition(x, y);
	}
	
	public void generateBricks() {
		Random rand = new Random();
		int bricksCount = rand.nextInt(50) + 20;
		for (int i = 0; i < getSizeOfMap(); i++) {
			for (int j = 0; j < getSizeOfMap(); j++) {
				if (getAtPosition(i, j) == null) {
					addBlockIntoMap(i, j, 2);
				}
			}
		}
	}
}
