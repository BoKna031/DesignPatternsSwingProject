package model.repository;

import geometry.*;

import java.util.Collection;
import java.util.HashMap;

public class ShapeRepository implements IShapeRepository{

    private HashMap<String, Shape> shapes = new HashMap<>();
    private int pointerCnt = 1;
    private int lineCnt = 1;
    private int rectangleCnt = 1;
    private int circleCnt = 1;
    private int donutCnt = 1;
    private int hexagonCnt = 1;

    @Override
    public Shape create(Shape entity) {
        String id = generateId(entity);
        if (id == null) return null;
        entity.setId(id);
        shapes.put(id, entity);
        return entity;
    }

    private String generateId(Shape entity) {
        String id;
        if(entity instanceof Point){
            id = "Point" + pointerCnt++;
        }else if(entity instanceof Line){
            id = "Line" + lineCnt++;
        }else if(entity instanceof Rectangle){
            id = "Rectangle" + rectangleCnt++;
        }else if (entity instanceof Donut) {
            id = "Donut" + donutCnt++;
        }else if(entity instanceof Circle){
            id = "Circle" + circleCnt++;
        }  else if (entity instanceof HexagonAdapter) {
            id = "Hexagon" + hexagonCnt++;
        } else {
            return null;
        }
        return id;
    }


    @Override
    public Shape read(String id) {
        return shapes.get(id);
    }

    @Override
    public Shape update(Shape entity) {
        return shapes.put(entity.getId(), entity);
    }

    @Override
    public Shape delete(String id) {
        return shapes.remove(id);
    }

    @Override
    public Collection<Shape> getAll() {
        return shapes.values().stream().toList();
    }

    @Override
    public void clear() {
        shapes.clear();
    }
}
