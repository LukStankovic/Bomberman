/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.destroyableblock;

import game.map.undestroyableBlock.Block;

/**
 *
 * @author lukstankovic
 */
public class DestroyableBlocks {
	private int sizeOfMap;
	private Block blocks[][];

	public DestroyableBlocks(int sizeOfMap) {
		this.sizeOfMap = sizeOfMap;
		blocks = new Block[sizeOfMap][sizeOfMap];
	}

	public Block[][] getBlocks() {
		return blocks;
	}
	
	public Block getBlockAtPosition(int x, int y) {
		return blocks[x][y];
	}
	
	public void setBlockAtPosition(int x, int y, Block blockType) {
		blocks[x][y] = blockType;
	}
}
