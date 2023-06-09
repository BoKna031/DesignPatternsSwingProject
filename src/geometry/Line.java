package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	private Point start;
	private Point end;
	private Color color;

	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	public Line(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}

	public Line(String id, Point start, Point end, Color color) {
		this(start, end, color);
		setId(id);
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public boolean contains(int x, int y) {
		return (start.distance(x, y) + end.distance(x, y)) - length() <= 0.9;
	}

	private double length() {
		return start.distance(end.getX(), end.getY());
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.LINE;
	}

	@Override
	public void drawSelection(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(this.getStart().getX() - 3, this.getStart().getY() - 3, 6, 6);
		g.drawRect(this.getEnd().getX() - 3, this.getEnd().getY() - 3, 6, 6);
		g.drawRect(this.middleOfLine().getX() - 3, this.middleOfLine().getY() - 3, 6, 6);
		g.setColor(Color.BLACK);
		
	}


	private Point middleOfLine() {
		int middleByX = (this.getStart().getX() + this.getEnd().getX()) / 2;
		int middleByY = (this.getStart().getY() + this.getEnd().getY()) / 2;
		Point p = new Point(middleByX, middleByY);
		return p;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.color != null ? color : Color.BLACK);
		g.drawLine(this.getStart().getX(), this.getStart().getY(), this.getEnd().getX(),
				this.getEnd().getY());

		if (isSelected()) {
			drawSelection(g);
		}
	}

}