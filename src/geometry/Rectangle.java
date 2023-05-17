package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {

	private Point upperLeftPoint = new Point();
	private int width;
	private int height;
	private Color outerColor;
	private Color innerColor;
	
	public Color getOuterColor() {
		return outerColor;
	}

	public void setOuterColor(Color outerColor) {
		this.outerColor = outerColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}


	public Rectangle() {

	}

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		setHeight(height);
		setWidth(width);
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


	@Override
	public void draw(Graphics g) {
		g.setColor(outerColor);
		fill(g);
		g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), width, height);

		if (isSelected()) {
			drawSelection(g);
		}
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(innerColor);
		g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), width, height);
	}

	@Override
	public void moveBy(int byX, int byY) {
		upperLeftPoint.moveBy(byX, byY);

	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return (int) (this.area() - ((Rectangle) o).area());
		}
		return 0;
	}

	public boolean contains(int x, int y) {
		if (this.getUpperLeftPoint().getX() <= x && x <= (this.getUpperLeftPoint().getX() + width)
				&& this.getUpperLeftPoint().getY() <= y && y <= (this.getUpperLeftPoint().getY() + height)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(Point p) {
		if (this.getUpperLeftPoint().getX() <= p.getX() && p.getX() <= (this.getUpperLeftPoint().getX() + width)
				&& this.getUpperLeftPoint().getY() <= p.getY()
				&& p.getY() <= (this.getUpperLeftPoint().getY() + height)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			if (this.upperLeftPoint.equals(r.getUpperLeftPoint()) && this.height == r.getHeight()
					&& this.width == r.getWidth()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int area() {
		return width * height;
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width){
			this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
			this.height = height;
	}

	public String toString() {

		return upperLeftPoint.toString() + ",height," + height + ",width," + width
				+ ",outerColor," + String.valueOf(outerColor.getRGB()) + ",innerColor," + String.valueOf(innerColor.getRGB());
	}

	@Override
	public void configureClone(Shape shapeToClone) {
		// TODO Auto-generated method stub
		
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

}