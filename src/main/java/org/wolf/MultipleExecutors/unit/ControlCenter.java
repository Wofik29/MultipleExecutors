package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Compiler;
import org.wolf.MultipleExecutors.Main;
import org.wolf.MultipleExecutors.commands.CommandException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ControlCenter
{
	private int x;
	private int y;

	private Executor[] explorers;
	private Executor[] harvesters;
	private String explorerAlgorithm = "";
	private int period = 300;
	private long last = 0;

	public void upSpeed()
	{
		period += 10;
	}

	public void downSpeed()
	{
		period -= 10;
	}

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
			Explorer explorer = new Explorer(unitX, unitY, directions[direction], game);
			explorer.setVisible(true);
			explorers[i] = explorer;
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
		long now = System.currentTimeMillis();
		if (now - last < period) return;
		boolean isEnd = false;

		for (Executor harvester : harvesters) {
			harvester.step();
			isEnd = isEnd || harvester.isEnd();
		}

		for (Executor explorer : explorers) {
			explorer.step();
			isEnd = isEnd || explorer.isEnd();
		}


		last = now;
	}

	public void updateExplorerAlgorithm(String string) throws CommandException
	{
		if (!explorerAlgorithm.equals(string)) {
			System.out.println(string);
			explorerAlgorithm = string;
			Compiler compiler = new Compiler(explorerAlgorithm);
			HashMap<Integer, String[]> algorithm = compiler.prepare();
			for (Executor e : explorers) {
				e.setAlgorithm(algorithm);
			}
		} else {
			for (Executor explorer : explorers) {
				explorer.reset();
			}
		}
	}
}
