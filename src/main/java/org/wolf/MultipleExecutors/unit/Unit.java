package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Main;
import org.wolf.MultipleExecutors.commands.CommandException;
import org.wolf.MultipleExecutors.commands.Commands;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Unit implements Executor
{
	protected Main game;
	protected int x;
	protected int y;
	protected int stepX;
	protected int stepY;
	protected int current = 0;
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

	@Override
	public void setAlgorithm(HashMap<Integer, String[]> algorithm)
	{
		algorithm.clear();
		algorithm.putAll(algorithm);
	}

	Unit(int x, int y, Main game)
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
	public void step()
	{
		if (this.current > algorithm.size() - 1) {
			return;
		}
		String[] current = algorithm.get(this.current);

		if (current == null) {
			return;
		}

		Commands command = Commands.valueOf(current[0]);

		try {
			switch (command) {
				case forward:
					stepForward();
					break;
				case back:
					stepBack();
					break;
				case turn_left:
					turnLeft();
					break;
				case turn_right:
					turnRight();
					break;
			}
		} catch (CommandException ex) {

		}

		this.current += 1;
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
