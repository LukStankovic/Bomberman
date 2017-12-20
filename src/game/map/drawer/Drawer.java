/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.drawer;

import game.bomb.Bomb;
import game.bomb.Explosion;
import game.bomb.Explosions;
import game.map.Map;
import game.map.moveableObjects.Enemy;
import game.map.moveableObjects.MovableObject;
import game.map.moveableObjects.Player;
import game.map.undestroyableBlock.Block;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author lukstankovic
 */
public class Drawer {

	private Image imgWall = new Image("file:./data/sprites/wall.png");

	private Image imgPlayer = new Image("file:./data/sprites/man.png");

	private Image imgBrick = new Image("file:./data/sprites/brick.png");

	private Image imgEnemy = new Image("file:./data/sprites/enemy.png");

	private Image imgBomb = new Image("file:./data/sprites/bomb.png");
	
	private Image imgExplosionCenter = new Image("file:./data/sprites/explosion_center.png");
	
	private Image imgExplosion = new Image("file:./data/sprites/explosion.png");

	
	/**
	 * Přenačte mapu
	 * @param sizeX
	 * @param sizeY
	 * @param map
	 * @param gc 
	 */
	public void updateUndestroyableBlocks(int sizeX, int sizeY, Map map, GraphicsContext gc) {
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, sizeY, sizeY);
		Block[][] blocks = map.getUndestroyableBlocks();
		double sizeOfBlockX = (double) sizeX / map.getSizeOfMap();
		double sizeOfBlockY = (double) sizeY / map.getSizeOfMap();
		for (int i = 0; i < map.getSizeOfMap(); i++) {
			for (int j = 0; j < map.getSizeOfMap(); j++) {
				if (blocks[i][j] == Block.WALL) {
					gc.drawImage(imgWall, i * sizeOfBlockX, j * sizeOfBlockY, sizeOfBlockX, sizeOfBlockY);
				} else if (blocks[j][i] == Block.BRICK) {
					gc.drawImage(imgBrick, i * sizeOfBlockX, j * sizeOfBlockY, sizeOfBlockX, sizeOfBlockY);
				}
			}
		}
	}

	/**
	 * Přenačte pohyblivé objekty (enemy, player)
	 * @param sizeX
	 * @param sizeY
	 * @param map
	 * @param gc 
	 */
	public void updateMovableObjects(int sizeX, int sizeY, Map map, GraphicsContext gc, GraphicsContext undestroyableBlocks) {
		ArrayList<MovableObject> objects = map.getMovableObjects();
		
		double sizeOfBlockX = (double) sizeX / map.getSizeOfMap();
		double sizeOfBlockY = (double) sizeY / map.getSizeOfMap();
	
		gc.clearRect(0, 0, sizeX, sizeY);
	
		for (Iterator<MovableObject> iterator = objects.iterator(); iterator.hasNext();) {
			MovableObject mo = iterator.next();
			ImageView iv = new ImageView();
			iv.setRotate(mo.getAngle());
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
				
			if (mo instanceof Player) {
				iv.setImage(imgPlayer);
			} else if (mo instanceof Enemy) {
				iv = new ImageView(imgEnemy);
			}	
			
			if (mo.isAlive()) {
				gc.drawImage(iv.snapshot(params, null), mo.getPositionX(), mo.getPositionY(), sizeOfBlockX - 10, sizeOfBlockY - 10);
			} else {
				if (mo instanceof Player) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Game Over");
					alert.setHeaderText("Game Over");
					alert.show();
					map.setGameOver(true);
				}
				map.removeObject(iterator);
				gc.clearRect(mo.getPositionX(), mo.getPositionY(), sizeOfBlockY, sizeOfBlockY);
				
			}
		}
	}

	/**
	 * Přenačte bombu
	 * @param sizeX
	 * @param sizeY
	 * @param map
	 * @param gc 
	 */
	public void updateBomb(int sizeX, int sizeY, Map map, GraphicsContext gc) {
		ArrayList<Bomb> bombs = map.getBombs();
		double sizeOfBlockX = (double) sizeX / map.getSizeOfMap();
		double sizeOfBlockY = (double) sizeY / map.getSizeOfMap();
				
		for (Iterator<Bomb> iterator = bombs.iterator(); iterator.hasNext();) {
			Bomb bomb = iterator.next();
			gc.clearRect(0, 0, sizeX, sizeY);

			if (bomb.isActive()) {
				ImageView iv = new ImageView(imgBomb);
				SnapshotParameters params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				gc.drawImage(iv.snapshot(params, null), bomb.getPositionX(), bomb.getPositionY(), sizeOfBlockX - 8, sizeOfBlockY - 8);
			} else {
				SnapshotParameters params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				gc.clearRect(bomb.getPositionX(), bomb.getPositionY(), sizeOfBlockX - 8, sizeOfBlockY - 8);
				map.addExplosion(bomb);
				map.setIsBombPlaced(iterator, false);
			}
		}

	}
	
	
	public void updateExplosions(int sizeX, int sizeY, Map map, GraphicsContext gc, GraphicsContext undestroyableBlocks) {
		ArrayList<Explosion> explosions = map.getExplosions();
		double sizeOfBlockX = (double) sizeX / map.getSizeOfMap();
		double sizeOfBlockY = (double) sizeY / map.getSizeOfMap();

		for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext();) {
			Explosion explosion = iterator.next();
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);

			if (explosion.isActive()) {
				ImageView iv = new ImageView(imgExplosion); 					
				
				for (Point explodedPosition : explosion.getExplodedPositions()) {
					gc.drawImage(iv.snapshot(params, null), map.getPositionFromMap(explodedPosition.x),  map.getPositionFromMap(explodedPosition.y), sizeOfBlockX - 5, sizeOfBlockY - 5);
				}

			} else {
				for (Point explodedPosition : explosion.getExplodedPositions()) {
					gc.clearRect(map.getPositionFromMap(explodedPosition.x),  map.getPositionFromMap(explodedPosition.y), sizeOfBlockX - 5, sizeOfBlockY - 5);
					Block[][] blocks = map.getUndestroyableBlocks();
					if (blocks[explodedPosition.y][explodedPosition.x] == Block.BRICK) {
						map.addBlockIntoMap(explodedPosition.y, explodedPosition.x, 0);
						updateUndestroyableBlocks(sizeX, sizeY, map, undestroyableBlocks);
					}
				}
				map.removeExplosion(iterator);
				updateUndestroyableBlocks(sizeX, sizeY, map, undestroyableBlocks);
			}
		}
	}
}
