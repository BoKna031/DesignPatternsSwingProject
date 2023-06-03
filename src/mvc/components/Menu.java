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
        setupListeners();
        this.add(menuFile);
    }
    private void setupListeners(){
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                saveItem();
            }
        });

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = createChooserDialog("Open",new FileNameExtensionFilter("bin", "bin", "txt"));

                int userSelection = fileChooser.showOpenDialog(null);

                if(userSelection != JFileChooser.APPROVE_OPTION)
                    return;

                File fileToLoad = fileChooser.getSelectedFile();
                String filename = fileToLoad.getName();
                if(getFileExtension(filename).contentEquals("bin")) {
                    try {
                        controller.load(fileToLoad);
                    } catch (IOException m) {
                        m.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else if (getFileExtension(filename).contentEquals("txt")) {
                    try {
                        controller.loadOneByOne(fileToLoad);
                    } catch (IOException m) {
                        m.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The file is not valid");
                }
            }

        });
    }

    private void saveItem() {
        JFileChooser fileChooser = createChooserDialog("Save",new FileNameExtensionFilter("Bin", "bin"));
        int userSelection = fileChooser.showSaveDialog(null);

        if(userSelection != JFileChooser.APPROVE_OPTION)
            return;

        saveFile(fileChooser.getSelectedFile());
    }

    private boolean saveFile(File file){
        try {
            controller.save(file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private JFileChooser createChooserDialog(String dialogName, FileNameExtensionFilter filter) {
        JFileChooser fileChooser = new JFileChooser();
        if(filter != null)
            fileChooser.setFileFilter(filter);
        if(dialogName != null)
            fileChooser.setDialogTitle(dialogName);
        return fileChooser;
    }

    private String getFileExtension(String filename){
        if(!filename.contains("."))
            return "";
        return filename.substring(filename.indexOf(".") + 1);
    }

    public void setController(DrawingController controller){
        this.controller = controller;
    }
}
