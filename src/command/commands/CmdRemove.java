package command.commands;

import command.Command;
import model.entity.geometry.Shape;
import model.service.IShapeService;
import model.service.LogService;

public class CmdRemove implements Command {

	private IShapeService service;
	private Shape shape;
	
	
	public CmdRemove(IShapeService service, Shape shape) {
		this.service = service;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		service.delete(shape.getId());
	}
	
	@Override
	public void unexecute() {
		service.create(shape);
	}

	@Override
	public String getLog() {
		return LogService.remove(shape.getId());
	}


}
