package mvc.components;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {

    private final ColorButton innerColorBtn;
    private final ColorButton outerColorBtn;

    public ColorPanel(){
        innerColorBtn = new ColorButton(Color.RED, "Inner color");
        outerColorBtn = new ColorButton(Color.RED, "Outer color");

        this.add(innerColorBtn);
        this.add(outerColorBtn);
    }

    public Color getInnerColor() {
        return innerColorBtn.getColor();
    }

    public Color getOuterColor() {
        return outerColorBtn.getColor();
    }

}
