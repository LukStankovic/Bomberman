/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.map.undestroyableBlock.Block;
import game.map.undestroyableBlock.UndestroyableBlocks;

/**
 *
 * @author lukstankovic
 */
public class Map {
	private int sizeOfMap;
	private UndestroyableBlocks undestroyableBlocks;
	
	public Map(int sizeOfMap) {
		this.sizeOfMap = sizeOfMap;
		undestroyableBlocks = new UndestroyableBlocks(sizeOfMap);
	}

	public Map() {
		this(21);
	}

	public int getSizeOfMap() {
		return sizeOfMap;
	}
	
	public void addBlockIntoMap(int x, int y, int type) {
		switch (type) {
			case 0: undestroyableBlocks.setBlockAtPosition(x, y, Block.ROAD);break;
			case 1: undestroyableBlocks.setBlockAtPosition(x, y, Block.CONCRETE);break;
		}
	}
	
}
