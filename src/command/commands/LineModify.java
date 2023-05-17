package command.commands;

import command.Command;
import geometry.Line;
import mvc.DrawingModel;
import observer.SelectedObjects;

public class LineModify implements Command {
	
	private Line oldState;
	private Line newState;
	private Line original = new Line();
	private DrawingModel model;
	private int index;
	private String nameString;
	private SelectedObjects selectedObjects;
	
	public LineModify(DrawingModel model, Line oldState, Line newState, int index, String nameString, SelectedObjects selectedObejcts) {
		this.oldState = oldState;
		this.newState = newState;
		this.nameString = nameString;
		this.model = model;
		this.index = index;
		this.selectedObjects = selectedObejcts;
	}
	
	@Override
	public void execute() {
		original.getStartPoint().setX(oldState.getStartPoint().getX());
		original.getStartPoint().setY(oldState.getStartPoint().getY());
		original.getEndPoint().setX(oldState.getEndPoint().getX());
		original.getEndPoint().setY(oldState.getEndPoint().getY());
		original.setColor(oldState.getColor());
		
		oldState.getStartPoint().setX(newState.getStartPoint().getX());
		oldState.getStartPoint().setY(newState.getStartPoint().getY());
		oldState.getEndPoint().setX(newState.getEndPoint().getX());
		oldState.getEndPoint().setY(newState.getEndPoint().getY());
		oldState.setColor(newState.getColor());
		
		selectedObjects.remove(oldState);
		selectedObjects.add(newState);
		model.remove(oldState);
		model.getShapes().add(index, newState);
	}
	
	@Override 
	public void unexecute() {
		oldState.getStartPoint().setX(original.getStartPoint().getX());
		oldState.getStartPoint().setY(original.getStartPoint().getY());
		oldState.getEndPoint().setX(original.getEndPoint().getX());
		oldState.getEndPoint().setY(original.getEndPoint().getY());
		oldState.setColor(original.getColor());
		
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
