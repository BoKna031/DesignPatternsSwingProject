package mvc;

import java.awt.*;
import java.util.Iterator;
import javax.swing.JPanel;
import geometry.Shape;

public class DrawingView extends JPanel {
	
	private final DrawingModel model;

	public DrawingView(Dimension dimension, DrawingModel model) {
		if(dimension != null)
			this.setPreferredSize(dimension);

		this.model = model;
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Iterator<Shape> it = model.getShapes().iterator();
		while(it.hasNext()) {
			it.next().draw(g);
		}
	}

}
