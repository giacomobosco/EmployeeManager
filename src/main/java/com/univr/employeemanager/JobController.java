package com.univr.employeemanager;

import com.sun.javafx.menu.MenuItemBase;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import java.util.Date;
import java.util.ResourceBundle;

import com.univr.employeemanager.Job;

public class JobController implements Initializable{

    @FXML
    private Button addTaskButton, removeTaskButton, saveButton, cancelButton;
    @FXML
    private TableView<Job> jobTable;
    @FXML
    private Label errorField;
    @FXML
    private TextField dailyPayField, jobPlaceField, companyNameField;
    @FXML
    private DatePicker beginDateField, endDateField;

    private Stage stage;

    public void AddTaskButtonPress(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddTask.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void RemoveTaskButtonPress(ActionEvent actionEvent) {
        Job selectedJob = jobTable.getSelectionModel().getSelectedItem();
        jobTable.getItems().remove(selectedJob);

    }

    public void CancelButtonPress(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    public void SaveButtonPress(ActionEvent actionEvent) {

       errorField.setText("");

        boolean exceptions = false;
        Job job = null;
        Integer myInt = Integer.parseInt(dailyPayField.getText());

        try {
            job = new Job(new Date(),
                    new Date(),
                    companyNameField.getText(),
                    jobPlaceField.getText(),
                    Integer.parseInt(dailyPayField.getText()));
        } catch (Exception e) {
            exceptions = true;
            errorField.setText(e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jobTable.getItems().addAll(new ArrayList<Job>());


    }
}
