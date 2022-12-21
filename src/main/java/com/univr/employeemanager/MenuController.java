package com.univr.employeemanager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.Date;
import java.util.ResourceBundle;

public class MenuController implements Initializable,EventHandler<ActionEvent> {

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
    private Parent root;
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
    protected void editButtonPress(ActionEvent e) throws IOException{

        Employee employee=mainTable.getSelectionModel().getSelectedItem();
        System.out.print(employee);

        if(employee!=null)
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            root=loader.load();

            EmployeeController scena=loader.getController();
            scena.updateField(employee);


            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
    @FXML
    protected void deleteButtonPress(ActionEvent e){
    }
    @FXML
    protected void detailsButtonPress(ActionEvent e){


    }

    public void textAreaClicked(MouseEvent mouseEvent) {
        mainTable.getSelectionModel().clearSelection();
        detailsButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        detailsButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);

       mainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue!=null)
           {
               System.out.print("evento tabella click\n");
               detailsButton.setDisable(false);
               deleteButton.setDisable(false);
               editButton.setDisable(false);
           }

       });

        people = FXCollections.observableArrayList(
                new Employee("Franci", "Manto", "a casa sua", new Date(100, 7, 16), "casa sua", "@", "234", null,null),
                new Employee("dio", "povero", "a casa mia", new Date(2000, 7, 16), "casa sua", "@fr", "234", null,null));

        nameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        birthDateField.setCellValueFactory(new PropertyValueFactory<Employee, String>("birthDateString"));
        cellNumberField.setCellValueFactory(new PropertyValueFactory<Employee, String>("cellNumber"));
        addressField.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));

        mainTable.setItems(people);


    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }



}
