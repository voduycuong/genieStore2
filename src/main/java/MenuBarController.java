package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private MenuItem customerMenuItem;

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
    private AnchorPane contentPane;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleExitMenuItem() {
        // Implement the logic to exit the application
        System.exit(0);
    }

    @FXML
    private void handleCreateItemsMenuItem() {
        System.out.println("Create Items menu item clicked");
    }

    @FXML
    private void handleCustomerMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/customer.fxml"));
            Parent root = loader.load();

            Stage customerStage = new Stage();
            customerStage.setScene(new Scene(root));
            customerStage.setTitle("Customer Info");
            customerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void handleAboutMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/AboutScreen.fxml"));
            Parent aboutScreenRoot = loader.load();

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double centerX = screenBounds.getMinX() + (screenBounds.getWidth() / 2);
            double centerY = screenBounds.getMinY() + (screenBounds.getHeight() / 2);

            Stage aboutStage = new Stage();
            aboutStage.initStyle(StageStyle.UNDECORATED);
            aboutStage.setX(centerX - (750 / 2)); // Set the X position at the center of the screen
            aboutStage.setY(centerY - (500 / 2)); // Set the Y position at the center of the screen
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

    @FXML
    private void handleDashboardMenuItem() {
        loadView("dashboard.fxml");
    }

    @FXML
    private void handleRentMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/RentItem.fxml"));
            Parent root = loader.load();

            Stage customerStage = new Stage();
            customerStage.setScene(new Scene(root));
            customerStage.setTitle("Rent Item");
            customerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReturnMenuItem() {
        loadView("return.fxml");
    }

    @FXML
    private void handleProfileMenuItem() {
        loadView("profile.fxml");
    }

    private void loadView(String viewPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(viewPath));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
