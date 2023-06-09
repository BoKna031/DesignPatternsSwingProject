package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape {

	private Point upperLeftPoint;
	private int width;
	private int height;
	private Color outerColor;
	private Color innerColor;

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.height = height;
		this.width = width;
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected) {
		this(upperLeftPoint, width, height);
		setSelected(selected);
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

	@Override
	public void drawSelection(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() - 3, 6, 6);
		g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() - 3, 6, 6);
		g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
		g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
		g.setColor(Color.BLACK);
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(outerColor);
		fill(g);
		g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), width, height);

		if (isSelected()) {
			drawSelection(g);
		}
	}

	private void fill(Graphics g) {
		g.setColor(innerColor);
		g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), width, height);
	}

}