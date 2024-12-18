package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.PageManager;

public class Main extends Application{
	private static Stage stage;
	
	public static void redirect(Scene newScene) {
		stage.setScene(newScene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		PageManager.initialize(stage);
		PageManager.getInstance().showLoginPage();
		stage.setTitle("StellarFest");
		stage.show();
	}
}