package org.wolf.MultipleExecutors.commands;

public enum Commands
{
	Forward("forward", false),
	TurnLeft("left", false),
	TurnRight("right", false),
	Back("back", false),
	End("end", true),
	If("if", true),
	Else("else", true),
	While("while", true);

	public String userTitle;
	public boolean isControl;

	Commands(String name, boolean isControl)
	{
		this.userTitle = name;
		this.isControl = isControl;
	}
}
