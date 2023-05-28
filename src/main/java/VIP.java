package main.java;

public class VIP extends Customer {
    private int rewardPoints;

    public VIP(String name, String customerId, String phone, String address, int numberOfRentals, String customerType, String username, String password) {
        super(name, customerId, phone, address, numberOfRentals, customerType, username, password);
        rewardPoints = 0;
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
