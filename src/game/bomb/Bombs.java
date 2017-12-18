/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.bomb;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author lukstankovic
 */
public class Bombs {
	
	private ArrayList<Bomb> bombs;

	public Bombs() {
		this.bombs = new ArrayList<>();
	}
	
	public void addBomb(Bomb bomb) {
		bombs.add(bomb);
	}
	
	public void removeBomb(Bomb bomb) {
		bombs.remove(bomb);
	}
	
	public void removeBomb(Iterator<Bomb> iterator) {
		iterator.remove();
	}
	
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}
}
