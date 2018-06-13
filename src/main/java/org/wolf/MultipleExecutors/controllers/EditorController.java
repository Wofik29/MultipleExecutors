package org.wolf.MultipleExecutors.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.wolf.MultipleExecutors.Main;
import org.wolf.MultipleExecutors.State;

public class EditorController
{
	@FXML
	public TextArea explorer;

	@FXML
	public TextArea harvester;

	@FXML
	public Label message;

	private Main game;

	@FXML
	private void initialize()
	{

	}

	public void setGame(Main game)
	{
		this.game = game;
	}

	@FXML
	public void onStart()
	{
		game.setStage(State.Play);
	}

	public void update()
	{
		switch (game.getState()) {
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
