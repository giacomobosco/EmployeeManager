package com.univr.employeemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serial;

public class EmployeeController{

    @FXML private Button spokenLanguageAddButton, spokenLanguageRemoveButton, addJobButton, removeJobButton, saveButton, cancelButton;


    @FXML private TextField cellNumberField;
    @FXML private TextField emailField;
    @FXML private TextField birthPlaceField;
    @FXML private DatePicker birthDateField;
    @FXML private TextField addressField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;



    private Stage stage;
    private Scene scene;

    JSONReadWrite save = new JSONReadWrite("src/main/java/com/univr/employeemanager/save.json");
    JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    public EmployeeController() throws IOException {

    }

    public void updateField(Employee e,boolean b)
    {
        cellNumberField.setText(e.getCellNumber());
        emailField.setText(e.getEmail());
        birthPlaceField.setText(e.getBirthplace());
        firstNameField.setText(e.getFirstName());
        lastNameField.setText(e.getLastName());

        if(e.getFormerJobs()!=null)
        {


        }

        if(!b)
        {
            cellNumberField.setDisable(true);
            emailField.setDisable(true);
            birthDateField.setDisable(true);
            firstNameField.setDisable(true);
            lastNameField.setDisable(true);
            saveButton.setDisable(true);
        }
        else
        {
            cellNumberField.setDisable(false);
            emailField.setDisable(false);
            birthDateField.setDisable(false);
            firstNameField.setDisable(false);
            lastNameField.setDisable(false);
            saveButton.setDisable(false);

        }
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
        if(cellNumberField.getText().matches("[0-9]+"))
        {
            System.out.print(cellNumberField.getCharacters());
        }
        else
        {
            cellNumberField.clear();
            cellNumberField.setStyle("-fx-prompt-text-fill: red");
            cellNumberField.setPromptText("campo errato");
        }

        if(emailField.getText().contains("@"))
        {
            System.out.print(emailField.getCharacters());
        }
        else
        {
            emailField.clear();
            emailField.setStyle("-fx-prompt-text-fill: red");
            emailField.setPromptText("email errata");
        }


    }
}

