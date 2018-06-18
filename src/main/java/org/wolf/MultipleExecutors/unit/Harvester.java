package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Main;

public class Harvester extends Unit
{
	public Harvester(int x, int y, Direction direction, Main game)
	{
		super(x, y, direction, game);
		cellWithDirection.put(Direction.Up, Cell.HarvesterUp);
		cellWithDirection.put(Direction.Down, Cell.HarvesterDown);
		cellWithDirection.put(Direction.Right, Cell.HarvesterRight);
		cellWithDirection.put(Direction.Left, Cell.HarvesterLeft);
	}
}
