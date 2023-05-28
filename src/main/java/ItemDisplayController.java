package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemDisplayController implements Initializable {
    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, String> idCol;

    @FXML
    private TableColumn<Item, String> titleCol;

    @FXML
    private TableColumn<Item, String> rentTypeCol;

    @FXML
    private TableColumn<Item, String> loanTypeCol;

    @FXML
    private TableColumn<Item, Integer> copiesCol;

    @FXML
    private TableColumn<Item, String> feesCol;

    @FXML
    private TableColumn<Item, String> gerneCol;

    ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

    public void readFile() {
        String line = "";
        String splitBy = ",";
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(
                    new FileReader("src\\resources\\database\\items.txt"));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                String[] items = line.split(splitBy); // use comma as separator

//                // Testing
//                System.out.println("-------------------------------");
//                System.out.println("1. ID:                 " + items[0]);
//                System.out.println("2. Title:              " + items[1]);
//                System.out.println("3. Rent type:          " + items[2]);
//                System.out.println("4. Loan type:          " + items[3]);
//                System.out.println("5. Number of copies:   " + items[4]);
//                System.out.println("6. Rental fee:         " + items[5]);
//                if (items.length == 6) {
//                    System.out.println("7. Genre: none");
//                } else {
//                    System.out.println("7. Genre: " + items[6]);
//                }

                if (items.length == 6) {
                    itemObservableList.add(new Item(items[0], items[1], items[2], items[3], 3, 6, "none"));
                } else {
                    itemObservableList.add(new Item(items[0], items[1], items[2], items[3], 5, 6, "none"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readFile();

        idCol.setCellValueFactory(new PropertyValueFactory<Item, String>("idCol"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Item, String>("titleCol"));
        rentTypeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("rentTypeCol"));
        loanTypeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("loanTypeCol"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("copiesCol"));
        feesCol.setCellValueFactory(new PropertyValueFactory<Item, String>("feesCol"));
        gerneCol.setCellValueFactory(new PropertyValueFactory<Item, String>("gerneCol"));

        itemTableView.setItems(itemObservableList);
    }
}
