package main.java;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginandSignupApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/SplashScreen.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Splash Screen");
        primaryStage.show();

        // Delay for 3 seconds (3000 milliseconds) before transitioning to the login page
        PauseTransition delay = new PauseTransition(Duration.millis(1000));
        delay.setOnFinished(event -> showLoginPage(primaryStage));
        delay.play();
    }

    private void showLoginPage(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/Login.fxml"));
            Parent root = loader.load();

            main.java.LoginController loginController = loader.getController();
            loginController.setStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login Application");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
