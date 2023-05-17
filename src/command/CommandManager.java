package command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import geometry.Shape;
import mvc.DrawingFrame;

public class CommandManager {
	
	private static CommandManager instance = null;
	private Stack<Command> stackNormal;
	private Stack<Command> stackReverse;
	private DrawingFrame frame;
	
	private List<String> commandHistory;
	private List<Shape> shapes = new ArrayList<Shape>();
	
	public static CommandManager getInstance() {
		if(instance == null)
			instance = new CommandManager();
		return instance;
	}
	
	private CommandManager() {
		stackNormal = new Stack<>();
		stackReverse = new Stack<>();
		commandHistory = new ArrayList<>();
		
	}
	
	public void execute(Command command) {
		command.execute();
		stackNormal.push(command);
		commandHistory.add(command.getName());
		stackReverse.clear();
		
		//logging
		frame.getLogArea().append(command.getName()+"\n");
	}
	
	public void undo() {
		if (!stackNormal.isEmpty()) {
			Command command = stackNormal.pop();
			stackReverse.push(command);
			commandHistory.add(command.getName() + " - Undo");
			command.unexecute();
			
		//logging
		frame.getLogArea().append(command.getName() + " - Undo\n");
			
		}
	}
	
	public void redo() {
		if(!stackReverse.isEmpty()) {
			Command command = stackReverse.pop();
			stackNormal.push(command);
			commandHistory.add(command.getName() + " - Redo");
			command.execute();
			
			//logging
			frame.getLogArea().append(command.getName()+ " - Redo\n"); 
		}
	}
	
	public void clearNormal() {
		stackNormal.clear();
	}
	
	public void clearReverse() {
		stackReverse.clear();
	}
	
	public List<String> getActionHistory() {
		return commandHistory;
	}
	
	public int sizeNormal() {
		return stackNormal.size();
	}
	
	public int sizeReverse() {
		return stackReverse.size();
	}
	
	public void setFrame(DrawingFrame frame) {
		this.frame = frame;
	}

}
