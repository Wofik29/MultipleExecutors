package org.wolf.MultipleExecutors;

import javafx.scene.paint.Color;

public enum Cell
{
	Ground(Color.BROWN, "Земля"),
	Water(Color.AQUA, "Вода"),
	UnitUp(Color.RED, "Исполнитель"),
	UnitDown(Color.RED, "Исполнитель"),
	UnitLeft(Color.RED, "Исполнитель"),
	UnitRight(Color.RED, "Исполнитель"),
	Center(Color.BLACK, "Командный центр");

	public Color color;
	public String title;
	Cell(Color color, String title)
	{
		this.color = color;
		this.title = title;
	}

}
