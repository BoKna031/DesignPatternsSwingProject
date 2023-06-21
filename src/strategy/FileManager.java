package strategy;

import java.io.File;
import java.io.IOException;

public class FileManager implements FileStorageStrategy{
    private FileStorageStrategy strategy;

    public FileManager(FileStorageStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(FileStorageStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void save(File file, Object o) throws IOException {
        strategy.save(file, o);
    }

    @Override
    public Object load(File file) throws ClassNotFoundException, IOException {
        return strategy.load(file);
    }
}
