package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Donut extends Circle {

	private int innerRadius;

	public Donut(Point center, int innerRadius, int outerRadius){
		super(center, outerRadius);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int innerRadius, int outerRadius, Color innerColor, Color outerColor) {
		super(center, outerRadius, innerColor, outerColor);
		this.innerRadius = innerRadius;
	}

	public Donut(String id, Point center, int innerRadius, int radius, Color innerColor, Color outerColor){
		this(center, innerRadius, radius, innerColor, outerColor);
		setId(id);
	}

	public void draw(Graphics g) {
				
		Color color = new Color (1.0f, 1.0f, 1.0f, 0.0f);
		g.setColor(color);
		g.fillOval(this.getCenter().getX() - this.getInnerRadius(), this.getCenter().getY() - this.getInnerRadius(),
				this.getInnerRadius() * 2, this.getInnerRadius() * 2);
		
		
		if (getInnerColor() != null) {
			g.setColor(getInnerColor());
			for(int i = getInnerRadius(); i < getRadius(); i++) {
				g.drawOval(this.getCenter().getX() - i,
						  this.getCenter().getY() - i, i * 2,
						  i * 2);		
			}	
		}
		
		
		if (getOuterColor() != null)
			g.setColor(getOuterColor());
		g.drawOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(),
				this.getRadius() * 2, this.getRadius() * 2);
		g.drawOval(this.getCenter().getX() - this.getInnerRadius(), this.getCenter().getY() - this.getInnerRadius(),
				this.getInnerRadius() * 2, this.getInnerRadius() * 2);
	
	 

		g.setColor(new Color(0, 0, 0));

		
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() + getInnerRadius() - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - getInnerRadius() - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getInnerRadius() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - getInnerRadius() - 3, 6, 6);
			
			g.drawRect(this.getCenter().getX() + getRadius() - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - getRadius() - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getRadius() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - getRadius() - 3, 6, 6);
			g.setColor(Color.BLACK);
		}
		 
		 
	}

	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}

	public boolean contains(Point p) {
		return contains(p.getX(),p.getY());
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.DONUT;
	}


}