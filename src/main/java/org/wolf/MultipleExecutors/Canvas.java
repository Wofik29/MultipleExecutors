package org.wolf.MultipleExecutors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Canvas extends javafx.scene.canvas.Canvas
{

	private Cell[][] map;
	public int widthCell = 15;
	public int shiftLeft;
	public int shiftUp;

	@Override
	public boolean isResizable()
	{
		return true;
	}

	public void setMap(Cell[][] map)
	{
		this.map = map;
	}

	public boolean isMap()
	{
		return map.length == 0;
	}

	public void clear()
	{
		GraphicsContext gx = getGraphicsContext2D();
		gx.setFill(Color.WHITE);
		gx.fillRect(0, 0, getWidth(), getHeight());
	}

	public void draw()
	{
		if (map == null) {
			return;
		}

		getGraphicsContext2D().setLineWidth(1);
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				getGraphicsContext2D().setStroke(Color.BLACK);
				getGraphicsContext2D().setFill(map[x][y].color);
				getGraphicsContext2D().fillRect(x * widthCell + shiftLeft, y * widthCell + shiftUp, widthCell, widthCell);
				getGraphicsContext2D().strokeRect(x * widthCell + shiftLeft, y * widthCell + shiftUp, widthCell, widthCell);
			}
		}

	}
}
