/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.moveableObjects;

import game.map.Map;
import game.map.undestroyableBlock.Block;
import java.util.ArrayList;

/**
 *
 * @author lukstankovic
 */
public class MovableObjects {
	private ArrayList<MovableObject> objects;

	public MovableObjects() {
		objects = new ArrayList<>();
	}
	
	public void addMoveableObject(MovableObject object) {
		objects.add(object);
	}
	
	public void updatePositionOfAllObjects(Map map) {
		for (MovableObject mo : objects) {
			mo.updatePosition();
		}
	}

	public ArrayList<MovableObject> getObjects() {
		return objects;
	}
}
