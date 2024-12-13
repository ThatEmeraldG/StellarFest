package main;

import view.Register;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
	public void start(Stage stage) throws Exception {
		Register register = new Register();
		stage.setScene(register.getScene());
		stage.show();
	}
}
