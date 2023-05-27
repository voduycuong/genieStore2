package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MenuBarApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/MenuBar.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Home");
        primaryStage.show();
    }

    public static Object loadWindow(URL loc, String title, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setStage(parentStage);

            parentStage.setScene(new Scene(root));
            parentStage.setTitle(title);

            parentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


