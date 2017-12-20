/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.operators;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author lukstankovic
 */
public class Writer {
	
	String fileName;

	public Writer(String fileName) {
		this.fileName = fileName;
	}
	
	public void saveScore(int score, int destroyed, int killed) {
		PrintWriter in = null;
		String timeStamp = new SimpleDateFormat("dd. MM. yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

		try {
			in = new PrintWriter(new FileWriter(fileName, true));
			in.append(timeStamp + ": " + score + " bodů, zničených bloků: " + destroyed + ", zabito nepřátel: " + killed + "\n");
		} catch (IOException ex) {
			System.err.println("Chyba při ukládání!");
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}
