package main.java;

public class Guest extends Customer {
    private int borrowedItemsCount;

    public Guest(String name, int customerId, String phone, String address, int numberOfRentals, String customerType, String username, String password) {
        super(name, customerId, phone, address, numberOfRentals, customerType, username, password);
        borrowedItemsCount = 0;
    }

    public int getBorrowedItemsCount() {
        return borrowedItemsCount;
    }

    public void incrementBorrowedItemsCount() {
        borrowedItemsCount++;
    }

    public boolean canBorrowItem() {
        return borrowedItemsCount < 2;
    }

    public boolean canBePromoted() {
        return borrowedItemsCount > 3;
    }
}
