package mvc.components;

import mvc.components.buttons.Icon;

import javax.swing.*;
import java.awt.*;

public class ShapesToolbar extends JPanel {

    private final JToggleButton btnPoint;
    private final JToggleButton btnLine;
    private final JToggleButton btnRectangle;
    private final JToggleButton btnCircle;
    private final JToggleButton btnDonut;
    private final JToggleButton btnHex;
    private final ButtonGroup buttons;


    public ShapesToolbar(Dimension dimension){
        buttons = new ButtonGroup();
        btnPoint = createToggleButton(mvc.components.buttons.Icon.Point, 50, 50);
        btnLine = createToggleButton(mvc.components.buttons.Icon.Line, 50, 50);
        btnRectangle = createToggleButton(mvc.components.buttons.Icon.Rectangle, 70, 70);
        btnCircle = createToggleButton(mvc.components.buttons.Icon.Circle, 50, 50);
        btnDonut = createToggleButton(mvc.components.buttons.Icon.Donut, 70, 70);
        btnHex = createToggleButton(Icon.Hexagon, 50, 50);

        if(dimension != null)
            this.setPreferredSize(dimension);

        this.setLayout(new GridLayout(0, 1, 10, 10));
    }

    private JToggleButton createToggleButton(ImageIcon icon, int width, int height) {
        JToggleButton btn = new JToggleButton();
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(newImg));
        buttons.add(btn);
        this.add(btn);
        return btn;
    }

    public JToggleButton getBtnPoint() {
        return btnPoint;
    }

    public JToggleButton getBtnLine() {
        return btnLine;
    }

    public JToggleButton getBtnRectangle() {
        return btnRectangle;
    }

    public JToggleButton getBtnCircle() {
        return btnCircle;
    }

    public JToggleButton getBtnDonut() {
        return btnDonut;
    }

    public JToggleButton getBtnHex() {
        return btnHex;
    }
}
