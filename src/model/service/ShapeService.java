package model.service;

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

    public ShapeService(){
        logRepository = new LogRepository();
        shapeRepository = new ShapeRepository();
        fileManager = new FileManager(shapeRepository,logRepository);
    }
    public ShapeService(IShapeRepository shapeRepository, ILogRepository logRepository){
        fileManager = new FileManager(shapeRepository, logRepository);
        this.logRepository = logRepository;
        this.shapeRepository = shapeRepository;
    }
    @Override
    public Shape create(Shape entity) {
        Shape createdShape = shapeRepository.create(entity);
        if(createdShape != null)
            logRepository.addLog(createdShape.getName() + " - Add");
        return createdShape;
    }

    @Override
    public Shape read(String id) {
        return shapeRepository.read(id);
    }

    @Override
    public Shape update(Shape entity) {
        if(shapeRepository.read(entity.getId()) == null)
            return null;
        Shape oldShape = shapeRepository.update(entity);
        logRepository.addLog(oldShape.getName() + ",Modify to," + entity.getName());
        return entity;
    }

    @Override
    public Shape delete(String id) {
        Shape deletedShape = shapeRepository.delete(id);
        if(deletedShape != null)
            logRepository.addLog(deletedShape.getName() + " - Deleted");
        return deletedShape;
    }

    @Override
    public void readFromFile(File file) throws ClassNotFoundException, IOException{
        fileManager.readFromFile(file);
    }

    @Override
    public void saveToFile(File file) throws IOException {
        fileManager.saveToFile(file);
    }

    @Override
    public Collection<Shape> getAll() {
        return shapeRepository.getAll();
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
            logRepository.addLog(shape.getName() + " - Selected");
        }
    }

    @Override
    public void deselect(String id) throws NoSuchFieldException {
        Shape shape = read(id);
        if(shape == null)
            throw new NoSuchFieldException("Shape with id " + id + " doesn't exists");
        if(shape.isSelected()){
            shape.setSelected(false);
            logRepository.addLog(shape.getName() + " - Deselected");
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
}
