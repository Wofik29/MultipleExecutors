package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Game;
import org.wolf.MultipleExecutors.commands.CommandException;

import java.util.ArrayList;

public abstract class Unit implements Executor
{
	protected Game game;
	protected int x;
	protected int y;
	protected int stepX;
	protected int stepY;
	protected Direction direction;
	protected Cell currentCell;

	protected ArrayList<Cell> allowCell;

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	Unit(int x, int y, Game game)
	{
		direction = Direction.Up;
		this.x = x;
		this.y = y;
		stepX = 0;
		stepY = -1;
		this.game = game;

		allowCell = new ArrayList<>();
		allowCell.add(Cell.Ground);

		flipMapCell();
	}

	private void flipMapCell()
	{
		currentCell = game.map[x][y];
		game.map[x][y] = Cell.Unit;
	}

	@Override
	public void checkCell() throws CommandException
	{
		int x = this.x + stepX;
		int y = this.y + stepY;

		if (x > game.widthMap || x < 0 || y > game.heightMap || y < 0) {
			throw new CommandException("Исполнитель не может выходить за границы карты!");
		}

		if (!allowCell.contains(game.map[x][y])) {
			throw new CommandException("Исполнитель не может быть на клетке : " + game.map[x][y].title);
		}
	}

	@Override
	public void stepForward() throws CommandException
	{
		checkCell();
		game.map[x][y] = currentCell;
		this.x += stepX;
		this.y += stepY;
		flipMapCell();
	}

	@Override
	public void stepBack() throws CommandException
	{
		checkCell();
		game.map[x][y] = currentCell;
		this.x -= stepX;
		this.y -= stepY;
		flipMapCell();
	}

	@Override
	public void turnRight()
	{
		switch (direction) {
			case Up:
				direction = Direction.Right;
				stepY = 0;
				stepX = 1;
				break;
			case Right:
				direction = Direction.Down;
				stepY = 1;
				stepX = 0;
				break;
			case Down:
				direction = Direction.Left;
				stepX = -1;
				stepY = 0;
				break;
			case Left:
				direction = Direction.Up;
				stepX = 0;
				stepY = -1;
				break;
		}
	}

	@Override
	public void turnLeft()
	{
		switch (direction) {
			case Up:
				direction = Direction.Left;
				stepY = 0;
				stepX = -1;
				break;
			case Right:
				direction = Direction.Up;
				stepY = -1;
				stepX = 0;
				break;
			case Down:
				direction = Direction.Right;
				stepX = 1;
				stepY = 0;
				break;
			case Left:
				direction = Direction.Down;
				stepX = 0;
				stepY = 1;
				break;
		}
	}


}
