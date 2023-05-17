package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mvc.DrawingFrame;

public class SaveLog implements SaveStrategy {
	
	@Override
	public void save(Object o, File fileToSaveLog) {
		DrawingFrame frame = (DrawingFrame)o;
		BufferedWriter bf = null;
		try {
			bf = new BufferedWriter((new FileWriter(fileToSaveLog.getAbsolutePath())));
			frame.getLogArea().write(bf);
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
