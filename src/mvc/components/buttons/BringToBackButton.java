package mvc.components.buttons;

import mvc.DrawingController;
import mvc.components.Icon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BringToBackButton extends JButton implements ActionListener {
    private final DrawingController controller;
    private boolean enabled = false;
    public BringToBackButton(DrawingController controller){
        Image img = Icon.BringBack.getImage();
        setIcon(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        this.controller = controller;
        setEnabled(enabled);
        addActionListener(this);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.bringToBack();
    }
}
