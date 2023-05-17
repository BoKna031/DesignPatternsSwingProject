package command.commands;

import command.Command;
import geometry.Donut;
import mvc.DrawingModel;
import observer.SelectedObjects;

public class DonutModify implements Command {

	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();
	private DrawingModel model;
	private int index;
	private String nameString;
	private SelectedObjects selectedObjects;
	
	public DonutModify(DrawingModel model, Donut oldState, Donut newState, int index, String nameString, SelectedObjects selectedObjects) {
		this.oldState = oldState;
		this.newState = newState;
		this.nameString = nameString;
		this.model = model;
		this.index = index;
		this.selectedObjects = selectedObjects;
	}

	@Override
	public void execute() {
		
		try {
			original.setInnerColor(oldState.getInnerColor());
			original.setOuterColor(oldState.getOuterColor());
			original.setRadius(oldState.getRadius());
			original.setInnerRadius(oldState.getInnerRadius());
			original.getCenter().setX(oldState.getCenter().getX());
			original.getCenter().setY(oldState.getCenter().getY());
			
			oldState.setInnerColor(newState.getInnerColor());
			oldState.setOuterColor(newState.getOuterColor());
			oldState.setRadius(newState.getRadius());
			oldState.setInnerRadius(newState.getInnerRadius());
			oldState.getCenter().setX(newState.getCenter().getX());
			oldState.getCenter().setY(newState.getCenter().getY());
			
			selectedObjects.remove(oldState);
			selectedObjects.add(newState);
			model.remove(oldState);
			model.getShapes().add(index, newState);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void unexecute() {
		
		try {
			oldState.setInnerColor(original.getInnerColor());
			oldState.setOuterColor(original.getOuterColor());
			oldState.setRadius(original.getRadius());
			oldState.setInnerRadius(original.getInnerRadius());
			oldState.getCenter().setX(original.getCenter().getX());
			oldState.getCenter().setY(original.getCenter().getY());
			
			selectedObjects.remove(newState);
			selectedObjects.add(oldState);
			model.remove(newState);
			model.getShapes().add(index, oldState);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getName() {
		return nameString;
	}

}
