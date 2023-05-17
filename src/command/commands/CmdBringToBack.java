package command.commands;

import command.Command;
import geometry.Shape;
import mvc.DrawingModel;

public class CmdBringToBack  implements Command {
	
	Shape shape;
	DrawingModel model;
	int index;
	int indexSave;
	String nameString;
	
	
	
	public CmdBringToBack(DrawingModel model, Shape shape, int index, String nameString) {
		this.model = model;
		this.shape = shape;
		this.index = index;
		this.nameString = nameString;
	}
	
	@Override
	public void execute() {
		indexSave = model.getShapes().indexOf(shape);
		if(index != 0) {
			model.getShapes().remove(shape);
			model.getShapes().add(0, shape);
		}
	}
	
	@Override
	public void unexecute() {
		model.getShapes().remove(shape);
		model.getShapes().add(indexSave, shape);
	}
	
	@Override
	public String getName() {
		return nameString;
	}

}
