package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;
import mvc.DrawingModel;

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
