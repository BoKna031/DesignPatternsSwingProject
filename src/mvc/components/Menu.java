package mvc.components;

import mvc.DrawingController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu extends JMenuBar {
    private final JMenu menuFile = new JMenu("File");
    private final JMenuItem openItem = new JMenuItem("Open");
    private final JMenuItem saveItem = new JMenuItem("Save");

    private DrawingController controller;

    public Menu(){
        menuFile.add(openItem);
        menuFile.add(saveItem);
        this.add(menuFile);
    }

    private void setupListeners(){
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                JFileChooser c = new JFileChooser();
                FileNameExtensionFilter f = new FileNameExtensionFilter("Bin", "bin");
                c.setFileFilter(f);

                int userSelection = c.showSaveDialog(null);

                if(userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = c.getSelectedFile();
                    File fileToSaveLog;
                    String filePath = fileToSave.getAbsolutePath();

                    if(!filePath.endsWith(".bin") && !filePath.contains(".")) {
                        fileToSave = new File(filePath + ".bin");
                        fileToSaveLog = new File(filePath + ".txt");
                    }

                    String filename = fileToSave.getPath();

                    if(filename.substring(filename.lastIndexOf("."), filename.length()).contentEquals(".bin")) {
                        try {

                            filename = fileToSave.getAbsolutePath().substring(0, filename.lastIndexOf(".")) + ".txt";
                            System.out.println(filename);
                            fileToSaveLog = new File(filename);
                            controller.save(fileToSave, fileToSaveLog);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "File is not valid");
                    }
                }
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
