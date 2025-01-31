package com.example.javafxapp.controller;

import com.example.javafxapp.model.VehicleModel;
import com.example.javafxapp.tm.VehicleTM;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadViewController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TableView<VehicleTM> tblView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<VehicleTM> tms = VehicleModel.loadVehicle();

            //Configure fx table
            tblView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("brand"));
            tblView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("model"));
            tblView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
            tblView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));

            tblView.setItems(FXCollections.observableList(tms));

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

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

}
