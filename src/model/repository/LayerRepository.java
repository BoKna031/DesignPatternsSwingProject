package model.repository;

import java.util.ArrayList;
import java.util.Collection;

public class LayerRepository implements ILayerRepository {

    private ArrayList<String> orderOfShapes;

    public LayerRepository(){
        orderOfShapes = new ArrayList<>();
    }

    public LayerRepository(Collection<String> shapes){
        orderOfShapes = new ArrayList<>(shapes);
    }

    @Override
    public boolean add(String shapeId) {
        if(IndexOf(shapeId) != -1)
            return false;
        orderOfShapes.add(shapeId);
        return true;
    }

    @Override
    public boolean placeTo(String shapeId, int index) {
        orderOfShapes.remove(shapeId);
        orderOfShapes.add(index, shapeId);
        return true;
    }

    @Override
    public Collection<String> getAll() {
        return orderOfShapes;
    }

    @Override
    public int IndexOf(String shapeId) {
        return orderOfShapes.indexOf(shapeId);
    }

    @Override
    public boolean delete(String shapeId) {
        return orderOfShapes.remove(shapeId);
    }

    @Override
    public int toFront(String shapeId) {
        int index = IndexOf(shapeId);
        if(index != -1 && index < orderOfShapes.size() - 1) {
            orderOfShapes.remove(shapeId);
            orderOfShapes.add(index + 1, shapeId);
        }
        return index;
    }

    @Override
    public int toBack(String shapeId) {
        int index = IndexOf(shapeId);
        if(index > 0) {
            orderOfShapes.remove(shapeId);
            orderOfShapes.add(index - 1, shapeId);
        }
        return index;
    }

    @Override
    public int bringBack(String shapeId) {
        int result = IndexOf(shapeId);
        if(result != -1) {
            orderOfShapes.remove(shapeId);
            orderOfShapes.add(0, shapeId);
        }
        return result;
    }

    @Override
    public int bringFront(String shapeId) {
        int result = IndexOf(shapeId);
        if(result != -1) {
            orderOfShapes.remove(shapeId);
            orderOfShapes.add(shapeId);
        }
        return result;
    }
}
