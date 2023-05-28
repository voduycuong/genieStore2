package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField searchField;
    @FXML
    private RadioButton guestRadio;
    @FXML
    private RadioButton regularRadio;
    @FXML
    private RadioButton vipRadio;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button promoteButton;
    @FXML
    private Button displayButton;
    @FXML
    private Button searchButton;
    @FXML
    private ToggleGroup levelGroup;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private CustomerManager customerManager;
    private boolean isAdmin;
    private Admin admin;

    private void clearLoginFields() {
        usernameField.clear();
        passwordField.clear();
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        // Disable or enable UI elements based on the isAdmin flag
        addButton.setDisable(!isAdmin);
        updateButton.setDisable(!isAdmin);
        promoteButton.setDisable(!isAdmin);
        displayButton.setDisable(!isAdmin);
    }


    public void initialize() {
        String databaseFilePath = "src\\resources\\database\\customers.txt";
        customerManager = new CustomerManager(databaseFilePath);
        admin = new Admin("admin", "password");
    }

    @FXML
    private void handleAdminLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (admin.authenticate(username, password)) {
            setAdmin(true);
            clearLoginFields();
        } else {
            showErrorAlert("Invalid username or password.");
        }
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        String name = nameField.getText();
        String idText = idField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || idText.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showErrorAlert("Please fill in all the fields.");
            return;
        }

        String customerId = idText;

        Customer customer;
        if (guestRadio.isSelected()) {
            customer = new Guest(name, customerId, phone, address, 0, "Guest", "username", "password");
        } else if (regularRadio.isSelected()) {
            customer = new Regular(name, customerId, phone, address, 0, "Regular", "username", "password");
        } else {
            customer = new VIP(name, customerId, phone, address, 0, "VIP", "username", "password");
        }

        customerManager.addOrUpdateCustomer(customer);
        showInfoAlert("Customer added successfully.");
        clearFields();
    }

    @FXML
    private void handleUpdateButton(ActionEvent event) {
//        if (!isAdmin) {
//            showErrorAlert("Only admin can perform this action.");
//            return;
//        }
        String idText = idField.getText();

        if (idText.isEmpty()) {
            showErrorAlert("Please enter the ID of the customer to update.");
            return;
        }

        String customerId = idText;

        Customer existingCustomer = customerManager.searchCustomerByNameOrId(customerId);
        if (existingCustomer == null) {
            showErrorAlert("Customer not found.");
            return;
        }

        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (!name.isEmpty()) {
            existingCustomer = new Customer(name, existingCustomer.getCustomerId(), existingCustomer.getPhone(),
                    existingCustomer.getAddress(), existingCustomer.getNumberOfRentals(),
                    existingCustomer.getCustomerType(), existingCustomer.getUsername(), existingCustomer.getPassword());
        }
        if (!phone.isEmpty()) {
            existingCustomer = new Customer(name, existingCustomer.getCustomerId(), existingCustomer.getPhone(),
                    existingCustomer.getAddress(), existingCustomer.getNumberOfRentals(),
                    existingCustomer.getCustomerType(), existingCustomer.getUsername(), existingCustomer.getPassword());
        }
        if (!address.isEmpty()) {
            existingCustomer = new Customer(name, existingCustomer.getCustomerId(), existingCustomer.getPhone(),
                    existingCustomer.getAddress(), existingCustomer.getNumberOfRentals(),
                    existingCustomer.getCustomerType(), existingCustomer.getUsername(), existingCustomer.getPassword());
        }

        customerManager.addOrUpdateCustomer(existingCustomer);
        showInfoAlert("Customer updated successfully.");
        clearFields();
    }

    @FXML
    private void handlePromoteButton(ActionEvent event) {
//        if (!isAdmin) {
//            showErrorAlert("Only admin can perform this action.");
//            return;
//        }
        String idText = idField.getText();

        if (idText.isEmpty()) {
            showErrorAlert("Please enter the ID of the customer to promote.");
            return;
        }

        String customerId = idText;

        Customer customer = customerManager.searchCustomerByNameOrId(customerId);
        if (customer == null) {
            showErrorAlert("Customer not found.");
            return;
        }

        if (customer.getCustomerType().equals("Guest")) {
            if (customer.getNumberOfRentals() > 3) {
                customer.setCustomerType("Regular");
                customerManager.saveCustomersToDatabase();
                showInfoAlert("Customer promoted to Regular.");
            } else {
                showErrorAlert("Guest customer must have borrowed and returned more than 3 items to be promoted.");
            }
        } else if (customer.getCustomerType().equals("Regular")) {
            if (customer.getNumberOfRentals() > 5) {
                customer.setCustomerType("VIP");
                customerManager.saveCustomersToDatabase();
                showInfoAlert("Customer promoted to VIP.");
            } else {
                showErrorAlert("Regular customer must have borrowed and returned more than 5 items to be promoted.");
            }
        } else {
            showErrorAlert("This customer is already a VIP.");
        }
        clearFields();
    }

    @FXML
    private void handleDisplayButton() {
//        if (!isAdmin) {
//            showErrorAlert("Only admin can perform this action.");
//            return;
//        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/CustomerDisplay.fxml"));
            Parent root = loader.load();

            Stage customerStage = new Stage();
            customerStage.setScene(new Scene(root));
            customerStage.setTitle("Customer Database");
            customerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
//        if (!isAdmin) {
//            showErrorAlert("Only admin can perform this action.");
//            return;
//        }
        String query = searchField.getText();

        if (query.isEmpty()) {
            showErrorAlert("Please enter a name or ID to search for.");
            return;
        }

        Customer customer = customerManager.searchCustomerByNameOrId(query);

        if (customer == null) {
            showErrorAlert("Customer not found.");
        } else {
            showInfoAlert(customer.toString());
        }

        clearFields();
    }

    private void clearFields() {
        nameField.clear();
        idField.clear();
        phoneField.clear();
        addressField.clear();
        searchField.clear();
        guestRadio.setSelected(false);
        regularRadio.setSelected(false);
        vipRadio.setSelected(false);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
