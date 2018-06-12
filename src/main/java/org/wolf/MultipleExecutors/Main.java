package org.wolf.MultipleExecutors;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.wolf.MultipleExecutors.commands.CommandException;
import org.wolf.MultipleExecutors.controllers.EditorController;
import org.wolf.MultipleExecutors.controllers.MainController;
import org.wolf.MultipleExecutors.controllers.WelcomeController;
import org.wolf.MultipleExecutors.unit.ControlCenter;
import org.wolf.MultipleExecutors.unit.Executor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Main extends Application
{
	private Stage primaryStage;
	private Stage editorStage;
	private String currentLayout = "";

	private WelcomeController welcomeController;
	private MainController mainController;
	private EditorController editorController;

	/**
	 * Delay of update
	 */
	public static int DELAY = 40;

	public int widthMap = 100;
	public int heightMap = 100;
	public int countExplorer = 1;
	public int countHarvester = 0;

	public Cell[][] map;
	private ControlCenter center;
	private State state = null;
	private HashMap<State, String> fxmls = new HashMap<>();
	private String currentFxml = "";

	public State getState()
	{
		return state;
	}

	public static int MAX_ALLOW_EXPLORER = 10;
	public static int MAX_ALLOW_HARVESTER = 5;

	private static String APP_NAME = "Multiple Executors";
	private static String VERSION = "v 0.2";

	public static void main(String args[])
	{
		launch(args);
	}

	public void start(Stage stage)
	{
		this.primaryStage = stage;
		allInit();
		setStage(State.Welcome);
	}

	public void allInit()
	{
		primaryStage.setTitle(APP_NAME + " " + VERSION);
		primaryStage.setOnCloseRequest(event -> {
			editorStage.close();
		});

		fxmls.put(State.Pause, "/fxml/main.fxml");
		fxmls.put(State.Play, "/fxml/main.fxml");
		fxmls.put(State.EditExplorer, "/fxml/main.fxml");
		fxmls.put(State.EditHarvester, "/fxml/main.fxml");
		fxmls.put(State.Welcome, "/fxml/welcome.fxml");

		initSecondLayout();
		initMap();
		primaryStage.show();
	}

	private void initMap()
	{
		map = new Cell[widthMap][heightMap];

		Random random = new Random();
		for (int i = 0; i < widthMap; i++) {
			for (int j = 0; j < heightMap; j++) {
				int next = random.nextInt(10);
				if (next > 9) {
					map[i][j] = Cell.Water;
				} else {
					map[i][j] = Cell.Ground;
				}
			}
		}
	}

	public void play()
	{

	}

	public void showEditor()
	{
		editorStage.show();
	}

	public void setStage(State state)
	{
		if (state == this.state) {
			return;
		}

		this.state = state;
		if (!currentFxml.equals(fxmls.get(state))) {
			setLayout();
			currentFxml = fxmls.get(state);
		}

		updateState(state);

		if (mainController != null) {
			mainController.update();
		}
		if (editorController != null) {
			editorController.update();
		}
	}

	private void setLayout()
	{
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
				if (welcomeController == null) {
					welcomeController = loader.getController();
					welcomeController.setGame(this);
				}
				break;
			case EditExplorer:
			case EditHarvester:
			case Play:
			case Pause:
				if (mainController == null) {
					mainController = loader.getController();
					mainController.setGame(this);
					mainController.startTimer();
					mainController.setMap(map);
					primaryStage.widthProperty().addListener(new ChangeListener<Number>()
					{
						@Override
						public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
						{
							mainController.resize();
						}
					});
					primaryStage.heightProperty().addListener(new ChangeListener<Number>()
					{
						@Override
						public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
						{
							mainController.resize();
						}
					});
				}

				if (center == null) {
					center = new ControlCenter(widthMap / 2, heightMap / 2, this.countExplorer, this.countHarvester, this);
				}
				editorStage.show();
				break;
		}

		primaryStage.setFocused(true);
	}

	private void updateState(State state)
	{
		if (state == State.Play) {
			try {
				center.updateExplorerAlgorithm(editorController.explorer.getText());
			} catch (CommandException ex) {
				editorController.setMessage(ex.getMessage());
			}
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
		this.editorStage = new Stage();
		editorStage.setTitle("Editor");
		editorStage.setScene(scene);
		editorController = loader.getController();
		editorController.setGame(this);
	}

	public void input(KeyEvent event)
	{
		Executor[] explorers = center.getExplorers();
		switch (event.getCode()) {
			case A:
				explorers[0].turnLeft();
				break;
			case D:
				explorers[0].turnRight();
				break;
			case W:
				try {
					explorers[0].stepForward();
				} catch (CommandException ex) {

				}
				break;
			case S:
				try {
					explorers[0].stepBack();
				} catch (CommandException ex) {

				}
				break;
			case O:
				center.upSpeed();
				break;
			case P:
				center.downSpeed();
				break;
		}
	}

	public void step()
	{
		switch (state) {
			case Pause:
				break;
			case Play:
				center.step();
				break;
			case EditExplorer:
				break;
			case EditHarvester:
				break;
		}
	}

}
