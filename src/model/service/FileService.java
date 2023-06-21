package model.service;

import strategy.BinaryFileStorageStrategy;
import strategy.FileManager;
import strategy.TextFileStorageStrategy;

import java.io.*;

public class FileService {

    public static Object load(File file) throws IOException, ClassNotFoundException {
        switch (getFileExtension(file.getAbsolutePath())){
            case "bin": return new FileManager(new BinaryFileStorageStrategy()).load(file);
            case "txt": return new FileManager(new TextFileStorageStrategy()).load(file);
            default: throw new IOException("Not supported type of file");
        }
    }

    public static void save(File file, Object o) throws IOException{
        switch (getFileExtension(file.getAbsolutePath())){
            case "bin": new FileManager(new BinaryFileStorageStrategy()).save(file, o); break;
            case "txt": new FileManager(new TextFileStorageStrategy()).save(file, o); break;
            default: throw new IOException("Not supported type of file");
        }
    }


    private static String getFileExtension(String filename){
        if(!filename.contains("."))
            return "";
        return filename.substring(filename.indexOf(".") + 1);
    }

    public static File changeFileExtension(File file, String newExtension) {
        int dotIndex = file.getAbsolutePath().lastIndexOf(".");
        if (dotIndex != -1) {
            return new File(file.getAbsolutePath().substring(0, dotIndex + 1) + newExtension);
        }

        return new File(file.getAbsolutePath() + "." + newExtension);

    }
}
