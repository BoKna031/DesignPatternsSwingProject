package geometry;
import java.io.Serializable;

public abstract class Shape implements Cloneable, Serializable {

	private boolean selected;
	private String id;

	public Shape() {}

	public Shape(boolean selected) {
		this.selected = selected;
	}

	public abstract boolean contains(int x, int y);

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public abstract ShapeType getShapeType();
}