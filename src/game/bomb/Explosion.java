/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.bomb;

import game.map.Map;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author lukstankovic
 */
public class Explosion {

	private ArrayList<Point> explodedPositions;

	private long activatedAt = Long.MAX_VALUE;
	
	private int duration = 3000;

	public Explosion(ArrayList<Point> explodedPositions) {
		this.explodedPositions = explodedPositions;
		this.activatedAt = System.currentTimeMillis();
	}

	public ArrayList<Point> getExplodedPositions() {
		return explodedPositions;
	}


	public boolean isActive() {
		long activeFor = System.currentTimeMillis() - activatedAt;
		return activeFor >= 0 && activeFor <= duration;
	}
	
}
