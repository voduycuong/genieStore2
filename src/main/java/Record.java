package main.java;

public class Record extends Item {
    
    public Record(String itemId, String title, String rentType, String loanType, int numberOfCopies, double rentalFee, String genre) {
        super(itemId, title, "Record", loanType, numberOfCopies, rentalFee, genre);
    }

}
