package org.wolf.MultipleExecutors.unit;

import org.wolf.MultipleExecutors.Game;

import java.util.Random;

public class ControlCenter
{
	Executor[] explorers;
	Executor[] harvesters;
	Game game;

	public ControlCenter(Game game, int countExplorer, int countHarvester)
	{
		this.game = game;
		this.explorers = new Explorer[countExplorer];
		this.harvesters = new Harvester[countHarvester];

		Random random = new Random();
		for(int i = 0; i<countExplorer; i++) {
			explorers[i] = new Explorer(1,2);
		}
	}

	public void step()
	{

	}
}
