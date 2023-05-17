package geometry;

import java.awt.Color;
import java.awt.Graphics;

public abstract class SurfaceShape extends Shape{
	
	private static final long serialVersionUID = 1L;
	private Color fillColor;
	private Color edgeColor;

	public abstract void fill(Graphics g);

	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public Color getEdgeColor() {
		return edgeColor;
	}
	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

}
