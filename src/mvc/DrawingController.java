package mvc;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
import model.entity.Converter;
import model.entity.geometry.*;
import model.service.IShapeService;
import model.service.ShapeService;
import mvc.components.buttons.ButtonType;
import observer.Observer;
import observer.ObserverUpdate;
import view.ViewService;

public class DrawingController {
	private final DrawingFrame frame;

	private final IShapeService shapeService;
	CommandManager commandManager = CommandManager.getInstance();
	Queue<String> temporarilyLogs;
	ObserverUpdate observerUpdate;
	Observer observer =  new Observer();

	
	public DrawingController(DrawingFrame frame, IShapeService shapeService) {
		this.shapeService = shapeService;
		this.frame = frame;
		observerUpdate = new ObserverUpdate(frame);
		observer.addPropertyChangeListener(observerUpdate);
	}


	public void createShape(int x, int y) {
		Color innerColor = frame.getInnerColor();
		Color outerColor = frame.getOuterColor();
		Shape newShape;
		Point p = new Point(x,y, outerColor);
		switch (frame.getSelectedButtonShape()){
			case POINT: newShape = ViewService.pointDialog(p, false); break;
			case LINE:
				newShape = lineBtnClicked(p,outerColor);
				break;
			case RECTANGLE:
				newShape = ViewService.rectDialog(new Rectangle(p, 0, 0, innerColor, outerColor), false);
				break;
			case CIRCLE:
				newShape = ViewService.circleDialog(new Circle(p,0,innerColor,outerColor), false);
				break;
			case DONUT:
				newShape = ViewService.donutDialog(new Donut(p,0,0,innerColor,outerColor), false);
				break;
			case HEXAGON:
				newShape = ViewService.hexDialog(new HexagonAdapter(x, y, 0, innerColor, outerColor), false);
				break;
			default:
				return;
		}
		if(newShape == null)
			return;

		CmdAdd cmd = new CmdAdd(shapeService, newShape);
		commandManager.execute(cmd);
		frame.appendLog(commandManager.getLastLog());

		frame.enableButton(ButtonType.UNDO, true);
		frame.enableButton(ButtonType.REDO, false);
		frame.enableButton(ButtonType.NEXT, false);
		frame.updateView();
	}

	public void delete(){
		for(Shape s: shapeService.getSelected()){
			CmdRemove cmdRemove = new CmdRemove(shapeService, s);
			commandManager.execute(cmdRemove);
			frame.appendLog(commandManager.getLastLog());
		}
		frame.enableButton(ButtonType.UNDO, true);
		frame.enableButton(ButtonType.REDO, false);
		notifyAllObservers(shapeService.getSelected().size());

		frame.updateView();
	}

	public void undo(){
		commandManager.undo();
		frame.appendLog(commandManager.getLastLog());

		frame.enableButton(ButtonType.REDO, true);
		frame.enableButton(ButtonType.UNDO, commandManager.isUndoAvailable());
		notifyAllObservers(shapeService.getSelected().size());
		frame.updateView();
	}

	public void redo(){
		commandManager.redo();
		frame.appendLog(commandManager.getLastLog());

		frame.enableButton(ButtonType.UNDO, true);
		frame.enableButton(ButtonType.REDO, commandManager.isRedoAvailable());
		notifyAllObservers(shapeService.getSelected().size());
		frame.updateView();
	}

	public void bringToBack(){
		if (shapeService.getSelected().size() != 1) return;

		ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
		Shape shape = selectedShapes.get(0);
		CmdBringToBack cmd = new CmdBringToBack(shapeService, shape.getId());
		commandManager.execute(cmd);
		frame.appendLog(commandManager.getLastLog());

		frame.enableButton(ButtonType.UNDO, true);
		frame.enableButton(ButtonType.REDO, false);
		frame.updateView();
	}

	public void bringToFront(){
		if (shapeService.getSelected().size() != 1) return;

		ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
		Shape shape = selectedShapes.get(0);
		CmdBringToFront cmd = new CmdBringToFront(shapeService, shape.getId());
		commandManager.execute(cmd);
		frame.appendLog(commandManager.getLastLog());

		frame.enableButton(ButtonType.UNDO, true);
		frame.enableButton(ButtonType.REDO, false);
		frame.updateView();
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
		if(newShape == null)
			return;

		ShapeModify shapeModify = new ShapeModify(shapeService, shape, newShape);
		commandManager.execute(shapeModify);
		frame.appendLog(commandManager.getLastLog());

		frame.enableButton(ButtonType.UNDO, true);
		frame.enableButton(ButtonType.REDO, false);
		frame.updateView();
	}

	public void toFront() {
		if (shapeService.getSelected().size() == 1) {
			ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
			Shape shape = selectedShapes.get(0);
			CmdToFront cmd = new CmdToFront(shapeService, shape.getId());
			commandManager.execute(cmd);
			frame.appendLog(commandManager.getLastLog());

			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
			frame.updateView();
		}
	}

