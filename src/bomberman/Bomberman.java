/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import game.map.GenerateMap;
import game.map.Map;
import game.map.drawer.Drawer;
import game.map.loader.Loader;
import game.map.moveableObjects.Player;
import game.map.undestroyableBlock.Block;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 *
 * @author lukstankovic
 */
public class Bomberman extends Application {

	private final int sizeOfCanvasX = 700;
	
	private final int sizeOfCanvasY = 700;
	
	private Drawer drawer;
	
	private Map map;
	
	private Player player;
	
	private GraphicsContext  movableObjects;
			
	
	@Override
	public void start(Stage primaryStage) {
		
		GenerateMap gm = new GenerateMap();	
		//for (int i = 6; i < 100; i++) {
			gm.generate(25, 25, 0);
	//	}
				
		
		map = new Map();
		Random rand = new Random();
		int mapId = rand.nextInt(99);
		Loader loader = new Loader();
		loader.loadMap("./data/maps/map_" + mapId + ".map", map);
		//map.generateBricks();
		player = new Player(30, 30, map);
		map.addMovableObject(player);
	
		StackPane root = new StackPane();
		drawer = new Drawer();
		
		Canvas unDestroyabelBlocksCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		drawer.updateUndestroyableBlocks(sizeOfCanvasX, sizeOfCanvasY, map, unDestroyabelBlocksCanvas.getGraphicsContext2D());
		
		Canvas movableObjectsCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		movableObjects = movableObjectsCanvas.getGraphicsContext2D();
		
		root.getChildren().addAll(unDestroyabelBlocksCanvas, movableObjectsCanvas);
		
		Scene scene = new Scene(root, sizeOfCanvasX + 200, sizeOfCanvasY);
		scene.setOnKeyPressed(player);
		scene.setOnKeyReleased(player);
		primaryStage.setTitle("Bomberman");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				map.updateMap();
				drawer.updateMovableObjects(sizeOfCanvasX, sizeOfCanvasY, map, movableObjects);
			}
		};
				
		timer.start();

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
