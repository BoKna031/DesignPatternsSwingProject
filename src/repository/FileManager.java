package repository;

import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SavePainting;
import java.io.File;
import java.io.IOException;

public class FileManager implements IFileManager{

    private IShapeRepository shapeRepository;
    private ILogRepository logRepository;

    public FileManager(IShapeRepository shapeRepository, ILogRepository logRepository){
        this.shapeRepository = shapeRepository;
        this.logRepository = logRepository;
    }

    public FileManager(){
        this.shapeRepository = new ShapeRepository();
        this.logRepository = new LogRepository();
    }

    @Override
    public void readFromFile(File path) {

    }

    @Override
    public void saveToFile(File file) throws IOException{
        if(!isBinFileNameValid(file.getName())) {
            throw new IOException("File name is not valid");
        }

        String path = getPathWithoutFileExtension(file.getAbsolutePath());

        File binFile = new File(path + ".bin");
        File logFile = new File(path + ".txt");


        try {
            save(binFile, logFile);
        } catch (IOException e) {
            throw new IOException("Unable to save file!");
        }

    }
    private boolean isBinFileNameValid(String filename){
        String regex = "^[a-zA-Z0-9_-]+(\\.(bin))?$";
        return filename.matches(regex);
    }

    private String getPathWithoutFileExtension(String path){
        int dotIndex = path.indexOf('.');
        if (dotIndex != -1) {
            return path.substring(0, dotIndex);
        } else {
            return path;
        }
    }
    private void save(File fileToSave, File fileToSaveLog) throws IOException {
        SaveManager savePainting = new SaveManager(new SavePainting());
        SaveManager saveLog = new SaveManager(new SaveLog());

        savePainting.save(shapeRepository.getAll(), fileToSave);
        saveLog.save(logRepository, fileToSaveLog);
    }

    public IShapeRepository getShapeRepository() {
        return shapeRepository;
    }

    public ILogRepository getLogRepository() {
        return logRepository;
    }
}
