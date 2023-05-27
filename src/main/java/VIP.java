package main.java;

public class VIP extends Customer {
    private int borrowedItemsCount;
    private int rewardPoints;

    public VIP(String name, int customerId, String phone, String address, int numberOfRentals, String customerType, String username, String password) {
        super(name, customerId, phone, address, numberOfRentals, customerType, username, password);
        borrowedItemsCount = 0;
        rewardPoints = 0;
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
        return borrowedItemsCount > 3;
    }

    public void addRewardPoints(int points) {
        rewardPoints += points;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public boolean canRentFreeItem() {
        return rewardPoints >= 100;
    }
}
