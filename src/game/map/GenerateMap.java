/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lukstankovic
 */
public class GenerateMap {
	
	
	public void generateBaseMap(int width, int height) {	
		int[][] map = new int[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i == 0 || j == 0 || i == height -1 || j == width -1) {
					map[i][j] = 1; 
				} else {
					if (i % 2 == 0 && j % 2 == 0) {
						map[i][j] = 1;
					} else {
						map[i][j] = 0;
					}
				}
			}
		}
		
		this.save(map, width, height, "./data/maps/base.map");
	}
	
	
	public void generate(int width, int height, int id) {
		int[][] map = new int[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i == 0 || j == 0 || i == height -1 || j == width -1) {
					map[i][j] = 1; 
				} else {
					if (i % 2 == 0 && j % 2 == 0) {
						map[i][j] = 1;
					} else {
						Random rand = new Random();
						
						if (rand.nextInt(4) == 1 && i != 1 && j != 1) {
							map[i][j] = 2;
						} else {
							map[i][j] = 0;
						}
					}
				}
			}
		}
		this.save(map, width, height, "./data/maps/map_" + id + ".map");
	}

	private void save(int[][] map, int width, int height, String filePath) {
		PrintWriter in = null;
		
		try {
			in = new PrintWriter(new FileWriter(filePath));
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					in.print(map[i][j]);
					in.print(" ");
				}
				in.println();
			}
		} catch (IOException ex) {
			Logger.getLogger(GenerateMap.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
	
}

