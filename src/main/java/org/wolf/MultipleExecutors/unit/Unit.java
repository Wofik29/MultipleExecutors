package org.wolf.MultipleExecutors.unit;

public abstract class Unit implements Executor
{
	private int x;
	private int y;
	private Direction direction;

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

	Unit(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void stepForward()
	{
		switch (direction) {
			case Up:
				y--;
				break;
			case Down:
				y++;
				break;
			case Left:
				x--;
				break;
			case Right:
				x++;
				break;
		}
	}

	@Override
	public void turnRight()
	{

	}

	@Override
	public void turnLeft()
	{

	}


}
