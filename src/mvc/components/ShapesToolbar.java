package mvc.components;

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


    public ShapesToolbar(){
        buttons = new ButtonGroup();
        btnPoint = createToggleButton(Icon.Point, 50, 50);
        btnLine = createToggleButton(Icon.Line, 50, 50);
        btnRectangle = createToggleButton(Icon.Rectangle, 70, 70);
        btnCircle = createToggleButton(Icon.Circle, 50, 50);
        btnDonut = createToggleButton(Icon.Donut, 70, 70);
        btnHex = createToggleButton(Icon.Hexagon, 50, 50);
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
