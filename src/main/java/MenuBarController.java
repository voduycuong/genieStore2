package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

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
//        MenuBarApplication.loadWindow(getClass().getResource("/resources/database/customer.fxml"), "Customer Info", null);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/AboutScreen.fxml"));
            Parent aboutScreenRoot = loader.load();

            Stage aboutStage = new Stage();
            aboutStage.initStyle(StageStyle.UNDECORATED);
            aboutStage.setTitle("About");
            aboutStage.setScene(new Scene(aboutScreenRoot));
            aboutStage.show();

            // Add event handler to close About screen on mouse click
            aboutScreenRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                aboutStage.close();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
