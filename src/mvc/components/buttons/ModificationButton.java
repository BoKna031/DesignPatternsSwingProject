package mvc.components.buttons;

import mvc.DrawingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificationButton extends JButton implements ActionListener {

    private final DrawingController controller;
    public ModificationButton(DrawingController controller){
        Image img = Icon.Modify.getImage();
        setIcon(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        this.controller = controller;
        super.setEnabled(false);
        addActionListener(this);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            controller.modify();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
