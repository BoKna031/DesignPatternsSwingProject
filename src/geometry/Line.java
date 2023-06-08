package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {

//	private static final long serialVersionUID = -8395868283026085837L;
	private Point startPoint = new Point();
	private Point endPoint = new Point();
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Line() {}

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		setEndPoint(endPoint);
	}
	
	public Line(Point startPoint, Point endPoint, Color color) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = color;
	}

	public Line(String id, Point startPoint, Point endPoint, Color color) {
		this(startPoint, endPoint, color);
		setId(id);
	}
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
			g.setColor(this.color != null ? color : Color.BLACK);
			g.drawLine(this.getStartPoint().getX(), this.getStartPoint().getY(), this.getEndPoint().getX(),
					this.getEndPoint().getY());
		
		if (isSelected()) {
			drawSelection(g);
		}
	}

	@Override
	public void moveBy(int byX, int byY) {
		startPoint.moveBy(byX, byY);
		endPoint.moveBy(byX, byY);

	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int) (this.length() - ((Line) o).length());
		}
		return 0;
	}

	public Point middleOfLine() {
		int middleByX = (this.getStartPoint().getX() + this.getEndPoint().getX()) / 2;
		int middleByY = (this.getStartPoint().getY() + this.getEndPoint().getY()) / 2;
		Point p = new Point(middleByX, middleByY);
		return p;
	}

	public boolean contains(int x, int y) {
		if ((startPoint.distance(x, y) + endPoint.distance(x, y)) - length() <= 0.9) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line l = (Line) obj;
			if (this.startPoint.equals(l.getStartPoint()) && this.endPoint.equals(l.getEndPoint())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public String toString() {
		return "Line (" + this.startPoint.getX() + "," + this.startPoint.getY() + ")"
		+ " (" + this.endPoint.getX() + "," + this.endPoint.getY() + ")" + " color=" + Integer.toString(this.getColor().getRGB());
	}
	
	public Line clone(Shape s) {
		Line lineClone = new Line(new Point(), new Point());

		if (s instanceof Line) {
			lineClone = (Line) s;
		}

		lineClone.getStartPoint().setX(this.getStartPoint().getX());
		lineClone.getStartPoint().setY(this.getStartPoint().getY());
		lineClone.getEndPoint().setX(this.getEndPoint().getX());
		lineClone.getEndPoint().setY(this.getEndPoint().getY());
		lineClone.setColor(this.getColor());

		return lineClone;
	}

	@Override
	public void configureClone(Shape shapeToClone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.LINE;
	}

	@Override
	public void drawSelection(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(this.getStartPoint().getX() - 3, this.getStartPoint().getY() - 3, 6, 6);
		g.drawRect(this.getEndPoint().getX() - 3, this.getEndPoint().getY() - 3, 6, 6);
		g.drawRect(this.middleOfLine().getX() - 3, this.middleOfLine().getY() - 3, 6, 6);
		g.setColor(Color.BLACK);
		
	}


}