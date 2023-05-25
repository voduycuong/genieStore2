package main.java;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class LoginandSignupApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Calculate the screen center coordinates
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() / 2);
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() / 2);

        // Create the splash screen stage
        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setX(centerX - (750 / 2)); // Set the X position at the center of the screen
        splashStage.setY(centerY - (500 / 2)); // Set the Y position at the center of the screen

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/SplashScreen.fxml"));
        Parent root = loader.load();
        splashStage.setScene(new Scene(root));
        splashStage.setTitle("Splash Screen");
        splashStage.show();

        // Delay for 3 seconds (3000 milliseconds) before transitioning to the login page
        PauseTransition delay = new PauseTransition(Duration.millis(3000));
        delay.setOnFinished(event -> {
            splashStage.close();
            showLoginPage(primaryStage);
        });
        delay.play();
    }

    private void showLoginPage(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login Application");

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double centerX = screenBounds.getMinX() + (screenBounds.getWidth() / 2);
            double centerY = screenBounds.getMinY() + (screenBounds.getHeight() / 2);

            primaryStage.setX(centerX - (600 / 2));
            primaryStage.setY(centerY - (400 / 2));

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
