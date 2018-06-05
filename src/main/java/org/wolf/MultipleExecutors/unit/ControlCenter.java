package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Main;
import org.wolf.MultipleExecutors.commands.CommandException;

import java.util.ArrayList;
import java.util.Random;

public class ControlCenter
{
	private int x;
	private int y;

	private Executor[] explorers;
	private Executor[] harvesters;
	private String explorerAlgorithm = "";

	public Executor[] getExplorers()
	{
		return explorers;
	}

	public ControlCenter(int x, int y, int countExplorer, int countHarvester, Main game)
	{
		this.x = x;
		this.y = y;
		this.explorers = new Explorer[countExplorer];
		this.harvesters = new Harvester[countHarvester];
		game.map[x][y] = Cell.Center;

		ArrayList<Cell> allowCell = new ArrayList<>();
		allowCell.add(Cell.Ground);

		Random random = new Random();
		Direction[] directions = {
				Direction.Up,
				Direction.Down,
				Direction.Left,
				Direction.Right,
		};

		for (int i = 0; i < countExplorer; i++) {
			int unitX = x;
			int unitY = y;

			while (!allowCell.contains(game.map[unitX][unitY])) {
				unitX = random.nextInt(5) + this.x;
				unitY = random.nextInt(5) + this.y;
			}

			int direction = random.nextInt(3);
			explorers[i] = new Explorer(unitX, unitY, directions[direction], game);
		}

		for (int i = 0; i < countHarvester; i++) {
			harvesters[i] = new Harvester(this.x, this.y, Direction.Down, game);
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
		for (Executor harvester : harvesters) {
			harvester.step();
		}

		for (Executor explorer : explorers) {
			explorer.step();
		}
	}

	public void updateExplorerAlgorithm(String string) throws CommandException
	{
		if (!explorerAlgorithm.equals(string)) {
			System.out.println(string);
			explorerAlgorithm = string;/*
			HashMap<Integer, String[]> algorithm = Compiler.prepare(string);
			for (Executor e : explorers) {
				e.setAlgorithm(algorithm);
			}*/
		}
	}
}
