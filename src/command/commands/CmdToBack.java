package command.commands;

import command.Command;
import geometry.Shape;
import mvc.DrawingModel;

public class CmdToBack implements Command {
	
	Shape shape;
	DrawingModel model;
	int index;
	String nameString;
	
	public CmdToBack(DrawingModel model, Shape shape, int index, String nameString) {
		this.model = model;
		this.shape = shape;
		this.index = index;
		this.nameString = nameString;
	}
	
	@Override
	public void execute() {
		index--;
		if(index >= 0) {
			model.getShapes().remove(shape);
			model.getShapes().add(index, shape);
		}
	}
	
	@Override
	public void unexecute() {
		index++;
		model.getShapes().remove(shape);
		model.getShapes().add(index, shape);
	}
	
	@Override
	public String getName() {
		return nameString;
	}

}
