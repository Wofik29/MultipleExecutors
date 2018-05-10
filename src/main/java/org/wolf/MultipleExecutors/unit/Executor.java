package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.commands.Command;
import org.wolf.MultipleExecutors.commands.CommandException;

public interface Executor
{
	public Command[][] algorithm = {};
	public int current = 0;

	void checkCell() throws CommandException;
	void stepForward() throws CommandException;
	void stepBack() throws CommandException;
	void turnRight();
	void turnLeft();
}
