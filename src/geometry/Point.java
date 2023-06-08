package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	private int x;
	private int y;
	private Color color;

	public Point() {
		
	}

	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}

	public Point(int x, int y, Color color) {
		this(x, y);
		this.color = color;
	}

	public Point(String id, int x, int y, Color color){
		this(x,y,color);
		setId(id);
	}
	
	public boolean equals(Object object) {
		if (object instanceof Point) {
			Point p = (Point) object;
			return this.x == p.getX() && this.y == p.getY();
		}
			return false;
	}
	
	public double distance(int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		return Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(this.x - 5, y, this.x + 5, y);
		g.drawLine(x, this.y - 5, x, this.y + 5);
		if (isSelected()) {
			drawSelection(g);
		}
		
	}
	
	@Override
	public void drawSelection(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.drawRect(this.x - 3 , this.y - 3, 6, 6);
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.x += byX;
		this.y += byY;

	}

	@Override
	public int compareTo(Object object) {
		if (object instanceof Point) {
			Point start = new Point(0, 0);
			return (int) (this.distance(start.getX(), start.getY()) - ((Point) object).distance(start.getX(), start.getY()));
		}
		return 0;
	}

	public boolean contains(int x, int y) {
		if (this.distance(x, y) <= 5)
			return true;
		else return false;
	}
	
	
	public Point clone() {
		Point pointClone = new Point();
		pointClone.configureClone(this);
		return pointClone;
	}


	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public String toString() {
		return "Point (" + this.getX() + "," + this.getY() + ")" + " color=" + this.getColor();
	}

	@Override
	public void configureClone(Shape shapeToClone) {
		if (shapeToClone instanceof Point) {
			Point pointClone = (Point) shapeToClone;
			x = pointClone.getX();
			y = pointClone.getY();
			setColor(pointClone.getColor());
			setSelected(pointClone.isSelected());
		}
		
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.POINT;
	}


}