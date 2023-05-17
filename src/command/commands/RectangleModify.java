package command.commands;

import command.Command;
import geometry.Rectangle;
import mvc.DrawingModel;
import observer.SelectedObjects;

public class RectangleModify implements Command {
	
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle();
	private DrawingModel model;
	private int index;
	private String nameString;
	private SelectedObjects selectedObjects;
	
	
	public RectangleModify(DrawingModel model, Rectangle oldState, Rectangle newState, int index, String nameString, SelectedObjects selectedObjects) {
		this.oldState = oldState;
		this.newState = newState;
		this.model = model;
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
				original.setWidth(oldState.getWidth());
				original.setHeight(oldState.getHeight());
				original.getUpperLeftPoint().setX(oldState.getUpperLeftPoint().getX());
				original.getUpperLeftPoint().setY(oldState.getUpperLeftPoint().getY());
				
				oldState.setInnerColor(newState.getInnerColor());
				oldState.setOuterColor(newState.getOuterColor());
				oldState.setWidth(newState.getWidth());
				oldState.setHeight(newState.getHeight());
				oldState.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
				oldState.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
				
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
				oldState.setWidth(original.getWidth());
				oldState.setHeight(original.getHeight());
				oldState.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
				oldState.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
				
				
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
