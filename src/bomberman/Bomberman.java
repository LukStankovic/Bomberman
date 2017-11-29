/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import game.map.Map;
import game.map.loader.Loader;
import java.awt.Image;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author lukstankovic
 */
public class Bomberman extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Map map = new Map();
		Loader loader = new Loader();
		loader.LoadMap("./data/maps/base.map", map);
	
		
		
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
