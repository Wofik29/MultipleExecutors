package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Main;
import org.wolf.MultipleExecutors.commands.CommandException;
import org.wolf.MultipleExecutors.commands.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class Unit implements Executor
{
	public HashMap<Direction, Cell> cellWithDirection = new HashMap<>();
	protected HashMap<Integer, String[]> algorithm = new HashMap<>();

	protected Main game;
	protected int x;
	protected int y;
	protected int stepX;
	protected int stepY;
	protected int startX;
	protected int startY;
	protected int current = 0;
	protected Direction direction;
	protected Cell currentCell;
	protected boolean visible = false;

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
	public void reset()
	{
		current = 0;
		game.map[x][y] = currentCell;
		x = startX;
		y = startY;
		flipMapCell();
	}

	@Override
	public boolean isEnd()
	{
		return this.current > algorithm.size();
	}

	@Override
	public void setAlgorithm(HashMap<Integer, String[]> algorithm)
	{
		this.algorithm.clear();
		this.algorithm.putAll(algorithm);
	}

	Unit(int x, int y, Direction direction, Main game)
	{
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.game = game;

		switch (direction) {
			case Up:
				stepX = 0;
				stepY = -1;
				break;
			case Left:
				stepX = -1;
				stepY = 0;
				break;
			case Right:
				stepX = 1;
				stepY = 0;
				break;
			case Down:
				stepX = 0;
				stepY = 1;
				break;
		}

		allowCell = new ArrayList<>();
		allowCell.add(Cell.Ground);
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
		if (visible) {
			flipMapCell();
		} else {
			game.map[x][y] = currentCell;
		}
	}

	private void flipMapCell()
	{
		currentCell = game.map[x][y];
		game.map[x][y] = cellWithDirection.get(direction);
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
		if (isEnd()) {
			return;
		}
		String[] current = algorithm.get(this.current);
		String[] condition;

		if (current == null) {
			this.current++;
			return;
		}

		Commands command = Commands.valueOf(current[0]);

		try {
			switch (command) {
				case Forward:
					stepForward();
					this.current += 1;
					break;
				case Back:
					stepBack();
					this.current += 1;
					break;
				case TurnLeft:
					turnLeft();
					this.current += 1;
					break;
				case TurnRight:
					turnRight();
					this.current += 1;
					break;
				case If:
					condition = current[1].split(",");
					if (Commands.valueOf(condition[1]) == Commands.True) {
						this.current += 1;
					} else {
						Cell cell = Cell.valueOf(condition[0]);
						Commands direction = Commands.valueOf(condition[1]);
						int switchX = 0;
						int switchY = 0;

						switch (direction) {
							case OnBack:
								switchX = this.stepX * -1;
								switchY = this.stepY * -1;
								break;
							case OnForward:
								switchX = this.stepX;
								switchY = this.stepY;
								break;
							case OnLeft:
								switch (this.direction) {
									case Right:
										switchX = -1;
										switchY = 0;
										break;
									case Left:
										switchX = 1;
										switchY = 0;
										break;
									case Down:
										switchX = 0;
										switchY = 1;
										break;
									case Up:
										switchX = 0;
										switchY = -1;
										break;
								}
								break;
							case OnRight:
								switch (this.direction) {
									case Right:
										switchX = 1;
										switchY = 0;
										break;
									case Left:
										switchX = -1;
										switchY = 0;
										break;
									case Down:
										switchX = 0;
										switchY = -1;
										break;
									case Up:
										switchX = 0;
										switchY = 1;
										break;
								}
								break;
						}

						if (condition[2].equals("=")) {
							if (game.map[this.x + switchX][this.y + switchY] == cell) {
								this.current++;
							} else {
								this.current = Integer.parseInt(current[2]);
							}
						} else if (condition[2].equals("!=")) {
							if (game.map[this.x + switchX][this.y + switchY] != cell) {
								this.current++;
							} else {
								this.current = Integer.parseInt(current[2]);
							}
						}
					}
					break;
				case While:
					condition = current[1].split(",");
					if (Commands.valueOf(condition[1]) == Commands.True) {
						this.current += 1;
					} else {
						Cell cell = Cell.valueOf(condition[0]);
						Commands direction = Commands.valueOf(condition[1]);
						int switchX = 0;
						int switchY = 0;

						switch (direction) {
							case OnBack:
								switchX = this.stepX * -1;
								switchY = this.stepY * -1;
								break;
							case OnForward:
								switchX = this.stepX;
								switchY = this.stepY;
								break;
							case OnLeft:
								switch (this.direction) {
									case Right:
										switchX = -1;
										switchY = 0;
										break;
									case Left:
										switchX = 1;
										switchY = 0;
										break;
									case Down:
										switchX = 0;
										switchY = 1;
										break;
									case Up:
										switchX = 0;
										switchY = -1;
										break;
								}
								break;
							case OnRight:
								switch (this.direction) {
									case Right:
										switchX = 1;
										switchY = 0;
										break;
									case Left:
										switchX = -1;
										switchY = 0;
										break;
									case Down:
										switchX = 0;
										switchY = -1;
										break;
									case Up:
										switchX = 0;
										switchY = 1;
										break;
								}
								break;
						}

						if (condition[2].equals("=")) {
							if (game.map[this.x + switchX][this.y + switchY] == cell) {
								this.current++;
							} else {
								this.current = Integer.parseInt(current[2]);
							}
						} else if (condition[2].equals("!=")) {
							if (game.map[this.x + switchX][this.y + switchY] != cell) {
								this.current++;
							} else {
								this.current = Integer.parseInt(current[2]);
							}
						}
					}
					break;
				case End:
					int ending = Integer.parseInt(current[1]);
					String[] tmp = algorithm.get(ending);
					Commands commandTmp = Commands.valueOf(tmp[0]);
					if (commandTmp == Commands.While) {
						this.current = ending;
					} else {
						this.current++;
					}
					break;
				case True:
					break;
				case Find:
					game.find();
					this.current++;
					break;
				case Else:
					this.current++;
					step();
					break;
				case Pick:
					game.pick();
					this.current++;
					break;
			}
		} catch (CommandException ex) {

		}
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
		game.map[x][y] = currentCell;
		flipMapCell();
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
		game.map[x][y] = currentCell;
		flipMapCell();
	}
}
