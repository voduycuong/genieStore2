package main.java;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ItemDisplayController extends Application {
    public void displayItem() throws FileNotFoundException {
        String line = "";
        String splitBy = ",";
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(
                    new FileReader("/resources/database/items.txt"));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                String[] items = line.split(splitBy); // use comma as separator
                System.out.println("-------------------------------");
                System.out.println("1. ID:                 " + items[0]);
                System.out.println("2. Title:              " + items[1]);
                System.out.println("3. Rent type:          " + items[2]);
                System.out.println("4. Loan type:          " + items[3]);
                System.out.println("5. Number of copies:   " + items[4]);
                System.out.println("6. Rental fee:         " + items[5]);
                if (items.length == 6) {
                    System.out.println("7. Genre: none");
                } else {
                    System.out.println("7. Genre: " + items[6]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        
    }
}
