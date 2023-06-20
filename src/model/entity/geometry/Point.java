package model.entity.geometry;

import java.awt.Color;

public class Point extends Shape {

	private final int x;
	private final int y;
	private Color color;

	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public Point(int x, int y, Color color) {
		this(x, y);
		this.color = color;
	}

	public Point(String id, int x, int y, Color color){
		this(x,y,color);
		setId(id);
	}
	@Override
	public boolean contains(int x, int y) {
		return distance(x, y) < 5;
	}

	public double distance(Point point){
		double deltaX = x - point.getX();
		double deltaY = y - point.getY();
		return  Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	public double distance(int x, int y){
		Point p2 = new Point(x,y);
		return distance(p2);
	}
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.POINT;
	}
}