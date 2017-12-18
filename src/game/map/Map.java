/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import game.bomb.Bomb;
import game.bomb.Bombs;
import game.bomb.Explosion;
import game.bomb.Explosions;
import game.map.moveableObjects.Enemy;
import game.map.moveableObjects.MovableObject;
import game.map.moveableObjects.MovableObjects;
import game.map.undestroyableBlock.Block;
import game.map.undestroyableBlock.UndestroyableBlocks;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author lukstankovic
 */
public class Map {

	private int sizeOfMap;

	private UndestroyableBlocks undestroyableBlocks;

	private MovableObjects movableObjects;

	private Bombs bombs;

	private boolean isBombPlaced = false;

	private int maxBricks = 30;

	private int sizeOfCanvas = 700;

	private Explosions explosions;
	
	public Map(int sizeOfMap) {
		this.sizeOfMap = sizeOfMap;
		undestroyableBlocks = new UndestroyableBlocks(sizeOfMap);
		movableObjects = new MovableObjects();
		bombs = new Bombs();
		explosions = new Explosions();
	}

	public Map() {
		this(19);
	}

	public int getSizeOfMap() {
		return sizeOfMap;
	}

	public void addBlockIntoMap(int x, int y, int type) {
		switch (type) {
			case 0:
				undestroyableBlocks.setBlockAtPosition(x, y, Block.GRASS);
				break;
			case 1:
				undestroyableBlocks.setBlockAtPosition(x, y, Block.WALL);
				break;
			case 2:
				undestroyableBlocks.setBlockAtPosition(x, y, Block.BRICK);
				break;
		}
	}

	public void addMovableObject(MovableObject movableObject) {
		movableObjects.addMoveableObject(movableObject);
	}

	public ArrayList<MovableObject> getMovableObjects() {
		return movableObjects.getObjects();
	}

	public ArrayList<Bomb> getBombs() {
		return bombs.getBombs();
	}

	public ArrayList<Explosion> getExplosions() {
		return explosions.getExplosions();
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
		double sizeOfObject = (double) (sizeOfCanvas / sizeOfMap) - 8;
		if (direction == 1) {// UP
			/*int positionOnBoardXLeft = (int) (mo.getPositionX()) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardYLeft = (int) (mo.getPositionY() - 2) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardXRight = (int) (mo.getPositionX() + sizeOfObject) / (sizeOfCanvas / getSizeOfMap());
			int positionOnBoardYRight = (int) (mo.getPositionY() - 2) / (sizeOfCanvas / getSizeOfMap());

			
			return (getAtPosition(positionOnBoardYLeft, positionOnBoardXLeft) != Block.GRASS || getAtPosition(positionOnBoardYRight, positionOnBoardXRight) != Block.GRASS); 
*/
			int positionOnBoardX = (int) (mo.getCenterPositionX()) / (700 / getSizeOfMap());
			int positionOnBoardY = (int) (mo.getCenterPositionY() - mo.getSpeed()) / (700 / getSizeOfMap());
			
			return getAtPosition(positionOnBoardY, positionOnBoardX) != Block.GRASS;
		} else if (direction == 2) { // DOWN
			int positionOnBoardX = (int) (mo.getCenterPositionX()) / (700 / getSizeOfMap());
			int positionOnBoardY = (int) (mo.getCenterPositionY() + mo.getSpeed()) / (700 / getSizeOfMap());

			return getAtPosition(positionOnBoardY, positionOnBoardX) != Block.GRASS;
		} else if (direction == 3) { //LEFT
			int positionOnBoardX = (int) (mo.getCenterPositionX() - mo.getSpeed()) / (700 / getSizeOfMap());
			int positionOnBoardY = (int) (mo.getCenterPositionY()) / (700 / getSizeOfMap());

			return getAtPosition(positionOnBoardY, positionOnBoardX) != Block.GRASS;
		} else if (direction == 4) { // RIGT
			int positionOnBoardX = (int) (mo.getCenterPositionX() + mo.getSpeed()) / (700 / getSizeOfMap());
			int positionOnBoardY = (int) (mo.getCenterPositionY()) / (700 / getSizeOfMap());

			return getAtPosition(positionOnBoardY, positionOnBoardX) != Block.GRASS;
		}

		return false;
	}

