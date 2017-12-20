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
public class Bomb {

	private double positionX;
	private double positionY;

	private int positionOnMapX;
	private int positionOnMapY;
	
	private long activatedAt = Long.MAX_VALUE;
	
	private int duration = 3000;
	
	Map map;

	public Bomb(double positionX, double positionY, int positionOnMapX, int positionOnMapY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.positionOnMapX = positionOnMapX;
		this.positionOnMapY = positionOnMapY;
		this.activatedAt = System.currentTimeMillis();
	}

	public int getDuration() {
		return duration;
	}

	public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public boolean isActive() {
		long activeFor = System.currentTimeMillis() - activatedAt;
		return activeFor >= 0 && activeFor <= duration;
	}

	 public ArrayList<Point> getExplodedPoints() {
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(positionOnMapX, positionOnMapY)); // BOMB
		points.add(new Point(positionOnMapX, positionOnMapY + 1)); // TOP
		points.add(new Point(positionOnMapX, positionOnMapY - 1)); // BOTTOM
		points.add(new Point(positionOnMapX - 1, positionOnMapY)); // LEFT
		points.add(new Point(positionOnMapX + 1, positionOnMapY)); // RIGHT

		return points;
	}
}
