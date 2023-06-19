package geometry;

import java.awt.Color;

public class Circle extends Shape {

	private final Point center;
	private final int radius;
	private Color innerColor;
	private Color outerColor;
	
	public Circle(Point center, int radius){
		this.center=center;
		this.radius=radius;
	}
	
	public Circle(Point center, int radius, Color innerColor, Color outerColor){
		this(center, radius);
		this.innerColor = innerColor;
		this.outerColor = outerColor;
	}

	public Circle(String id, Point center, int radius, Color innerColor, Color outerColor){
		this(center, radius, innerColor, outerColor);
		setId(id);
	}
	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public Color getOuterColor() {
		return outerColor;
	}

	public void setOuterColor(Color outerColor) {
		this.outerColor = outerColor;
	}

	public boolean contains(int x, int y) {
		return this.center.distance(x, y) <= radius;
	}

	public boolean contains(Point p) {
		return contains(p.getX(),p.getY());
	}

	public Point getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.CIRCLE;
	}


}