	public void toBack() {
		if (shapeService.getSelected().size() == 1) {
			ArrayList<Shape> selectedShapes = (ArrayList<Shape>)shapeService.getSelected();
			Shape shape = selectedShapes.get(0);
			CmdToBack cmd = new CmdToBack(shapeService, shape.getId());
			commandManager.execute(cmd);
			frame.appendLog(commandManager.getLastLog());

			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
			frame.updateView();
		}
	}

	public IShapeService getShapeService(){
		return shapeService;
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

	public void load(File fileToLoad) throws ClassNotFoundException, IOException {
		frame.enableButton(ButtonType.NEXT, false);
		frame.clearLogArea();
		shapeService.readFromFile(fileToLoad);
		commandManager.clear();
		frame.enableButton(ButtonType.UNDO, false);
		frame.enableButton(ButtonType.REDO, false);

		frame.updateView();

		for(String log: shapeService.getAllLogs()){
			frame.appendLog(log); //TODO
		}
	}
	
	public void loadOneByOne(File file) throws IOException {
		frame.enableButton(ButtonType.NEXT, true);
		frame.enableButton(ButtonType.UNDO, false);
		frame.enableButton(ButtonType.REDO, false);

		commandManager.clear();

		notifyAllObservers(0);
		frame.clearLogArea();
		frame.updateView();

		try {
			ShapeService tempService = new ShapeService();
			tempService.readFromFile(file);

			temporarilyLogs = new LinkedList<>(tempService.getAllLogs());
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void loadNext() {
		String line = temporarilyLogs.poll();
		if(line == null){
			frame.enableButton(ButtonType.NEXT, false);
			return;
		}
		Shape shape;
		if (line.contains("Undo")) {
			commandManager.undo();
			notifyAllObservers(shapeService.getSelected().size());
		} else if (line.contains("RedoButton")) {
			commandManager.redo();
			notifyAllObservers(shapeService.getSelected().size());
		} else if (line.contains("To Front")) {
			shape = Converter.StringToShape(line);
			CmdToFront cmd = new CmdToFront(shapeService,shape.getId());
			commandManager.execute(cmd);
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
		} else if (line.contains("To Back")) {
			shape = Converter.StringToShape(line);
			CmdToBack cmd = new CmdToBack(shapeService,shape.getId());
			commandManager.execute(cmd);

			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
		} else if (line.contains("Bring Front")) {
			shape = Converter.StringToShape(line);
			CmdBringToFront cmd = new CmdBringToFront(shapeService,shape.getId());
			commandManager.execute(cmd);
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
		} else if (line.contains("Bring Back")) {
			shape = Converter.StringToShape(line);
			CmdBringToBack cmd = new CmdBringToBack(shapeService,shape.getId());
			commandManager.execute(cmd);
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
		} else if (line.contains("Add")) {
			line = line.replaceAll(" - Add", "");
			shape = Converter.StringToShape(line);
			CmdAdd cmd = new CmdAdd(shapeService,shape);
			commandManager.execute(cmd);
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);

		} else if (line.contains("Modify to")) {
			String[] shapeDescriptions = line.split(",Modify to ");
			Shape oldShape = Converter.StringToShape(shapeDescriptions[0]);
			shape = Converter.StringToShape(shapeDescriptions[1]);

			ShapeModify shapeModify = new ShapeModify(shapeService, shapeService.read(oldShape.getId()), shape);
			commandManager.execute(shapeModify);
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
				
		} else if (line.contains("Deleted")) {
			shape = Converter.StringToShape(line);
			CmdRemove cmdDelete = new CmdRemove(shapeService, shape);
			commandManager.execute(cmdDelete);
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
		} else if (line.contains("Selected")) {
			shape = Converter.StringToShape(line);
			CmdSelect cmdSelect = new CmdSelect(shapeService, shape.getId());
			commandManager.execute(cmdSelect);

			notifyAllObservers(shapeService.getSelected().size());
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
		} else if (line.contains("Deselected")) {
			shape = Converter.StringToShape(line);
			CmdDeselect cmdDeselect = new CmdDeselect(shapeService, shape.getId());
			commandManager.execute(cmdDeselect);

			notifyAllObservers(shapeService.getSelected().size());
			frame.enableButton(ButtonType.UNDO, true);
			frame.enableButton(ButtonType.REDO, false);
		}
		frame.enableButton(ButtonType.NEXT, temporarilyLogs.size() > 0);
		frame.appendLog(commandManager.getLastLog());
		frame.updateView();
	}


	public void select(int x, int y){
		if(shapeService.getAll() == null || shapeService.getAll().size() == 0)
			return;

		boolean isObjectFound = changeStatusOfSelectedObject(x, y);

		if(isObjectFound)
			frame.appendLog(commandManager.getLastLog());
		else	//if user clicks on background
			deselectAllObjects();

		notifyAllObservers(shapeService.getSelected().size());
		frame.updateView();
	}

	private void deselectAllObjects(){
		for (Shape s : shapeService.getSelected()) {
			CmdDeselect deselectCommand = new CmdDeselect(shapeService, s.getId());
			commandManager.execute(deselectCommand);
			frame.appendLog(commandManager.getLastLog());
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

		Shape line = ViewService.lineDialog(new Line(p1,point, color), false);
		frame.startPoint = null;
		return line;
	}
}
