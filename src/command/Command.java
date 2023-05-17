package command;

public interface Command {
	public void execute();
	public void unexecute();
	public String getName();//Izbaciti, mislim da sluzi za log aktivnosti

}
