package geometry;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Moveable, Comparable<Object>, Cloneable, Serializable {

	private boolean selected;
	public String nameString;
	private String id;

	public Shape() {}

	public Shape(boolean selected) {
		this.selected = selected;
	}

	public abstract boolean contains(int x, int y);

	public abstract void draw(Graphics g);

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getName() {
		return nameString;
	}

	public void setNameString(String nameString) {
		this.nameString = nameString;
	}
	
	public abstract void drawSelection(Graphics g);

	public abstract void configureClone(Shape shapeToClone);

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

}