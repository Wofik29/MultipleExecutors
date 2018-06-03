package org.wolf.MultipleExecutors;

import javafx.scene.input.KeyEvent;
import org.wolf.MultipleExecutors.commands.CommandException;
import org.wolf.MultipleExecutors.controllers.EditorController;
import org.wolf.MultipleExecutors.unit.ControlCenter;
import org.wolf.MultipleExecutors.unit.Executor;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Game extends Observable
{
	Main application;
	ArrayList<Observer> observers = new ArrayList<>();
	EditorController editorController;

	/**
	 * Delay of update
	 */
	public static int DELAY = 40;
	public int widthMap;
	public int heightMap;
	public int countExplorer = 1;
	public int countHarvester = 0;

	public Cell[][] map;
	private ControlCenter center;
	private State state = State.Welcome;

	public static int MAX_ALLOW_EXPLORER = 10;
	public static int MAX_ALLOW_HARVESTER = 5;

	public Game(Main application)
	{
		this.application = application;
		widthMap = 100;
		heightMap = 100;
	}

	public void init()
	{
		map = new Cell[widthMap][heightMap];

		Random random = new Random();
		for (int i = 0; i < widthMap; i++) {
			for (int j = 0; j < heightMap; j++) {
				int next = random.nextInt(10);
				if (next > 8) {
					map[i][j] = Cell.Water;
				} else {
					map[i][j] = Cell.Ground;
				}
			}
		}
		center = new ControlCenter(widthMap / 2, heightMap / 2, this, 1, 0);
	}

	public void changeState(State state)
	{
		if (state == State.Play) {
			try {
				center.updateExplorerAlgorithm(editorController.explorer.getText());
			} catch (CommandException ex) {
				editorController.setMessage(ex.getMessage());
				return;
			}
		}
		this.state = state;
		application.setStage(state);
		application.getPrimaryStage().setFocused(true);
		this.setChanged();
		notifyObservers();
	}

	public State getState()
	{
		return state;
	}

	public void setEditorController(EditorController editorController)
	{
		this.editorController = editorController;
	}

	public void input(KeyEvent event)
	{
		Executor[] explorers = center.getExplorers();
		switch (event.getCode()) {
			case A:
				explorers[0].turnLeft();
				break;
			case D:
				explorers[0].turnRight();
				break;
			case W:
				try {
					explorers[0].stepForward();
				} catch (CommandException ex) {

				}
				break;
			case S:
				try {
					explorers[0].stepBack();
				} catch (CommandException ex) {

				}
				break;
		}
	}

	public void step()
	{
		switch (state) {
			case Pause:
				break;
			case Play:
				center.step();
				break;
			case EditExplorer:
				break;
			case EditHarvester:
				break;
		}
	}
}
