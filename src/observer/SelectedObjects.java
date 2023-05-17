package observer;

import java.util.ArrayList;

import geometry.Shape;

public class SelectedObjects {
	
	private ArrayList<Shape> selectedObjects;
	
	public SelectedObjects() {
		selectedObjects = new ArrayList<Shape>();
	}
	
	public void add(Shape shape) {
		selectedObjects.add(shape);
	}
	
	public void remove(Shape shape) {
		selectedObjects.remove(shape);
	}
	
	public Shape get(int index) {
		return selectedObjects.get(index);
	}
	
	public void set(int index, Shape shape) {
		selectedObjects.set(index, shape);
	}
	
	public int size() {
		return selectedObjects.size();
	}
	
	public void clear() {
		selectedObjects.clear();
	}

}
