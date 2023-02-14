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
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    @FXML
    public TableView<Job> jobTable;
    @FXML
    public Label errorField, savedLabel;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField birthPlaceField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField cellNumberField;
    @FXML
    public TextField emergencyEmailField;
    @FXML
    public TextField emergencyCellNumberField;
    @FXML
    public TextField emergencyLastNameField;
    @FXML
    public TextField emergencyFirstNameField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    private DatePicker periodFromField;
    @FXML
    private DatePicker periodToField;
    @FXML
    public CheckBox hasCar;
    @FXML
    private CheckBox licenseA;
    @FXML
    private CheckBox licenseB;
    @FXML
    private CheckBox licenseC;
    @FXML
    private CheckBox licenseD;
    @FXML
    private CheckBox licenseE;
    @FXML
    public CheckBox italian;
    @FXML
    public CheckBox english;
    @FXML
    public CheckBox french;
    @FXML
    public CheckBox spanish;
    @FXML
    private CheckBox arabic;
    @FXML
    private CheckBox chinese;
    @FXML
    public CheckBox portoguese;
    @FXML
    private CheckBox japanese;
    @FXML
    private CheckBox german;
    @FXML
    private Button addJobButton, removeJobButton, saveButton, cancelButton, editJobButton;

    @FXML
    public TableColumn<Job,String> taskField;
    @FXML
    public TableColumn<Job, Date> endField;
    @FXML
    public TableColumn<Job,String> companyField;
    @FXML
    public TableColumn<Job,String> payField;
    @FXML
    public TableColumn<Job,String> jobPlaceField;
    @FXML
    public TableColumn<Job, Date> beginField;

    private ObservableList<Job> jobs;
    private Employee previousEmployee = null;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private final JSONReadWriteEmployee data = new JSONReadWriteEmployee("src/main/java/com/univr/employeemanager/data.json");

    public AddEmployeeController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskField.setCellValueFactory(new PropertyValueFactory<>("tasks"));
        beginField.setCellValueFactory(new PropertyValueFactory<>("begin"));
        endField.setCellValueFactory(new PropertyValueFactory<>("end"));
        companyField.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        jobPlaceField.setCellValueFactory(new PropertyValueFactory<>("jobPlace"));
        payField.setCellValueFactory(new PropertyValueFactory<>("DailyPay"));

        jobTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
            {
                editJobButton.setDisable(false);
                removeJobButton.setDisable(false);
            }

        });

    }

    public void updateField(boolean editable) {

        EmployeeSingleton employeeSingleton = EmployeeSingleton.getInstance();

        Employee e = employeeSingleton.getEmployee();

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


        periodFromField.setValue(e.getAvailablePeriod()[0]);
        periodToField.setValue(e.getAvailablePeriod()[1]);


        jobs = FXCollections.observableArrayList(e.getFormerJobs());
        //System.out.print("\n"+e.getFormerJobs().toString());
        jobTable.setItems(jobs);

        //se sono arrivato a questa finestra tramite detailButton o tramite editButton
        //disabilito o no i campi e il tasto salva
        if(!editable) {
            saveButton.setDisable(true);
            cellNumberField.setDisable(true);
            emailField.setDisable(true);
            birthPlaceField.setDisable(true);
            birthDateField.setDisable(true);
            addressField.setDisable(true);
            firstNameField.setDisable(true);
            lastNameField.setDisable(true);
            addJobButton.setVisible(false);
            removeJobButton.setVisible(false);
            editJobButton.setVisible(false);
            saveButton.setVisible(false);
            periodFromField.setDisable(true);
            periodToField.setDisable(true);
            licenseA.setDisable(true);
            licenseB.setDisable(true);
            licenseC.setDisable(true);
            licenseD.setDisable(true);
            licenseE.setDisable(true);
            hasCar.setDisable(true);
            italian.setDisable(true);
            english.setDisable(true);
            french.setDisable(true);
            spanish.setDisable(true);
            arabic.setDisable(true);
            chinese.setDisable(true);
            portoguese.setDisable(true);
            japanese.setDisable(true);
            german.setDisable(true);
            emergencyCellNumberField.setDisable(true);
            emergencyEmailField.setDisable(true);
            emergencyFirstNameField.setDisable(true);
            emergencyLastNameField.setDisable(true);
        }
    }

    @FXML
    public void AddJobButtonPress(ActionEvent actionEvent) throws IOException {

        errorField.setText("");
        Employee employee = getEmployee();

        if (employee != null && employee.compareTo(previousEmployee) == 0){

            EmployeeSingleton employeeSingleton = EmployeeSingleton.getInstance();
            employeeSingleton.setEmployee(employee);
            employeeSingleton.setJob(null);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddJob.fxml"));
            root = loader.load();

            AddJobController addJobController = loader.getController();

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Edit Job");
            stage.setScene(scene);
            stage.show();
        }

        else if (Objects.equals(errorField.getText(), "")) errorField.setText("Employee must be saved before");
    }

    public void EditJobButtonPress(ActionEvent actionEvent) throws IOException {

        errorField.setText("");
        Job selected = jobTable.getSelectionModel().getSelectedItem();
        Employee employee = getEmployee();

        if (selected != null){

            if (employee != null && employee.compareTo(previousEmployee) == 0){

                EmployeeSingleton employeeSingleton = EmployeeSingleton.getInstance();
                employeeSingleton.setEmployee(employee);
                employeeSingleton.setJob(selected);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddJob.fxml"));
                root = loader.load();

                AddJobController addJobController = loader.getController();

                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Edit Job");
                stage.setScene(scene);
                stage.show();
            }

            else if (Objects.equals(errorField.getText(), "")) errorField.setText("Employee must be saved before");
        }
        else errorField.setText("Please select a job");
    }

    @FXML
    public void RemoveJobButtonPress() {

        Job selected = jobTable.getSelectionModel().getSelectedItem();
        Employee employee = getEmployee();

        if (selected != null && employee.compareTo(previousEmployee) == 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Job delete");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {

                jobs.remove(selected);

                jobTable.setItems(jobs);
            }
        }

        else if (Objects.equals(errorField.getText(), "")) errorField.setText("Employee must be saved before");
    }

    @FXML
    public void CancelButtonPress(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SaveButtonPress(ActionEvent actionEvent) throws IOException {

        Employee employee = getEmployee();

        if (employee != null){

            if(previousEmployee == null) {
                data.write(employee);
            }
            else {
                data.remove(previousEmployee);
                data.write(employee);
            }
            previousEmployee = employee;
            savedLabel.setVisible(true);
        }
    }

    public void saveLabelDisappear(){
        savedLabel.setVisible(false);
    }

    private Employee getEmployee(){

        errorField.setText("");

        Employee returnEmployee = null;

        try{
            Employee person;
            person = new Employee(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    cellNumberField.getText());

            person.setBirthPlace(birthPlaceField.getText());
            person.setBirthDate(birthDateField.getValue());
            person.setAddress(addressField.getText());
            person.setCar(hasCar.isSelected());
            person.setEmergency(new Person(emergencyFirstNameField.getText(),
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

            person.setAvailablePeriod(periodFromField.getValue(),periodToField.getValue());

            if(jobs != null)
                for (Job job:jobs) {
                    person.setFormerJob(job);
                }

            returnEmployee = person;

        } catch (IllegalArgumentException | NullPointerException e){
            errorField.setText(e.getMessage());
           // e.printStackTrace();
        }

        return returnEmployee;
    }
}
