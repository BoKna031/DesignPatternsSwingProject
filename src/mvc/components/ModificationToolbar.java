package mvc.components;

import mvc.DrawingController;
import mvc.components.buttons.*;
import mvc.components.buttons.Icon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificationToolbar extends JPanel implements ActionListener {

    private  JButton btnNext;
    private  JToggleButton btnSelect;
    private ModificationButton modify;
    private DeleteButton delete;
    private ToBackButton toBack;
    private ToFrontButton toFront;
    private BringToBackButton bringBack;
    private  BringToFrontButton bringFront;
    private UndoButton undo;
    private RedoButton redo;
    private DrawingController controller;

    public ModificationToolbar(Dimension dimension, DrawingController controller){
        this.controller = controller;
        updateButtons();

        if(dimension != null)
            this.setPreferredSize(dimension);

        this.setLayout(new FlowLayout());


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
        return modify;
    }

    public JButton getBtnDelete() {
        return delete;
    }

    public JButton getToBack() {
        return toBack;
    }

    public JButton getToFront() {
        return toFront;
    }

    public JButton getBringBack() {
        return bringBack;
    }

    public JButton getBringFront() {
        return bringFront;
    }

    public JButton getUndo() {
        return undo;
    }

    public JButton getRedo() {
        return redo;
    }

    private void updateButtons(){
        removeAll();
        btnNext = createButton(Icon.Next, 50, 50, false);
        btnSelect = createToggleButton(Icon.Select, 50, 50, true);
        modify = new ModificationButton(controller);
        add(modify);
        delete = new DeleteButton(controller);
        add(delete);
        toBack = new ToBackButton(controller);
        add(toBack);
        toFront = new ToFrontButton(controller);
        add(toFront);
        bringBack = new BringToBackButton(controller);
        add(bringBack);
        bringFront = new BringToFrontButton(controller);
        add(bringFront);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        undo = new UndoButton(controller);
        add(undo);
        redo = new RedoButton(controller);
        add(redo);
        createActionListener();
    }

    public void setController(DrawingController controller) {
        this.controller = controller;
        updateButtons();
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
