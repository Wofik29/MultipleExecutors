package org.wolf.MultipleExecutors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Canvas extends javafx.scene.canvas.Canvas {

    private byte[][] map;
    private int widthCell = 50;

    public void setMap(byte[][] map) {
        this.map = map;
    }

    public void clear() {
        GraphicsContext gx = getGraphicsContext2D();
        gx.setFill(Color.WHITE);
        gx.fillRect(0, 0, getWidth(), getHeight());
    }

    public void drawMap() {
        if (map == null) {
            return;
        }

        getGraphicsContext2D().setStroke(Color.BLACK);
        getGraphicsContext2D().setFill(Color.BLACK);
        getGraphicsContext2D().setLineWidth(2);
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                getGraphicsContext2D().strokeRect(x * widthCell, y * widthCell, widthCell, widthCell );
            }
        }

    }
}
