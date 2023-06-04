package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import command.CommandManager;
import command.commands.CmdBringToBack;
import command.commands.CmdBringToFront;
import command.commands.ShapeModify;
import command.commands.CmdAdd;
import command.commands.CmdRemove;
import command.commands.CmdDeselect;
import command.commands.CmdSelect;
import command.commands.CmdToBack;
import command.commands.CmdToFront;
import dialogs.CircleDialog;
import dialogs.DonutDialog;
import dialogs.HexagonDialog;
import dialogs.LineDialog;
import dialogs.PointDialog;
import dialogs.RectangleDialog;
import geometry.Circle;
import geometry.Donut;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import model.service.IShapeService;
import model.service.ShapeService;
import observer.Observer;
import observer.ObserverUpdate;
import observer.SelectedObjects;

public class DrawingController extends MouseAdapter implements ActionListener {

	
	private DrawingModel model;
	private DrawingFrame frame;

	private IShapeService shapeService;
	private ArrayList<Shape> selectedObjects = new ArrayList<>();
	CommandManager commandManager = CommandManager.getInstance();
	ArrayList<String> temporarilyLogs = new ArrayList<>();
	int i = 0;
	
	
	ObserverUpdate observerUpdate;
	Observer observer =  new Observer();

	
	public DrawingController(DrawingModel model, DrawingFrame frame, IShapeService shapeService) {
		this.shapeService = shapeService;
		this.model = model;
		this.frame = frame;
		commandManager.setFrame(frame);
		observerUpdate = new ObserverUpdate(frame);
		observer.addPropertyChangeListener(observerUpdate);
		
	}


	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		Color innerColor = frame.getInnerColor();
		Color outerColor = frame.getOuterColor();

		if(frame.getBtnSelect().isSelected()) {
			selectBtnClicked(x,y);
			return;
		}

		Shape newShape;
		Point p = new Point(x,y);
		if (frame.getBtnPoint().isSelected()) {
			newShape = pointDialog(new Point(x, y, outerColor), false);
		} else if (frame.getBtnLine().isSelected()) {
			newShape = lineBtnClicked(p);
			if(newShape == null)
				return;
		} else if (frame.getBtnRectangle().isSelected()) {
			newShape = rectDialog(new Rectangle(p, 0, 0, innerColor, outerColor), false);
		} else if (frame.getBtnCircle().isSelected()) {
			newShape = circleDialog(new Circle(p,0,innerColor,outerColor), false);
		} else if (frame.getBtnDonut().isSelected()) {
			newShape = donutDialog(new Donut(p,0,0,innerColor,outerColor), false);
		} else if (frame.getBtnHex().isSelected()) {
			newShape = hexDialog(new HexagonAdapter(x, y, 0, innerColor, outerColor), false);
		} else {
			return;
		}

