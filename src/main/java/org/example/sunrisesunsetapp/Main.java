package org.example.sunrisesunsetapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/sunrisesunsetapp/layout.fxml"));
        Scene scene = new Scene(loader.load());

        // Add the stylesheet programmatically
        scene.getStylesheets().add(getClass().getResource("/org/example/sunrisesunsetapp/styles.css").toExternalForm());

        // Set the application icon
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/sunrisesunsetapp/icon.png")));

        primaryStage.setTitle("Sunrise and Sunset Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
