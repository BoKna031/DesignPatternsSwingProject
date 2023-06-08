package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;

public class ShapeModify implements Command {

	private final Shape oldState;
	private final Shape newState;
	private final IShapeService service;
	private String log;

	public ShapeModify(IShapeService service, Shape oldState, Shape newState) {
		this.oldState = oldState;
		this.newState = newState;
		this.service = service;
	}

	@Override
	public void execute() {

		try {
			service.update(newState);
			log = service.getLastLog();
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
	public String getName() {
		return log;
	}

}
