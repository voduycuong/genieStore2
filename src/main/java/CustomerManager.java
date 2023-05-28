package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerManager {
    private List<Customer> customers;
    private String databaseFilePath;

    public CustomerManager(String databaseFilePath) {
        this.customers = new ArrayList<>();
        this.databaseFilePath = databaseFilePath;
        loadCustomersFromDatabase();
    }

    private void loadCustomersFromDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 8) {
                    String customerId = fields[0];
                    String name = fields[1];
                    String address = fields[2];
                    String phone = fields[3];
                    int numberOfRentals = Integer.parseInt(fields[4]);
                    String customerType = fields[5];
                    String username = fields[6];
                    String password = fields[7];
                    Customer customer = new Customer(name, customerId, phone, address, numberOfRentals, customerType, username, password);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveCustomersToDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFilePath))) {
            for (Customer customer : customers) {
                String line = customer.getCustomerId() + "," +
                        customer.getName() + "," +
                        customer.getAddress() + "," +
                        customer.getPhone() + "," +
                        customer.getNumberOfRentals() + "," +
                        customer.getCustomerType() + "," +
                        customer.getUsername() + "," +
                        customer.getPassword();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addOrUpdateCustomer(Customer customer) {
        int index = findCustomerIndex(customer.getCustomerId());
        if (index != -1) {
            customers.set(index, customer);
        } else {
            customers.add(customer);
        }
        saveCustomersToDatabase();
    }

    public void promoteCustomer(Customer customer) {
        if (customer instanceof Guest) {
            Guest guest = (Guest) customer;
            if (guest.getBorrowedItemsCount() > 3) {
                Regular regular = new Regular(customer.getName(), customer.getCustomerId(), customer.getPhone(), customer.getAddress(), customer.getNumberOfRentals(), customer.getCustomerType(), customer.getUsername(), customer.getPassword());
                customers.remove(customer);
                customers.add(regular);
            }
        } else if (customer instanceof Regular) {
            Regular regular = (Regular) customer;
            if (regular.getBorrowedItemsCount() > 5) {
                VIP vip = new VIP(customer.getName(), customer.getCustomerId(), customer.getPhone(), customer.getAddress(), customer.getNumberOfRentals(), customer.getCustomerType(), customer.getUsername(), customer.getPassword());
                customers.remove(customer);
                customers.add(vip);
            }
        }
    }

    public List<Customer> getAllCustomersSortedByName() {
        List<Customer> sortedCustomers = new ArrayList<>(customers);
        sortedCustomers.sort(Comparator.comparing(Customer::getName));
        return sortedCustomers;
    }

    public List<Customer> getCustomersByLevel(String level) {
        List<Customer> filteredCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if ((level.equals("Guest") && customer instanceof Guest)
                    || (level.equals("Regular") && customer instanceof Regular)
                    || (level.equals("VIP") && customer instanceof VIP)) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;
    }

    public Customer searchCustomerByNameOrId(String query) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(query) || customer.getCustomerId().equals(query)) {
                return customer;
            }
        }
        return null;
    }

    private int findCustomerIndex(String customerId) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId().equals(customerId)) {
                return i;
            }
        }
        return -1;
    }
}
