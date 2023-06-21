package command.commands;

import command.Command;
import model.entity.geometry.Shape;
import model.service.IShapeService;
import model.service.LogService;

public class CmdRemove implements Command {

	private final IShapeService service;
	private final String shapeId;
	private Shape oldShape;
	
	public CmdRemove(IShapeService service, String shapeId) {
		this.service = service;
		this.shapeId = shapeId;
	}
	
	@Override
	public void execute() {
		oldShape = service.delete(shapeId);
	}
	
	@Override
	public void unexecute() {
		service.create(oldShape);
	}

	@Override
	public String getLog() {
		return LogService.remove(shapeId);
	}


}
