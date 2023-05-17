package command.commands;

import command.Command;
import geometry.Shape;
import observer.SelectedObjects;

public class CmdDeselect implements Command {
	
	
	Shape shape;
	SelectedObjects selectedObjects;
	String nameString;
	
	
	public CmdDeselect(Shape shape, SelectedObjects selectedObjects, String nameString) {
		this.shape = shape;
		this.selectedObjects = selectedObjects;
		this.nameString = nameString;
	}
	
	@Override
	public void execute() {
		shape.setSelected(false);
		selectedObjects.remove(shape);
	}
	
	@Override
	public void unexecute() {
		shape.setSelected(true);
		selectedObjects.add(shape);
	}
	
	@Override
	public String getName() {
		return nameString;
	}

}
