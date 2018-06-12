package org.wolf.MultipleExecutors.commands;

import org.wolf.MultipleExecutors.unit.Executor;

public class Forward implements Command
{
	@Override
	public boolean execute(Executor executor)
	{
		//executor.stepForward();
		return true;
	}
}
