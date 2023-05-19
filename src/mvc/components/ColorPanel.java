package mvc.components;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {

    private final ColorButton innerColorBtn;
    private final ColorButton outerColorBtn;

    public ColorPanel(Dimension dimension){
        innerColorBtn = new ColorButton(Color.RED, "Inner color");
        outerColorBtn = new ColorButton(Color.RED, "Outer color");

        this.add(innerColorBtn);
        this.add(outerColorBtn);

        if(dimension != null)
            this.setPreferredSize(dimension);

        this.setLayout(new GridLayout(0, 1, 3, 3));
    }

    public Color getInnerColor() {
        return innerColorBtn.getColor();
    }

    public Color getOuterColor() {
        return outerColorBtn.getColor();
    }

}
