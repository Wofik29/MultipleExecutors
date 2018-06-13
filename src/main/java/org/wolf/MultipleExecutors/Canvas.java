package org.wolf.MultipleExecutors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class Canvas extends javafx.scene.canvas.Canvas
{

	private Cell[][] map;
	public int widthCell = 15;
	public int shiftLeft;
	public int shiftUp;
	private HashMap<Cell, Double> unitRotate;

	public Canvas()
	{
		super();
		unitRotate = new HashMap<>();
		unitRotate.put(Cell.UnitDown, 0.0);
		unitRotate.put(Cell.UnitUp, 180.0);
		unitRotate.put(Cell.UnitLeft, 90.0);
		unitRotate.put(Cell.UnitRight, -90.0);
	}

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
/*
				if (map[x][y].isVisible) {
					getGraphicsContext2D().setFill(map[x][y].color);
				} else {
					getGraphicsContext2D().setFill(Cell.Ground.color);
				}
*/
				getGraphicsContext2D().setFill(map[x][y].color);
				double xTranslate = x * widthCell + widthCell/2 + shiftLeft;
				double yTranslate = y * widthCell + widthCell/2 + shiftUp;
				getGraphicsContext2D().translate(xTranslate, yTranslate);

				if (map[x][y].toString().contains("Unit")) {
					getGraphicsContext2D().rotate(unitRotate.get(map[x][y]));
					double[] xPoints = {
							-widthCell/2,
							widthCell/2,
							0,
					};

					double[] yPoints = {
							-widthCell/2,
							-widthCell/2,
							widthCell/2,
					};

					getGraphicsContext2D().fillPolygon(xPoints, yPoints, 3);
					getGraphicsContext2D().rotate(unitRotate.get(map[x][y]) * -1);

				} else {

					getGraphicsContext2D().fillRect(-widthCell/2, -widthCell/2, widthCell, widthCell);
					getGraphicsContext2D().strokeRect(-widthCell/2, -widthCell/2, widthCell, widthCell);
				}
				getGraphicsContext2D().translate(xTranslate * -1, yTranslate * -1);
			}
		}

	}
}
