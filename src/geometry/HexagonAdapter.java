package geometry;

import java.awt.Color;
import java.awt.Graphics;
import hexagon.*;

public class HexagonAdapter extends Shape {
	
	Hexagon hexagon;
	
	public HexagonAdapter() {
		hexagon = new Hexagon(0, 0, 0);
	}
	
	public HexagonAdapter(int x, int y, int r, Color innerColor, Color outerColor) {
		hexagon = new Hexagon(x, y, r);
		hexagon.setAreaColor(innerColor);
		hexagon.setBorderColor(outerColor);
	}
	public HexagonAdapter(String id, int x, int y, int r, Color innerColor, Color outerColor) {
		this(x, y, r, innerColor, outerColor);
		setId(id);
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}
	
	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}
	
	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}
	
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}
	
	public Color getOuterColor() {
		return hexagon.getBorderColor();
	}
	
	public int getX() {
		return hexagon.getX();
	}
	
	public int getY() {
		return hexagon.getY();
	}
	
	public int getR() {
		return hexagon.getR();
	}
	
	public void setInnerColor(Color innerColor) {
		hexagon.setAreaColor(innerColor);
	}
	
	public void setOuterColor(Color outerColor) {
		hexagon.setBorderColor(outerColor);
	}
	
	public void setX(int x) {
		hexagon.setX(x);
	}
	
	public void setY(int y) {
		hexagon.setY(y);
	}
	
	public void setR(int r) {
		hexagon.setR(r);
	}
	
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
	}
	
	public boolean isSelected() {
		return hexagon.isSelected();
	}
	
	public String toString() {
		return "x," + getX() + ",y," + getY() + ",radius," + getR() + ",outerColor," 
				+ String.valueOf(getOuterColor().getRGB()) + ",innerColor," + String.valueOf(getInnerColor().getRGB());
	}

	@Override
	public void drawSelection(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.HEXAGON;
	}

	@Override
	public void configureClone(Shape shapeToClone) {
		// TODO Auto-generated method stub
		
	}


}
