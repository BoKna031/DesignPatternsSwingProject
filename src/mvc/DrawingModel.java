package mvc;

import java.util.ArrayList;

import geometry.Shape;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();//zbog multithreading
	
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public void add(Shape sh) {
		shapes.add(sh);
	}
	
	public void remove(Shape sh) {
		shapes.remove(sh);
	}
	
	public Shape get(int i) {
		return shapes.get(i);
	}

}
