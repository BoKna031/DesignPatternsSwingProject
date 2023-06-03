package model.service;

import geometry.Shape;
import model.repository.*;

import java.io.File;
import java.io.IOException;
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
        Shape oldShape = shapeRepository.read(entity.getId());
        if(oldShape == null)
            return null;
        Shape newShape = shapeRepository.update(entity);
        logRepository.addLog(oldShape.getName() + ",Modify to," + newShape.getName());
        return newShape;
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
}
