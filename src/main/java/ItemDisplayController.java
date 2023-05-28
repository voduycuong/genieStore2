package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        rentTypeCol.setCellValueFactory(new PropertyValueFactory<>("rentType"));
        loanTypeCol.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<>("numberOfCopies"));
        feesCol.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }

}
