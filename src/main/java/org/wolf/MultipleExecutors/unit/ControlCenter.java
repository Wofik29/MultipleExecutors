package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Game;

import java.util.ArrayList;
import java.util.Random;

public class ControlCenter
{
	private int x;
	private int y;

	private Executor[] explorers;
	private Executor[] harvesters;
	private Game game;

	public Executor[] getExplorers()
	{
		return explorers;
	}

	public ControlCenter(int x, int y, Game game, int countExplorer, int countHarvester)
	{
		this.x = x;
		this.y = y;
		this.game = game;
		this.explorers = new Explorer[countExplorer];
		this.harvesters = new Harvester[countHarvester];
		game.map[x][y] = Cell.Center;

		ArrayList<Cell> allowCell = new ArrayList<>();
		allowCell.add(Cell.Ground);

		Random random = new Random();
		for (int i = 0; i < countExplorer; i++) {
			int unitX = x;
			int unitY = y;

			while (!allowCell.contains(game.map[unitX][unitY])) {
				unitX = random.nextInt(5) + this.x;
				unitY = random.nextInt(5) + this.y;
			}
			explorers[i] = new Explorer(unitX, unitY, game);
		}

		for (int i = 0; i < countHarvester; i++) {
			harvesters[i] = new Harvester(this.x, this.y, game);
		}
	}

	public int getExplorersCount()
	{
		return explorers.length;
	}

	public int getHarvesterCount()
	{
		return harvesters.length;
	}


	public void step()
	{

	}
}
