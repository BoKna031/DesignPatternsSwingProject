package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;
import observer.SelectedObjects;

import java.util.List;

public class CmdSelect implements Command {

	String shapeId;
	IShapeService shapeService;
	String nameString;


	public CmdSelect(String shapeId, IShapeService shapeService) {
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
