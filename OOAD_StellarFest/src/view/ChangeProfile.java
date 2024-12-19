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
import javafx.stage.Stage;

public class ChangeProfile {
    private VBox root, formVbox;
    private Scene loginScene;
    private GridPane formGp;
    private Label emailLabel,nameLabel, passLabel, oldPassLabel, changeProfileLabel, homeLabel;
    private TextField emailField, nameField;
    private PasswordField newPassField, oldPassField;
    private Button submitButton;

    public ChangeProfile() {
        init();
        layout();
        style();
        setEventHandler();
    }

    public void init() {
        this.root = new VBox();
        this.loginScene = new Scene(root, 800, 600);
        this.formGp = new GridPane();
        this.changeProfileLabel = new Label("Change Profile");
        this.emailLabel = new Label("Email");
        this.nameLabel = new Label("Username");
        this.passLabel = new Label("New Password");
        this.oldPassLabel = new Label("Old Password");
        this.emailField = new TextField();
        this.nameField = new TextField();
        this.newPassField = new PasswordField();
        this.oldPassField = new PasswordField();
        this.submitButton = new Button("Enter");
        this.homeLabel = new Label("Return to home page");
        this.formVbox = new VBox();
    }

    public void layout() {
        formGp.add(emailLabel, 0, 0);
        formGp.add(emailField, 1, 0);
        formGp.add(nameLabel, 0, 1);
        formGp.add(nameField, 1, 1);
        formGp.add(passLabel, 0, 2);
        formGp.add(newPassField, 1, 2);
        formGp.add(oldPassLabel, 0, 3);
        formGp.add(oldPassField, 1, 3);
        formVbox.getChildren().addAll(changeProfileLabel, formGp, submitButton, homeLabel);
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
        homeLabel.setStyle("-fx-text-fill: blue; -fx-cursor: hand;");
    }

    public void setEventHandler() {
        UserController userCon = new UserController();

        submitButton.setOnAction(e -> {
            String email = emailField.getText();
            String username = nameField.getText();
            String newPassword = newPassField.getText();
            String oldPassword = oldPassField.getText();
            userCon.updateProfile(username, email, oldPassword, newPassword);
        });

        homeLabel.setOnMouseClicked(e -> {
        	Register registerView = new Register();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(registerView.getScene());
        });
    }

    public Scene getScene() {
        return loginScene;
    }
}
