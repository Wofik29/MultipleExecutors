package org.wolf.MultipleExecutors.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.wolf.MultipleExecutors.*;

public class MainController
{
	private Canvas canvas;
	private Main game;
	private boolean isStart = false;

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
		if (game != null) {
			game.showEditor();
		}
	}

	@FXML
	public void playState()
	{
		game.setStage(State.Play);
	}

	@FXML
	public void pauseState()
	{
		game.setStage(State.Pause);
	}

	private void playDisplay()
	{
		message.setText("Проигрывание алгоритма");
		message.setTextFill(Color.valueOf("1DE037"));
	}

	private void pauseDisplay(String text)
	{
		message.setText(text);
		message.setTextFill(Color.valueOf("EEE413"));
	}

	private void gameOverDisplay()
	{
		message.setText("КОНЕЦ! ПОЗДРАВЛЯЕМ!!!");
		message.setTextFill(Color.valueOf("1DE037"));
	}

	public void startTimer()
	{
		if (isStart) {
			return;
		}
		isStart = true;

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

	public boolean isMap()
	{
		return canvas.isMap();
	}

	public void setGame(Main game)
	{
		this.game = game;
	}

	public void update()
	{
		switch (game.getState()) {
			case EditExplorer:
				pauseDisplay("Редактор искателя");
				break;
			case EditHarvester:
				pauseDisplay("Редактор сборщика");
				break;
			case Pause:
				pauseDisplay("Пауза");
				break;
			case Welcome:
				break;
			case Play:
				playDisplay();
				break;
			case GameOver:
				gameOverDisplay();
				break;
		}
	}
}
