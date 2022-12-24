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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

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
    private Stage stage;
    private Scene scene;
    private Parent root;
    private JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    public MenuController() {
    }

    /**
     * It initializes the table and the buttons, and it sets the listeners for the table
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that was specified in the FXML file.
     */
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



        nameField.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthDateField.setCellValueFactory(new PropertyValueFactory<>("birthDateString"));
        cellNumberField.setCellValueFactory(new PropertyValueFactory<>("cellNumber"));
        addressField.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            updateTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * When the user clicks on the text area, the selection in the table is cleared, and the buttons are disabled
     *
     * @param mouseEvent The mouse event that triggered the method.
     */
    @FXML
    public void textAreaClicked(MouseEvent mouseEvent) {
        mainTable.getSelectionModel().clearSelection();
        detailsButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }

    /**
     * It loads the AddEmployee.fxml file into the scene, and then sets the scene to the stage
     *
     * @param e The event that triggered the method.
     */
    @FXML
    protected void newButtonPress(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**
     * It loads the AddEmployee.fxml file, gets the controller for that file, and then calls the updateField function in
     * the controller, passing in the selected employee and a boolean value
     *
     * @param e The event that triggered the method.
     */
    @FXML
    protected void editButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        if(selected != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            root = loader.load();

            EmployeeController employeeController = loader.getController();
            employeeController.updateField(selected, true);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
    /**
     * It deletes the selected employee from the table
     *
     * @param e the event that triggered the method
     */
    @FXML
    protected void deleteButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();
        if (selected != null){
            //il messaggio di errore viene rimosso
            display.setVisible(false);

            data.remove(selected);
            updateTable();
        }
    }
    /**
     * When the details button is pressed, the selected employee is passed to the AddEmployeeController, which then updates
     * the fields in the AddEmployee.fxml file
     *
     * @param e The event that triggered the method.
     */
    @FXML
    protected void detailsButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        if (selected != null){

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

        ObservableList<Employee> people = FXCollections.observableArrayList(data.readJSON());
        mainTable.setItems(people);
    }
}
