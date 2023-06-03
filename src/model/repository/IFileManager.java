package model.repository;

import java.io.File;
import java.io.IOException;

public interface IFileManager {
    void readFromFile(File path) throws ClassNotFoundException, IOException;
    void saveToFile(File path) throws IOException;
}
