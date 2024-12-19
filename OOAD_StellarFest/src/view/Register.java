package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Register {
	private VBox root, formVbox; 
	private Scene registerScene; 
	private GridPane formGp; 
	private Label nameLabel, emailLabel, passLabel, roleLabel, loginLabel; 
	private TextField nameField, emailField;
	private ComboBox<String> roleField;
	private PasswordField passField;
	private Button submitButton; 
	private Label registerLabel;
	
	public Register() {
		init(); 
		layout(); 
		style();
		setEventHandler(); 
	}
	
	public void init() {
		this.root = new VBox(); 
		this.registerScene = new Scene(root, 800, 600);
		this.formGp = new GridPane();
		this.registerLabel = new Label("Register");
		this.nameLabel = new Label("Username"); 
		this.emailLabel = new Label("Email"); 
		this.passLabel = new Label("Password");
		this.roleLabel = new Label("Role");
		this.nameField = new TextField(); 
		this.emailField = new TextField();
		this.passField = new PasswordField();
		this.roleField = new ComboBox<>();
		this.roleField.getItems().addAll("Event Organizer", "Vendor", "Guest");
		this.roleField.setValue("Select an option");
		this.loginLabel = new Label("Already have an account? Login here!");
		this.submitButton = new Button("Submit"); 
		this.formVbox = new VBox();
	}
	
	public void layout() {
		formGp.add(nameLabel, 0, 0);
		formGp.add(nameField, 1, 0);
		formGp.add(emailLabel, 0, 1);
		formGp.add(emailField, 1, 1);
		formGp.add(passLabel, 0, 2);
		formGp.add(passField, 1, 2);
		formGp.add(roleLabel,  0, 3);
		formGp.add(roleField, 1, 3);
		formVbox.getChildren().addAll(registerLabel, formGp, submitButton, loginLabel); 
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
		loginLabel.setStyle("-fx-text-fill: blue; -fx-cursor: hand;");
	}
	
	public void setEventHandler() {
		UserController userCon = new UserController();
//		insert data 
		submitButton.setOnAction(e -> {
			String username = nameField.getText(); 
			String email = emailField.getText(); 
			String pass = passField.getText(); 
			String role = roleField.getValue();
			
			userCon.register(username, email, pass, role);
		});
		
		loginLabel.setOnMouseClicked(e -> {
        	Login loginView = new Login();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(loginView.getScene());
        });
	}
	
	public Scene getScene() {
        return registerScene;
    }
}
