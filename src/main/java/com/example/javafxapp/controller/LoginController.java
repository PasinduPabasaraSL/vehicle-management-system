package com.example.javafxapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane root;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void login(ActionEvent event){
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        if (name.equals("admin") && password.equals("admin@123")) {
            Stage stage = (Stage) this.root.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/example/javafxapp/view/dashboard-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);


        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("failed");
            alert.setHeaderText(null);
            alert.setContentText("Login Failed");
            alert.showAndWait();
        }
    }

}
