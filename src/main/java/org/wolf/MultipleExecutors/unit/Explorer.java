package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Main;

public class Explorer extends Unit
{
	public Explorer(int x, int y, Direction direction, Main game)
	{
		super(x, y, direction, game);
		cellWithDirection.put(Direction.Up, Cell.ExplorerUp);
		cellWithDirection.put(Direction.Down, Cell.ExplorerDown);
		cellWithDirection.put(Direction.Right, Cell.ExplorerRight);
		cellWithDirection.put(Direction.Left, Cell.ExplorerLeft);
	}
}
