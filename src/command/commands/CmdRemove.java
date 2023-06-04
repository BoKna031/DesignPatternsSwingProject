package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;
import mvc.DrawingModel;
import observer.SelectedObjects;

public class CmdRemove implements Command {

	private IShapeService service;
	private Shape shape;
	private String nameString;
	
	
	public CmdRemove(IShapeService service, Shape shape) {
		this.service = service;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		service.delete(shape.getId());
		nameString = service.getLastLog();
	}
	
	@Override
	public void unexecute() {
		service.create(shape);
		nameString = service.getLastLog();
	}
	
	@Override
	public String getName() {
		return nameString;
	}

}
