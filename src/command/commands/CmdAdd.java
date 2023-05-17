package command.commands;

import command.Command;
import geometry.Shape;
import mvc.DrawingModel;

public class CmdAdd implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private String nameString;
	
	public CmdAdd(DrawingModel model, Shape shape, String nameString) {
		this.model = model;
		this.shape = shape;
		this.nameString = nameString;
	}
	
	@Override
	public void execute() {
		model.add(shape);
	}
	
	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	@Override
	public String getName() {
		return nameString;
	}

}
