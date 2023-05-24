package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RentalController {

    private List<Item> items;
    private List<Customer> customers;

    public RentalController() {
        items = new ArrayList<>();
        customers = new ArrayList<>();

        loadItems();
        loadCustomers();
    }

    public void rentItem(String itemId, String customerId) {
        Item item = getItemById(itemId);
        Customer customer = getCustomerById(customerId);

        if (item != null && customer != null) {
            if (item.isAvailable()) {
                if (item.getLoanType().equals("2-day")) {
                    if (customer.getCustomerType().equals("Guest")) {
                        System.out.println("Guest customers cannot borrow 2-day items.");
                    } else if (customer.getCustomerType().equals("Regular") || customer.getCustomerType().equals("VIP")) {
                        if (customer.getCustomerType().equals("VIP")) {
                            customer.incrementRewardPoints(10);
                            if (customer.getRewardPoints() >= 100) {
                                System.out.println("Congratulations! You have earned a free rental.");
                                item.decreaseCopies(); // Rent the item for free
                                customer.resetRewardPoints(); // Reset reward points
                            }
                        }
                        item.decreaseCopies();
                        customer.rentItem(itemId);
                        System.out.println("Item rented successfully.");
                    } else {
                        System.out.println("Only Regular and VIP customers can borrow 2-day items.");
                    }
                } else {
                    item.decreaseCopies();
                    customer.rentItem(itemId);
                    System.out.println("Item rented successfully.");
                }

                // Update the customers.txt file
                updateCustomersFile();
            } else {
                System.out.println("Item is not available for rent.");
            }
        } else {
            System.out.println("Item or customer not found.");
        }
    }


    public void returnItem(String itemId, String customerId) {
        Item item = getItemById(itemId);
        if (item != null) {
            item.increaseCopies();
            Customer customer = getCustomerById(customerId);
            if (customer != null) {
                customer.returnItem(itemId); // Remove returned item from customer's list
                System.out.println("Item returned successfully.");
                updateCustomersFile(); // Update the customers.txt file
            } else {
                System.out.println("Customer not found.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }

    private void updateCustomersFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/database/customers.txt"))) {
            for (Customer customer : customers) {
                writer.write(customer.getCustomerId() + "," + customer.getName() + "," + customer.getAddress()
                        + "," + customer.getPhone() + "," + customer.getNumberOfRentals() + "," + customer.getCustomerType()
                        + "," + customer.getUsername() + "," + customer.getPassword());
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
                        item = new Game(id, title, loanType, numberOfCopies, rentalFee);
                    } else if (rentType.equalsIgnoreCase("Record")) {
                        item = new Record(id, title, loanType, numberOfCopies, rentalFee, genre);
                    } else if (rentType.equalsIgnoreCase("DVD")) {
                        item = new DVD(id, title, loanType, numberOfCopies, rentalFee, genre);
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
                    currentCustomer = new Customer(id, name, address, phone, numberOfRentals, customerType, username, password);

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

    public static void main(String[] args) {
        RentalController rentalController = new RentalController();

        rentalController.rentItem("I017-2012", "C001");  // Renting an item
        rentalController.rentItem("I015-1984", "C002");  // Renting an item
        rentalController.returnItem("I001-2001", "C001");  // Returning an item
        rentalController.returnItem("I002-1988", "C003");  // Returning an item
    }
}
