package com.example.javafxapp.controller;

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


public class DeleteVehicleDataController {
    @FXML
    private TextField txtId;

    @FXML
    private AnchorPane root;

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
    void delete(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(txtId.getText());

        boolean status = VehicleModel.deleteVehicle(id);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (status) {
            alert.setTitle("success");
            alert.setHeaderText(null);
            alert.setContentText("Vehicle deleted successfully");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vehicle could not be deleted");
            alert.showAndWait();
        }

    }
}
