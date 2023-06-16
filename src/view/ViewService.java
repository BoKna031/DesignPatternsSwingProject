package view;

import dialogs.*;
import geometry.*;

import javax.swing.*;
import java.awt.Color;


public class ViewService {
    public static Point pointDialog(Point point, boolean editable) {
        PointDialog dlg = new PointDialog(point, editable);
        dlg.setVisible(true);

        if(dlg.isAccepted()) {
            int x = Integer.parseInt(dlg.getTextFieldX());
            int y = Integer.parseInt(dlg.getTextFieldY());
            Color outerColor = dlg.getBtnColor();
            return new Point(x, y, outerColor);
        }

        return null;
    }

    public static Line lineDialog(Line line, boolean editable) {
        LineDialog dlg = new LineDialog(line, editable);
        dlg.setVisible(true);

        if (dlg.isAccepted()) {
            int x1 = Integer.parseInt(dlg.getTextFieldX());
            int y1 = Integer.parseInt(dlg.getTextFieldY());
            int x2 = Integer.parseInt(dlg.getTextFieldX2());
            int y2 = Integer.parseInt(dlg.getTextFieldY2());
            Color outerColor = dlg.getBtnColor();
            Line result = new Line(line.getId(), new Point(x1, y1), new Point(x2, y2), outerColor);
            result.setSelected(line.isSelected());
            return result;
        }

        return null;
    }

    public static Rectangle rectDialog(geometry.Rectangle rectangle, boolean editable){
        RectangleDialog dlg = new RectangleDialog(rectangle, editable);
        dlg.setVisible(true);

        if (dlg.isAccepted()) {
            int x = Integer.parseInt(dlg.getTextFieldX().getText());
            int y = Integer.parseInt(dlg.getTextFieldY().getText());
            Color innerColor = dlg.getBtnInnerColor().getBackground();
            Color outerColor = dlg.getBtnOuterColor().getBackground();
            int height = Integer.parseInt(dlg.getTextFieldHeight().getText());
            int width = Integer.parseInt(dlg.getTextFieldWidth().getText());

            Point p = new Point(x, y);
            return new Rectangle(p, width, height, innerColor, outerColor);
        }

        return null;
    }

    public static Circle circleDialog(Circle circle, boolean editable){

        CircleDialog dlg = new CircleDialog(circle, editable);
        dlg.setVisible(true);

        if (dlg.isAccepted()) {

            int x = Integer.parseInt(dlg.getTextFieldX().getText());
            int y = Integer.parseInt(dlg.getTextFieldY().getText());
            int radius = Integer.parseInt(dlg.getTextFieldRadius().getText());
            Color innerColor = dlg.getBtnInnerColor().getBackground();
            Color outerColor = dlg.getBtnOuterColor().getBackground();
            return new Circle(new Point(x, y), radius, innerColor, outerColor);
        }
        return null;
    }

    public static Donut donutDialog(Donut donut, boolean editable){

        DonutDialog dlg = new DonutDialog(donut, editable);
        dlg.setVisible(true);

        if (dlg.isAccepted()) {

            int x = Integer.parseInt(dlg.getTextFieldX().getText());
            int y = Integer.parseInt(dlg.getTextFieldY().getText());
            int innerRadius = Integer.parseInt(dlg.getTextFieldInRadius().getText());
            int outerRadius = Integer.parseInt(dlg.getTextFieldOutRadius().getText());
            Color innerColor = dlg.getBtnInnerColor().getBackground();
            Color outerColor = dlg.getBtnOuterColor().getBackground();
            if(innerRadius == 0)
                JOptionPane.showMessageDialog(new JFrame(), "Inner radius must be greater than 0","Error", JOptionPane.WARNING_MESSAGE);
            else if (innerRadius >= outerRadius)
                JOptionPane.showMessageDialog(new JFrame(), "Outer radius must be greater than inner radius","Error", JOptionPane.WARNING_MESSAGE);
            else
                return new Donut(new Point(x, y), innerRadius, outerRadius, innerColor, outerColor);
        }
        return null;
    }

    public static HexagonAdapter hexDialog(HexagonAdapter hexagon, boolean editable) {

        HexagonDialog dlg = new HexagonDialog(hexagon, editable);
        dlg.setVisible(true);

        if (dlg.isAccepted()) {
            int x = Integer.parseInt(dlg.getTextFieldX().getText());
            int y = Integer.parseInt(dlg.getTextFieldY().getText());
            int radius = Integer.parseInt(dlg.getTextFieldRadius().getText());
            Color innerColor = dlg.getBtnInnerColor().getBackground();
            Color outerColor = dlg.getBtnOuterColor().getBackground();

            return new HexagonAdapter(x, y, radius, innerColor, outerColor);
        }

        return null;
    }


}
