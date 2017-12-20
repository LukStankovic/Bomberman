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
import game.map.moveableObjects.Direction;
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
	private int sizeOfCanvas = 714;
	private double sizeOfObject;

	private UndestroyableBlocks undestroyableBlocks;
	private MovableObjects movableObjects = new MovableObjects();;
	private Bombs bombs = new Bombs();
	private Explosions explosions = new Explosions();

	private boolean isBombPlaced = false;	
	private boolean gameOver = false;
	
	private int score = 0;
	
	public Map(int sizeOfMap) {
		this.sizeOfMap = sizeOfMap;
		undestroyableBlocks = new UndestroyableBlocks(sizeOfMap);
		sizeOfObject = (double) (sizeOfCanvas / sizeOfMap) - 10;
	}

	public Map() {
		this(21);
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

	public boolean isColliding(MovableObject mo, Direction direction) {
		if (direction == Direction.UP) {
			int posOnBoardX1 = getPositionOnMap(mo.getPositionX());
			int posOnBoardY1 = getPositionOnMap(mo.getPositionY());
			int posOnBoardX2 = getPositionOnMap(mo.getPositionX() + sizeOfObject);
			int posOnBoardY2 = getPositionOnMap(mo.getPositionY());
			
			return (getAtPosition(posOnBoardY1, posOnBoardX1) != Block.GRASS || getAtPosition(posOnBoardY2, posOnBoardX2) != Block.GRASS); 
		} else if (direction == Direction.DOWN) {
			int posOnBoardX1 = getPositionOnMap(mo.getPositionX());
			int posOnBoardY1 = getPositionOnMap(mo.getPositionY() + sizeOfObject);
			int posOnBoardX2 = getPositionOnMap(mo.getPositionX() + sizeOfObject);
			int posOnBoardY2 = getPositionOnMap(mo.getPositionY() + sizeOfObject);

			return (getAtPosition(posOnBoardY1, posOnBoardX1) != Block.GRASS || getAtPosition(posOnBoardY2, posOnBoardX2) != Block.GRASS); 
		} else if (direction == Direction.LEFT) {
			int posOnBoardX1 = getPositionOnMap(mo.getPositionX());
			int posOnBoardY1 = getPositionOnMap(mo.getPositionY());
			int posOnBoardX2 = getPositionOnMap(mo.getPositionX());
			int posOnBoardY2 = getPositionOnMap(mo.getPositionY() + sizeOfObject);

			return (getAtPosition(posOnBoardY1, posOnBoardX1) != Block.GRASS || getAtPosition(posOnBoardY2, posOnBoardX2) != Block.GRASS); 
		} else if (direction == Direction.RIGHT) {
			int posOnBoardX1 = getPositionOnMap(mo.getPositionX() + sizeOfObject);
			int posOnBoardY1 = getPositionOnMap(mo.getPositionY());
			int posOnBoardX2 = getPositionOnMap(mo.getPositionX() + sizeOfObject);
			int posOnBoardY2 = getPositionOnMap(mo.getPositionY() + sizeOfObject);

			return (getAtPosition(posOnBoardY1, posOnBoardX1) != Block.GRASS || getAtPosition(posOnBoardY2, posOnBoardX2) != Block.GRASS); 
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
	
	public int getSizeOfMap() {
		return sizeOfMap;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void increaseScore(int type) {
		score += (type == 1 ? 10 : 100);
	}

	public int getScore() {
		return score;
	}
}
