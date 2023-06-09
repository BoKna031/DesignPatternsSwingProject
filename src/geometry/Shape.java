package geometry;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Cloneable, Serializable {

	private boolean selected;
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
	
	public abstract void drawSelection(Graphics g);

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public abstract ShapeType getShapeType();
}