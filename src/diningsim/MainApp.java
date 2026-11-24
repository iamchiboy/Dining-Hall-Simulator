package diningsim;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * MainApp
 * The main application class for the dining hall queue simulation.
 * Builds and runs the JavaFX window
 * It initializes the simulation and starts it.
 */
public class MainApp extends Application {
    private Simulation simulation;
    private List<MenuItem> menu = new ArrayList<>();
    private Map<MenuItem, CheckBox> checkBoxes = new LinkedHashMap<>();
    private Map<MenuItem, Spinner<Integer>> spinners = new LinkedHashMap<>();
    private List<MenuItem> tickOrder = new ArrayList<>();
    private Map<String, Label> queueLabels = new LinkedHashMap<>();
    private TextField nameField;
    private TextField idField;
    private Label totalServedLabel;
    private Label avgWaitLabel;
    private Label statusLabel;
    private Button startButton;
    private Button stopButton;
    private Timeline timeline;
    public static void main(String[] args) {
        launch(args);
    }
}
