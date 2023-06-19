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
	
	public void clear() {
		stackNormal.clear();
		stackReverse.clear();
	}
	
	public boolean isUndoAvailable() {
		return stackNormal.size() > 0;
	}

	public boolean isRedoAvailable() {
		return stackReverse.size() > 0;
	}
	
	public void setFrame(DrawingFrame frame) {
		this.frame = frame;
	}

}
