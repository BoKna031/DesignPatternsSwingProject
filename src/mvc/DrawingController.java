package mvc;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

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
import geometry.*;
import model.service.IShapeService;
import model.service.ShapeService;
import observer.Observer;
import observer.ObserverUpdate;
import view.ViewService;

public class DrawingController {

	
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


	public void createShape(int x, int y) {
		Color innerColor = frame.getInnerColor();
		Color outerColor = frame.getOuterColor();
		Shape newShape;
		Point p = new Point(x,y, outerColor);
		if (frame.getBtnPoint().isSelected()) {
			newShape = ViewService.pointDialog(p, false);
		} else if (frame.getBtnLine().isSelected()) {
			newShape = lineBtnClicked(p,outerColor);
			if(newShape == null)
				return;
		} else if (frame.getBtnRectangle().isSelected()) {
			newShape = ViewService.rectDialog(new Rectangle(p, 0, 0, innerColor, outerColor), false);
		} else if (frame.getBtnCircle().isSelected()) {
			newShape = ViewService.circleDialog(new Circle(p,0,innerColor,outerColor), false);
		} else if (frame.getBtnDonut().isSelected()) {
			newShape = ViewService.donutDialog(new Donut(p,0,0,innerColor,outerColor), false);
		} else if (frame.getBtnHex().isSelected()) {
			newShape = ViewService.hexDialog(new HexagonAdapter(x, y, 0, innerColor, outerColor), false);
		} else {
			return;
		}

		CmdAdd cmd = new CmdAdd(shapeService, newShape);
		commandManager.execute(cmd);

		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);

		updateView();
		frame.getBtnNext().setEnabled(false);
		
	}

	public void delete(){
		for(Shape s: shapeService.getSelected()){
			CmdRemove cmdRemove = new CmdRemove(shapeService, s);
			commandManager.execute(cmdRemove);
		}
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
		notifyAllObservers(selectedObjects.size());
		updateView();
	}

	public void undo(){
		commandManager.undo();
		frame.getBtnRedo().setEnabled(true);
		if(commandManager.sizeNormal() == 0)
			frame.getBtnUndo().setEnabled(false);
		else
			frame.getBtnUndo().setEnabled(true);
		notifyAllObservers(selectedObjects.size());
		updateView();
	}

	public void redo(){
		commandManager.redo();
		frame.getBtnUndo().setEnabled(true);
		if (commandManager.sizeReverse() == 0)
			frame.getBtnRedo().setEnabled(false);
		else
			frame.getBtnRedo().setEnabled(true);
		notifyAllObservers(selectedObjects.size());
		updateView();
	}

	public void bringToBack(){
		if (shapeService.getSelected().size() != 1) return;

		ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
		Shape shape = selectedShapes.get(0);
		CmdBringToBack cmd = new CmdBringToBack(shapeService, shape.getId());
		commandManager.execute(cmd);

		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
		updateView();
	}

	public void bringToFront(){
		if (shapeService.getSelected().size() != 1) return;

		ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
		Shape shape = selectedShapes.get(0);
		CmdBringToFront cmd = new CmdBringToFront(shapeService, shape.getId());
		commandManager.execute(cmd);

		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
		updateView();
	}

	public void modify() throws Exception{
		ArrayList<Shape> selectedShapes = (ArrayList<Shape>) shapeService.getSelected();
		if(selectedShapes.size() != 1)
			throw new Exception("Izaberite 1 objekat!");
		Shape shape = selectedShapes.get(0);
		Shape newShape;
		switch (shape.getShapeType()) {
			case POINT:
				newShape = ViewService.pointDialog((Point) shape, true);
				break;
			case LINE:
				newShape = ViewService.lineDialog((Line) shape, true);
				break;
			case RECTANGLE:
				newShape = ViewService.rectDialog((Rectangle) shape, true);
				break;
			case CIRCLE:
				newShape = ViewService.circleDialog((Circle) shape, true);
				break;
			case DONUT:
				newShape = ViewService.donutDialog((Donut) shape, true);
				break;
			case HEXAGON:
				newShape = ViewService.hexDialog((HexagonAdapter) shape, true);
				break;
			default:
				throw new Exception("Not valid shape");
		}
		ShapeModify shapeModify = new ShapeModify(shapeService, shape, newShape);
		commandManager.execute(shapeModify);

		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);

		updateView();
	}

	public void toFront() {
		if (shapeService.getSelected().size() == 1) {
			ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
			Shape shape = selectedShapes.get(0);
			CmdToFront cmd = new CmdToFront(shapeService, shape.getId());
			commandManager.execute(cmd);

			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);

			updateView();
		}
	}

	public void toBack() {
		if (shapeService.getSelected().size() == 1) {
			ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
			Shape shape = selectedShapes.get(0);
			CmdToBack cmd = new CmdToBack(shapeService, shape.getId());
			commandManager.execute(cmd);

			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			updateView();
		}
	}

	private void updateView(){
		model.getShapes().clear();
		model.getShapes().addAll(shapeService.getAll());
		frame.getView().repaint();
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
			
		} else if (line.contains("RedoButton")) {
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


	public void select(int x, int y){
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


		Shape line = ViewService.lineDialog(new Line(p1,p2, color), false);
		frame.startPoint = null;
		return line;
	}
}
