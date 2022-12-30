package com.univr.employeemanager;

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
    public TableView jobTable;
    @FXML
    public Label errorField;
    @FXML
    private TextField firstNameField, lastNameField, addressField, birthPlaceField, emailField, cellNumberField, emergencyEmailField, emergencyCellNumberField, emergencyLastNameField, emergencyFirstNameField;
    @FXML
    private DatePicker birthDateField,periodFromField,periodToField;
    @FXML
    private CheckBox hasCar, licenseA, licenseB, licenseC, licenseD, licenseE, italian, english, french, spanish, arabic, chinese, portoguese, japanese, german,yearConsidered;
    @FXML
    private Button spokenLanguageAddButton, spokenLanguageRemoveButton, addJobButton, removeJobButton, saveButton, cancelButton;

    @FXML
    private TableColumn<Job,String> taskField;
    @FXML
    private TableColumn<Job, Date> endField;
    @FXML
    private TableColumn<Job,String> companyField;
    @FXML
    private TableColumn<Job,String> payField;
    @FXML
    private TableColumn<Job,String> jobPlaceField;
    @FXML
    private TableColumn<Job, Date> beginField;

    private ObservableList<Job> jobs;
    private Employee previousEmployee = null;
    private Stage stage;
    private Scene scene;


    JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    public EmployeeController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskField.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        beginField.setCellValueFactory(new PropertyValueFactory<Job,Date>("begin"));
        endField.setCellValueFactory(new PropertyValueFactory<Job,Date>("end"));
        companyField.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        jobPlaceField.setCellValueFactory(new PropertyValueFactory<>("jobPlace"));
        payField.setCellValueFactory(new PropertyValueFactory<>("DailyPay"));

    }

    public void updateField(Employee e, boolean editable) {

        previousEmployee = e;

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

        if(periodFromField.getValue()!=null && periodToField.getValue()!=null)
        {
            periodFromField.setValue(e.getAvailablePeriod()[0]);
            periodToField.setValue(e.getAvailablePeriod()[1]);
        }

        jobs = FXCollections.observableArrayList(e.getFormerJobs());
        //System.out.print("\n"+e.getFormerJobs().toString());
        jobTable.setItems(jobs);

        //se sono arrivato a questa finestra tramite detailButton o tramite editButton
        //disabilito o no i campi e il tasto salva
        if(editable==true)
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
        Parent root = FXMLLoader.load(getClass().getResource("AddJob.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void RemoveJobButtonPress(ActionEvent actionEvent) {
    }

    //cancelButton
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

            //LocalDate localDate = birthDateField.getValue();
            //Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            //person.setBirthDate(Date.from(instant));

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

            if(periodFromField.getValue()!=null && periodToField.getValue()!=null)
                person.setAvailablePeriod(periodFromField.getValue(),periodToField.getValue());
            if(yearConsidered.isSelected()) person.setConsiderYear(true);

        } catch (IllegalArgumentException e){
            errorField.setText(e.getMessage());
            e.printStackTrace();
            exceptions = true;
        }

        if(!exceptions){

            System.out.print("SAVED "+person.toString());

            if(previousEmployee == null) {
                data.write(person);
            }
            else {
                data.remove(previousEmployee);
                data.write(person);
            }
        }
    }
}
