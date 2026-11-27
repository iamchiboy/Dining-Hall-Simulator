package diningsim;

import java.util.ArrayList; //ready-made type of list basically
import java.util.LinkedHashMap;
import java.util.List; // for ordered collections of data
import java.util.Map; //Stores pairs like a dictionary, key to value

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application; //The base class that turns this
//class into the JavaFX program
import javafx.geometry.Insets; // The padding around the things on the window
import javafx.geometry.Pos; // Alignments, either left, right, or center
import javafx.scene.Node; //Basically anything we will be able to see
// Our panel method will return this
import javafx.scene.Scene; // What is inside the window
import javafx.scene.control.Alert; // This allows for pop ups to prompt the user
import javafx.scene.control.Button; // Allows for buttons
import javafx.scene.control.CheckBox; // Same for tickable boxes here
import javafx.scene.control.Label; // Plain text to specify things
import javafx.scene.control.ScrollPane; //A scrollable area of the interface
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField; // A box to input text
import javafx.scene.control.TitledPane; // A box or section with a heading/title
import javafx.scene.layout.BorderPane; // Has border area slots
import javafx.scene.layout.HBox; // Places the items in a horizontal row
import javafx.scene.layout.VBox; // Stacks the items vertically / in a column
import javafx.stage.Stage; // The simulation window itself
import javafx.util.Duration; // This is a time length (example 1000 ms or 1s)


// Application is from JavaFX, therefore MainApp is a JavaFX application
// JavaFX can open windows and etc. so now can MainApp
// Application has a method called start(Stage) which would have to be written
public class MainApp extends Application {
    private Simulation simulation; // This is the engine, empty(null) until
    // the start method creates it
    private List<MenuItem> menu = new ArrayList<>(); // This is the menu aka
    // the list of 12 food items, the list only accepts menu items
    private Map<MenuItem, CheckBox> checkBoxes = new LinkedHashMap<>();
    // Like a dictionary that maps specific checkbxes to specific menu items
    // the checkbox is a value and MenuItem is the key
    private Map<MenuItem, Spinner<Integer>> spinners = new LinkedHashMap<>();
    // the spinners map/dictionary sets Spinner<Integer> as the value and
    // MenuItem as the key, where Spinner is a GUI control that lets a user
    // choose a value by clicking up/down arrows like amount, in this case the 
    // Spinner only accepts integer values
    private List<MenuItem> tickOrder = new ArrayList<>();
    // This is a ordered list of the sequence of how items were ticked, this is
    // important because it will determine the counter order (based on what was 
    // ticked first), maps dont remember order, lists do

    private Map<String, Label> queueLabels = new LinkedHashMap<>();
    // the map called queueLabels that maps the labels "Meals", "Drinks", "Pastries"
    // to the "in queue" number under each counter 
    private TextField nameField;
    private TextField idField;
    private Label totalServedLabel;
    private Label avgWaitLabel;
    private Label statusLabel;
    // Indicates at the bottom whether the simulation is running or not
    private Button startButton;
    private Button stopButton;
    private Timeline timeline;
    public static void main(String[] args) {
        launch(args);
    }
    // The start(Stage) method was an abstarct method from the Application class
    // until we implement it the MainApp class would have an error or underline red
    @Override
    // The Stage stage parameter is the empty window JavaFX hands us
    // Stage is the data type, stage is the name of the parameter
    public void start(Stage stage) {
        // new QueueManager() builds the three counters (meals, drinks and pastries)
        // new Simulation (...) is the engine that recieves the queue manager, and
        // stores it in a simulation field
        simulation = new Simulation(new QueueManager()); 
        menu = createMenu(); // fills the menu list, before order panel is built
        // This timeline basically means every 1 second run the code e -> {...}
        // which is a lambda aka a small unnamed function
        timeline = new Timeline(new KeyFrame(Duration.millis(Constants.TICK_MILLIS), e -> {
            //Each tick calls the function below which moves the simulated
            // clock forward by one minute
            simulation.tick();
            //This function wredraws the numbers
            refreshDisplay();

        }));
        // INDEFINITE means it repeats forever
        timeline.setCycleCount(Timeline.INDEFINITE);

        Label title = new Label("Dining Hall Queue Simulator - Akonor Cafeteria, Ashesi University");
        // root is the container for all the parts (top, left,center) of the window
        BorderPane root = new BorderPane();
        // Insets adds a 15 px margin arounf the edges
        root.setPadding(new Insets(15));
        root.setTop(title);
        root.setLeft(buildOrderPanel());
        // Each build method returns one finished panel
        // The center is a VBox aka vertical box stacking the Queue Status panel above the
        // the statistics panel
        root.setCenter(new VBox(15, buildQueuePanel(), buildStatsPanel()));
        // Places the Start/Stop bar at the bottom
        root.setBottom(buildControlBar());

        stage.setScene(new Scene(root, 1000, 700));
        stage.setTitle("Dining Hall Queue Simulator");
        stage.show();
        refreshDisplay();

    }

