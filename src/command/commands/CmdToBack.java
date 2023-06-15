package command.commands;

import command.Command;
import model.service.IShapeService;

public class CmdToBack implements Command {
	
	private final String shapeId;
	private final IShapeService service;
	
	public CmdToBack(IShapeService service, String shapeId) {
		this.service = service;
		this.shapeId = shapeId;
	}
	
	@Override
	public void execute() {
		service.toBack(shapeId);
	}
	
	@Override
	public void unexecute() {
		service.toFront(shapeId);
	}


}
