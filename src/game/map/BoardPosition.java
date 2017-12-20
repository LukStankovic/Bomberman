package game.map;

import game.map.moveableObjects.Direction;

/**
 *
 * @author lukstankovic
 */
public class BoardPosition {
	private int x1;
	private int y1;
	
	private int x2;
	private int y2;
	
	private double positionX;
	private double positionY;
	
	private Map map;
	
	private Direction direction;

	public BoardPosition(double positionX, double positionY, Map map, Direction direction) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.map = map;
		this.direction = direction;
		this.positions(direction);
	}
	
	private void positions(Direction direction) {
		switch (direction) {
			case UP: up();break;
			case DOWN: down();break;
			case LEFT: left();break;
			case RIGHT: right();break;
			default: none();break;
		}
	}
	
	private void up() {
		x1 = map.getPositionOnMap(positionX);
		y1 = map.getPositionOnMap(positionY);
		x2 = map.getPositionOnMap(positionX + map.getSizeOfObject());
		y2 = map.getPositionOnMap(positionY);
	}
	
	private void down() {
		x1 = map.getPositionOnMap(positionX);
		y1 = map.getPositionOnMap(positionY + map.getSizeOfObject());
		x2 = map.getPositionOnMap(positionX + map.getSizeOfObject());
		y2 = map.getPositionOnMap(positionY + map.getSizeOfObject());
	}
	
	private void left() {
		x1 = map.getPositionOnMap(positionX);
		y1 = map.getPositionOnMap(positionY);
		x2 = map.getPositionOnMap(positionX);
		y2 = map.getPositionOnMap(positionY + map.getSizeOfObject());
	}
	
	private void right() {
		x1 = map.getPositionOnMap(positionX + map.getSizeOfObject());
		y1 = map.getPositionOnMap(positionY);
		x2 = map.getPositionOnMap(positionX + map.getSizeOfObject());
		y2 = map.getPositionOnMap(positionY + map.getSizeOfObject());
	}
	
	private void none() {
		x1 = map.getPositionOnMap(positionX + (map.getSizeOfObject() / 2));
		y1 = map.getPositionOnMap(positionY + (map.getSizeOfObject() / 2));
		x2 = map.getPositionOnMap(positionX + (map.getSizeOfObject() / 2));
		y2 = map.getPositionOnMap(positionY + (map.getSizeOfObject() / 2));
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}
	
}
