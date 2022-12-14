package com.univr.employeemanager;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    private TableColumn nameField, lastNameField, birthDateField, cellNumberField, addressField;
    @FXML
    private TableView<Employee> mainTable;
    private ObservableList<Employee> people;

    private Stage stage;
    private Scene scene;
    private JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");
    @FXML
    protected void newButtonPress(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void editButtonPress(ActionEvent e){
    }
    @FXML
    protected void deleteButtonPress(ActionEvent e){
    }
    @FXML
    protected void detailsButtonPress(ActionEvent e){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*try {
            people = FXCollections.observableArrayList(data.readSet());
        } catch (IOException e) {
            textField.setText("Errore durante la lettura del database");
        }*/
        people = FXCollections.observableArrayList(
                new Employee("Franci", "Manto", "a casa sua", new Date(100, 7, 16), "casa sua", "@", "234", null,null));

        nameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        birthDateField.setCellValueFactory(new PropertyValueFactory<Employee, String>("birthDateString"));
        cellNumberField.setCellValueFactory(new PropertyValueFactory<Employee, String>("cellNumber"));
        addressField.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));

        mainTable.setItems(people);

        /*
        if (!people.isEmpty()){
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
       */
    }
}
