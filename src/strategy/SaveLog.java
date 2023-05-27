package strategy;

import java.io.*;

import mvc.DrawingFrame;

public class SaveLog implements SaveStrategy {
	
	@Override
	public void save(Object o, File fileToSaveLog) throws IOException {
		ObjectOutputStream ous = null;
		try {
			ous = new ObjectOutputStream(new FileOutputStream(fileToSaveLog));
			ous.writeObject(o);
			ous.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
