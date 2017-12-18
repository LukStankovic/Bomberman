/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.moveableObjects;

import game.map.Map;
import java.util.Random;

/**
 *
 * @author lukstankovic
 */
public class Enemy extends MovableObject {

	private Map map;
	
	public Enemy(double positionX, double positionY, Map map) {
		super(positionX, positionY);
		this.map = map;
	}

	@Override
	public void updatePosition() {
		
	}
}
