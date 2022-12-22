package com.univr.employeemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label display;

    private Stage stage;
    private Scene scene;

    /**
     * It checks if the username and password fields are empty, if they are it displays an error message, if they aren't it
     * checks if the username and password are correct, if they are it loads the next scene, if they aren't it displays an
     * error message
     *
     * @param e the event that triggered the method
     */
    @FXML
    protected void loginButtonPress(ActionEvent e) throws IOException {
        display.setTextFill(Color.rgb(255,0,0));
        if(username.getText().isBlank() || password.getText().isBlank())
            display.setText("Username o password vuoti");
        if (username.getText().equals("") && password.getText().equals("")){
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

        else display.setText("Nome o password errati");
    }
}