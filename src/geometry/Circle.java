package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

	private Point center = new Point();
	private int radius;
	private Color innerColor;
	private Color outerColor;
		
	public Circle() {

	}
	
	public Circle(Point center) {
		this.center =  center;
	}
	
	public Circle(Point center, int radius){
		this.center=center;
		this.radius=radius;
	}
	
	public Circle(Point center, int radius, boolean selected){
		this(center, radius);
		setSelected(selected);
	}
	
	public Circle(Point center, int radius, Color innerColor, Color outerColor){
		this(center, radius);
		this.innerColor = innerColor;
		this.outerColor = outerColor;
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

	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);

	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return (this.radius - ((Circle) o).radius);
		}
		return 0;
	}

	public boolean contains(int x, int y) {
		return this.center.distance(x, y) <= radius;
	}

	public boolean contains(Point p) {
		return this.center.distance(p.getX(), p.getY()) <= radius;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			return this.center == c.getCenter() && this.radius == c.getRadius();
		}
			return false;
		}

	public double area() {
		return radius * radius * Math.PI;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius){
			this.radius = radius;
	}

	public String toString() {
		return center.toString() + ",radius," + radius
				+ ",outerColor," + String.valueOf(outerColor.getRGB()) + ",innerColor," + String.valueOf(innerColor.getRGB());
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
	public void configureClone(Shape shapeToClone) {
		// TODO Auto-generated method stub
		
	}

}