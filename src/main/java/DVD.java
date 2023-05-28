package main.java;

public class DVD extends Item {
    private String genre;

    public DVD(String itemId, String title, String rentType, String loanType, int numberOfCopies, double rentalFee, String genre) {
        super(itemId, title, "DVD", loanType, numberOfCopies, rentalFee, genre);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
