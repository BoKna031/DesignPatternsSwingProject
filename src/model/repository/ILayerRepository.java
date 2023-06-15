package model.repository;

import java.util.Collection;

public interface ILayerRepository {

    boolean add(String shapeId);
    boolean placeTo(String shapeId, int index);
    Collection<String> getAll();
    int IndexOf(String shapeId);
    boolean delete(String shapeId);
    int toFront(String shapeId);
    int toBack(String shapeId);
    int bringBack(String shapeId);
    int bringFront(String shapeId);

}
