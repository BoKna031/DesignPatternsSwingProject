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
    public boolean add(String shapeId, int index) {
        if(IndexOf(shapeId) != -1)
            return false;
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
    public void toFront(String shapeId) {
        int index = IndexOf(shapeId);
        if(index == -1 || index == orderOfShapes.size() - 1)
            return;
        orderOfShapes.remove(shapeId);
        orderOfShapes.add(++index, shapeId);
    }

    @Override
    public void toBack(String shapeId) {
        int index = IndexOf(shapeId);
        if(index <= 0)
            return;
        orderOfShapes.remove(shapeId);
        orderOfShapes.add(--index, shapeId);
    }

    @Override
    public void bringBack(String shapeId) {
        boolean exists = orderOfShapes.remove(shapeId);
        if(exists)
            orderOfShapes.add(0, shapeId);
    }

    @Override
    public void bringFront(String shapeId) {
        boolean exists = orderOfShapes.remove(shapeId);
        if(exists)
            orderOfShapes.add(shapeId);
    }
}
