package mvc;
import model.service.ShapeService;

import javax.swing.JFrame;

public class DrawingApp {

	public static void main(String[] args) {
		ShapeService shapeService = new ShapeService();
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame(model);
		DrawingController controller = new DrawingController(model, frame, shapeService);
		frame.setController(controller);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
}
	}
