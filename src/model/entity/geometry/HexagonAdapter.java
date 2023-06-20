package model.entity.geometry;

import java.awt.Color;
import hexagon.*;

public class HexagonAdapter extends Shape {
	
	Hexagon hexagon;
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
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
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
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
	}
	
	public boolean isSelected() {
		return hexagon.isSelected();
	}

	@Override
	public ShapeType getShapeType() {
		return ShapeType.HEXAGON;
	}

}
