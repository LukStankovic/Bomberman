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
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author lukstankovic
 */
public class Bomberman extends Application {

	private final int sizeOfCanvasX = 714;

	private final int sizeOfCanvasY = 714;

	private Drawer drawer;

	private Map map;

	private Player player;

	private GraphicsContext movableObjects;

	private GraphicsContext bombGc;
	
	private GraphicsContext explosions;
	
	private GraphicsContext undestroyableBlocks;

	@Override
	public void start(Stage primaryStage) {
		/*
		GenerateMap gm = new GenerateMap();	
		for (int i = 0; i < 101; i++) {
			gm.generate(21, 21, i);
		}
		*/
		map = new Map();
		Random rand = new Random();
		int mapId = rand.nextInt(99);
		Loader loader = new Loader();
		loader.loadMap("./data/maps/map_" + mapId + ".map", map);
		
		player = new Player(37, 37, map);
		map.addMovableObject(player);

		map.generateEnemies(5);

		StackPane root = new StackPane();
		drawer = new Drawer();

		Canvas unDestroyabelBlocksCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		undestroyableBlocks = unDestroyabelBlocksCanvas.getGraphicsContext2D();
		drawer.updateUndestroyableBlocks(sizeOfCanvasX, sizeOfCanvasY, map, undestroyableBlocks);

		Canvas movableObjectsCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		movableObjects = movableObjectsCanvas.getGraphicsContext2D();

		Canvas bombCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		bombGc = bombCanvas.getGraphicsContext2D();

		Canvas explosionsCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		explosions = explosionsCanvas.getGraphicsContext2D();
		
		root.getChildren().addAll(unDestroyabelBlocksCanvas, movableObjectsCanvas, bombCanvas, explosionsCanvas);

		Scene scene = new Scene(root, sizeOfCanvasX, sizeOfCanvasY);
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
				drawer.updateBomb(sizeOfCanvasX, sizeOfCanvasY, map, bombGc);
				drawer.updateExplosions(sizeOfCanvasX, sizeOfCanvasY, map, explosions, undestroyableBlocks);
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
