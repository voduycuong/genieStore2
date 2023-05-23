package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class SignupController {

    private Stage stage;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void signUp() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Signup Failed", "Please fill in all the fields.");
            return;
        }

        if (!phone.matches("\\d+")) {
            showAlert("Signup Failed", "Phone number must contain only digits.");
            return;
        }

        try {
            // Read the existing customer data
            List<String> customerData = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/database/customers.txt")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    customerData.add(line);
                }
            }

            // Check if the username is already taken
            for (String customer : customerData) {
                String[] fields = customer.split(",");
                if (fields.length >= 8 && fields[6].trim().equals(username)) {
                    showAlert("Signup Failed", "Username already exists. Please choose a different username.");
                    return;
                }
            }

            // Generate a new customer record
            String newCustomer = generateNewCustomer(name, address, phone, username, password);

            // Append the new customer record to the file
            Path customersFilePath = Path.of("src/resources/database/customers.txt");
            Files.writeString(customersFilePath, newCustomer + System.lineSeparator(), StandardOpenOption.APPEND);

            showAlert("Signup Successful!", "Your account has been created. Please login with your new credentials.");
        } catch (IOException e) {
            showAlert("Error", "Unable to read/write customer data.");
        }
    }


    private String generateNewCustomer(String name, String address, String phone, String username, String password) {
        StringBuilder customerBuilder = new StringBuilder();
        customerBuilder.append("C").append(String.format("%03d", getNextCustomerId())).append(",");
        customerBuilder.append(name).append(",");
        customerBuilder.append(address).append(",");
        customerBuilder.append(phone).append(",");
        customerBuilder.append("0").append(",");
        customerBuilder.append("Guest").append(",");
        customerBuilder.append(username).append(",");
        customerBuilder.append(password);
        return customerBuilder.toString();
    }

    private int getNextCustomerId() {
        int nextId = 1;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/resources/database/customers.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 1 && fields[0].startsWith("C")) {
                    int id = Integer.parseInt(fields[0].substring(1));
                    nextId = Math.max(nextId, id + 1);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Unable to read customer data.");
        }

        return nextId;
    }

    @FXML
    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/Login.fxml"));
            Parent root = loader.load();

            main.java.LoginController loginController = loader.getController();
            loginController.setStage(stage);

            stage.setScene(new Scene(root));
            stage.show(); // Show the login screen
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
