package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;

public class CmdAdd implements Command {
	
	private final IShapeService service;
	private final Shape shape;
	
	public CmdAdd(IShapeService service, Shape shape) {
		this.service = service;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		service.create(shape);
	}
	
	@Override
	public void unexecute() {
		service.delete(shape.getId());
	}


}
