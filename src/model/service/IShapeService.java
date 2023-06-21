package model.service;

import model.entity.geometry.Shape;
import model.repository.CRUD;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IShapeService extends CRUD<String, Shape>, ILayerService {
    void readFromFile(File file) throws ClassNotFoundException, IOException;
    void saveToFile(File file) throws IOException;

    Collection<Shape> getAll();

    void select(String id) throws NoSuchFieldException;

    void deselect(String id) throws NoSuchFieldException;

    Collection<Shape> getSelected();
}