		CmdAdd cmd = new CmdAdd(shapeService, newShape);
		commandManager.execute(cmd);

		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);

		model.getShapes().clear();
		model.getShapes().addAll(shapeService.getAll());

		frame.getView().repaint();
		frame.getBtnNext().setEnabled(false);
		
	}

	private Shape lineBtnClicked(Point point) {
		if(frame.startPoint == null) {
			frame.startPoint = point;
			return null;
		}
		Point p1 = frame.startPoint;
		Point p2 = point;


		Shape line = lineDialog(new Line(p1,p2,p2.getColor()), false);
		frame.startPoint = null;
		return line;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//DELETE
		if (e.getSource() == frame.getBtnDelete() && model.getShapes().size() > 0 && selectedObjects.size() > 0) {
			int input = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?");
			if (input == 0) {
				while (selectedObjects.size() > 0) {
					Shape shape = selectedObjects.get(0);
					if(shape.isSelected()) {
						int index = model.getShapes().indexOf(shape);
						CmdRemove cmdRemove = new CmdRemove(model, shape, index, shape.getName() + " - Deleted");
						selectedObjects.remove(shape);
						commandManager.execute(cmdRemove);
						
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
					}
				}
				notifyAllObservers(selectedObjects.size());
				frame.getView().repaint();
			}
		}
		
		//MODIFY
		else if (e.getSource() == frame.getBtnModify()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				String s = shape.getClass().getSimpleName();
				Shape newShape;
				switch (s) {
					case "Point":
						newShape = pointDialog((Point) shape, true);
						break;
					case "Line":
						newShape = lineDialog((Line) shape, true);
						break;
					case "Rectangle":
						newShape = rectDialog((Rectangle) shape, true);
						break;
					case "Circle":
						newShape = circleDialog((Circle) shape, true);
						break;
					case "Donut":
						newShape = donutDialog((Donut) shape, true);
						break;
					case "HexagonAdapter":
						newShape = hexDialog((HexagonAdapter) shape, true);
						break;
					default:
						newShape = null;
				}
				if(newShape == null)
					return;

				newShape.setSelected(true);
				newShape.setId(shape.getId());
				newShape.setNameString(newShape.getId()+ "," + shape);
				ShapeModify shapeModify = new ShapeModify(shapeService, shape, newShape);
				commandManager.execute(shapeModify);
				model.getShapes().clear();
				model.getShapes().addAll(shapeService.getAll());

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

				frame.getView().repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Izaberite 1 objekat!");
			}
		}
		
		//Undo
		else if (e.getSource() == frame.getBtnUndo()) {
			commandManager.undo();
			frame.getBtnRedo().setEnabled(true);
			if(commandManager.sizeNormal() == 0)
				frame.getBtnUndo().setEnabled(false);
			else
				frame.getBtnUndo().setEnabled(true);
			notifyAllObservers(selectedObjects.size());
			frame.getView().repaint();
		}
		
		
		//Redo
		else if (e.getSource() == frame.getBtnRedo()) {
			commandManager.redo();
			frame.getBtnUndo().setEnabled(true);
			if (commandManager.sizeReverse() == 0)
				frame.getBtnRedo().setEnabled(false);
			else
				frame.getBtnRedo().setEnabled(true);
			notifyAllObservers(selectedObjects.size());
			frame.getView().repaint();
		}
		
		
		//ToBack
		else if (e.getSource() == frame.getBtnToBack()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				CmdToBack cmd = new CmdToBack(model, shape, index, shape.getName() + " - To Back");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
		
		//ToFront
		else if (e.getSource() == frame.getBtnToFront()) {
			if (selectedObjects.size() == 1) {
				
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				
				CmdToFront cmd = new CmdToFront(model, shape, index, shape.getName() + " - To Front");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
		
		//BringBack
		else if (e.getSource() == frame.getBtnBringBack()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				CmdBringToBack cmd = new CmdBringToBack(model, shape, index, shape.getName() + " - Bring Back");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
		
		//BringFront
		else if (e.getSource() == frame.getBtnBringFront()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				int length = model.getShapes().size();
				CmdBringToFront cmd = new CmdBringToFront(model, shape, index, length, shape.getName() + " - Bring Front");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
	}
	
	//Metode za dijaloge
	
	private Point pointDialog(Point point, boolean editable) {
		PointDialog dlg = new PointDialog(point, editable);
		dlg.setVisible(true);
		
		if(dlg.isAccepted()) {
			int x = Integer.parseInt(dlg.getTextFieldX());
			int y = Integer.parseInt(dlg.getTextFieldY());
			Color outerColor = dlg.getBtnColor();
			return new Point(x, y, outerColor);
		}
		
		return null;
	}
	
	private Line lineDialog(Line line, boolean editable) {
		LineDialog dlg = new LineDialog(line, editable);
		dlg.setVisible(true);

		if (dlg.isAccepted()) {
			int x1 = Integer.parseInt(dlg.getTextFieldX());
			int y1 = Integer.parseInt(dlg.getTextFieldY());
			int x2 = Integer.parseInt(dlg.getTextFieldX2());
			int y2 = Integer.parseInt(dlg.getTextFieldY2());
			Color outerColor = dlg.getBtnColor();
			return new Line(new Point(x1, y1), new Point(x2, y2), outerColor);
		}

		return null;
	}
	
	private Rectangle rectDialog(Rectangle rectangle, boolean editable){
		RectangleDialog dlg = new RectangleDialog(rectangle, editable);
		dlg.setVisible(true);

		if (dlg.isAccepted()) {
			int x = Integer.parseInt(dlg.getTextFieldX().getText());
			int y = Integer.parseInt(dlg.getTextFieldY().getText());
			Color innerColor = dlg.getBtnInnerColor().getBackground();
			Color outerColor = dlg.getBtnOuterColor().getBackground();
			int height = Integer.parseInt(dlg.getTextFieldHeight().getText());
			int width = Integer.parseInt(dlg.getTextFieldWidth().getText());

			Point p = new Point(x, y);
			return new Rectangle(p, width, height, innerColor, outerColor);
		}

		return null;
	}
	
	private Circle circleDialog(Circle circle, boolean editable){

		CircleDialog dlg = new CircleDialog(circle, editable);
		dlg.setVisible(true);

		if (dlg.isAccepted()) {

			int x = Integer.parseInt(dlg.getTextFieldX().getText());
			int y = Integer.parseInt(dlg.getTextFieldY().getText());
			int radius = Integer.parseInt(dlg.getTextFieldRadius().getText());
			Color innerColor = dlg.getBtnInnerColor().getBackground();
			Color outerColor = dlg.getBtnOuterColor().getBackground();
			return new Circle(new Point(x, y), radius, innerColor, outerColor);
		}
		return null;
	}
	
	private Donut donutDialog(Donut donut, boolean editable){

		DonutDialog dlg = new DonutDialog(donut, editable);
		dlg.setVisible(true);

		if (dlg.isAccepted()) {

			int x = Integer.parseInt(dlg.getTextFieldX().getText());
			int y = Integer.parseInt(dlg.getTextFieldY().getText());
			int innerRadius = Integer.parseInt(dlg.getTextFieldInRadius().getText());
			int outerRadius = Integer.parseInt(dlg.getTextFieldOutRadius().getText());
			Color innerColor = dlg.getBtnInnerColor().getBackground();
			Color outerColor = dlg.getBtnOuterColor().getBackground();
			if(innerRadius == 0)
				JOptionPane.showMessageDialog(new JFrame(), "Inner radius must be greater than 0","Error", JOptionPane.WARNING_MESSAGE);
			else if (innerRadius >= outerRadius)
				JOptionPane.showMessageDialog(new JFrame(), "Outer radius must be greater than inner radius","Error", JOptionPane.WARNING_MESSAGE);
			else
				return new Donut(new Point(x, y), innerRadius, outerRadius, innerColor, outerColor);
		}
		return null;
	}
	
	private HexagonAdapter hexDialog(HexagonAdapter hexagon, boolean editable) {

		HexagonDialog dlg = new HexagonDialog(hexagon, editable);
		dlg.setVisible(true);

		if (dlg.isAccepted()) {
			int x = Integer.parseInt(dlg.getTextFieldX().getText());
			int y = Integer.parseInt(dlg.getTextFieldY().getText());
			int radius = Integer.parseInt(dlg.getTextFieldRadius().getText());
			Color innerColor = dlg.getBtnInnerColor().getBackground();
			Color outerColor = dlg.getBtnOuterColor().getBackground();

			return new HexagonAdapter(x, y, radius, innerColor, outerColor);
		}

		return null;
	}
	
	private void notifyAllObservers(int size) {
		
		if (size > 0) {
			if (size == 1) {
				observer.setButtonModifyEnabled(true);
				observer.setButtonBringBackEnabled(true);
				observer.setButtonBringFrontEnabled(true);
				observer.setButtonToBackEnabled(true);
				observer.setButtonToFrontEnabled(true);
			} else {
				observer.setButtonModifyEnabled(false);
				observer.setButtonBringBackEnabled(false);
				observer.setButtonBringFrontEnabled(false);
				observer.setButtonToBackEnabled(false);
				observer.setButtonToFrontEnabled(false);
				
			}
			observer.setButtonDeleteEnabled(true);
		} else {
				observer.setButtonDeleteEnabled(false);
				observer.setButtonModifyEnabled(false);
				observer.setButtonBringBackEnabled(false);
				observer.setButtonBringFrontEnabled(false);
				observer.setButtonToBackEnabled(false);
				observer.setButtonToFrontEnabled(false);
		}
	}
	
	public void save(File file) throws IOException {
			shapeService.saveToFile(file);
	}
	
	@SuppressWarnings("unchecked")
	public void load(File fileToLoad) throws ClassNotFoundException, IOException {
			frame.getBtnNext().setEnabled(false);
			frame.clearLogArea();
			shapeService.readFromFile(fileToLoad);
			model.getShapes().clear();
			selectedObjects.clear();
			commandManager.clearNormal();
			commandManager.clearReverse();
			frame.getBtnUndo().setEnabled(false);
			frame.getBtnRedo().setEnabled(false);

			model.getShapes().addAll(shapeService.getAll());

			frame.getView().repaint();

			for(String log: shapeService.getAllLogs()){
				frame.appendLog(log);
			}

			for (int i = 0; i < model.getShapes().size(); i++) {
				if (model.getShapes().get(i).isSelected()) {
					selectedObjects.add(model.getShapes().get(i));
				}
			}
	}
	
	public void loadOneByOne(File file) throws IOException {
		
		frame.getBtnNext().setEnabled(true);
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);
		
		model.getShapes().clear();
		selectedObjects.clear();
		commandManager.clearNormal();
		commandManager.clearReverse();
		notifyAllObservers(0);
		frame.clearLogArea();
		temporarilyLogs.clear();
		
		frame.repaint();

		try {
			ShapeService tempService = new ShapeService();
			tempService.readFromFile(file);

			temporarilyLogs = (ArrayList<String>) tempService.getAllLogs();
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void loadNext() throws Exception {
		
		String line = temporarilyLogs.get(i);
		Shape shape = null;
		
		if (line.contains("Undo")) {
			commandManager.undo();
			notifyAllObservers(selectedObjects.size());
			
		} else if (line.contains("Redo")) {
			commandManager.redo();
			notifyAllObservers(selectedObjects.size());
		} else if (line.contains("To Front")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			
			CmdToFront cmd = new CmdToFront(model, shape, index, shape.getName() + " - To Front");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
			
		} else if (line.contains("To Back")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			CmdToBack cmd = new CmdToBack(model, shape, index, shape.getName() + " - To Back");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
			
		} else if (line.contains("Bring Front")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			int length = model.getShapes().size();
			CmdBringToFront cmd = new CmdBringToFront(model, shape, index, length, shape.getName() + " - Bring Front");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
			
		} else if (line.contains("Bring Back")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			CmdBringToBack cmd = new CmdBringToBack(model, shape, index, shape.getName() + " - Bring Back");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
		} else if (line.contains("Add")) {
			line = line.replaceAll(" - Add", "");
			if(line.contains("Point")) {
				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				Color color = new Color(Integer.parseInt(attributes[6]));
				
				Point point = new Point(x, y, color);
				
				//point.setNameString("Point" + pointerCount++ + "," + point.toString() + ",color," + String.valueOf(point.getColor().getRGB()));
				
				//CmdAdd cmd = new CmdAdd(model, point, point.getName() + " - Add");
				//commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
				
			}  else if (line.contains("Line")) {
				
				String[] attributes = line.split(",");
				int x1 = Integer.parseInt(attributes[2]);
				int y1 = Integer.parseInt(attributes[4]);
				int x2 = Integer.parseInt(attributes[6]);
				int y2 = Integer.parseInt(attributes[8]);
				Color color = new Color(Integer.parseInt(attributes[10]));
				
				Point startPoint = new Point(x1, y1);
				Point endPoint = new Point(x2, y2);
				
				Line lineObject = new Line(startPoint, endPoint, color);
				
				//lineObject.setNameString("Line" + lineCount++ + "," + lineObject.toString());
				
				//CmdAdd cmd =  new CmdAdd(model, lineObject, lineObject.getName() + " - Add");
				//commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Rectangle")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int height = Integer.parseInt(attributes[6]);
				int width = Integer.parseInt(attributes[8]);
				Color outerColor = new Color(Integer.parseInt(attributes[10]));
				Color innerColor = new Color(Integer.parseInt(attributes[12]));

				Point upperLeftPoint = new Point(x, y);

				Rectangle rect = new Rectangle(upperLeftPoint, width, height, innerColor, outerColor);

				//rect.setNameString("Rectangle" + rectangleCount++ + "," + rect.toString());
				//CmdAdd cmd = new CmdAdd(model, rect, rect.getName() + " - Add");
				//commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Circle")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int radius = Integer.parseInt(attributes[6]);
				Color outerColor = new Color(Integer.parseInt(attributes[8]));
				Color innerColor = new Color(Integer.parseInt(attributes[10]));

				Point center = new Point(x, y);

				Circle circle = new Circle(center, radius, innerColor, outerColor);

				//circle.setNameString("Circle" + circleCount++ + "," + circle.toString());
				//CmdAdd cmd = new CmdAdd(model, circle, circle.getName() + " - Add");
				//commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

			} else if (line.contains("Donut")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int radius = Integer.parseInt(attributes[6]);
				Color outerColor = new Color(Integer.parseInt(attributes[8]));
				Color innerColor = new Color(Integer.parseInt(attributes[10]));
				int innerRadius = Integer.parseInt(attributes[12]);

				Point center = new Point(x, y);

				Donut donut = new Donut(center, innerRadius, radius, innerColor, outerColor);

				//donut.setNameString("Donut" + donutCount++ + "," + donut.toString());
				//CmdAdd cmd = new CmdAdd(model, donut, donut.getName() + " - Add");
				//commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
				
			} else if (line.contains("Hexagon")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int radius = Integer.parseInt(attributes[6]);
				Color outerColor = new Color(Integer.parseInt(attributes[8]));
				Color innerColor = new Color(Integer.parseInt(attributes[10]));


				HexagonAdapter hex = new HexagonAdapter(x, y, radius, innerColor, outerColor);

				//hex.setNameString("Hexagon" + hexagonCount++ + "," + hex.toString());
				//CmdAdd cmd = new CmdAdd(model, hex, hex.getName() + " - Add");
				//commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
					
			}
		} else if (line.contains("Modify to")) {
			
			String[] splitString = line.split(",Modify to");
			line = line.replace(",Modify to", "");
			if (line.contains("Point")) {
				
				Point p = new Point();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						p = (Point) model.get(i);
						index = model.getShapes().indexOf(p);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				
				String name = attributes[7];
				int x = Integer.parseInt(attributes[9]);
				int y = Integer.parseInt(attributes[11]);
				Color color = new Color(Integer.parseInt(attributes[13]));
				
				Point point = new Point(x, y, color);
				point.setSelected(true);
				point.setNameString(name + "," +point.toString() + ",color," + String.valueOf(point.getColor().getRGB()));
				
				//PointModify pointModify = new PointModify(model, p, point, index, p.getName() + ",Modify to," + point.getName(), selectedObjects);
				//commandManager.execute(pointModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Line")) {
				
				Line l = new Line();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						l = (Line) model.get(i);
						index = model.getShapes().indexOf(l);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				String name = attributes[11];
				int x1 = Integer.parseInt(attributes[13]);
				int y1 = Integer.parseInt(attributes[15]);
				int x2 = Integer.parseInt(attributes[17]);
				int y2 = Integer.parseInt(attributes[19]);
				Color color = new Color(Integer.parseInt(attributes[21]));
				Point startPoint = new Point(x1, y1);
				Point endPoint = new Point(x2, y2);
				
				Line line2 = new Line(startPoint, endPoint, color);
				line2.setSelected(true);
				line2.setNameString(name + "," + line2.toString());
				
				//LineModify lineModify = new LineModify(model, l, line2, index, l.getName() + ",Modify to," + line2.getName(), selectedObjects);
				//commandManager.execute(lineModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Rectangle")) {
				
				Rectangle r = new Rectangle();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						r = (Rectangle) model.get(i);
						index = model.getShapes().indexOf(r);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				
				String name = attributes[13];
				int x = Integer.parseInt(attributes[15]);
				int y = Integer.parseInt(attributes[17]);
				int height = Integer.parseInt(attributes[19]);
				int width = Integer.parseInt(attributes[21]);
				Color outerColor = new Color(Integer.parseInt(attributes[23]));
				Color innerColor = new Color(Integer.parseInt(attributes[25]));
				Point point = new Point(x, y);
				
				Rectangle rectangle = new Rectangle(point, width, height, innerColor, outerColor);
				rectangle.setSelected(true);
				rectangle.setNameString(name + "," + rectangle.toString());
				
				//RectangleModify rectangleModify = new RectangleModify(model, r, rectangle, index, r.getName() + ",Modify to," + rectangle.getName(), selectedObjects);
				//commandManager.execute(rectangleModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Circle")) {

				Circle c = new Circle();
				int index = 0;

				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						c = (Circle) model.get(i);
						index = model.getShapes().indexOf(c);
						break;
					}
				}

				String[] attributes = line.split(",");

				String name = attributes[11];
				int x = Integer.parseInt(attributes[13]);
				int y = Integer.parseInt(attributes[15]);
				int radius = Integer.parseInt(attributes[17]);
				Color outerColor = new Color(Integer.parseInt(attributes[19]));
				Color innerColor = new Color(Integer.parseInt(attributes[21]));
				Point center = new Point(x, y);

				Circle circle = new Circle(center, radius, innerColor, outerColor);
				circle.setSelected(true);
				circle.setNameString(name + "," + circle.toString());

				//ShapeModify shapeModify = new ShapeModify(shapeService, c, circle, selectedObjects);
				//commandManager.execute(shapeModify);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

				
			} else if (line.contains("Donut")) {
				
				Donut d = new Donut();
				int index = 0;

				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						d = (Donut) model.get(i);
						index = model.getShapes().indexOf(d);
						break;
					}
				}

				String[] attributes = line.split(",");

				String name = attributes[13];
				int x = Integer.parseInt(attributes[15]);
				int y = Integer.parseInt(attributes[17]);
				int radius = Integer.parseInt(attributes[19]);
				Color outerColor = new Color(Integer.parseInt(attributes[21]));
				Color innerColor = new Color(Integer.parseInt(attributes[23]));
				int innerRadius = Integer.parseInt(attributes[25]);
				Point center = new Point(x, y);

				Donut donut = new Donut(center, innerRadius, radius, innerColor, outerColor);
				donut.setSelected(true);
				donut.setNameString(name + "," + donut.toString());

				//ShapeModify shapeModify = new ShapeModify(shapeService, d, donut, selectedObjects);
				//commandManager.execute(shapeModify);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

				
			} else if (line.contains("Hexagon")) {
				
				HexagonAdapter h = new HexagonAdapter();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						h = (HexagonAdapter) model.get(i);
						index = model.getShapes().indexOf(h);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				
				String name = attributes[11];
				int x = Integer.parseInt(attributes[13]);
				int y = Integer.parseInt(attributes[15]);
				int radius = Integer.parseInt(attributes[17]);
				Color outerColor = new Color(Integer.parseInt(attributes[19]));
				Color innerColor = new Color(Integer.parseInt(attributes[21]));
				
				HexagonAdapter hex = new HexagonAdapter(x, y, radius, innerColor, outerColor);
				hex.setSelected(true);
				hex.setNameString(name + "," + hex.toString());
				
				// HexModify hexModify = new HexModify(model, h, hex, index, h.getName() + ",Modify to," + hex.getName(), selectedObjects);
				//commandManager.execute(hexModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
		
				
			}
		}
		
		else if (line.contains("Deleted")) {
			line = line.replace(" - Deleted", "");
			
			shape = selectedObjects.get(0);
			if (shape.isSelected()) {
				int index = model.getShapes().indexOf(shape);
				//CmdRemove cmdRemove = new CmdRemove(model, shape, index, shape.getName() + " - Deleted", selectedObjects);
				//selectedObjects.remove(shape);
				//commandManager.execute(cmdRemove);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		} else if (line.contains("Selected")) {
			line = line.replace(" - Selected", "");
			
			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				shape = model.get(i);
				
				if (shape.getName().equals(line)) {
					//CmdSelect selectCommand = new CmdSelect(shape, selectedObjects, shape.getName() + " - Selected");
					//commandManager.execute(selectCommand);
					
					notifyAllObservers(selectedObjects.size());
					break;
				}
			}
		} else if (line.contains("Deselected")) {
			
			line = line.replace(" - Deselected", "");
			
			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				shape = model.get(i);
				
				if (shape.getName().equals(line)) {
					//CmdDeselect deselectCommand = new CmdDeselect(shape, selectedObjects, shape.getName() + " - Deselected");
					//commandManager.execute(deselectCommand);
					
					notifyAllObservers(selectedObjects.size());
					break;
				}
			}
		}
		i++;
		if (i == temporarilyLogs.size() || temporarilyLogs.get(i) == null) {
			frame.getBtnNext().setEnabled(false);
		}
		frame.repaint();
	}


	private void selectBtnClicked(int x, int y){
		if(shapeService.getAll() == null || shapeService.getAll().size() == 0)
			return;

		boolean isObjectFound = changeStatusOfSelectedObject(x, y);

		if(!isObjectFound) //if user clicks on background
			deselectAllObjects();

		notifyAllObservers(shapeService.getSelected().size());

	}

	private void deselectAllObjects(){
		for (Shape s : shapeService.getSelected()) {
			CmdDeselect deselectCommand = new CmdDeselect(s.getId(), shapeService);
			commandManager.execute(deselectCommand);
		}
	}
	private boolean changeStatusOfSelectedObject(int x, int y) {
		for (Shape shape : shapeService.getAll()) {
			if(!shape.contains(x, y)) {
				continue;
			}
			if(shape.isSelected()) {
				CmdDeselect deselectCommand = new CmdDeselect(shape.getId(), shapeService);
				commandManager.execute(deselectCommand);
			}else{
				CmdSelect selectCommand = new CmdSelect(shape.getId(), shapeService);
				commandManager.execute(selectCommand);
			}
			return true;
		}
		return false;
	}
}
