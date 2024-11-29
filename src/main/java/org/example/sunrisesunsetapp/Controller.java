package org.example.sunrisesunsetapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField cityField;

    @FXML
    private Label latitudeLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private Label sunriseLabel;

    @FXML
    private Label sunsetLabel;

    @FXML
    private Label dayLengthLabel;

    @FXML
    public void fetchSunriseSunsetData(ActionEvent event) {
        String city = cityField.getText();
        if (city.isEmpty()) {
            System.out.println("City name cannot be empty.");
            return;
        }

        try {
            Location location = APIHandler.getLocation(city);
            if (location == null) {
                System.out.println("Error: Unable to fetch location data.");
                return;
            }

            latitudeLabel.setText(String.valueOf(location.getLatitude()));
            longitudeLabel.setText(String.valueOf(location.getLongitude()));

            SunriseSunsetData data = APIHandler.getSunriseSunsetData(location.getLatitude(), location.getLongitude());
            if (data != null) {
                sunriseLabel.setText(data.getSunrise());
                sunsetLabel.setText(data.getSunset());
                dayLengthLabel.setText(formatDayLength(data.getDayLength()));
            } else {
                System.out.println("Error: Unable to fetch sunrise and sunset data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearFields(ActionEvent event) {
        cityField.clear();
        latitudeLabel.setText("-");
        longitudeLabel.setText("-");
        sunriseLabel.setText("-");
        sunsetLabel.setText("-");
        dayLengthLabel.setText("-");
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        System.exit(0);
    }

    private String formatDayLength(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02dh %02dm %02ds", hours, minutes, remainingSeconds);
    }
}
