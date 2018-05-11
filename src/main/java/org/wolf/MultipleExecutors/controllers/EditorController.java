package org.wolf.MultipleExecutors.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.wolf.MultipleExecutors.Game;

import java.util.Observable;
import java.util.Observer;

public class EditorController implements Observer
{
	@FXML
	private TextArea explorer;

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
				System.out.println("UPDATE");
				explorer.setDisable(true);
		}
	}
}
