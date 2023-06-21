package strategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextFileStorageStrategy implements FileStorageStrategy{
    @Override
    public void save(File file, Object o) throws IOException {
        if(!isFileNameValid(file.getName()))
            throw new IOException("File name is not valid");

        ArrayList<String> lines = (ArrayList<String>) o;
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String l : lines) {
                writer.println(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object load(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> result = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        br.close();
        return result;
    }

    private boolean isFileNameValid(String filename){
        String regex = "^[a-zA-Z0-9_-]+(\\.(txt))?$";
        return filename.matches(regex);
    }
}
