package main.java;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class SplashScreenController implements Initializable {

    @FXML
    private Label loadingLabel;

    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingLabel.setText("0%");

        // Calculate the increment value per step
        final int totalSteps = 100;
        final double increment = (double) totalSteps / 5; // Total steps divided by 5 seconds

        // Simulate loading progress
        PauseTransition loadingTransition = new PauseTransition(Duration.seconds(5));
        loadingTransition.setOnFinished(event -> closeSplashScreen());

        AtomicInteger currentProgress = new AtomicInteger(0);
        loadingTransition.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double elapsedSeconds = newValue.toSeconds();
            double progress = Math.min((elapsedSeconds / 5.0) * totalSteps, totalSteps);
            loadingLabel.setText((int) progress + "%");
            currentProgress.set((int) progress);
        });

        loadingTransition.play();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void closeSplashScreen() {
        primaryStage.show();
        Stage splashStage = (Stage) loadingLabel.getScene().getWindow();
        splashStage.close();
    }
}


