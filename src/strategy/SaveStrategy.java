package strategy;

import java.io.File;
import java.io.IOException;

public interface SaveStrategy {
	void save(Object o, File f) throws IOException;

}
