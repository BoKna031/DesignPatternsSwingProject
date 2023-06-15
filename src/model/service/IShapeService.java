package model.service;

import geometry.Shape;
import model.repository.CRUD;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface IShapeService extends CRUD<String, Shape>, ILayerService {
    void readFromFile(File file) throws ClassNotFoundException, IOException;
    void saveToFile(File file) throws IOException;

    Collection<Shape> getAll();

    List<String> getAllLogs();

    String getLastLog();

    void select(String id) throws NoSuchFieldException;

    void deselect(String id) throws NoSuchFieldException;

    Collection<Shape> getSelected();
}
