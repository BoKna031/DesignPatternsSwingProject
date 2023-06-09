package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

	private Point center;
	private int radius;
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
	public void drawSelection(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(this.center.getX() - 3, this.center.getY() - 3, 6, 6);
		g.drawRect(this.center.getX() + this.radius - 3, this.center.getY() - 3, 6, 6);
		g.drawRect(this.center.getX() - this.radius - 3, this.center.getY() - 3, 6, 6);
		g.drawRect(this.center.getX() - 3, this.center.getY() + this.radius - 3, 6, 6);
		g.drawRect(this.center.getX() - 3, this.center.getY() - this.radius - 3, 6, 6);
		g.setColor(Color.BLACK);
		
	}

	@Override
	public void draw(Graphics g) {
		if (innerColor != null)
			g.setColor(innerColor);
		else
			g.setColor(Color.black);
		g.fillOval(this.center.getX() - this.radius, this.center.getY() - this.radius,
				this.radius * 2, this.radius * 2);
		if (outerColor != null)
			g.setColor(outerColor);
		else
			g.setColor(Color.black);
		g.drawOval(this.center.getX() - this.radius, this.center.getY() - this.radius,
				this.radius * 2, this.radius * 2);
		if (isSelected()) {
			drawSelection(g);
		}
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.CIRCLE;
	}


}