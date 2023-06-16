package mvc;

import javax.swing.JPanel;
import view.DrawingService;
import view.IDrawingService;

import java.awt.*;

public class DrawingView extends JPanel {
	
	private final DrawingModel model;
	private final IDrawingService drawingService = new DrawingService();

	public DrawingView(Dimension dimension, DrawingModel model) {
		if(dimension != null)
			this.setPreferredSize(dimension);
		this.model = model;
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(geometry.Shape shape: model.getShapes()) {
			drawingService.draw(g, shape);
		}
	}


}
