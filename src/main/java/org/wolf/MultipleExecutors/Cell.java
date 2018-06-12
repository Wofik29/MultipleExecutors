package org.wolf.MultipleExecutors;

import javafx.scene.paint.Color;

public enum Cell
{
	Ground(Color.BROWN, "ground"),
	Water(Color.AQUA, "water"),
	UnitUp(Color.RED, "executor"),
	UnitDown(Color.RED, "executor"),
	UnitLeft(Color.RED, "executor"),
	UnitRight(Color.RED, "executor"),
	Center(Color.BLACK, "center"),
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
