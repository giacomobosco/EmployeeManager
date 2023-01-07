package com.univr.employeemanager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private TableView<Job> jobTable;
    @FXML
    private Label errorField;
    @FXML
    private TextField firstNameField, lastNameField, addressField, birthPlaceField, emailField, cellNumberField, emergencyEmailField, emergencyCellNumberField, emergencyLastNameField, emergencyFirstNameField;
    @FXML
    private DatePicker birthDateField;
    @FXML
    private CheckBox hasCar, licenseA, licenseB, licenseC, licenseD, licenseE, italian, english, french, spanish, arabic, chinese, portoguese, japanese, german;
    @FXML
    private Button addJobButton, removeJobButton, saveButton, cancelButton;
    @FXML
    private TableColumn<Job,String> taskField,companyField,payField,jobPlaceField;
    @FXML
    private TableColumn<Job, Date> beginField, endField;
    private ObservableList<Job> jobs;
    private Employee employee, previousEmployee = null;
    private Stage stage;
    private Scene scene;
    private Parent root;

    JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void updateField(Employee e,  boolean editable) {

        employee = e;
        this.previousEmployee = previousEmployee;

        cellNumberField.setText(e.getCellNumber());
        emailField.setText(e.getEmail());
        birthPlaceField.setText(e.getBirthplace());
        firstNameField.setText(e.getFirstName());
        lastNameField.setText(e.getLastName());
        addressField.setText(e.getAddress());
        birthDateField.setValue(e.getBirthDate());

        if (e.getEmergency() != null) {
            emergencyFirstNameField.setText(e.getEmergency().getFirstName());
            emergencyLastNameField.setText(e.getEmergency().getLastName());
            emergencyEmailField.setText(e.getEmergency().getEmail());
            emergencyCellNumberField.setText(e.getEmergency().getCellNumber());
        }

        hasCar.setSelected(e.hasCar());

        italian.setSelected(e.getSpokenLanguage().contains(Employee.Language.ITALIAN));
        english.setSelected(e.getSpokenLanguage().contains(Employee.Language.ENGLISH));
        french.setSelected(e.getSpokenLanguage().contains(Employee.Language.FRENCH));
        spanish.setSelected(e.getSpokenLanguage().contains(Employee.Language.SPANISH));
        portoguese.setSelected(e.getSpokenLanguage().contains(Employee.Language.PORTOGUESE));
        arabic.setSelected(e.getSpokenLanguage().contains(Employee.Language.ARABIC));
        chinese.setSelected(e.getSpokenLanguage().contains(Employee.Language.CHINESE));
        german.setSelected(e.getSpokenLanguage().contains(Employee.Language.GERMAN));
        japanese.setSelected(e.getSpokenLanguage().contains(Employee.Language.JAPANESE));

        licenseA.setSelected(e.getLicenses().contains(Employee.License.A));
        licenseB.setSelected(e.getLicenses().contains(Employee.License.B));
        licenseC.setSelected(e.getLicenses().contains(Employee.License.C));
        licenseD.setSelected(e.getLicenses().contains(Employee.License.D));
        licenseE.setSelected(e.getLicenses().contains(Employee.License.E));

        taskField.setCellValueFactory(new PropertyValueFactory<Job,String>("companyName"));
        beginField.setCellValueFactory(new PropertyValueFactory<Job,Date>("begin"));

        updateTable();

        //se sono arrivato a questa finestra tramite detailButton o tramite editButton
        //disabilito o no i campi e il tasto salva
        if(editable)
        {
            saveButton.setDisable(false);
            saveButton.setDisable(false);
            cellNumberField.setDisable(false);
            emailField.setDisable(false);
            birthPlaceField.setDisable(false);
            birthDateField.setDisable(false);
            addressField.setDisable(false);
            firstNameField.setDisable(false);
            lastNameField.setDisable(false);
        }
        else
        {
            saveButton.setDisable(true);
            cellNumberField.setDisable(true);
            emailField.setDisable(true);
            birthPlaceField.setDisable(true);
            birthDateField.setDisable(true);
            addressField.setDisable(true);
            firstNameField.setDisable(true);
            lastNameField.setDisable(true);
        }

    }

    @FXML
    public void AddJobButtonPress(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddJob.fxml"));
        root = loader.load();

        JobController jobController = loader.getController();
        jobController.updateField(null, employee, previousEmployee);

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Job");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void RemoveJobButtonPress(ActionEvent actionEvent) {
    }

    public void EditJobButtonPress(ActionEvent actionEvent) throws IOException {

        Job selected = jobTable.getSelectionModel().getSelectedItem();

        if(selected != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddJob.fxml"));
            root = loader.load();

            JobController jobController = loader.getController();
            jobController.updateField(selected, employee, previousEmployee);

            stage = new Stage();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Edit Job");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    @FXML
    public void CancelButtonPress(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SaveButtonPress(ActionEvent actionEvent) throws IOException {

        errorField.setText("");

        boolean exceptions = false;
        Employee person = null;

        try{
            person = new Employee(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    birthPlaceField.getText(),
                    birthDateField.getValue(),
                    addressField.getText(),
                    emailField.getText(),
                    cellNumberField.getText(),
                    hasCar.isSelected(),
                    new Person(emergencyFirstNameField.getText(),
                            emergencyLastNameField.getText(),
                            emergencyCellNumberField.getText(),
                            emergencyEmailField.getText()));


            if(italian.isSelected()) person.setSpokenLanguage(Employee.Language.ITALIAN);
            if(english.isSelected()) person.setSpokenLanguage(Employee.Language.ENGLISH);
            if(french.isSelected()) person.setSpokenLanguage(Employee.Language.FRENCH);
            if(spanish.isSelected()) person.setSpokenLanguage(Employee.Language.SPANISH);
            if(portoguese.isSelected()) person.setSpokenLanguage(Employee.Language.PORTOGUESE);
            if(arabic.isSelected()) person.setSpokenLanguage(Employee.Language.ARABIC);
            if(chinese.isSelected()) person.setSpokenLanguage(Employee.Language.CHINESE);
            if(japanese.isSelected()) person.setSpokenLanguage(Employee.Language.JAPANESE);
            if(german.isSelected()) person.setSpokenLanguage(Employee.Language.GERMAN);

            if(licenseA.isSelected()) person.setLicense(Employee.License.A);
            if(licenseB.isSelected()) person.setLicense(Employee.License.B);
            if(licenseC.isSelected()) person.setLicense(Employee.License.C);
            if(licenseD.isSelected()) person.setLicense(Employee.License.D);
            if(licenseE.isSelected()) person.setLicense(Employee.License.E);

        } catch (IllegalArgumentException e){
            errorField.setText(e.getMessage());
            exceptions = true;
        }

        if(!exceptions){

            if(previousEmployee == null) {
                data.write(person);
            }
            else {
                data.remove(previousEmployee);
                data.write(person);
            }
        }
    }

    private void updateTable(){
        jobs = FXCollections.observableArrayList(employee.getFormerJobs());
        jobTable.setItems(jobs);
    }

    public class SharedJob {
        private final ObjectProperty<Job> sharedJob = new SimpleObjectProperty<>();

        public Job getSharedJob() {
            return sharedJob.get();
        }

        public void setSharedJob(Job value) {
            sharedJob.set(value);
        }

        public ObjectProperty<Job> sharedJobProperty() {
            return sharedJob;
        }
    }
}
