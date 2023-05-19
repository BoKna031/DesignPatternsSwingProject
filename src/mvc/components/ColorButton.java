package mvc.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorButton extends JButton implements ActionListener {

    private Color color;

    public ColorButton(Color color, String text){
        this.color = color;
        this.setBackground(color);
        this.addActionListener(this);
        this.setText(text);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        color = JColorChooser.showDialog(this, "Choose your inner color", color);
        this.setBackground(color);
    }

    public Color getColor(){
        return color;
    }
}
