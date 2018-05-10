package org.wolf.MultipleExecutors.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.wolf.MultipleExecutors.Canvas;
import org.wolf.MultipleExecutors.Cell;
import org.wolf.MultipleExecutors.Game;

public class MainController
{
	private Canvas canvas;

	@FXML
	private AnchorPane parent;

	@FXML
	private void initialize()
	{
		canvas = new Canvas();
		resize();
		parent.getChildren().add(canvas);
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> canvas.requestFocus());
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
				case ADD: case EQUALS: case PLUS:
					canvas.widthCell += 2;
					break;
				case MINUS: case SUBTRACT:
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
}
