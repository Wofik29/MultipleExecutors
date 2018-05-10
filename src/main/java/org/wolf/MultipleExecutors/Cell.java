package org.wolf.MultipleExecutors;

import javafx.scene.paint.Color;

public enum Cell
{
	Ground(Color.BROWN, "Земля"),
	Water(Color.AQUA, "Вода"),
	Unit(Color.RED, "Исполнитель");

	public Color color;
	public String title;
	Cell(Color color, String title)
	{
		this.color = color;
		this.title = title;
	}

}
