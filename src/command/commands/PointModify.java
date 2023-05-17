package command.commands;

import command.Command;
import geometry.Point;
import mvc.DrawingModel;
import observer.SelectedObjects;

public class PointModify implements Command {
	private Point oldState;
	private Point newState;
	private Point original = new Point();
	private DrawingModel model;
	private int index;
	private String nameString;
	private SelectedObjects selectedObjects;
	
	public PointModify(DrawingModel model, Point oldState, Point newState, int index, String nameString, SelectedObjects selectedObjects) {
		this.oldState = oldState;
		this.newState = newState;
		this.nameString = nameString;
		this.model = model;
		this.index = index;
		this.selectedObjects = selectedObjects;
	}
	
	@Override
	public void execute() {
		this.original = oldState.clone();
		this.oldState = newState.clone();
		
		selectedObjects.remove(oldState);
		selectedObjects.add(newState);
		model.remove(oldState);
		model.getShapes().add(index, newState);
	}
	
	@Override
	public void unexecute() {
		this.oldState = original.clone();
		
		selectedObjects.remove(newState);
		selectedObjects.add(oldState);
		model.remove(newState);
		model.getShapes().add(index, oldState);
	}
	
	@Override
	public String getName() {
		return nameString;
	}

}
