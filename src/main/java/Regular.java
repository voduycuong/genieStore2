package main.java;

public class Regular extends Customer {
    private int borrowedItemsCount;

    public Regular(String name, int customerId, String phone, String address, int numberOfRentals, String customerType, String username, String password) {
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
        return true;
    }

    public boolean canBePromoted() {
        return borrowedItemsCount > 5;
    }
}
