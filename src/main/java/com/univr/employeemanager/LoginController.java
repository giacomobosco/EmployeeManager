package com.univr.employeemanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label display;
    @FXML private Button loginButton;

    @FXML
    protected void loginButtonPress() throws IOException {
        display.setTextFill(Color.rgb(255,0,0));
        if(username.getText().isBlank() || password.getText().isBlank())
            display.setText("Username o password vuoti");
        if (username.getText().equals("user") && password.getText().equals("password"))
            display.setText("Username o password esatti");
        else display.setText("Nome o password errati");
    }
}