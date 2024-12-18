package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Login {
	private VBox root, formVbox;
	private Scene loginScene; 
	private GridPane formGp; 
	private Label emailLabel, passLabel; 
	private TextField emailField;
	private PasswordField passField;
	private Button submitButton; 
	private Label loginLabel;
	
	public Login() {
		init();
		layout();
		style();
		setEventHandler();
	}
	
	public void init() {
		this.root = new VBox(); 
		this.loginScene = new Scene(root, 1600, 900);
		this.formGp = new GridPane();
		this.loginLabel = new Label("Login");
		this.emailLabel = new Label("Email"); 
		this.passLabel = new Label("Password");
		this.emailField = new TextField();
		this.passField = new PasswordField();
		this.submitButton = new Button("Enter"); 
		this.formVbox = new VBox();
	}
	
	public void layout() {
		formGp.add(emailLabel, 0, 0);
		formGp.add(emailField, 1, 0);
		formGp.add(passLabel, 0, 1);
		formGp.add(passField, 1, 1);
		formVbox.getChildren().addAll(loginLabel, formGp, submitButton); 
		root.getChildren().addAll(formVbox); 
	}
	
	public void style() {
		root.setStyle("-fx-background-color : lightblue");
		formGp.setAlignment(Pos.CENTER);
		formVbox.setAlignment(Pos.CENTER);
		formGp.setHgap(20);
		formGp.setVgap(10);
		formVbox.setSpacing(20);
		formVbox.setPadding(new Insets(20));
	}
	
	public void setEventHandler() {
		UserController userCon = new UserController();
//		insert data 
		submitButton.setOnAction(e -> {
			String email = emailField.getText();
			String password = passField.getText();
			
			userCon.login(email, password);
		});
	}
	
	public Scene getScene() {
        return loginScene;
    }
	
}
