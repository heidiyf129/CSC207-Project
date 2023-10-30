package view;

import interface_adapter.AirQualityController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

public class AirQualityView extends Application {

    private final AirQualityController airQualityController;

    public AirQualityView(AirQualityController airQualityController) {
        this.airQualityController = airQualityController;
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        // Input field
        TextField cityInput = new TextField();
        cityInput.setPromptText("Enter your city name");

        // Button to fetch data
        Button fetchButton = new Button("Get Air Quality Index");

        // Label to display the result
        Label resultLabel = new Label();

        fetchButton.setOnAction(e -> {
            String city = cityInput.getText();
            String data = airQualityController.fetchAirQuality(city, "Your State Here", "Your Country Here"); // Fill in the state and country as needed

            // Parsing and displaying the AQI from the returned data
            JSONObject jsonResponse = new JSONObject(data);
            int aqi = jsonResponse.getJSONObject("data").getJSONObject("current").getJSONObject("pollution").getInt("aqi");
            resultLabel.setText("Air Quality Index (AQI) for " + city + ": " + aqi);
        });

        layout.getChildren().addAll(cityInput, fetchButton, resultLabel);

        // Create the scene and stage
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Air Quality Checker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}