package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mvc.DrawingFrame;

public class SaveLog implements SaveStrategy {
	
	@Override
	public void save(Object o, File fileToSaveLog) throws IOException {
		DrawingFrame frame = (DrawingFrame)o;
		BufferedWriter bf = null;
		bf = new BufferedWriter((new FileWriter(fileToSaveLog.getAbsolutePath())));
		frame.getLogArea().write(bf);
		bf.close();

	}

}
