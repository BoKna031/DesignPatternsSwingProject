package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;

public class CmdAdd implements Command {
	
	private IShapeService service;
	private Shape shape;
	private String nameString;
	
	public CmdAdd(IShapeService service, Shape shape) {
		this.service = service;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		service.create(shape);
		nameString = service.getLastLog();
	}
	
	@Override
	public void unexecute() {
		service.delete(shape.getId());
		nameString = service.getLastLog();
	}
	
	@Override
	public String getName() {
		return nameString;
	}

}
