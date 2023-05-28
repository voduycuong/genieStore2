package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
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
    private TableColumn<Item, String> genreCol;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    private ItemManager itemManager;
    private ObservableList<Item> itemObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RentalController rentalController = new RentalController();
        List<Item> items = rentalController.getItems();

        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        rentTypeCol.setCellValueFactory(new PropertyValueFactory<>("rentType"));
        loanTypeCol.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<>("numberOfCopies"));
        feesCol.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        itemObservableList = FXCollections.observableArrayList(items);
        itemTableView.setItems(itemObservableList);

        itemManager = new ItemManager(items);

        // Search button event handler
        searchButton.setOnAction(event -> handleSearchButton());
    }

    @FXML
    private void handleSearchButton() {
        String keyword = searchField.getText().toLowerCase().trim();

        // Apply the filter based on the search keyword
        itemManager.applySearchFilter(keyword);

        // Update the item list in the table view
        itemObservableList.setAll(itemManager.getFilteredList());

        // Clear the search field after filtering
        searchField.clear();
    }
}
