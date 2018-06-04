package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.commands.CommandException;

import java.util.HashMap;

public interface Executor
{
	HashMap<Integer, String[]> algorithm = new HashMap<>();

	void setAlgorithm(HashMap<Integer, String[]> algorithm);
	void step();
	void checkCell() throws CommandException;
	void stepForward() throws CommandException;
	void stepBack() throws CommandException;
	void turnRight();
	void turnLeft();
}
