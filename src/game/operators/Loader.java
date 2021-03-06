/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.operators;

import game.map.Map;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
/**
 *
 * @author lukstankovic
 */
public class Loader {
	public void loadMap(String fileMap, Map map) {
		Scanner sc = null;
		
		try {
			sc = new Scanner(new BufferedReader(new FileReader(fileMap)));
			for (int i = 0; i < map.getSizeOfMap(); i++) {
				for (int j = 0; j < map.getSizeOfMap(); j++) {
					int tmp = sc.nextInt();
					map.addBlockIntoMap(i, j, tmp);
				}
			}
		} catch (FileNotFoundException ex) {
			System.err.println("File with map was not found!");
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		
	}
}
