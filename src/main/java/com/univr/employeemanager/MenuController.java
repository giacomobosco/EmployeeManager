package com.univr.employeemanager;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class MenuController {

    @FXML
    private Button newButton, editButton, deleteButton, detailsButton;
    @FXML
    private TextArea textField;
    @FXML
    private TableColumn nameField, jobField, birthDateField, birthPlaceField, languageField;

    @FXML
    protected void newButtonPress(ActionEvent e) throws IOException {



    }
    @FXML
    protected void editButtonPress(ActionEvent e){
    }
    @FXML
    protected void deleteButtonPress(ActionEvent e){
    }
    @FXML
    protected void detailsButtonPress(ActionEvent e){
    }
}
