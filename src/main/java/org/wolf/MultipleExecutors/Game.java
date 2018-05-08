package org.wolf.MultipleExecutors;

import java.util.Random;

public class Game
{
	/**
	 * Delay of update
	 */
	public static int DELAY = 40;
	public int widthMap;
	public int heightMap;

	public Cell[][] map;

	public Game()
	{
		widthMap = 50;
		heightMap = 50;
		map = new Cell[widthMap][heightMap];

		Random random = new Random();
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				int next = random.nextInt(10);
				if (next > 7) {
					map[i][j] = Cell.Water;
				} else {
					map[i][j] = Cell.Ground;
				}
			}
		}
	}

	public void step() {
		System.out.println("Game step");
	}
}
