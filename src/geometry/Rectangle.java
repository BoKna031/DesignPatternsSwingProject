package geometry;

import java.awt.Color;

public class Rectangle extends Shape {

	private final Point upperLeftPoint;
	private final int width;
	private final int height;
	private Color outerColor;
	private Color innerColor;

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.height = height;
		this.width = width;
	}

	public Rectangle(Point upperLeftPoint, int width, int height, Color innerColor, Color outerColor){
		this(upperLeftPoint, width, height);
		this.innerColor = innerColor;
		this.outerColor = outerColor;
	}

	public Rectangle(String id, Point upperLeftPoint, int width, int height, Color innerColor, Color outerColor){
		this(upperLeftPoint,width,height,innerColor,outerColor);
		setId(id);
	}
	
	public Color getOuterColor() {
		return outerColor;
	}

	public void setOuterColor(Color outerColor) {
		this.outerColor = outerColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public boolean contains(int x, int y) {
		Point p = upperLeftPoint;
		return p.getX() <= x && x <= (p.getX() + width) && p.getY() <= y && y <= (p.getY() + height);
	}

	public boolean contains(Point p) {
		return contains(p.getX(), p.getY());
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.RECTANGLE;
	}

}