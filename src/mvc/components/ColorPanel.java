package mvc.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPanel extends JPanel implements ActionListener {

    private Color innerColor;
    private Color outerColor;


    private final JButton btnInnerColor = new JButton("InnerColor");
    private final JButton btnOuterColor = new JButton("OuterColor");


    public ColorPanel(){
        innerColor = outerColor = Color.RED;

        btnInnerColor.setBackground(innerColor);
        btnOuterColor.setBackground(outerColor);

        this.add(btnInnerColor);
        this.add(btnOuterColor);

        btnOuterColor.addActionListener(this);
        btnInnerColor.addActionListener(this);
    }

    public Color getInnerColor() {
        return innerColor;
    }

    public Color getOuterColor() {
        return outerColor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnInnerColor) {
            innerColor = JColorChooser.showDialog(this, "Choose your inner color", innerColor);
            btnInnerColor.setBackground(innerColor);
        } else if (e.getSource() == btnOuterColor) {
            outerColor = JColorChooser.showDialog(this, "Choose your outer color", outerColor);
            btnOuterColor.setBackground(outerColor);
        }
    }
}
