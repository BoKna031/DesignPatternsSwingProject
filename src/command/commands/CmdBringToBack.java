package command.commands;

import command.Command;
import model.service.IShapeService;
import model.service.LogService;

public class CmdBringToBack  implements Command {
	
	private final String shapeId;
	private final IShapeService service;
	private int oldIndex;

	public CmdBringToBack(IShapeService service, String shapeId) {
		this.service = service;
		this.shapeId = shapeId;
	}
	
	@Override
	public void execute() {
		oldIndex = service.bringBack(shapeId);
	}
	
	@Override
	public void unexecute() {
		service.placeTo(shapeId, oldIndex);
	}

	@Override
	public String getLog() {
		return LogService.bringBack(shapeId);
	}


}
