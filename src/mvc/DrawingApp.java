package mvc;
import javax.swing.JFrame;

public class DrawingApp {

	public static void main(String[] args) {
			
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
}
	}
