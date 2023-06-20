package mvc;

import javax.swing.JPanel;

import model.service.IShapeService;
import view.DrawingService;
import view.IDrawingService;

import java.awt.*;

public class DrawingView extends JPanel {
	
	private final IShapeService service;
	private final IDrawingService drawingService = new DrawingService();

	public DrawingView(Dimension dimension, IShapeService service) {
		if(dimension != null)
			this.setPreferredSize(dimension);
		this.service = service;
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(model.entity.geometry.Shape shape: service.getAll()) {
			drawingService.draw(g, shape);
		}
	}


}
