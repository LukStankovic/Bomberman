package game.bomb;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author lukstankovic
 */
public class Explosions {
	
	private ArrayList<Explosion> explosions;

	public Explosions() {
		this.explosions = new ArrayList<>();
	}
	
	public void addExplosion(Explosion explosion) {
		explosions.add(explosion);
	}
	
	public void removeExplosion(Explosion explosion) {
		explosions.remove(explosion);
	}
	
	public void removeExplosion(Iterator<Explosion> iterator) {
		iterator.remove();
	}
	
	public ArrayList<Explosion> getExplosions() {
		return explosions;
	}
}
