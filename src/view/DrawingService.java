package view;

import model.entity.geometry.*;
import model.entity.geometry.Point;
import model.entity.geometry.Rectangle;
import model.entity.geometry.Shape;
import hexagon.Hexagon;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class DrawingService implements IDrawingService{

    @Override
    public void draw(Graphics g, Shape shape) {
        switch (shape.getShapeType()){
            case POINT: drawPoint(g, (Point) shape); break;
            case LINE: drawLine(g, (Line) shape); break;
            case RECTANGLE: drawRectangle(g, (Rectangle) shape); break;
            case CIRCLE: drawCircle(g, (Circle) shape); break;
            case DONUT: drawDonut(g, (Donut) shape); break;
            case HEXAGON: drawHexagon(g, (HexagonAdapter) shape); break;
        }
    }


    private void drawPoint(Graphics g, Point point) {
        g.setColor(point.getColor());
        int x = point.getX();
        int y = point.getY();
        g.drawLine(x - 5, y, x+ 5, y);
        g.drawLine(x, y - 5, x, y + 5);
        if (point.isSelected()) {
            drawPointSelection(g, point);
        }

    }
    private void drawPointSelection(Graphics g, Point point) {
        g.setColor(Color.BLUE);
        int x = point.getX();
        int y = point.getY();
        g.drawRect(x - 3 , y - 3, 6, 6);
    }
    private void drawLine(Graphics g, Line line) {
        g.setColor(line.getColor() != null ? line.getColor() : Color.BLACK);
        int x1 = line.getStart().getX();
        int y1 = line.getStart().getY();
        int x2 = line.getEnd().getX();
        int y2 = line.getEnd().getY();
        g.drawLine(x1, y1, x2, y2);

        if (line.isSelected()) {
            drawLineSelection(g, line);
        }
    }
    private void drawLineSelection(Graphics g, Line line) {
        g.setColor(Color.BLUE);
        int x1 = line.getStart().getX();
        int y1 = line.getStart().getY();
        int x2 = line.getEnd().getX();
        int y2 = line.getEnd().getY();
        g.drawRect(x1 - 3, y1 - 3, 6, 6);
        g.drawRect(x2 - 3, y2 - 3, 6, 6);
        Point middle = getMiddlePointOfLine(line);
        g.drawRect(middle.getX() - 3, middle.getY() - 3, 6, 6);
        g.setColor(Color.BLACK);

    }
    private Point getMiddlePointOfLine(Line line) {
        int middleByX = (line.getStart().getX() + line.getEnd().getX()) / 2;
        int middleByY = (line.getStart().getY() + line.getEnd().getY()) / 2;
        return new Point(middleByX, middleByY);
    }

    private void drawRectangle(Graphics g, Rectangle rectangle) {
        g.setColor(rectangle.getInnerColor());
        g.fillRect(rectangle.getUpperLeftPoint().getX(), rectangle.getUpperLeftPoint().getY(), rectangle.getWidth(), rectangle.getHeight());
        g.setColor(rectangle.getOuterColor());
        g.drawRect(rectangle.getUpperLeftPoint().getX(), rectangle.getUpperLeftPoint().getY(), rectangle.getWidth(), rectangle.getHeight());

        if (rectangle.isSelected()) {
            drawRectangleSelection(g, rectangle);
        }
    }

    private void drawRectangleSelection(Graphics g, Rectangle rectangle) {
        g.setColor(Color.BLUE);
        int x = rectangle.getUpperLeftPoint().getX();
        int y = rectangle.getUpperLeftPoint().getY();
        int width = rectangle.getWidth();
        int height = rectangle.getHeight();
        g.drawRect(x - 3        , y - 3         , 6, 6);
        g.drawRect(x + width - 3, y - 3         , 6, 6);
        g.drawRect(x - 3        , y + height - 3, 6, 6);
        g.drawRect(x + width - 3, y + height - 3, 6, 6);
        g.setColor(Color.BLACK);

    }

    private void drawCircle(Graphics g, Circle circle) {
        Color innerColor = circle.getInnerColor() == null ? Color.BLACK : circle.getInnerColor();
        g.setColor(innerColor);

        int x = circle.getCenter().getX();
        int y = circle.getCenter().getY();
        int r = circle.getRadius();
        g.fillOval(x - r, y - r,r * 2, r* 2);

        Color outerColor = circle.getOuterColor() == null ? Color.BLACK : circle.getOuterColor();
        g.setColor(outerColor);

        g.drawOval(x - r, y - r,r * 2, r* 2);
        if (circle.isSelected()) {
            drawCircleSelection(g, x, y, r);
        }
    }
    private void drawCircleSelection(Graphics g, int x, int y, int r) {
        g.setColor(Color.BLUE);
        g.drawRect(x - 3    , y - 3     , 6, 6);
        g.drawRect(x + r - 3, y - 3     , 6, 6);
        g.drawRect(x - r - 3, y - 3     , 6, 6);
        g.drawRect(x - 3    , y + r - 3 , 6, 6);
        g.drawRect(x - 3    , y - r - 3 , 6, 6);
        g.setColor(Color.BLACK);

    }

    private void drawDonut(Graphics g, Donut donut) {
        Graphics2D gr = (Graphics2D)g;
        int x = donut.getCenter().getX();
        int y = donut.getCenter().getY();
        int innerRadius = donut.getInnerRadius();
        int radius = donut.getRadius();
        java.awt.Shape donutArea = createDonutShape(x, y, radius, radius - innerRadius);
        Color innerColor = donut.getInnerColor() == null? Color.BLACK : donut.getInnerColor();
        gr.setColor(innerColor);
        gr.fill(donutArea);
        Color outerColor = donut.getOuterColor() == null? Color.BLACK : donut.getOuterColor();
        gr.setColor(outerColor);
        gr.draw(donutArea);
        if(donut.isSelected()) {
            drawCircleSelection(g, x, y , radius);
        }
        g.setColor(Color.black);
    }

    private java.awt.Shape createDonutShape(
            double centerX, double centerY, double outerRadius, double thickness)
    {
        Ellipse2D outer = new Ellipse2D.Double(
                centerX - outerRadius,
                centerY - outerRadius,
                outerRadius + outerRadius,
                outerRadius + outerRadius);
        Ellipse2D inner = new Ellipse2D.Double(
                centerX - outerRadius + thickness,
                centerY - outerRadius + thickness,
                outerRadius + outerRadius - thickness - thickness,
                outerRadius + outerRadius - thickness - thickness);
        Area area = new Area(outer);
        area.subtract(new Area(inner));
        return area;
    }

	private void drawHexagon(Graphics g, HexagonAdapter hexagon) {
		Hexagon hex = new Hexagon(hexagon.getX(), hexagon.getY(), hexagon.getR());
        hex.setAreaColor(hexagon.getInnerColor());
        hex.setBorderColor(hexagon.getOuterColor());
        hex.setSelected(hexagon.isSelected());
        hex.paint(g);
	}
}
