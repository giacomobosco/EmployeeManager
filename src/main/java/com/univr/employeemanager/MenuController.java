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

import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class MenuController implements Initializable {

    @FXML
    private Button newButton, editButton, deleteButton, detailsButton;
    @FXML
    private TextArea textField;
    @FXML
    private TableColumn <Employee, String> addressField, nameField, lastNameField, birthDateField, cellNumberField;
    @FXML
    private TableView<Employee> mainTable;
    @FXML
    private Label display;
    private ObservableList<Employee> people;

    private Stage stage;
    private Scene scene;

    private JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");
    private JSONReadWrite temp = new JSONReadWrite("src/main/java/com/univr/employeemanager/temp.json");
    public MenuController() {
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
        if (selected != null){
            temp.eraseJSON();
            temp.write(selected);
            Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            display.setText("Please select something");
            display.setVisible(true);
        }
    }
    @FXML
    protected void deleteButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();
        if (selected != null){
            display.setVisible(false);
            data.remove(selected);
            updateTable();
        }
        else {
            display.setText("Please select something");
            display.setVisible(true);
        }
    }
    @FXML
    protected void detailsButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        if (selected != null){
            temp.eraseJSON();
            temp.write(selected);
            Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            display.setText("Please select something");
            display.setVisible(true);
        }
    }

    private void updateTable() throws IOException {
        people = FXCollections.observableArrayList(data.readSet());
        mainTable.setItems(people);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Employee manto1 = new Employee("Franci", "Manto", "a casa sua", new Date(100, 7, 16), "casa sua", "@", "234", false, new Person("Giacomo", "Bosco", "478294", "a@b"));

            manto1.setSpokenLanguage(Employee.Language.ITALIAN);
            manto1.setSpokenLanguage(Employee.Language.ENGLISH);

            data.write(manto1);
            Employee manto2 = new Employee("Franci", "Mano", "a casa sua", new Date(100, 7, 16), "casa sua", "@", "234", true,null);
            data.write(manto2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        nameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        birthDateField.setCellValueFactory(new PropertyValueFactory<Employee, String>("birthDateString"));
        cellNumberField.setCellValueFactory(new PropertyValueFactory<Employee, String>("cellNumber"));
        addressField.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));

        try {
            updateTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        if (!people.isEmpty()){
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
       */
    }
}
