package org.wolf.MultipleExecutors.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.wolf.MultipleExecutors.*;

import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer
{
	private Canvas canvas;
	private Main application;
	private Game game;

	@FXML
	private AnchorPane parent;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Label message;

	@FXML
	private void initialize()
	{
		canvas = new Canvas();
		borderPane.setCenter(canvas);
		resize();
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> canvas.requestFocus());
		message.setText("Редактирование алгоритма для искателя");
		message.setTextFill(Color.valueOf("EEE413"));
	}

	@FXML
	public void showEditor()
	{
		if (application != null) {
			application.showSecondStage();
		}
	}

	@FXML
	public void playState()
	{
		game.changeState(State.Play);
	}

	@FXML
	public void pauseState()
	{
		game.changeState(State.Pause);
	}

	private void playDisplay()
	{
		message.setText("Проигрывание алгоритма");
		message.setTextFill(Color.valueOf("1DE037"));
	}

	private void pauseDisplay()
	{
		message.setText("Пауза");
		message.setTextFill(Color.valueOf("EEE413"));
	}

	public void startTimer(Game game)
	{
		canvas.setOnScroll(event -> {
			int delta = (int) event.getDeltaY() / 10;
			canvas.widthCell += delta;
		});
		canvas.setOnKeyPressed(event -> {
			game.input(event);
			switch (event.getCode()) {
				case LEFT:
					canvas.shiftLeft += 2 * canvas.widthCell;
					break;
				case RIGHT:
					canvas.shiftLeft -= 2 * canvas.widthCell;
					break;
				case UP:
					canvas.shiftUp += 2 * canvas.widthCell;
					break;
				case DOWN:
					canvas.shiftUp -= 2 * canvas.widthCell;
					break;
				case ADD:
				case EQUALS:
				case PLUS:
					canvas.widthCell += 2;
					break;
				case MINUS:
				case SUBTRACT:
					canvas.widthCell -= 2;
					break;
			}
		});

		new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				canvas.clear();
				canvas.draw();
				game.step();
			}
		}.start();
	}

	public void resize()
	{
		if (parent.getScene() != null) {
			parent.setPrefHeight(parent.getScene().getWindow().getHeight());
			parent.setPrefWidth(parent.getScene().getWindow().getWidth());
		}

		final double w = parent.getPrefWidth();
		final double h = parent.getPrefHeight();
		canvas.setLayoutX(0);
		canvas.setLayoutY(0);
		canvas.setWidth(w);
		canvas.setHeight(h);
	}

	public void setMap(Cell[][] map)
	{
		canvas.setMap(map);
	}

	public void setApplication(Main application)
	{
		this.application = application;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		Game g = (Game) o;
		switch (g.getState()) {
			case EditExplorer:
			case EditHarvester:
			case Pause:
				pauseDisplay();
				break;
			case Welcome:
			case Play:
				playDisplay();
		}
	}
}
