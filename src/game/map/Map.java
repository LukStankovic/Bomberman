/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import exceptions.WrongSizeOfMapException;
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
	private MovableObjects movableObjects = new MovableObjects();
	;
	private Bombs bombs = new Bombs();
	private Explosions explosions = new Explosions();

	private boolean isBombPlaced = false;
	private boolean gameOver = false;

	private int score = 0;
	private int enemyKilled = 0;
	private int bricksDestroyed = 0;

	public Map(int sizeOfMap) {
		if (sizeOfMap < 5 || sizeOfMap % 2 == 0) {
			throw new WrongSizeOfMapException();
		}
		this.sizeOfMap = sizeOfMap;
		undestroyableBlocks = new UndestroyableBlocks(sizeOfMap);
		sizeOfObject = (double) (sizeOfCanvas / sizeOfMap) - 10;
	}

	public Map() {
		this(21);
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

	public boolean isCollidingWithBlock(MovableObject mo, Direction direction) {
		BoardPosition bp = new BoardPosition(mo.getPositionX(), mo.getPositionY(), this, direction);
		return getAtPosition(bp.getY1(), bp.getX1()) != Block.GRASS || getAtPosition(bp.getY2(), bp.getX2()) != Block.GRASS;
	}

	public boolean isCollidingWithEnemy(double posX, double posY) {
		for (MovableObject mo : getMovableObjects()) {
			if (mo instanceof Enemy) {
				int moPositionOnBoardX = getPositionOnMap(mo.getCenterPositionX() + mo.getSpeed());
				int moPositionOnBoardY = getPositionOnMap(mo.getCenterPositionY());
				if (moPositionOnBoardX == getPositionOnMap(posX) && moPositionOnBoardY == getPositionOnMap(posY)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCollidingWithExplosion(double posX, double posY, MovableObject mo) {
		for (Explosion ex : getExplosions()) {
			for (Point explodedPosition : ex.getExplodedPositions()) {
				if (explodedPosition.x == getPositionOnMap(posX) && explodedPosition.y == getPositionOnMap(posY)) {
					if (mo instanceof Enemy) {
						increaseScore(2);
					}
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCollidingWithBomb(double posX, double posY) {
		for (Bomb bomb : getBombs()) {
			if (getPositionOnMap(bomb.getPositionX()) == getPositionOnMap(posX) && getPositionOnMap(bomb.getPositionY()) == getPositionOnMap(posY)) {
				return true;
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
		return (int) ((pos) / (sizeOfCanvas / getSizeOfMap()));
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

	/**
	 * Zvýší skoŕe podle typu: 1 - blok, 2 - enemy
	 *
	 * @param type
	 */
	public void increaseScore(int type) {
		switch (type) {
			case 1: score += 10; bricksDestroyed++;break;
			case 2: score += 100; enemyKilled++;break;
		}
	}

	public int getScore() {
		return score;
	}

	public double getSizeOfObject() {
		return sizeOfObject;
	}
	
	public int getEnemyKilled() {
		return enemyKilled;
	}

	public int getBricksDestroyed() {
		return bricksDestroyed;
	}

}
