package org.wolf.MultipleExecutors;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.wolf.MultipleExecutors.controllers.MainController;
import org.wolf.MultipleExecutors.controllers.WelcomeController;

import java.io.IOException;
import java.util.HashMap;

public class Main extends Application
{

	private Stage primaryStage;
	private Stage secondStage;
	private Game game;
	private State state;
	private String currentLayout = "";

	private static String APP_NAME = "Multiple Executors";
	private static String VERSION = "v 0.1";

	public static void main(String args[])
	{
		launch(args);
	}

	public void start(Stage stage)
	{
		this.primaryStage = stage;
		this.secondStage = new Stage();
		primaryStage.setTitle(APP_NAME + " " + VERSION);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> {
			secondStage.close();
		});

		secondStage.setTitle("Editor");

		this.game = new Game(this);
		setStage(State.Welcome);
	}

	public void setStage(State state)
	{
		this.state = state;
		setLayout();
	}

	private void setLayout()
	{
		HashMap<State, String> fxmls = new HashMap<>();
		fxmls.put(State.Pause, "/fxml/main.fxml");
		fxmls.put(State.Play, "/fxml/main.fxml");
		fxmls.put(State.EditExplorer, "/fxml/main.fxml");
		fxmls.put(State.EditHarvester, "/fxml/main.fxml");
		fxmls.put(State.Welcome, "/fxml/welcome.fxml");

		if (currentLayout.equals(fxmls.get(state))) {
			return;
		}

		currentLayout = fxmls.get(state);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmls.get(state)));

		AnchorPane rootLayout = null;
		try {
			rootLayout = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);

		switch (state) {
			case Welcome:
				WelcomeController welcomeController = loader.getController();
				welcomeController.game = game;
				break;
			case EditExplorer: case EditHarvester: case Play: case Pause:
				MainController controller = loader.getController();
				game.init();
				controller.setMap(game.map);
				controller.startTimer(this.game);

				primaryStage.widthProperty().addListener(new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
					{
						controller.resize();
					}
				});
				primaryStage.heightProperty().addListener(new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
					{
						controller.resize();
					}
				});

				initSecondLayout();
				secondStage.show();
				break;
		}
	}

	private void initSecondLayout()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/editor.fxml"));

		AnchorPane rootLayout = null;
		try {
			rootLayout = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		Scene scene = new Scene(rootLayout);
		secondStage.setScene(scene);
	}

	private void clear()
	{

	}
}
