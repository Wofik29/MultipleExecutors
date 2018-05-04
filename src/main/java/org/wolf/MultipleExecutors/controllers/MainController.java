package org.wolf.MultipleExecutors.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.wolf.MultipleExecutors.Canvas;

public class MainController {

    private Canvas canvas;

    @FXML
    private AnchorPane parent;

    @FXML
    private void initialize() {
        System.out.println("INIT");
        final double w = parent.getPrefWidth();
        final double h = parent.getPrefHeight();

        canvas = new Canvas();
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        canvas.setWidth(w);
        canvas.setHeight(h);
        parent.getChildren().add(canvas);
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> canvas.requestFocus());

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                canvas.clear();
                canvas.drawMap();
            }
        }.start();

    }

}
