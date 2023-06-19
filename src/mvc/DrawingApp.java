package mvc;
import model.service.ShapeService;

import javax.swing.JFrame;

public class DrawingApp {

	public static void main(String[] args) {
		ShapeService shapeService = new ShapeService();
		DrawingFrame frame = new DrawingFrame();
		DrawingController controller = new DrawingController(frame, shapeService);
		frame.setController(controller);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
}
	}
