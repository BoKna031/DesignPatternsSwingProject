package strategy;

import java.io.*;
import java.util.ArrayList;

public class SaveLog implements SaveStrategy {
	
	@Override
	public void save(Object o, File fileToSaveLog) {
		ArrayList<String> logs = (ArrayList<String>) o;
		try (PrintWriter writer = new PrintWriter(new FileWriter(fileToSaveLog))) {
			for (String line : logs) {
				writer.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
