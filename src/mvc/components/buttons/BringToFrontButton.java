package mvc.components.buttons;

import mvc.DrawingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BringToFrontButton extends JButton implements ActionListener {

    private final DrawingController controller;
    public BringToFrontButton(DrawingController controller){
        Image img = Icon.BringBack.getImage();
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
        controller.bringToFront();
    }
}
