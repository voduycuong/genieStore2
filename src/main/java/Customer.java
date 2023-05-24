package main.java;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String address;
    private String phone;
    private int numberOfRentals;
    private String customerType;
    private String username;
    private String password;
    private int rewardPoints;

    private List<String> rentedItems;

    public Customer(String customerId, String name, String address, String phone, int numberOfRentals, String customerType, String username, String password) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.numberOfRentals = numberOfRentals;
        this.customerType = customerType;
        this.username = username;
        this.password = password;
        this.rewardPoints = 0;
        this.rentedItems = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getNumberOfRentals() {
        return numberOfRentals;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void incrementRewardPoints(int points) {
        rewardPoints += points;
    }

    public void resetRewardPoints() {
        rewardPoints = 0;
    }

    public List<String> getRentedItems() {
        return rentedItems;
    }

    public void addRentedItem(String itemId) {
        rentedItems.add(itemId);
    }

    public void rentItem(String itemId) {
        rentedItems.add(itemId);
    }

    public void returnItem(String itemId) {
        rentedItems.remove(itemId);
        numberOfRentals++;
    }
}
