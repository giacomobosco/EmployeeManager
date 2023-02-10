package com.univr.employeemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddJobController implements Initializable {

    @FXML
    private Label jobEndLabel;
    @FXML
    private TextArea tasks;
    @FXML
    private TextField dailyPayField, jobPlaceField, companyNameField;
    @FXML
    private DatePicker beginJobField, endJobField;
    @FXML
    private Button addTaskButton, removeTaskButton, cancelButton, saveButton;
    @FXML
    private Label errorField, savedLabel;
    @FXML
    private CheckBox inProgress;
    private Employee employee;
    private Job previousJob;
    private final JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updateField(Job job, Employee employee){

        this.employee = employee;
        this.previousJob = job;

        if(job != null) {

            dailyPayField.setText(job.getDailyPay().toString());
            jobPlaceField.setText(job.getJobPlace());
            companyNameField.setText(job.getCompanyName());
            tasks.setText(job.getTasks());
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
    /**
     * It loads the AddEmployee.fxml file, gets the controller for that file, and then calls the updateField function in
     * the controller, passing in the employee object and a boolean value
     *
     * @param actionEvent This is the event that is triggered when the button is pressed.
     */
    public void CancelButtonPress(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
        root = loader.load();

        AddEmployeeController employeeController = loader.getController();
        employeeController.updateField(employee, true);

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Edit Employee");
        stage.setScene(scene);
        stage.show();
    }

    public void SaveButtonPress(ActionEvent actionEvent) throws IOException {

        errorField.setText("");

        try{
            Job job = null;
            job = new Job(
                    beginJobField.getValue(),
                    endJobField.getValue(),
                    companyNameField.getText(),
                    tasks.getText(),
                    jobPlaceField.getText(),
                    Integer.valueOf(dailyPayField.getText())
            );


            if(previousJob == null) {
                data.remove(employee);
                employee.setFormerJob(job);
                data.write(employee);
                this.previousJob = job;
            }
            else {
                data.remove(employee);
                employee.removeFormerJob(previousJob);
                employee.setFormerJob(job);
                data.write(employee);
                this.previousJob = job;
            }
            savedLabel.setVisible(true);
        }
        catch (IllegalArgumentException e){
            errorField.setText(e.getMessage());
        }
    }

    public void SaveLabelDisappear(){

        savedLabel.setVisible(false);
    }
}
