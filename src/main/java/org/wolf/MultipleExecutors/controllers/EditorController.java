package org.wolf.MultipleExecutors.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.wolf.MultipleExecutors.Game;
import org.wolf.MultipleExecutors.State;

import java.util.Observable;
import java.util.Observer;

public class EditorController implements Observer
{
	@FXML
	public TextArea explorer;

	@FXML
	public Label message;

	private Game game;

	@FXML
	private void initialize()
	{

	}

	public void setGame(Game game)
	{
		this.game = game;
		game.setEditorController(this);
	}

	@FXML
	public void onStart()
	{
		game.changeState(State.Play);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		Game g = (Game) o;
		switch (g.getState()) {
			case EditExplorer:
				explorer.setDisable(false);
				break;
			case EditHarvester:
			case Welcome:
			case Pause:
			case Play:
				explorer.setDisable(true);
		}
	}

	public void setMessage(String message)
	{
		this.message.setText(message);
	}
}
