package model.service;

import geometry.Converter;
import geometry.Shape;
import model.repository.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShapeService implements IShapeService{

    IFileManager fileManager;
    ILogRepository logRepository;
    IShapeRepository shapeRepository;

    ILayerRepository layerRepository;

    public ShapeService(){
        logRepository = new LogRepository();
        shapeRepository = new ShapeRepository();
        fileManager = new FileManager(shapeRepository,logRepository);
        layerRepository = new LayerRepository();
    }
    public ShapeService(IShapeRepository shapeRepository, ILogRepository logRepository){
        fileManager = new FileManager(shapeRepository, logRepository);
        this.logRepository = logRepository;
        this.shapeRepository = shapeRepository;
    }
    @Override
    public Shape create(Shape entity) {
        Shape createdShape = shapeRepository.create(entity);
        if(createdShape != null) {
            logRepository.addLog(Converter.ShapeToString(entity) + " - Add");
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
        logRepository.addLog(Converter.ShapeToString(oldShape) + ",Modify to " + Converter.ShapeToString(entity));
        return entity;
    }

    @Override
    public Shape delete(String id) {
        Shape deletedShape = shapeRepository.delete(id);
        if(deletedShape != null) {
            logRepository.addLog(Converter.ShapeToString(deletedShape) + " - Deleted");
            layerRepository.delete(id);
        }
        return deletedShape;
    }

    @Override
    public void readFromFile(File file) throws ClassNotFoundException, IOException{
        fileManager.readFromFile(file);
        for(Shape sh: shapeRepository.getAll()){
            layerRepository.add(sh.getId());
        }
    }

    @Override
    public void saveToFile(File file) throws IOException {
        fileManager.saveToFile(file);
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
    public List<String> getAllLogs() {
        return logRepository.getAllLogs();
    }

    @Override
    public String getLastLog() {
        List<String> logs = getAllLogs();
        if (!logs.isEmpty()){
            return logs.get(logs.size() - 1);
        }
        return null;
    }

    @Override
    public void select(String id) throws NoSuchFieldException {
        Shape shape = read(id);
        if(shape == null)
            throw new NoSuchFieldException("Shape with id " + id + " doesn't exists");
        if(!shape.isSelected()){
            shape.setSelected(true);
            logRepository.addLog(Converter.ShapeToString(shape)  + " - Selected");
        }
    }

    @Override
    public void deselect(String id) throws NoSuchFieldException {
        Shape shape = read(id);
        if(shape == null)
            throw new NoSuchFieldException("Shape with id " + id + " doesn't exists");
        if(shape.isSelected()){
            shape.setSelected(false);
            logRepository.addLog(Converter.ShapeToString(shape)  + " - Deselected");
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
        int result = layerRepository.toFront(shapeId);
        logRepository.addLog(Converter.ShapeToString(read(shapeId))  + " - To Front");
        return result;
    }

    @Override
    public int toBack(String shapeId) {
        int result = layerRepository.toBack(shapeId);
        logRepository.addLog(Converter.ShapeToString(read(shapeId))  + " - To Back");
        return  result;
    }

    @Override
    public int bringBack(String shapeId) {
        int result = layerRepository.bringBack(shapeId);
        logRepository.addLog(Converter.ShapeToString(read(shapeId))  + " - Bring Back");
        return result;
    }

    @Override
    public int bringFront(String shapeId) {
        int result = layerRepository.bringFront(shapeId);
        logRepository.addLog(Converter.ShapeToString(read(shapeId))  + " - Bring Front");
        return result;
    }

    @Override
    public void placeTo(String shapeId, int index) {
        layerRepository.placeTo(shapeId, index);
    }
}
