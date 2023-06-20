package command.commands;

import command.Command;
import model.entity.geometry.Shape;
import model.service.IShapeService;
import model.service.LogService;

public class ShapeModify implements Command {

	private final Shape oldState;
	private final Shape newState;
	private final IShapeService service;

	public ShapeModify(IShapeService service, Shape oldState, Shape newState) {
		this.oldState = oldState;
		this.newState = newState;
		this.service = service;
	}

	@Override
	public void execute() {

		try {
			service.update(newState);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void unexecute() {
		try {
			service.update(oldState);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getLog() {
		return LogService.modify(oldState, newState);
	}

}
