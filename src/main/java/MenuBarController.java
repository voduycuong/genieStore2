package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MenuBarController {
    private RentalController rentalController;
    private ReturnController returnController;

    private String currentUserId;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRentalController(RentalController rentalController) {
        this.rentalController = rentalController;
    }

    public void setReturnController(ReturnController returnController) {
        this.returnController = returnController;
    }

    public void setUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }


    @FXML
    private void handleItemMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/ItemDisplay.fxml"));
            Parent root = loader.load();

            Stage customerStage = new Stage();
            customerStage.setScene(new Scene(root));
            customerStage.setResizable(false);
            customerStage.setTitle("Items List");
            RentalController rentalController = loader.getController();
            rentalController.setCurrentUserId(currentUserId);
            customerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCustomerMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/CustomerProfile.fxml"));
            Parent root = loader.load();

            Stage customerStage = new Stage();
            customerStage.setScene(new Scene(root));
            customerStage.setResizable(false);
            customerStage.setTitle("Customer Profile");
            customerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitMenuItem() {
        // Implement the logic to exit the application
        System.exit(0);
    }

    @FXML
    private void handleSettingsMenuItem() {
    }

    @FXML
    private void handleHowToUseMenuItem() {
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
    private void handleRentButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/RentItem.fxml"));
            Parent root = loader.load();

            Stage rentalStage = new Stage();
            rentalStage.setScene(new Scene(root));
            rentalStage.setResizable(false);
            rentalStage.setTitle("Rent Item");

            RentalController rentalController = loader.getController();
            rentalController.setCurrentUserId(currentUserId);
            System.out.println("Current User ID in rent menu: " + currentUserId);

            rentalController.setStage(rentalStage); // Set the stage in RentalController

            rentalStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReturnButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/database/ReturnItem.fxml"));
            Parent root = loader.load();

            Stage returnStage = new Stage();
            returnStage.setScene(new Scene(root));
            returnStage.setResizable(false);
            returnStage.setTitle("Return Items");

            ReturnController returnController = loader.getController();
            returnController.setCurrentUserId(currentUserId);
            returnController.setStage(returnStage);
            System.out.println("Current User ID in return menu: " + currentUserId);


            returnStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfileButton() {

    }
}
