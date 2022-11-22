package com.univr.employeemanager;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class MenuController {

    @FXML
    private Button newButton, editButton, deleteButton, detailsButton;
    @FXML
    private TextArea textField;
    @FXML
    private TableColumn nameField, jobField, birthDateField, birthPlaceField, languageField;

    public MenuController(){}

    @FXML
    protected void newButtonPress(ActionEvent e) throws IOException {
        Date date = new Date(13, 2, 2000);

        Employee giacomo = new Employee("Giacomo", "Bosco", "Isola della Scala", date, "Isola della Scala",
                "giacomoboscoids@gmail.com", "3408294770", false, null);

        giacomo.setSpokenLanguage(Employee.Language.ITALIAN);
        giacomo.setSpokenLanguage(Employee.Language.ENGLISH);
        giacomo.setLicense(Employee.License.B);
        FileWriter writer = new FileWriter("src/main/java/com/univr/employeemanager/data.json");
        Gson gson = new Gson();
        gson.toJson(giacomo, writer);
        writer.close();

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
}
