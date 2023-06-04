package command.commands;

import command.Command;
import geometry.Shape;
import model.service.IShapeService;
import observer.SelectedObjects;

public class ShapeModify implements Command {

	private final Shape oldState;
	private final Shape newState;
	private final IShapeService service;
	private String log;
	private final SelectedObjects selectedObjects;

	public ShapeModify(IShapeService service, Shape oldState, Shape newState,
					   SelectedObjects selectedObjects) {
		this.oldState = oldState;
		this.newState = newState;
		this.service = service;
		this.selectedObjects = selectedObjects;
	}

	@Override
	public void execute() {

		try {
			selectedObjects.remove(oldState);
			selectedObjects.add(newState);
			service.update(newState);
			log = service.getLastLog();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void unexecute() {
		try {
			selectedObjects.remove(newState);
			selectedObjects.add(oldState);
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
