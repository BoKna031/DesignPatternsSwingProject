package mvc.components;

import mvc.DrawingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificationToolbar extends JPanel implements ActionListener {

    private final JButton btnNext;
    private final JToggleButton btnSelect;
    private final JButton btnModify;
    private final JButton btnDelete;
    private final JButton btnToBack;
    private final JButton btnToFront;
    private final JButton btnBringBack;
    private final JButton btnBringFront;
    private JButton btnUndo;
    private JButton btnRedo;

    private DrawingController controller;

    public ModificationToolbar(DrawingController controller){
        this.controller = controller;
        btnNext = createButton(Icon.Next, 50, 50, false);
        btnSelect = createToggleButton(Icon.Select, 50, 50, true);
        btnModify = createButton(Icon.Modify, 50, 50, false);
        btnDelete = createButton(Icon.Delete, 50, 50, false);
        btnToBack = createButton(Icon.ToBack, 50, 50, false);
        btnToFront = createButton(Icon.ToFront, 50, 50, false);
        btnBringBack = createButton(Icon.BringBack, 50, 50, false);
        btnBringFront = createButton(Icon.BringFront, 50, 50, false);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnUndo = createButton(Icon.Undo, 50, 50, false);
        btnRedo = createButton(Icon.Redo, 50, 50, false);
        createActionListener();
    }

    private JButton createButton(ImageIcon icon, int width, int height, boolean isEnabled) {
        return (JButton) createAbstractButton(icon, width, height, false, isEnabled);
    }

    private JToggleButton createToggleButton(ImageIcon icon, int width, int height, boolean isEnabled) {
        return (JToggleButton) createAbstractButton(icon, width, height, true, isEnabled);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private void createActionListener(){
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        btnUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        btnRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        btnToBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        btnToFront.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        btnBringBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        //dugme BringFornt
        btnBringFront.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(e);
            }
        });

        btnNext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    controller.loadNext();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JButton getBtnNext() {
        return btnNext;
    }

    public JToggleButton getBtnSelect() {
        return btnSelect;
    }

    public JButton getBtnModify() {
        return btnModify;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnToBack() {
        return btnToBack;
    }

    public JButton getBtnToFront() {
        return btnToFront;
    }

    public JButton getBtnBringBack() {
        return btnBringBack;
    }

    public JButton getBtnBringFront() {
        return btnBringFront;
    }

    public JButton getBtnUndo() {
        return btnUndo;
    }

    public JButton getBtnRedo() {
        return btnRedo;
    }

    public void setController(DrawingController controller) {
        this.controller = controller;
    }

    private JComponent createAbstractButton(ImageIcon icon, int width, int height, boolean isToggle, boolean isEnabled){
        AbstractButton btn = (isToggle)? new JToggleButton() : new JButton();
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(newImg));
        btn.setEnabled(isEnabled);
        this.add(btn);
        return btn;
    }
}
