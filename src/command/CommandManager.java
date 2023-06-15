package command;

import java.util.Stack;
import mvc.DrawingFrame;

public class CommandManager {
	
	private static CommandManager instance = null;
	private Stack<Command> stackNormal;
	private Stack<Command> stackReverse;
	private DrawingFrame frame;

	public static CommandManager getInstance() {
		if(instance == null)
			instance = new CommandManager();
		return instance;
	}
	
	private CommandManager() {
		stackNormal = new Stack<>();
		stackReverse = new Stack<>();
		
	}
	
	public void execute(Command command) {
		command.execute();
		stackNormal.push(command);
		stackReverse.clear();
	}
	
	public void undo() {
		if (!stackNormal.isEmpty()) {
			Command command = stackNormal.pop();
			stackReverse.push(command);
			command.unexecute();

			
		}
	}
	
	public void redo() {
		if(!stackReverse.isEmpty()) {
			Command command = stackReverse.pop();
			stackNormal.push(command);
			command.execute();
		}
	}
	
	public void clearNormal() {
		stackNormal.clear();
	}
	
	public void clearReverse() {
		stackReverse.clear();
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
