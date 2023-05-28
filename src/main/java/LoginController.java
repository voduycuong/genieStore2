package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LoginController {

    private Stage stage;
    private String currentUserId;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private RentalController rentalController;

    private ReturnController returnController;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    @FXML
    public void initialize() {
        usernameField.setOnAction(event -> login());
        passwordField.setOnAction(event -> login());
    }

    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            List<String> customerData = readCustomerData();

            for (String customer : customerData) {
                String[] fields = customer.split(",");
                if (fields.length >= 8 && fields[6].trim().equals(username) && fields[7].trim().equals(password)) {
                    currentUserId = fields[0].trim();
                    showAlert("Login Successful!", "Welcome, " + fields[1].trim() + "!");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/MenuBar.fxml"));
                    Parent root = loader.load();

                    MenuBarController menuBarController = loader.getController();
                    menuBarController.setStage(stage);
                    menuBarController.setRentalController(rentalController);
                    menuBarController.setUserId(currentUserId);
                    menuBarController.setReturnController(returnController);
                    menuBarController.setUserId(currentUserId);
                    stage.setTitle("Dragon's Den");

                    RentalController rentalController = new RentalController();
                    rentalController.setCurrentUserId(currentUserId);

                    ReturnController returnController = new ReturnController();
                    returnController.setCurrentUserId(currentUserId);

                    menuBarController.setRentalController(rentalController);
                    menuBarController.setReturnController(returnController);

                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    double centerX = screenBounds.getMinX() + (screenBounds.getWidth() / 2);
                    double centerY = screenBounds.getMinY() + (screenBounds.getHeight() / 2);
                    System.out.println("Current User ID: " + currentUserId);

                    stage.setX(centerX - (1000 / 2));
                    stage.setY(centerY - (800 / 2));

                    stage.setScene(new Scene(root));
                    stage.show();

                    return;
                }
            }

            showAlert("Login Failed", "Invalid username or password.");
        } catch (IOException e) {
            showAlert("Error", "Unable to read customer data.");
        }
    }


    private List<String> readCustomerData() throws IOException {
        Path customersFilePath = Path.of("src/resources/database/customers.txt");
        return Files.readAllLines(customersFilePath);
    }

    public void setRentalController(RentalController rentalController) {
        this.rentalController = rentalController;
    }

    @FXML
    public void goToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/Signup.fxml"));
            Parent root = loader.load();

            SignupController signupController = loader.getController();
            signupController.setStage(stage);

            stage.setScene(new Scene(root));
            stage.show(); // Show the signup screen
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
