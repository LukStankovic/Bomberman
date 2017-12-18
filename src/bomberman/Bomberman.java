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

	private final int sizeOfCanvasX = 700;

	private final int sizeOfCanvasY = 700;

	private Drawer drawer;

	private Map map;

	private Player player;

	private GraphicsContext movableObjects;

	private GraphicsContext bombGc;
	
	private GraphicsContext explosions;

	@Override
	public void start(Stage primaryStage) {
		/*
		GenerateMap gm = new GenerateMap();	
		for (int i = 0; i < 101; i++) {
			gm.generate(19, 19, i);
		}
		 */

		map = new Map();
		Random rand = new Random();
		int mapId = rand.nextInt(99);
		Loader loader = new Loader();
		loader.loadMap("./data/maps/map_" + mapId + ".map", map);
		player = new Player(40, 40, map);
		map.addMovableObject(player);

		map.generateEnemies(5);

		StackPane root = new StackPane();
		drawer = new Drawer();

		Canvas unDestroyabelBlocksCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		drawer.updateUndestroyableBlocks(sizeOfCanvasX, sizeOfCanvasY, map, unDestroyabelBlocksCanvas.getGraphicsContext2D());

		Canvas movableObjectsCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		movableObjects = movableObjectsCanvas.getGraphicsContext2D();

		Canvas bombCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		bombGc = bombCanvas.getGraphicsContext2D();

		Canvas explosionsCanvas = new Canvas(sizeOfCanvasX, sizeOfCanvasY);
		explosions = explosionsCanvas.getGraphicsContext2D();
		
		root.getChildren().addAll(unDestroyabelBlocksCanvas, movableObjectsCanvas, bombCanvas, explosionsCanvas);

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
				drawer.updateBomb(sizeOfCanvasX, sizeOfCanvasY, map, bombGc);
				drawer.updateExplosions(sizeOfCanvasX, sizeOfCanvasY, map, explosions);
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
