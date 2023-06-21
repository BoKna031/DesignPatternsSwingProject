package model.service;
import model.entity.geometry.Shape;
import model.repository.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ShapeService implements IShapeService{
    IShapeRepository shapeRepository;
    ILayerRepository layerRepository;

    public ShapeService(){
        shapeRepository = new ShapeRepository();
        layerRepository = new LayerRepository();
    }
    @Override
    public Shape create(Shape entity) {
        Shape createdShape = shapeRepository.create(entity);
        if(createdShape != null) {
            layerRepository.add(createdShape.getId());
        }
        return createdShape;
    }

    @Override
    public Shape read(String id) {
        return shapeRepository.read(id);
    }

    @Override
    public Shape update(Shape entity) {
        Shape oldShape = shapeRepository.read(entity.getId());
        if(oldShape == null)
            return null;
        entity.setSelected(oldShape.isSelected());
        shapeRepository.update(entity);
        return entity;
    }

    @Override
    public Shape delete(String id) {
        Shape deletedShape = shapeRepository.delete(id);
        if(deletedShape != null) {
            layerRepository.delete(id);
        }
        return deletedShape;
    }

    @Override
    public void readFromFile(File file) throws ClassNotFoundException, IOException{
        Collection<Shape> shapes = (Collection<Shape>) FileService.load(file);
        for(Shape sh: shapes){
            Shape shape = shapeRepository.create(sh);
            layerRepository.add(shape.getId());
        }
    }

    @Override
    public void saveToFile(File file) throws IOException {
        FileService.save(file, getAll());
    }

    @Override
    public Collection<Shape> getAll() {
        Collection<Shape> result = new ArrayList<>();
        for(String id: layerRepository.getAll()){
            result.add(read(id));
        }
        return result;
    }

    @Override
    public void select(String id) throws NoSuchFieldException {
        Shape shape = read(id);
        if(shape == null)
            throw new NoSuchFieldException("Shape with id " + id + " doesn't exists");
        if(!shape.isSelected()){
            shape.setSelected(true);
        }
    }

    @Override
    public void deselect(String id) throws NoSuchFieldException {
        Shape shape = read(id);
        if(shape == null)
            throw new NoSuchFieldException("Shape with id " + id + " doesn't exists");
        if(shape.isSelected()){
            shape.setSelected(false);
        }
    }

    @Override
    public Collection<Shape> getSelected() {
        ArrayList<Shape> selectedShapes = new ArrayList<>();
        for(Shape s: getAll()){
            if(s.isSelected())
                selectedShapes.add(s);
        }
        return selectedShapes;
    }

    @Override
    public int toFront(String shapeId) {
        return layerRepository.toFront(shapeId);
    }

    @Override
    public int toBack(String shapeId) {
        return layerRepository.toBack(shapeId);
    }

    @Override
    public int bringBack(String shapeId) {
        return layerRepository.bringBack(shapeId);
    }

    @Override
    public int bringFront(String shapeId) {
        return layerRepository.bringFront(shapeId);
    }

    @Override
    public void placeTo(String shapeId, int index) {
        layerRepository.placeTo(shapeId, index);
    }
}
