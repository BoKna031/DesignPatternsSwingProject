package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SavePainting implements SaveStrategy {
	
	@Override
	public void save(Object o, File fileToSave){

		ObjectOutputStream ous;
		try {
			ous = new ObjectOutputStream(new FileOutputStream(fileToSave));
			ous.writeObject(o);
			ous.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
