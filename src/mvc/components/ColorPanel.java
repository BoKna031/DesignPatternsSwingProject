package mvc.components;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {


    private final ButtonGroup colors = new ButtonGroup();

    private Color innerColor = Color.RED;
    private Color outerColor = Color.RED;


    private final JButton btnInnerColor = new JButton("InnerColor");
    private final JButton btnOuterColor = new JButton("OuterColor");


    public ColorPanel(){
        btnInnerColor.setBackground(innerColor);
        btnOuterColor.setBackground(outerColor);
        colors.add(btnInnerColor);
        colors.add(btnOuterColor);
        this.add(btnInnerColor);
        this.add(btnOuterColor);
    }

    public Color getInnerColor() {
        return innerColor;
    }

    public Color getOuterColor() {
        return outerColor;
    }
}