	public boolean isColidingWithEnemy(double posX, double posY) {
		int positionOnBoardX = (int) (posX) / (700 / getSizeOfMap());
		int positionOnBoardY = (int) (posY) / (700 / getSizeOfMap());

		for (MovableObject mo : getMovableObjects()) {
			if (mo instanceof Enemy) {
				int moPositionOnBoardX = (int) (mo.getCenterPositionX() + mo.getSpeed()) / (700 / getSizeOfMap());
				int moPositionOnBoardY = (int) (mo.getCenterPositionY()) / (700 / getSizeOfMap());
				if (moPositionOnBoardX == positionOnBoardX && moPositionOnBoardY == positionOnBoardY) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isColidingWithExplosion(double posX, double posY) {
		int positionOnBoardX = (int) (posX) / (sizeOfCanvas / getSizeOfMap());
		int positionOnBoardY = (int) (posY) / (sizeOfCanvas / getSizeOfMap());

		for (Explosion ex : getExplosions()) {
			for (Point explodedPosition : ex.getExplodedPositions()) {
				if (explodedPosition.x == positionOnBoardX && explodedPosition.y == positionOnBoardY) {
					return true;
				}
			}
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
	}

	public void generateEnemies(int enemies) {
		Random rand = new Random();
		int enemiesCount = 0;
		while (enemiesCount <= enemies) {
			for (int i = 0; i < getSizeOfMap(); i++) {
				for (int j = 0; j < getSizeOfMap(); j++) {
					if (getAtPosition(i, j) == Block.GRASS && i != 1 && j != 1 && enemiesCount <= enemies && rand.nextInt(50) == 5) {
						addEnemyIntoMap(j, i);
						enemiesCount++;
					}
				}
			}
		}
	}

	private void addEnemyIntoMap(int x, int y) {
		Enemy enemy = new Enemy((x * (sizeOfCanvas / sizeOfMap)) + 10, (y * (sizeOfCanvas / sizeOfMap)) + 10, this);
		addMovableObject(enemy);
	}
	
	public void removeObject(Iterator<MovableObject> iterator) {
		movableObjects.removeObject(iterator);
	}

	public boolean isIsBombPlaced() {
		return isBombPlaced;
	}

	public void setIsBombPlaced(Iterator<Bomb> iterator, boolean isBombPlaced) {
		if (!isBombPlaced) {
			bombs.removeBomb(iterator);
		}
		this.isBombPlaced = isBombPlaced;
	}

	public void placeBomb(MovableObject player) {
		int positionOnBoardX = (int) (player.getCenterPositionX()) / (sizeOfCanvas / getSizeOfMap());
		int positionOnBoardY = (int) (player.getCenterPositionY()) / (sizeOfCanvas / getSizeOfMap());
		System.out.println(positionOnBoardX + " - " + positionOnBoardY);
		bombs.addBomb(
				new Bomb(
						(positionOnBoardX * ((double) sizeOfCanvas / (double) sizeOfMap)) + 5, 
						(positionOnBoardY * ((double) sizeOfCanvas / (double) sizeOfMap)) + 5,
						positionOnBoardX,
						positionOnBoardY
				)
		);
		isBombPlaced = true;
	}
	
	public void addExplosion(Bomb bomb) {
		explosions.addExplosion(new Explosion(bomb.getExplodedPoints()));
	}
	
	public void removeExplosion(Iterator<Explosion> iterator) {
		explosions.removeExplosion(iterator);
	}
	
	public int getPositionOnMap(double pos) {
		return (int)((pos)/(sizeOfCanvas/getSizeOfMap() ));
	}
	
	public double getPositionFromMap(int pos) {
		return (pos * ((double) sizeOfCanvas / (double) sizeOfMap)) + 5;
	}
	
}
