package command.commands;

import command.Command;
import geometry.HexagonAdapter;
import mvc.DrawingModel;
import observer.SelectedObjects;

public class HexModify implements Command {
	
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter();
	private DrawingModel model;
	private int index;
	private String nameString;
	private SelectedObjects selectedObjects;

	
	public HexModify(DrawingModel model, HexagonAdapter oldState, HexagonAdapter newState, int index, String nameString, SelectedObjects selectedObjects) {
		this.oldState = oldState;
		this.newState = newState;
		this.model = model;
		this.nameString = nameString;
		this.model = model;
		this.index = index;
		this.selectedObjects = selectedObjects;
	}
	
	@Override
	public void execute() {
		try {
			original.setX(oldState.getX());
			original.setY(oldState.getY());
			original.setR(oldState.getR());
			original.setInnerColor(oldState.getInnerColor());
			original.setOuterColor(oldState.getOuterColor());
			
			
			oldState.setX(newState.getX());
			oldState.setY(newState.getY());
			oldState.setR(newState.getR());
			oldState.setInnerColor(newState.getInnerColor());
			oldState.setOuterColor(newState.getOuterColor());
			
			
			selectedObjects.remove(oldState);
			selectedObjects.add(newState);
			model.remove(oldState);
			model.getShapes().add(index, newState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void unexecute() {
		try {
			oldState.setX(original.getX());
			oldState.setY(original.getY());
			oldState.setR(original.getR());
			oldState.setInnerColor(original.getInnerColor());
			oldState.setOuterColor(original.getOuterColor());
			
			
			selectedObjects.remove(newState);
			selectedObjects.add(oldState);
			model.remove(newState);
			model.getShapes().add(index, oldState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return nameString;
	}
	
}
