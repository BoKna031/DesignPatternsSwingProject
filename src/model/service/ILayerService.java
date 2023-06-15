package model.service;

public interface ILayerService {

    int toFront(String shapeId);
    int toBack(String shapeId);
    int bringBack(String shapeId);
    int bringFront(String shapeId);

    void placeTo(String shapeId, int index);
}
