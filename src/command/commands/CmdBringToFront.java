package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;
import mvc.DrawingModel;

public class CmdBringToFront implements Command {

	private final String shapeId;
	private final IShapeService service;

	private int oldIndex;

	public CmdBringToFront(IShapeService service, String shapeId) {
		this.service = service;
		this.shapeId = shapeId;
	}

	@Override
	public void execute() {
		oldIndex = service.bringFront(shapeId);
	}

	@Override
	public void unexecute() {
		service.placeTo(shapeId, oldIndex);
	}


}


