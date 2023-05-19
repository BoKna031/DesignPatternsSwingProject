package mvc.components;

import mvc.DrawingController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Menu extends JMenuBar {
    private final JMenu menuFile = new JMenu("File");
    private final JMenuItem openItem = new JMenuItem("Open");
    private final JMenuItem saveItem = new JMenuItem("Save");

    private DrawingController controller;

    public Menu(){
        menuFile.add(openItem);
        menuFile.add(saveItem);
        setupListeners();
        this.add(menuFile);
    }

    private boolean saveFile(File file){
        if(!isBinFileNameValid(file.getName())) {
            JOptionPane.showMessageDialog(null, "File name is not valid");
            return false;
        }

        String path = getPathWithoutFileExtension(file.getAbsolutePath());

        File binFile = new File(path + ".bin");
        File logFile = new File(path + ".txt");


        try {
            controller.save(binFile, logFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to save file!");
            e.printStackTrace();
            return false;
        }

        return true;
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
    private void setupListeners(){
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //kreiranje Prozora za odabir lokacije
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Bin", "bin");
                fileChooser.setFileFilter(fileFilter);

                int userSelection = fileChooser.showSaveDialog(null);

                if(userSelection != JFileChooser.APPROVE_OPTION) return;

                saveFile(fileChooser.getSelectedFile());

            }
        });

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                FileNameExtensionFilter f = new FileNameExtensionFilter("bin", "bin", "txt");

                c.setFileFilter(f);

                c.setDialogTitle("Open");
                int userSelection = c.showOpenDialog(null);

                if(userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToLoad = c.getSelectedFile();
                    String filename = fileToLoad.getPath();
                    if(filename.substring(filename.lastIndexOf("."), filename.length()).contentEquals(".bin")) {
                        try {
                            controller.load(fileToLoad);
                        } catch (IOException m) {
                            m.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    } else if (filename.substring(filename.lastIndexOf("."), filename.length()).contentEquals(".txt")) {
                        try {
                            controller.loadOneByOne(fileToLoad);
                        } catch (IOException m) {
                            m.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "The file is not valid");
                    }
                }
            }
        });
    }

    public void setController(DrawingController controller){
        this.controller = controller;
    }
}
