package com.univr.employeemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeController {

    @FXML private Button spokenLanguageAddButton, spokenLanguageRemoveButton, addJobButton, removeJobButton, saveButton, cancelButton;
    private Stage stage;
    private Scene scene;

    JSONReadWrite save = new JSONReadWrite("src/main/java/com/univr/employeemanager/save.json");
    JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    public EmployeeController() throws IOException {
    }

    public void SpokenLanguageAddButtonPress(ActionEvent actionEvent) {
    }

    public void SpokenLanguageRemoveButtonPress(ActionEvent actionEvent) {
    }

    public void AddJobButtonPress(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddJob.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void RemoveJobButtonPress(ActionEvent actionEvent) {
    }

    public void CancelButtonPress(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SaveButtonPress(ActionEvent actionEvent) {
    }
}
