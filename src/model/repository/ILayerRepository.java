package model.repository;

import java.util.Collection;

public interface ILayerRepository {

    boolean add(String shapeId);
    boolean add(String shapeId, int index);
    Collection<String> getAll();
    int IndexOf(String shapeId);
    boolean delete(String shapeId);
    void toFront(String shapeId);
    void toBack(String shapeId);
    void bringBack(String shapeId);
    void bringFront(String shapeId);

}
