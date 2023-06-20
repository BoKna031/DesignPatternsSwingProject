package model.entity.geometry;

import java.awt.Color;

public class Line extends Shape {
	private final Point start;
	private final Point end;
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







}