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
import geometry.*;
import model.service.IShapeService;
import model.service.ShapeService;
import observer.Observer;
import observer.ObserverUpdate;

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
			frame.getView().repaint();
			return;
		}

		Shape newShape;
		Point p = new Point(x,y);
		if (frame.getBtnPoint().isSelected()) {
			newShape = pointDialog(new Point(x, y, outerColor), false);
		} else if (frame.getBtnLine().isSelected()) {
			newShape = lineBtnClicked(p,outerColor);
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

	private void preformDelete(){
		for(Shape s: shapeService.getSelected()){
			CmdRemove cmdRemove = new CmdRemove(shapeService, s);
			commandManager.execute(cmdRemove);
		}
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//DELETE
		if (e.getSource() == frame.getBtnDelete()) {
			int input = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?");
			if (input == JOptionPane.YES_OPTION) {
				preformDelete();
				notifyAllObservers(selectedObjects.size());
			}
		}
		
		//MODIFY
		else if (e.getSource() == frame.getBtnModify()) {
			if (shapeService.getSelected().size() == 1) {
				ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
				Shape shape = selectedShapes.get(0);
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
				ShapeModify shapeModify = new ShapeModify(shapeService, shape, newShape);
				commandManager.execute(shapeModify);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
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
		}
		
		
		//ToBack
		else if (e.getSource() == frame.getBtnToBack()) {
			if (shapeService.getSelected().size() == 1) {
				ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
				Shape shape = selectedShapes.get(0);
				CmdToBack cmd = new CmdToBack(shapeService, shape.getId());
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		}
		
		
		//ToFront
		else if (e.getSource() == frame.getBtnToFront()) {
			if (shapeService.getSelected().size() == 1) {
				ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
				Shape shape = selectedShapes.get(0);
				CmdToFront cmd = new CmdToFront(shapeService, shape.getId());
				commandManager.execute(cmd);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		}
		
		
		//BringBack
		else if (e.getSource() == frame.getBtnBringBack()) {
			if (shapeService.getSelected().size() == 1) {
				ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
				Shape shape = selectedShapes.get(0);
				CmdBringToBack cmd = new CmdBringToBack(shapeService, shape.getId());
				commandManager.execute(cmd);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		}
		
		
		//BringFront
		else if (e.getSource() == frame.getBtnBringFront()) {
			if (shapeService.getSelected().size() == 1) {
				ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
				Shape shape = selectedShapes.get(0);
				CmdBringToFront cmd = new CmdBringToFront(shapeService, shape.getId());
				commandManager.execute(cmd);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

			}
		}
		model.getShapes().clear();
		model.getShapes().addAll(shapeService.getAll());
		frame.getView().repaint();
		
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
		observer.setButtonDeleteEnabled(size>0);
		observer.setButtonModifyEnabled(size == 1);
		observer.setButtonBringBackEnabled(size == 1);
		observer.setButtonBringFrontEnabled(size == 1);
		observer.setButtonToBackEnabled(size == 1);
		observer.setButtonToFrontEnabled(size == 1);
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
	
	public void loadNext() {
		
		String line = temporarilyLogs.get(i);
		Shape shape;
		
		if (line.contains("Undo")) {
			commandManager.undo();
			notifyAllObservers(shapeService.getSelected().size());
			
		} else if (line.contains("Redo")) {
			commandManager.redo();
			notifyAllObservers(selectedObjects.size());
		} else if (line.contains("To Front")) {
			shape = Converter.StringToShape(line);
			CmdToFront cmd = new CmdToFront(shapeService,shape.getId());
			commandManager.execute(cmd);
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			
		} else if (line.contains("To Back")) {
			shape = Converter.StringToShape(line);
			CmdToBack cmd = new CmdToBack(shapeService,shape.getId());
			commandManager.execute(cmd);
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
		} else if (line.contains("Bring Front")) {
			shape = Converter.StringToShape(line);
			CmdBringToFront cmd = new CmdBringToFront(shapeService,shape.getId());
			commandManager.execute(cmd);
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
		} else if (line.contains("Bring Back")) {
			shape = Converter.StringToShape(line);
			CmdBringToBack cmd = new CmdBringToBack(shapeService,shape.getId());
			commandManager.execute(cmd);
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
		} else if (line.contains("Add")) {
			line = line.replaceAll(" - Add", "");
			shape = Converter.StringToShape(line);
			CmdAdd cmd = new CmdAdd(shapeService,shape);
			commandManager.execute(cmd);
			model.getShapes().clear();
			model.getShapes().addAll(shapeService.getAll());
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);

		} else if (line.contains("Modify to")) {
			String[] shapeDescriptions = line.split(",Modify to ");
			Shape oldShape = Converter.StringToShape(shapeDescriptions[0]);
			shape = Converter.StringToShape(shapeDescriptions[1]);

			ShapeModify shapeModify = new ShapeModify(shapeService, shapeService.read(oldShape.getId()), shape);
			commandManager.execute(shapeModify);
			model.getShapes().clear();
			model.getShapes().addAll(shapeService.getAll());

			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
				
		} else if (line.contains("Deleted")) {
			shape = Converter.StringToShape(line);
			CmdRemove cmdDelete = new CmdRemove(shapeService, shape);
			commandManager.execute(cmdDelete);

			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
		} else if (line.contains("Selected")) {
			shape = Converter.StringToShape(line);
			CmdSelect cmdSelect = new CmdSelect(shapeService, shape.getId());
			commandManager.execute(cmdSelect);

			notifyAllObservers(shapeService.getSelected().size());
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
		} else if (line.contains("Deselected")) {
			shape = Converter.StringToShape(line);
			CmdDeselect cmdDeselect = new CmdDeselect(shapeService, shape.getId());
			commandManager.execute(cmdDeselect);

			notifyAllObservers(shapeService.getSelected().size());
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
		}
		i++;
		if (i == temporarilyLogs.size() || temporarilyLogs.get(i) == null) {
			frame.getBtnNext().setEnabled(false);
		}

		model.getShapes().clear();
		model.getShapes().addAll(shapeService.getAll());
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
			CmdDeselect deselectCommand = new CmdDeselect(shapeService, s.getId());
			commandManager.execute(deselectCommand);
		}
	}
	private boolean changeStatusOfSelectedObject(int x, int y) {
		ArrayList<Shape> shapes = (ArrayList<Shape>) shapeService.getAll();
		for (int i = shapes.size() - 1; i >= 0; i--) {
			Shape shape = shapes.get(i);
			if(!shape.contains(x, y)) {
				continue;
			}
			if(shape.isSelected()) {
				CmdDeselect deselectCommand = new CmdDeselect(shapeService, shape.getId());
				commandManager.execute(deselectCommand);
			}else{
				CmdSelect selectCommand = new CmdSelect(shapeService, shape.getId());
				commandManager.execute(selectCommand);
			}
			return true;
		}
		return false;
	}

	private Shape lineBtnClicked(Point point, Color color) {
		if(frame.startPoint == null) {
			frame.startPoint = point;
			return null;
		}
		Point p1 = frame.startPoint;
		Point p2 = point;


		Shape line = lineDialog(new Line(p1,p2, color), false);
		frame.startPoint = null;
		return line;
	}
}
