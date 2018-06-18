package org.wolf.MultipleExecutors;

import javafx.scene.paint.Color;

public enum Cell
{
	Ground(Color.BROWN, "ground"),
	Water(Color.AQUA, "water"),
	ExplorerUp(Color.RED, "explorer"),
	ExplorerDown(Color.RED, "explorer"),
	ExplorerLeft(Color.RED, "explorer"),
	ExplorerRight(Color.RED, "explorer"),
	HarvesterUp(Color.GREEN, "harvester"),
	HarvesterDown(Color.GREEN, "harvester"),
	HarvesterLeft(Color.GREEN, "harvester"),
	HarvesterRight(Color.GREEN, "harvester"),
	Center(Color.YELLOW, "center"),
	Plate(Color.GRAY, "plate")
	;

	public Color color;
	public String title;
	public String value;
	public boolean isVisible = true;

	Cell(Color color, String title)
	{
		this.color = color;
		this.title = title;
		value = "";
	}

}
