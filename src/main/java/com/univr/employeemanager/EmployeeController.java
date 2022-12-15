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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class EmployeeController implements Initializable {

    @FXML
    public TableView jobTable;
    @FXML
    private TextField firstNameField, lastNameField, addressField, birthPlaceField, emailField, cellNumberField, emergencyEmailField, emergencyCellNumberField, emergencyLastNameField, emergencyFirstNameField;
    @FXML
    private DatePicker birthDateField;
    @FXML
    private CheckBox hasCar, licenseA, licenseB, licenseC, licenseD, licenseE, italian, english, french, spanish, arabic, chinese, portoguese, japanese, german;
    @FXML
    private Button spokenLanguageAddButton, spokenLanguageRemoveButton, addJobButton, removeJobButton, saveButton, cancelButton;

    private Stage stage;
    private Scene scene;

    JSONReadWrite temp = new JSONReadWrite("src/main/java/com/univr/employeemanager/temp.json");
    JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    public EmployeeController() throws IOException {
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

    @FXML
    public void CancelButtonPress(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SaveButtonPress(ActionEvent actionEvent) {

        Employee person = new Employee(
                firstNameField.getText(),
                lastNameField.getText(),
                birthPlaceField.getText(),
                null,
                addressField.getText(),
                emailField.getText(),
                cellNumberField.getText(),
                hasCar.isSelected(),
                new Person(emergencyFirstNameField.getText(),
                        emergencyLastNameField.getText(),
                        emergencyCellNumberField.getText(),
                        emergencyEmailField.getText())
        );

        //Take the value from datepicker
        LocalDate localDate = birthDateField.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        person.setBirthDate(Date.from(instant));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            TreeSet<Employee> personSet = temp.readSet();
            if (!personSet.isEmpty()){

                Employee person = personSet.first();

                firstNameField.setText(person.getFirstName());
                lastNameField.setText(person.getLastName());
                addressField.setText(person.getAddress());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(person.getBirthDateString(), formatter);
                birthDateField.setValue(localDate);

                birthPlaceField.setText(person.getBirthplace());
                emailField.setText(person.getBirthplace());
                cellNumberField.setText(person.getCellNumber());

                if (person.getEmergency() != null) {
                    emergencyFirstNameField.setText(person.getEmergency().getFirstName());
                    emergencyLastNameField.setText(person.getEmergency().getLastName());
                    emergencyEmailField.setText(person.getEmergency().getEmail());
                    emergencyCellNumberField.setText(person.getEmergency().getCellNumber());
                }

                hasCar.setSelected(person.hasCar());

                italian.setSelected(person.getSpokenLanguage().contains(Employee.Language.ITALIAN));
                english.setSelected(person.getSpokenLanguage().contains(Employee.Language.ENGLISH));
                french.setSelected(person.getSpokenLanguage().contains(Employee.Language.FRENCH));
                spanish.setSelected(person.getSpokenLanguage().contains(Employee.Language.SPANISH));
                portoguese.setSelected(person.getSpokenLanguage().contains(Employee.Language.PORTOGUESE));
                arabic.setSelected(person.getSpokenLanguage().contains(Employee.Language.ARABIC));
                chinese.setSelected(person.getSpokenLanguage().contains(Employee.Language.CHINESE));
                german.setSelected(person.getSpokenLanguage().contains(Employee.Language.GERMAN));
                japanese.setSelected(person.getSpokenLanguage().contains(Employee.Language.JAPANESE));

                licenseA.setSelected(person.getLicenses().contains(Employee.License.A));
                licenseB.setSelected(person.getLicenses().contains(Employee.License.B));
                licenseC.setSelected(person.getLicenses().contains(Employee.License.C));
                licenseD.setSelected(person.getLicenses().contains(Employee.License.D));
                licenseE.setSelected(person.getLicenses().contains(Employee.License.E));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