    @Override
    public void stop() {
        // This is what Java calls when the window closes, stopping the timer
        timeline.stop();
    }

    // only MainApp can call this method
    private List<MenuItem> createMenu() {
        // makes an empty list called list (a list is unordered)
        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem("M01", "Jollof Rice", 25.00, Constants.MEALS));
        list.add(new MenuItem("M02", "Waakye", 22.00, Constants.MEALS));
        list.add(new MenuItem("M03", "Fried Rice & Chicken", 30.00, Constants.MEALS));
        list.add(new MenuItem("M04", "Banku & Tilapia", 35.00, Constants.MEALS));
        list.add(new MenuItem("M05", "Water", 3.00, Constants.DRINKS));
        list.add(new MenuItem("M06", "Sobolo", 8.00, Constants.DRINKS));
        // This appends each item to the list
        list.add(new MenuItem("M07", "Fruit Juice", 10.00, Constants.DRINKS));
        list.add(new MenuItem("M08", "Lemonade", 8.00, Constants.DRINKS));
        list.add(new MenuItem("M09", "Meat Pie", 12.00, Constants.PASTRIES));
        list.add(new MenuItem("M10", "Doughnut", 5.00, Constants.PASTRIES));
        list.add(new MenuItem("M11", "Sausage Roll", 8.00, Constants.PASTRIES));
        list.add(new MenuItem("M12", "Chicken Pie", 14.00, Constants.PASTRIES));
        // this gives us the finished list
        return list;
    }

    // A node is anything you can draw???
    private Node buildOrderPanel() {
        nameField = new TextField();
        nameField.setPromptText("Enter your name...");
        idField = new TextField();
        // Grey indication to enter 8 digits
        idField.setPromptText("8 digits");
        // only allows input of 8 digits
        idField.setPrefColumnCount(8);
        // adds STU- to the entered student ID
        HBox idRow = new HBox(5, new Label(Constants.STUDENT_ID_PREFIX), idField);
        // itemsBox is an empty vertical space where the checkboxes will go
        VBox itemsBox = new VBox(8);
        for (String category : Constants.CATEGORIES) {
            itemsBox.getChildren().add(new Label(category));
            for (MenuItem item : menu) {
                if (item.getCategory().equals(category)) {
                    CheckBox box = new CheckBox(item.toString() + " - GHC" + item.getPrice());
                    Spinner<Integer> spinner = new Spinner<> (Constants.MIN_QUANTITY, Constants.MAX_QUANTITY, 1);
                    spinner.setPrefWidth(75);
                    spinner.setDisable(true);
                    box.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
                        if (isSelected) {
                            tickOrder.add(item);
                            spinner.setDisable(false);

                        } else {
                            tickOrder.remove(item);
                            spinner.setDisable(true);
                        }
                    });

                    checkBoxes.put(item, box);
                    spinners.put(item, spinner);
                    itemsBox.getChildren().add(new HBox(10, box, spinner));

                }
            }
        }

        ScrollPane scroll = new ScrollPane(itemsBox);
        scroll.setPrefHeight(380);
        Button submitButton = new Button("Submit Order");
        submitButton.setOnAction(e -> handleSubmit());
        
        VBox content = new VBox(10, new Label("Student Name:"), nameField,
                new Label("Student ID:"), idRow,
                new Label("Select Items:"), scroll, submitButton);
        content.setPadding(new Insets(10));
        TitledPane panel = new TitledPane("Place Order", content);
        // Doesnt allow user to fold the panel
        panel.setCollapsible(false);
        // hands the finished panel back to start
        return panel;

    }

    private Node buildQueuePanel() {
        HBox row = new HBox(30);
        row.setPadding(new Insets(10));
        for (String category : Constants.CATEGORIES) {
            Label countLabel = new Label("0");
            countLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
            queueLabels.put(category, countLabel);
            VBox box = new VBox(5, new Label(category + " Counter"), countLabel, new Label("in queue"));
            box.setAlignment(Pos.CENTER);
            row.getChildren().add(box);

        }
        TitledPane panel = new TitledPane("Queue Status", row);
        panel.setCollapsible(false);
        return panel;

    }

    private Node buildStatsPanel() {
        totalServedLabel = new Label("0");
        avgWaitLabel = new Label("0.0 min");
        totalServedLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        avgWaitLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        VBox served = new VBox(5, new Label("Total Students Served"), totalServedLabel);
        VBox wait = new VBox(5, new Label("Average Wait Time"), avgWaitLabel);
        HBox row = new HBox(60, served, wait);
        row.setPadding(new Insets(10));
        TitledPane panel = new TitledPane("Statistics", row);
        panel.setCollapsible(false);
        return panel;

    }

    private Node buildControlBar() {
    startButton = new Button("Start Simulation");
    // method in simulation that calls start()
    startButton.setOnAction(e -> handleStart());
    stopButton = new Button("Stop Simulation");
    stopButton.setOnAction(e -> handleStop());
    stopButton.setDisable(true);
    statusLabel = new Label("Simulation Ready...");

    HBox bar = new HBox(15, new Label("Simulation Control:"), startButton, stopButton, statusLabel);
    bar.setPadding(new Insets(10));
    bar.setAlignment(Pos.CENTER_LEFT);
    return bar;
    }

    private void handleStart() {
        simulation.startSimulation();
        timeline.play();
        startButton.setDisable(true);
        //means that you can press the stop
        stopButton.setDisable(false);
        statusLabel.setText("Simulation Running...");
        
    }

    private void handleStop() {
        simulation.stopSimulation();
        timeline.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        statusLabel.setText("Simulation Stopped");
        refreshDisplay();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Simulation Summary");
        alert.setHeaderText("Performance statistics");
        alert.setContentText(simulation.getSummaryReport());
        alert.showAndWait();
    }

    private void handleSubmit() {
        String studentId = Constants.STUDENT_ID_PREFIX + idField.getText().trim();
        // LinkedHashMap keeps the order items are added
        Map<MenuItem, Integer> chosen = new LinkedHashMap<> ();
        // Loop goes through tickOrder  which is in the order the student ticked
        // so that counter sequence comes out correct
        for (MenuItem item : tickOrder) {
            chosen.put(item, spinners.get(item).getValue());
        }

        try {
            Order order = simulation.placeOrderFor(nameField.getText(), studentId, chosen);
            statusLabel.setText("Order " + order.getOrderId() + " placed for " + order.getStudent().getName());
            clearForm();
            refreshDisplay();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        
        }

    }

    private void refreshDisplay() {
        for (String category : Constants.CATEGORIES) {
            queueLabels.get(category).setText(String.valueOf(simulation.getQueueLength(category)));
        }
        totalServedLabel.setText(String.valueOf(simulation.getTotalStudentsServed()));
        avgWaitLabel.setText(String.format("%.1f", simulation.getOverallAverageWaitingTime()) + " min");

    }

    private void clearForm() {
        nameField.clear();
        idField.clear();
        for (CheckBox box : checkBoxes.values()) {
            box.setSelected(false);
        }
        for (Spinner<Integer> spinner : spinners.values()) {
            spinner.getValueFactory().setValue(1);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
