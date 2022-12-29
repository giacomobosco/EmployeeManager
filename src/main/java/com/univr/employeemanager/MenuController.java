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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MenuController implements Initializable {

    //oggetti usati per la ricerca
    //---------------------------------------------------
    @FXML
    private RadioButton ORenable,ANDenable;
    @FXML
    private CheckBox hasLicenseEnable,hasCarEnable,birthIntervalEnable;
    @FXML
    private DatePicker birthFromDate;
    @FXML
    private DatePicker birthToDate;
    @FXML
    private Button searchButton,restoreButton;
    //----------------------------------------------------

    @FXML
    private Button newButton, editButton, deleteButton, detailsButton;
    @FXML
    private TableColumn <Employee, String> addressField, nameField, lastNameField, cellNumberField;
    @FXML
    private TableColumn<Employee,LocalDate>birthDateField;
    @FXML
    private TableView<Employee> mainTable;
    @FXML
    private Label display;
    private ObservableList<Employee> people;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");
    private JSONReadWrite temp = new JSONReadWrite("src/main/java/com/univr/employeemanager/temp.json");
    public MenuController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        detailsButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        ORenable.setSelected(true);

        birthFromDate.setDisable(false);
        birthIntervalEnable.setDisable(true);


        mainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
            {
                System.out.print("evento tabella click\n");
                detailsButton.setDisable(false);
                deleteButton.setDisable(false);
                editButton.setDisable(false);
            }

        });


        nameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        birthDateField.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("birthDate"));
        cellNumberField.setCellValueFactory(new PropertyValueFactory<Employee, String>("cellNumber"));
        addressField.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));

        try {
            updateTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void textAreaClicked(MouseEvent mouseEvent) {
        mainTable.getSelectionModel().clearSelection();
        detailsButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }

    @FXML
    protected void newButtonPress(ActionEvent e) throws IOException {

        temp.eraseJSON();

        Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void editButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        if(selected != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            root = loader.load();

            EmployeeController employeeController = loader.getController();
            employeeController.updateField(selected,true);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    protected void deleteButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();
        if (selected != null){
            //il messaggio di errore viene rimosso
            //display.setVisible(false);

            data.remove(selected);
            updateTable();
        }
    }
    @FXML
    protected void detailsButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        if(selected != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            root = loader.load();

            EmployeeController employeeController = loader.getController();
            employeeController.updateField(selected,false);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
    private void updateTable() throws IOException {

        people = FXCollections.observableArrayList(data.readJSON());
        mainTable.setItems(people);
    }

    //metodi usati per la ricerca
    //------------------------------------------------------------------------

    TreeSet<Employee> result=new TreeSet<>();
    TreeSet<Employee> hasCarResult =new TreeSet<>();
    TreeSet<Employee> hasLicenseResult=new TreeSet<>();
    TreeSet<Employee> birthDateResult=new TreeSet<>();

    public void ORbuttonPress(ActionEvent actionEvent) {

        ANDenable.setSelected(false);
        searchButton.setDisable(false);
    }

    public void ANDbuttonPress(ActionEvent actionEvent) {

        ORenable.setSelected(false);
        searchButton.setDisable(false);
    }

    public void searchButtonPress(ActionEvent actionEvent) {

        if(this.result!=null)
            result.clear();

        //ricerca in or: aggiungo al treeset risultato, tutti i treeset delle ricerche singole
        //il treeset si occuperà di non avere ripetizioni (vedi compareTo di Person)
        if(ORenable.isSelected())
        {
            result.addAll(hasCarResult);
            result.addAll(hasLicenseResult);
            result.addAll(birthDateResult);

        }

        //ricerca in AND: aggiungo al treeset risultato, tutti i treeset delle ricerche singole
        //infine elimino dal risultato tutti gli elementi che non sono in comune con ogni treeset di singola ricerca
        //difatto eseguendo un intersezione, quindi un AND
        if(ANDenable.isSelected())
        {
            result.addAll(hasCarResult);
            result.addAll(hasLicenseResult);
            result.addAll(birthDateResult);

            if(hasCarEnable.isSelected())
                result.retainAll(hasCarResult);
            if(hasLicenseEnable.isSelected())
                result.retainAll(hasLicenseResult);
            if(birthIntervalEnable.isSelected())
                result.retainAll(birthDateResult);
        }

        people= FXCollections.observableArrayList(result);
        mainTable.setItems(people);


    }

    public void restoreButtonPress(ActionEvent actionEvent) throws IOException {
        people = FXCollections.observableArrayList(data.readJSON());
        mainTable.setItems(people);
        result.clear();

        hasCarResult.clear();
        hasLicenseResult.clear();
        birthDateResult.clear();
        hasCarEnable.setSelected(false);
        hasLicenseEnable.setSelected(false);
        birthIntervalEnable.setSelected(false);
    }

    //stream di employee, filtro quelli che non hanno il set licenze vuoto
    //quindi li aggiungo al set risultato di questa ricerca
    public void hasLicenseEnablePress(ActionEvent actionEvent) {

        if(hasLicenseResult!=null)
            hasLicenseResult.clear();

        people.stream()
                .filter(p -> !p.getLicenses().isEmpty())
                    .forEach(p->hasLicenseResult.add(p));
    }

    //stream di employee, filtro quelli che hanno una macchina
    //quindi li aggiungo al set risultato di questa ricerca
    public void hasCarEnablePress(ActionEvent actionEvent) {

        if(hasCarResult!=null)
            hasCarResult.clear();

        people.stream()
                .filter(p -> p.hasCar())
                    .forEach(p-> hasCarResult.add(p));

    }


    public void birthIntervalEnablePress(ActionEvent actionEvent) {

        if(birthDateResult!=null)
            birthDateResult.clear();

        //se la data di fine e di inizio contengono data valida
        if(birthFromDate!=null&&birthToDate!=null)
        {
            System.out.print("\nsearching from: "+birthFromDate.getValue());
            System.out.print("\nto              "+birthToDate.getValue());

            people.stream()
                    .filter(p->p.getBirthDate().isAfter(birthFromDate.getValue())&&p.getBirthDate().isBefore(birthToDate.getValue()))
                    .forEach(p->birthDateResult.add(p));

        }

        System.out.print("\ndate stream result:");
        for (Employee d:birthDateResult
        ) {
            System.out.print("\n"+d.getBirthDate()+" "+d.getFirstName());
        }

        System.out.print("\n");

    }



    //permetto di abilitare le date solo se hanno un valore dentro
    public void birthFromDatePress(ActionEvent actionEvent) {

        if(birthFromDate.getValue()!=null&&birthToDate.getValue()!=null)
            birthIntervalEnable.setDisable(false);
        else
            birthIntervalEnable.setDisable(true);


    }
    public void birthToDatePress(ActionEvent actionEvent) {
        if(birthToDate.getValue()!=null&&birthFromDate.getValue()!=null)
            birthIntervalEnable.setDisable(false);
        else
            birthIntervalEnable.setDisable(true);

    }

}
