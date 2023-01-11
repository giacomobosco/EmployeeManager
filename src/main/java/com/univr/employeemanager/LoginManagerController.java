package com.univr.employeemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginManagerController implements Initializable {
    @FXML private TableView<LoginPerson> mainTable;
    @FXML private TableColumn<LoginPerson, String> firstNameColoumn, lastNameColoumn, emailColoumn, cellNumberColoumn, userColoumn, passwordColoumn;
    @FXML private Button deleteButton;

    private ObservableList<LoginPerson> list;
    private final JSONReadWriteLogin jsonReadWriteLogin = new JSONReadWriteLogin("src/main/java/com/univr/employeemanager/login.json");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
            {
                deleteButton.setDisable(false);
            }
        });

        firstNameColoumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColoumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColoumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cellNumberColoumn.setCellValueFactory(new PropertyValueFactory<>("cellNumber"));
        userColoumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColoumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        try {
            list = FXCollections.observableArrayList(jsonReadWriteLogin.readJSON());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainTable.setItems(list);
    }

    @FXML
    protected void deleteButtonPress(ActionEvent e) throws IOException {

        LoginPerson selected = mainTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Person delete");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            if (selected != null) {

                jsonReadWriteLogin.remove(selected);
                list = FXCollections.observableArrayList(jsonReadWriteLogin.readJSON());
                mainTable.setItems(list);
            }
        }
    }
}
