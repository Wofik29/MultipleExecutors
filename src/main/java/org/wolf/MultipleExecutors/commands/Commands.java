package org.wolf.MultipleExecutors.commands;

public enum Commands
{
	forward("forward", false), turn_left("left", false), turn_right("right", false), back("back", false);

	public String userTitle;
	public boolean isControl;

	Commands(String name, boolean isControl)
	{
		this.userTitle = name;
		this.isControl = isControl;
	}
}
