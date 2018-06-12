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
	While("while", true),
	OnForward("onforward", false, true),
	OnRight("onright", false, true),
	OnLeft("onleft", false, true),
	OnBack("onback", false, true)
	;

	public String userTitle;
	public boolean isControl;
	public boolean isDirection;

	Commands(String name, boolean isControl)
	{
		this.userTitle = name;
		this.isControl = isControl;
		this.isDirection = false;
	}

	Commands(String name, boolean isControl, boolean isDirection)
	{
		this.userTitle = name;
		this.isControl = isControl;
		this.isDirection = isDirection;
	}
}
