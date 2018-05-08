package org.wolf.MultipleExecutors;

import javafx.scene.paint.Color;

public enum Cell
{
	Ground(Color.BROWN),
	Water(Color.AQUA);

	Color color;
	Cell(Color color)
	{
		this.color = color;
	}

}
