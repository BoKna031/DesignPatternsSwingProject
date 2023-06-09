package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	private int x;
	private int y;
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