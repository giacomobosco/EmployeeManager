package com.univr.employeemanager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JobController implements Initializable {

    @FXML
    private Label jobEndLabel;
    @FXML
    private TextArea tasks;
    @FXML
    private TextField dailyPayField, jobPlaceField, companyNameField, taskDescription;
    @FXML
    private DatePicker beginJobField, endJobField;
    @FXML
    private Button addTaskButton, removeTaskButton, cancelButton, saveButton;
    @FXML
    private Label errorField;
    @FXML
    private CheckBox inProgress;
    private Employee previousEmployee, employee;
    private Job previousJob;
    private JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updateField(Job job, Employee employee, Employee previousEmployee){

        this.previousEmployee = previousEmployee;
        this.employee = employee;
        this.previousJob = job;

        if(job != null) {

            dailyPayField.setText(job.getDailyPay().toString());
            jobPlaceField.setText(job.getJobPlace());
            companyNameField.setText(job.getCompanyName());

            beginJobField.setValue(job.getBegin());
            endJobField.setValue(job.getEnd());
        }
    }

    @FXML
    public void inProgressPressed(ActionEvent actionEvent){
        if (inProgress.isSelected()) {
            endJobField.setVisible(false);
            jobEndLabel.setVisible(false);
        }
        else {
            endJobField.setVisible(true);
            jobEndLabel.setVisible(true);
        }
    }
    public void CancelButtonPress(ActionEvent actionEvent) {

    }

    public void SaveButtonPress(ActionEvent actionEvent) throws IOException {

        errorField.setText("");

        boolean exceptions = false;
        Job job = null;

        try {
            job = new Job(
                    beginJobField.getValue(),
                    endJobField.getValue(),
                    companyNameField.getText(),
                    tasks.getText(),
                    jobPlaceField.getText(),
                    Integer.valueOf(dailyPayField.getText())
            );


        } catch (IllegalArgumentException e){
            errorField.setText(e.getMessage());
            exceptions = true;
        }

        if(!exceptions){

            if(previousJob == null) {
                data.remove(previousEmployee);
                employee.setFormerJob(job);
                data.write(employee);
            }
            else {
                data.remove(previousEmployee);
                employee.removeFormerJob(previousJob);
                employee.setFormerJob(job);
                data.write(employee);
            }
        }
    }
}
