package org.wolf.MultipleExecutors.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.wolf.MultipleExecutors.Main;
import org.wolf.MultipleExecutors.State;

public class WelcomeController
{
	private Main game;

	public void setGame(Main g)
	{
		this.game = g;
	}

	@FXML
	public Label message;

	@FXML
	public TextField countExplorer;

	@FXML
	public TextField countHarvester;

	@FXML
	private void initialize()
	{
		countExplorer.setText("0");
		countHarvester.setText("0");
	}

	@FXML
	public void onStartAction()
	{
		message.setText("");
		String errorText = "";
		boolean isError = false;
		int countExplorer = 0;
		int countHarvester = 0;
		try {
			countExplorer = Integer.parseInt(this.countExplorer.getText());
			countHarvester = Integer.parseInt(this.countHarvester.getText());
		} catch (NumberFormatException ex) {
			isError = true;
			errorText = "Кол-во должно быть числом";
		}

		if (countExplorer > Main.MAX_ALLOW_EXPLORER) {
			isError = true;
			errorText = "Кол-во искателей не может быть больше " + Main.MAX_ALLOW_EXPLORER;
		}

		if (countHarvester > Main.MAX_ALLOW_HARVESTER) {
			isError = true;
			errorText = "Кол-во сборщиков не может быть больше " + Main.MAX_ALLOW_HARVESTER;
		}

		if (countExplorer < 0 || countHarvester < 0) {
			isError = true;
			errorText = "Кол-во исполнителей не может быть меньше нуля";
		}

		if (isError) {
			message.setText(errorText);
			return;
		}

		game.countHarvester = countHarvester;
		game.countExplorer = countExplorer;
		game.setStage(State.EditExplorer);
	}
}
