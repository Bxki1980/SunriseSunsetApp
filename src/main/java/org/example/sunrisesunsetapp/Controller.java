package org.example.sunrisesunsetapp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
    @FXML
    private TextField cityTextField;
    @FXML
    private Label sunriseLabel, sunsetLabel, durationLabel;
    @FXML
    private TableView<SunriseSunsetData> savedTimesTable;
    @FXML
    private TableColumn<SunriseSunsetData, String> cityColumn, sunriseColumn, sunsetColumn, dayLengthColumn;

    private APIHandler apiHandler = new APIHandler();
    private DataService dataService = new DataService();

    public void initialize() {
        // Initialize table columns and load saved data
    }

    @FXML
    private void handleFetchButtonAction() {
        // Handle fetch button click: get data from API and save to DB
    }

    private void loadSavedData() {
        // Load data from the database into the table
    }

    private void showAlert(String message) {
        // Utility method to show alert dialogs
    }
}