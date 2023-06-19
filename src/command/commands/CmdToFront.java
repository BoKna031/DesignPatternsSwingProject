package command.commands;

import command.Command;
import model.service.IShapeService;

public class CmdToFront implements Command {
	
	private final String shapeId;
	private final IShapeService service;
	
	public CmdToFront(IShapeService service, String shapeId) {
		this.service = service;
		this.shapeId = shapeId;
	}
	
	@Override
	public void execute() {
		service.toFront(shapeId);
	}
	
	@Override
	public void unexecute( ) {
		service.toBack(shapeId);
	}

}
