package strategy;

import java.io.File;
import java.io.IOException;

public interface FileStorageStrategy {
    void save(File file, Object o) throws IOException;
    Object load(File file) throws ClassNotFoundException, IOException;
}
