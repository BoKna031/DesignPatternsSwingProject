package command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandManager {
	
	private static CommandManager instance = null;
	private Stack<Command> executedCommands;
	private Stack<Command> unexecutedCommands;
	private List<String> logs;

	public static CommandManager getInstance() {
		if(instance == null)
			instance = new CommandManager();
		return instance;
	}
	
	private CommandManager() {
		executedCommands = new Stack<>();
		unexecutedCommands = new Stack<>();
		logs = new ArrayList<>();
	}
	
	public void execute(Command command) {
		command.execute();
		logs.add(command.getLog());
		executedCommands.push(command);
		unexecutedCommands.clear();
	}
	
	public void undo() {
		if (executedCommands.isEmpty())
			return;
		Command command = executedCommands.pop();
		logs.add(command.getLog() + " - Undo");
		unexecutedCommands.push(command);
		command.unexecute();
	}
	
	public void redo() {
		if(unexecutedCommands.isEmpty())
			return;
		Command command = unexecutedCommands.pop();
		logs.add(command.getLog() + " - Redo");
		executedCommands.push(command);
		command.execute();

	}
	
	public void clear() {
		executedCommands.clear();
		unexecutedCommands.clear();
		logs.clear();
	}

	public String getLastLog(){
		if (!logs.isEmpty()){
			return logs.get(logs.size() - 1);
		}
		return null;
	}

	public List<String> getLogs() {
		return logs;
	}

	public boolean isUndoAvailable() {
		return executedCommands.size() > 0;
	}

	public boolean isRedoAvailable() {
		return unexecutedCommands.size() > 0;
	}


	public void setLogs(List<String> logs) {
		this.logs = logs;
	}
}
