package org.wolf.MultipleExecutors.commands;

import org.wolf.MultipleExecutors.unit.Executor;

public interface Command
{
	boolean execute(Executor executor);
}
