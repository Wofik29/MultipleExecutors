package org.wolf.MultipleExecutors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Canvas extends javafx.scene.canvas.Canvas
{

	private Cell[][] map;
	private int widthCell = 15;

	public void setMap(Cell[][] map)
	{
		this.map = map;
	}

	public void clear()
	{
		GraphicsContext gx = getGraphicsContext2D();
		gx.setFill(Color.WHITE);
		gx.fillRect(0, 0, getWidth(), getHeight());
	}

	public void drawMap()
	{
		if (map == null) {
			return;
		}

		getGraphicsContext2D().setLineWidth(2);
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				getGraphicsContext2D().setStroke(map[x][y].color);
				getGraphicsContext2D().setFill(map[x][y].color);
				getGraphicsContext2D().fillRect(x * widthCell, y * widthCell, widthCell, widthCell);
			}
		}

	}
}
