package view;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;
import utils.SQLConnect;

public class Register {
	private VBox root, formVbox; 
	private Scene registerScene; 
	private GridPane formGp; 
	private Label nameLabel, emailLabel, passLabel, roleLabel; 
	private TextField nameField, emailField;
	private ComboBox<String> roleField;
	private PasswordField passField;
	private Button submitButton; 
	private Label registerLabel;
	private ArrayList<User> userList = new ArrayList<>(); 
	
	public Register() {
		init(); 
		layout(); 
		style();
		setEventHandler(); 
	}
	
	public void init() {
		this.root = new VBox(); 
		this.registerScene = new Scene(root, 1600, 900);
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
		formVbox.getChildren().addAll(registerLabel, formGp, submitButton); 
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
//		insert data 
		submitButton.setOnAction(e -> {
			String username = nameField.getText(); 
			String email = nameField.getText(); 
			String pass = passField.getText(); 
			String role = roleField.getValue();
			
			UserController.register(username, email, pass, role);
		});
	}
	
	public Scene getScene() {
        return registerScene;
    }
}
