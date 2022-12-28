package com.univr.employeemanager;

import com.sun.javafx.menu.MenuItemBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class JobController {

    @FXML
    private Button addTaskButton, removeTaskButton, saveButton, cancelButton;
    @FXML
    private TableView<Job> jobTable;
    private Stage stage;
    @FXML
    private Label errorField;
    @FXML
    private TextField dailyPayField,beginJobField, jobPlaceField, companyNameField;

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

    public void CancelButtonPress(ActionEvent actionEvent) {

    }

    @FXML
    public void SaveButtonPress(ActionEvent actionEvent) {

        errorField.setText("");

        boolean exceptions = false;
        Job job = null;

        try {
            job = new Job(
                    beginJobField.getText(),
                    dailyPayField.getText(),
                    jobPlaceField.getText(),
                    companyNameField.getText()
                    );
        } catch (Exception e) {
            exceptions = true;
            errorField.setText(e.getMessage());
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTaskButton.setDisable(true);
        removeTaskButton.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);

        jobTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
            {
                System.out.print("evento tabella click\n");
                addTaskButton.setDisable(false);
                removeTaskButton.setDisable(false);
                saveButton.setDisable(false);
                cancelButton.setDisable(false);
            }
        });

        jobTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue==null)
            {
                System.out.print("evento tabella click\n");
                addTaskButton.setDisable(true);
                removeTaskButton.setDisable(true);
                saveButton.setDisable(true);
                cancelButton.setDisable(true);
            }
        });


    }
}
