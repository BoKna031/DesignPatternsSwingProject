package repository;

import java.io.File;
import java.io.IOException;

public interface IFileManager {
    void readFromFile(File path);
    void saveToFile(File path) throws IOException;
}
