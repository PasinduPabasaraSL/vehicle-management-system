package com.example.javafxapp.controller;

import com.example.javafxapp.dto.OrderDetailDto;
import com.example.javafxapp.dto.OrderDto;
import com.example.javafxapp.dto.VehicleDto;
import com.example.javafxapp.model.OrderModel;
import com.example.javafxapp.model.VehicleModel;
import com.example.javafxapp.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.layout.AnchorPane;

public class OrderViewController {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtId;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<OrderTM> tblItems;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    private ArrayList<OrderTM> orderTMS;

    private ArrayList<OrderDetailDto> orderDetailDtos;

    private double subTotal = 0.0;

    @FXML
    void search(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(txtId.getText());
        VehicleDto vehicleDto = VehicleModel.searchVehicle(id);

        if (vehicleDto != null) {
            txtBrand.setText(vehicleDto.getBrand());
            txtModel.setText(vehicleDto.getModel());
            txtQtyOnHand.setText(String.valueOf(vehicleDto.getQty()));
            txtUnitPrice.setText(String.valueOf(vehicleDto.getPrice()));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Found");
            alert.setHeaderText(null);
            alert.setContentText("No vehicle found with ID: " + id);
            alert.showAndWait();
        }

    }

    public void initialize() {
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("brand"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("model"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblItems.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        orderTMS = new ArrayList<>();
        orderDetailDtos = new ArrayList<>();
    }

    @FXML
    void addToCart(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        int qtyNeed = Integer.parseInt(txtQty.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        String brand = txtBrand.getText();
        String model = txtModel.getText();
        double price = Double.parseDouble(txtUnitPrice.getText());
        double total = price * qtyNeed;
        subTotal += total;

        if (qtyNeed <= qtyOnHand) {
            orderTMS.add(new OrderTM(id, brand, model, qtyNeed, price, total));

            tblItems.setItems(FXCollections.observableList(orderTMS));

            orderDetailDtos.add(new OrderDetailDto(id, qtyNeed, price));

            lblTotal.setText(String.valueOf(subTotal));

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Quantity on hand must be more than Quantity needed");
            alert.showAndWait();
        }

    }

    @FXML
    void placeOrder(ActionEvent event) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);

        boolean status = false;
        try {
            status = OrderModel.placeOrder(new OrderDto(formattedDate, subTotal, orderDetailDtos));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (status) {
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Placing Order Successfully");

                Stage stage = (Stage) this.root.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/javafxapp/view/bill-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);

            } else {
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Placing Order Failed");
            }
            alert.showAndWait();

        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) this.root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/javafxapp/view/dashboard-view.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
    }

}
