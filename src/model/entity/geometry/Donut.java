package model.entity.geometry;

import java.awt.Color;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut(Point center, int innerRadius, int outerRadius, Color innerColor, Color outerColor) {
		super(center, outerRadius, innerColor, outerColor);
		this.innerRadius = innerRadius;
	}

	public Donut(String id, Point center, int innerRadius, int radius, Color innerColor, Color outerColor){
		this(center, innerRadius, radius, innerColor, outerColor);
		setId(id);
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