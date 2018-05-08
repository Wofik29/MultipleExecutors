package org.wolf.MultipleExecutors;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.wolf.MultipleExecutors.controllers.MainController;

import java.io.IOException;

public class Main extends Application
{

	private Stage primaryStage;
	private AnchorPane rootLayout;
	private Game game;

	private static String APP_NAME = "Multiple Executors";
	private static String VERSION = "v 0.1";

	public static void main(String args[])
	{
		launch(args);
	}

	public void start(Stage stage)
	{
		this.primaryStage = stage;
		primaryStage.setTitle(APP_NAME + " " + VERSION);
		primaryStage.show();

		this.game = new Game();
		initLayout();
	}

	private void initLayout()
	{
		String fxmlFile = "/fxml/main.fxml";
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlFile));

		try {
			rootLayout = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);

		MainController controller = loader.getController();
		controller.setMap(game.map);
		controller.startTimer(this.game);

		primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				controller.resize();
			}
		});
		primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				controller.resize();
			}
		});
	}
}
