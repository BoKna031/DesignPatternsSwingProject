package strategy;

import java.io.File;
import java.io.IOException;

public class SaveManager implements SaveStrategy {
	private SaveStrategy saveStrategy;
	
	public SaveManager(SaveStrategy saveStrategy) {
		this.saveStrategy = saveStrategy;
	}
	
	@Override
	public void save(Object o, File file) throws IOException {
		saveStrategy.save(o, file);
	}

}
