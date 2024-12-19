package view;

import java.util.List;

import controller.EventController;
import controller.EventOrganizerController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;

public class ViewOrganizedEvents {
    private VBox root, formVbox;
    private Scene loginScene;
    private GridPane formGp;
    private Label createEventLabel, homeLabel;
    private ObservableList<Event> eventList;
    private TableView<Event> eventTable;
    private String organizerId;

    
    public ViewOrganizedEvents() {
        init();
        layout();
        style();
        setEventHandler();
    }

    public void init() {
    	EventOrganizerController eventOrganizerController = new EventOrganizerController();
        this.root = new VBox();
        this.loginScene = new Scene(root, 800, 600);
        this.formGp = new GridPane();
        this.createEventLabel = new Label("View Organized Events");
        this.homeLabel = new Label("Return to home page");
        this.formVbox = new VBox();
        this.eventTable = new TableView<>();
        this.organizerId = eventOrganizerController.getId();
        this.eventList.addAll(eventOrganizerController.viewOrganizedEvents(organizerId));
    }

    public void layout() {
    	TableColumn<Event, String> eventIdColumn = new TableColumn<>("Event ID");
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventIdColumn.setPrefWidth(100);
        
        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        eventNameColumn.setPrefWidth(220);  // Set preferred width for this column

        TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        eventDateColumn.setPrefWidth(220);  // Set preferred width for this column

        TableColumn<Event, String> eventLocationColumn = new TableColumn<>("Event Location");
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventLocationColumn.setPrefWidth(220);  // Set preferred width for this column


        eventTable.getColumns().addAll(eventIdColumn, eventNameColumn, eventDateColumn, eventLocationColumn);

        eventTable.setItems(eventList);

        formVbox.getChildren().addAll(createEventLabel, eventTable, homeLabel);

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
