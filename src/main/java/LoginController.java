package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LoginController {

    private Stage stage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void setStage(Stage stage) {
        this.stage = stage;
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
                    showAlert("Login Successful!", "Welcome, " + fields[1].trim() + "!");
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

    @FXML
    public void goToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/Signup.fxml"));
            Parent root = loader.load();

            main.java.SignupController signupController = loader.getController();
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
