package main.java;

public class Game extends Item {
    public Game(String itemId, String title, String rentType, String loanType, int numberOfCopies, double rentalFee, String genre) {
        super(itemId, title, "Game", loanType, numberOfCopies, rentalFee, null);
    }
}
