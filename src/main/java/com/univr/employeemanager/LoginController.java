package com.univr.employeemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class LoginController implements Initializable {

    @FXML
    private TextField username, firstNameField, lastNameField, emailField, cellNumberField;
    @FXML
    private PasswordField password;
    @FXML
    private Label display;
    @FXML
    private AnchorPane registerFields;
    @FXML
    private Button accountRegisterButton, accountManageButton, loginButton;

    private Stage stage;
    private Scene scene;
    private TreeSet<LoginPerson> loginPeople;
    private boolean isLogin = true;

    JSONReadWriteLogin login = new JSONReadWriteLogin("src/main/java/com/univr/employeemanager/login.json");


    @FXML
    protected void loginButtonPress(ActionEvent e) throws IOException {

        try {
            loginPeople = login.readJSON();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        display.setTextFill(Color.rgb(255,0,0));
        if(isLogin) {

            if (username.getText().isBlank() || password.getText().isBlank())
                display.setText("Username o password vuoti");

            for (LoginPerson loginPerson : loginPeople) {

                if (username.getText().equals(loginPerson.getUsername()) && password.getText().equals(loginPerson.getPassword())) {

                    Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Menu");
                    stage.setScene(scene);
                    stage.show();
                }
            }
            display.setText("Nome o password errati");
        }
        else{
            if (username.getText().isBlank() ||
                    password.getText().isBlank() ||
                    firstNameField.getText().isBlank() ||
                    lastNameField.getText().isBlank() ||
                    emailField.getText().isBlank() ||
                    cellNumberField.getText().isBlank())
                display.setText("Fields can't be blank");

            else {
                try {
                    LoginPerson loginPerson = new LoginPerson(
                            firstNameField.getText(),
                            lastNameField.getText(),
                            cellNumberField.getText(),
                            emailField.getText(),
                            username.getText(),
                            password.getText());

                    if(!loginPeople.contains(loginPerson)){
                        display.setTextFill(Color.rgb(50,200,0));
                        display.setText("Saved");
                        login.write(loginPerson);
                    }
                    else display.setText("This account already exists");
                } catch (IllegalArgumentException exception){
                    display.setText(exception.getMessage());
                }
            }
        }
    }

    @FXML
    protected void accountRegisterButtonPress(ActionEvent e){


        if (isLogin) {
            if (username.getText().isBlank() || password.getText().isBlank())
                display.setText("Admin can't be blank");

            boolean find = false;

            for (LoginPerson loginPerson : loginPeople) {

                if (username.getText().equals(loginPerson.getUsername())
                        && password.getText().equals(loginPerson.getPassword())
                        && loginPerson.getAdmin() == true) {

                    find = true;
                    registerFields.setVisible(true);
                    accountManageButton.setVisible(false);
                    loginButton.setText("Register");
                    accountRegisterButton.setText("Login with an account");
                    isLogin = false;

                    break;
                }
            }

            if (find == false) display.setText("Wrong admin user or password");

        } else {

            registerFields.setVisible(false);
            accountManageButton.setVisible(true);
            loginButton.setText("Login");
            accountRegisterButton.setText("Register an account");
            isLogin = true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loginPeople = login.readJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    protected void accountManageButtonPress(ActionEvent e) throws IOException {

        if (username.getText().isBlank() || password.getText().isBlank())
            display.setText("Admin can't be blank");

        for (LoginPerson loginPerson : loginPeople) {

            if (username.getText().equals(loginPerson.getUsername())
                    && password.getText().equals(loginPerson.getPassword())
                    && loginPerson.getAdmin() == true) {

                Parent root = FXMLLoader.load(getClass().getResource("LoginManager.fxml"));
                stage = new Stage();
                scene = new Scene(root);
                stage.setTitle("Menu");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            }
        }
        display.setText("Wrong admin user or password");
    }
    @FXML
    private void labelDisappear(MouseEvent mouseEvent) {
        display.setText("");
    }
}