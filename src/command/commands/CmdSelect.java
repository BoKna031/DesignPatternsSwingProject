package command.commands;

import command.Command;
import model.service.IShapeService;

public class CmdSelect implements Command {

	String shapeId;
	IShapeService shapeService;
	String nameString;


	public CmdSelect(IShapeService shapeService, String shapeId) {
		this.shapeId = shapeId;
		this.shapeService = shapeService;
	}

	@Override
	public void execute() {
		try {
			shapeService.select(shapeId);
			nameString = shapeService.getLastLog();
		} catch (NoSuchFieldException e) {
			e.getMessage();
		}
	}

	@Override
	public void unexecute() {
		try {
			shapeService.deselect(shapeId);
			nameString = shapeService.getLastLog();
		} catch (NoSuchFieldException e) {
			e.getMessage();
		}
	}

	@Override
	public String getName() {
		return nameString;
	}

}
