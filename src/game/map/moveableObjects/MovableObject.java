package game.map.moveableObjects;

/**
 *
 * @author lukstankovic
 */
public abstract class MovableObject {
	protected double positionX;
	protected double positionY;
	protected double speed;
	protected double angle;
	protected boolean isAlive;
	
	protected Direction direction;

	public MovableObject(double positionX, double positionY) {
		this(positionX, positionY, 3, 0);
	}

	public MovableObject(double positionX, double positionY, double speed, double angle) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.speed = speed;
		this.angle = angle;
		this.isAlive = true;
		this.direction = Direction.NONE;
	}

	public Direction getDirection() {
		return direction;
	}

	public abstract void updatePosition();

	public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
	}
	
	public double getCenterPositionX() {
		return positionX + 12;
	}
	
	public double getCenterPositionY() {
		return positionY + 12;
	}

	public double getSpeed() {
		return speed;
	}

	public double getAngle() {
		return angle;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	
	
}
