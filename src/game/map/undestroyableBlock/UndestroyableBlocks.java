/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.undestroyableBlock;

/**
 *
 * @author lukstankovic
 */
public class UndestroyableBlocks {
	private int sizeOfMap;
	private Block blocks[][];

	public UndestroyableBlocks(int sizeOfMap) {
		this.sizeOfMap = sizeOfMap;
		blocks = new Block[sizeOfMap][sizeOfMap];
	}

	public Block[][] getBlocks() {
		return blocks;
	}
	
	public void setBlockAtPosition(int x, int y, Block blockType) {
		blocks[x][y] = blockType;
	}
}
