package command.commands;

import command.Command;
import model.service.IShapeService;
import model.service.LogService;

public class CmdDeselect implements Command {
	String shapeId;
	IShapeService shapeService;
	
	public CmdDeselect(IShapeService shapeService, String shapeId) {
		this.shapeId = shapeId;
		this.shapeService = shapeService;
	}
	
	@Override
	public void execute() {
		try {
			shapeService.deselect(shapeId);
		} catch (NoSuchFieldException e) {
			e.getMessage();
		}
	}
	
	@Override
	public void unexecute() {
		try {
			shapeService.select(shapeId);
		} catch (NoSuchFieldException e) {
			e.getMessage();
		}
	}

	@Override
	public String getLog() {
		return LogService.deselect(shapeId);
	}


}
