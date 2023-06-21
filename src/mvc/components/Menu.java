package mvc.components;

import mvc.DrawingController;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
                FileNameExtensionFilter[] filters = {
                        new FileNameExtensionFilter("Bin", "bin"),
                        new FileNameExtensionFilter("Txt", "txt")
                };

                JFileChooser fileChooser = createChooserDialog("Open", List.of(filters));

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
        JFileChooser fileChooser = createChooserDialog("Save", null);

        int userSelection = fileChooser.showSaveDialog(null);

        if(userSelection != JFileChooser.APPROVE_OPTION)
            return;

        FileFilter fileFilter = fileChooser.getFileFilter();
        System.out.println(fileFilter.getDescription());
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

    private JFileChooser createChooserDialog(String dialogName, List<FileNameExtensionFilter> filters) {
        JFileChooser fileChooser = new JFileChooser();
        configureFilters(fileChooser, filters);
        if(dialogName != null)
            fileChooser.setDialogTitle(dialogName);
        return fileChooser;
    }

    private void configureFilters(JFileChooser fileChooser, List<FileNameExtensionFilter> filters){
        if(filters == null || fileChooser == null)
            return;
        for(FileNameExtensionFilter filter: filters){
            fileChooser.addChoosableFileFilter(filter);
        }
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
