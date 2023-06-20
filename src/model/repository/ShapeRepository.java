package model.repository;

import model.entity.geometry.*;

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
        String id;
        if(entity.getId() == null) {
            id = generateId(entity);
        }
        else{
            id = entity.getId();
            updateCounter(entity);
        }
        if (id == null) return null;
        entity.setId(id);
        shapes.put(id, entity);
        return entity;
    }

    private void updateCounter(Shape entity) {
        String idCnt = entity.getId().replaceAll("[^0-9]", "");
        int cnt = Integer.parseInt(idCnt);
        switch (entity.getShapeType()){
            case POINT :    pointerCnt = cnt + 1;   break;
            case LINE:      lineCnt = cnt + 1;      break;
            case RECTANGLE: rectangleCnt = cnt + 1; break;
            case DONUT:     donutCnt = cnt + 1;     break;
            case CIRCLE:    circleCnt = cnt + 1;    break;
            case HEXAGON:   hexagonCnt = cnt + 1;   break;
        }
    }

    private String generateId(Shape entity) {
        String id;
        if(entity.getShapeType() == ShapeType.POINT){
            id = "Point" + pointerCnt++;
        }else if(entity.getShapeType() == ShapeType.LINE){
            id = "Line" + lineCnt++;
        }else if(entity.getShapeType() == ShapeType.RECTANGLE){
            id = "Rectangle" + rectangleCnt++;
        }else if (entity.getShapeType() == ShapeType.DONUT) {
            id = "Donut" + donutCnt++;
        }else if(entity.getShapeType() == ShapeType.CIRCLE){
            id = "Circle" + circleCnt++;
        }  else if (entity.getShapeType() == ShapeType.HEXAGON) {
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
