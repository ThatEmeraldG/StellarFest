package view;

import controller.EventController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateEvent {
    private VBox root, formVbox;
    private Scene loginScene;
    private GridPane formGp;
    private Label eventLabel, dateLabel, locationLabel, descLabel, createEventLabel, homeLabel;
    private TextField eventField, locationField, descField;
    private DatePicker dateField;
    private Button submitButton;
    
    public CreateEvent() {
        init();
        layout();
        style();
        setEventHandler();
    }

    public void init() {
        this.root = new VBox();
        this.loginScene = new Scene(root, 800, 600);
        this.formGp = new GridPane();
        this.createEventLabel = new Label("Create Event");
        this.eventLabel = new Label("Event Name");
        this.dateLabel = new Label("Event Date");
        this.locationLabel = new Label("Event Location");
        this.descLabel = new Label("Event Description");
        this.eventField = new TextField();
        this.locationField = new TextField();
        this.descField = new TextField();
        this.dateField = new DatePicker();
        this.submitButton = new Button("Enter");
        this.homeLabel = new Label("Return to home page");
        this.formVbox = new VBox();
    }

    public void layout() {
        formGp.add(eventLabel, 0, 0);
        formGp.add(eventField, 1, 0);
        formGp.add(dateLabel, 0, 1);
        formGp.add(dateField, 1, 1);
        formGp.add(locationLabel, 0, 2);
        formGp.add(locationField, 1, 2);
        formGp.add(descLabel, 0, 3);
        formGp.add(descField, 1, 3);
        formVbox.getChildren().addAll(createEventLabel, formGp, submitButton, homeLabel);
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
        EventController eventCon = new EventController();

        submitButton.setOnAction(e -> {
            String event = eventField.getText();
            String description = descField.getText();
            String location = locationField.getText();
            String date = (dateField.getValue() != null) ? dateField.getValue().toString() : null;
            String organizerId = "a";
            eventCon.createEvent(event, date, location, description, organizerId);
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
