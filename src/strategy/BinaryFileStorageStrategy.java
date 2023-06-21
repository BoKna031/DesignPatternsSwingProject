package strategy;

import java.io.*;

public class BinaryFileStorageStrategy implements FileStorageStrategy{
    @Override
    public void save(File file, Object o) throws IOException {
        if(!isFileNameValid(file.getName()))
            throw new IOException("File name is not valid");

        ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file));
        ous.writeObject(o);
        ous.close();
    }

    @Override
    public Object load(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object result = ois.readObject();
        ois.close();
        return result;
    }

    private boolean isFileNameValid(String filename){
        String regex = "^[a-zA-Z0-9_-]+(\\.(bin))?$";
        return filename.matches(regex);
    }
}
