package com.example.javafxapp.controller;

import com.example.javafxapp.dto.VehicleDto;
import com.example.javafxapp.model.VehicleModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateVehicleDataController {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) this.root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/javafxapp/view/vehicle-crud-view.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
    }


    @FXML
    void search(ActionEvent event) throws SQLException, ClassNotFoundException {

        try {
            int id = Integer.parseInt(txtId.getText());

            VehicleDto vehicleData = VehicleModel.searchVehicle(id);

            if (vehicleData != null) {
                txtBrand.setText(vehicleData.getBrand());
                txtModel.setText(vehicleData.getModel());
                txtQty.setText(String.valueOf(vehicleData.getQty()));
                txtPrice.setText(String.valueOf(vehicleData.getPrice()));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Found");
                alert.setHeaderText(null);
                alert.setContentText("No vehicle found with ID: " + id);
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid ID");
            alert.showAndWait();
        }

    }

    @FXML
    void update(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(txtId.getText());
        String brand = txtBrand.getText();
        String model = txtModel.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        boolean status = VehicleModel.updateVehicle(new VehicleDto(id, brand, model, qty, price));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (status){
            alert.setTitle("success");
            alert.setHeaderText(null);
            alert.setContentText("Updated Successfully");
            alert.showAndWait();
        } else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("Update Failed");
            alert.showAndWait();
        }



    }
}