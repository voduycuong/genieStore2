package main.java;

public class Item {
    private String name;
    private boolean isTwoDayItem;
    private String itemId;
    private String title;
    private String rentType;
    private String loanType;
    private int numberOfCopies;
    private double rentalFee;
    private String genre;

    public Item(String name, boolean isTwoDayItem, String itemId, String title, String rentType, String loanType, int numberOfCopies, double rentalFee, String genre) {
        this.name = name;
        this.isTwoDayItem = isTwoDayItem;
        this.itemId = itemId;
        this.title = title;
        this.rentType = rentType;
        this.loanType = loanType;
        this.numberOfCopies = numberOfCopies;
        this.rentalFee = rentalFee;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public boolean isTwoDayItem() {
        return isTwoDayItem;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getRentType() {
        return rentType;
    }

    public String getLoanType() {
        return loanType;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return numberOfCopies > 0;
    }

    public void decreaseCopies() {
        if (numberOfCopies > 0) {
            numberOfCopies--;
        }
    }

    public void increaseCopies() {
        numberOfCopies++;
    }
}
