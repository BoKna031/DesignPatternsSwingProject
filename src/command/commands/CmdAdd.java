package command.commands;

import command.Command;
import model.entity.geometry.Shape;
import model.service.IShapeService;
import model.service.LogService;

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

	@Override
	public String getLog() {
		return LogService.add(shape);
	}


}
