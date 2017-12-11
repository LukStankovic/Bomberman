/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.drawer;

import game.map.Map;
import game.map.moveableObjects.MovableObject;
import game.map.moveableObjects.Player;
import game.map.undestroyableBlock.Block;
import java.util.ArrayList;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
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

	public void updateUndestroyableBlocks(int sizeX, int sizeY, Map map, GraphicsContext gc) {
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, sizeY, sizeY);
		Block[][] blocks = map.getUndestroyableBlocks();
		double sizeOfBlockX = (double)sizeX/map.getSizeOfMap();
		double sizeOfBlockY = (double)sizeY/map.getSizeOfMap();
		
		for (int i = 0; i < map.getSizeOfMap(); i++) {
			for (int j = 0; j < map.getSizeOfMap(); j++) {
				if (blocks[i][j] == Block.WALL) {
					gc.drawImage(imgWall, i*sizeOfBlockX, j*sizeOfBlockY, sizeOfBlockX, sizeOfBlockY);
				}
			}
		}
	}
	
	public void updateMovableObjects(int sizeX, int sizeY, Map map, GraphicsContext gc) {
		gc.clearRect(0, 0, sizeX, sizeY);
		ArrayList<MovableObject> objects = map.getMovableObjects();
		double sizeOfBlockX = (double)sizeX/map.getSizeOfMap();
		double sizeOfBlockY = (double)sizeY/map.getSizeOfMap();
		
		for (MovableObject mo : objects) {
			if (mo instanceof Player) {
				ImageView iv = new ImageView(imgPlayer);
				iv.setRotate(mo.getAngle());
				SnapshotParameters params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				gc.drawImage(iv.snapshot(params, null), mo.getPositionX() + 2, mo.getPositionY() + 22, sizeOfBlockX - 4, sizeOfBlockY - 4);
			}
		}
		
	}
	
}
