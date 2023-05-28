package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnController implements Initializable {

    private List<Item> items;
    private List<Customer> customers;
    private String currentUserId;
    private String currentItemId;
    private Stage stage;

    @FXML
    private Text currentUserText;


    public ReturnController() {
        items = new ArrayList<>();
        customers = new ArrayList<>();

        loadItems();
        loadCustomers();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setCurrentItemId(String currentItemId) {
        this.currentItemId = currentItemId;
    }

    @FXML
    public void returnItem() {
        if (currentUserId == null || currentItemId == null) {
            System.out.println("No user or item selected.");
            return;
        }

        Customer customer = getCustomerById(currentUserId);
        Item item = getItemById(currentItemId);

        if (customer != null && item != null) {
            if (customer.getRentedItems().contains(currentItemId)) {
                item.increaseCopies();
                customer.returnItem(currentItemId); // Remove returned item from customer's list
                showAlert("Success","Item returned successfully.");
                updateItemsFile(); // Update the items.txt file
                updateCustomersFile(); // Update the customers.txt file
            } else {
                showAlert("Error","You do not own this item.");
            }
        } else {
            System.out.println("Item or customer not found.");
        }
    }


    private void updateCustomersFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/database/customers.txt"))) {
            for (Customer customer : customers) {
                writer.write(customer.getCustomerId() + "," + customer.getName() + "," + customer.getAddress() + "," + customer.getPhone() + "," + customer.getNumberOfRentals() + "," + customer.getCustomerType() + "," + customer.getUsername() + "," + customer.getPassword());
                writer.newLine();

                List<String> rentedItems = customer.getRentedItems();
                for (String itemId : rentedItems) {
                    writer.write(itemId);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to update customers file: " + e.getMessage());
        }
    }

    public void updateItemsFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/database/items.txt"))) {
            for (Item item : items) {
                String line = item.getItemId() + "," + item.getTitle() + "," + item.getRentType() + "," + item.getLoanType() + "," + item.getNumberOfCopies() + "," + item.getRentalFee();

                if (item instanceof Record || item instanceof DVD) {
                    line += "," + item.getGenre();
                }

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to update items file: " + e.getMessage());
        }
    }

    private Item getItemById(String itemId) {
        for (Item item : items) {
            if (item.getItemId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    private Customer getCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    private void loadItems() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/database/items.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 6) {
                    String id = data[0];
                    String title = data[1];
                    String rentType = data[2];
                    String loanType = data[3];

                    int numberOfCopies;
                    try {
                        numberOfCopies = Integer.parseInt(data[4]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of copies format: " + line);
                        continue;
                    }

                    double rentalFee;
                    try {
                        rentalFee = Double.parseDouble(data[5]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid rental fee format: " + line);
                        continue;
                    }

                    String genre = "";
                    if (data.length > 6) {
                        genre = data[6];
                    }

                    Item item;
                    if (rentType.equalsIgnoreCase("Game")) {
                        item = new Game(id, title, rentType, loanType, numberOfCopies, rentalFee, genre);
                    } else if (rentType.equalsIgnoreCase("Record")) {
                        item = new Record(id, title, rentType, loanType, numberOfCopies, rentalFee, genre);
                    } else if (rentType.equalsIgnoreCase("DVD")) {
                        item = new DVD(id, title, rentType, loanType, numberOfCopies, rentalFee, genre);
                    } else {
                        System.out.println("Invalid item data: " + line);
                        continue;
                    }


                    items.add(item);
                } else {
                    System.out.println("Invalid item data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load item data: " + e.getMessage());
        }
    }

    public List<Item> getItems() {
        return items;
    }

    private void loadCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/database/customers.txt"))) {
            String line;
            Customer currentCustomer = null;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 8) {
                    if (currentCustomer != null) {
                        // Add the previously processed customer to the list
                        customers.add(currentCustomer);
                    }

                    String id = data[0];
                    String name = data[1];
                    String address = data[2];
                    String phone = data[3];

                    int numberOfRentals;
                    try {
                        numberOfRentals = Integer.parseInt(data[4]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of rentals format: " + line);
                        continue;
                    }

                    String customerType = data[5];
                    String username = data[6];
                    String password = data[7];

                    // Create a new customer object
                    currentCustomer = new Customer(name, id, phone, address, numberOfRentals, customerType, username, password);

                    // Process the rented items
                    for (int i = 8; i < data.length; i++) {
                        String itemId = data[i];
                        currentCustomer.rentItem(itemId);
                    }
                } else {
                    // Add the item ID to the current customer's rented items
                    if (currentCustomer != null) {
                        currentCustomer.rentItem(data[0]);
                    } else {
                        System.out.println("Invalid customer data: " + line);
                    }
                }
            }

            // Add the last processed customer to the list
            if (currentCustomer != null) {
                customers.add(currentCustomer);
            }
        } catch (IOException e) {
            System.out.println("Failed to load customer data: " + e.getMessage());
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    private void displayItemDetails(Item item) {
        System.out.println("Item ID: " + item.getItemId());
        System.out.println("Title: " + item.getTitle());
        System.out.println("Rent Type: " + item.getRentType());
        System.out.println("Loan Type: " + item.getLoanType());
        System.out.println("Number of Copies: " + item.getNumberOfCopies());
        System.out.println("Rental Fee: " + item.getRentalFee());
        if (item instanceof Record || item instanceof DVD) {
            System.out.println("Genre: " + item.getGenre());
        }
    }

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, String> idCol;

    @FXML
    private TableColumn<Item, String> titleCol;

    @FXML
    private TableColumn<Item, String> rentTypeCol;

    @FXML
    private TableColumn<Item, String> loanTypeCol;

    @FXML
    private TableColumn<Item, Integer> copiesCol;

    @FXML
    private TableColumn<Item, String> feesCol;

    @FXML
    private TableColumn<Item, String> genreCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ReturnController initialized"); // Debug print statement
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        rentTypeCol.setCellValueFactory(new PropertyValueFactory<>("rentType"));
        loanTypeCol.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<>("numberOfCopies"));
        feesCol.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        itemTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayItemDetails(newSelection);
                setCurrentItemId(newSelection.getItemId());
            }
        });

        List<Item> rentedItems = new ArrayList<>();
        for (Customer customer : getCustomers()) {
            if (customer.getCustomerId().equals(currentUserId)) {
                for (String itemId : customer.getRentedItems()) {
                    Item rentedItem = getItemById(itemId);
                    if (rentedItem != null) {
                        rentedItems.add(rentedItem);
                    }
                }
                break;
            }
        }

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList(this.items);
        itemTableView.setItems(itemObservableList);
        currentUserText.setText("Current User ID: " + currentUserId);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
