package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;

    @FXML
    private Menu customersMenu;

    @FXML
    private Menu itemsMenu;

    @FXML
    private Menu optionMenu;

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem createCustomerMenuItem;

    @FXML
    private MenuItem editCustomerMenuItem;

    @FXML
    private MenuItem deleteCustomerMenuItem;

    @FXML
    private MenuItem createItemMenuItem;

    @FXML
    private MenuItem editItemMenuItem;

    @FXML
    private MenuItem deleteItemMenuItem;

    @FXML
    private MenuItem appearanceMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private void handleExitMenuItem() {
        // Implement the logic to exit the application
        System.exit(0);
    }

    @FXML
    private void handleCreateCustomersMenuItem() {
        System.out.println("Create Customers menu item clicked");
    }

    @FXML
    private void handleEditCustomersMenuItem() {
        System.out.println("Edit Customers menu item clicked");
    }

    @FXML
    private void handleDeleteCustomersMenuItem() {
        System.out.println("Delete Customers menu item clicked");
    }

    @FXML
    private void handleCreateItemsMenuItem() {
        System.out.println("Create Items menu item clicked");
    }

    @FXML
    private void handleEditItemsMenuItem() {
        System.out.println("Edit Items menu item clicked");
    }

    @FXML
    private void handleDeleteItemsMenuItem() {
        System.out.println("Delete Items menu item clicked");
    }

    @FXML
    private void handleAppearanceMenuItem() {
        System.out.println("Appearance menu item clicked");
    }

    @FXML
    private void handleAboutMenuItem() {
        System.out.println("About menu item clicked");
    }

}
