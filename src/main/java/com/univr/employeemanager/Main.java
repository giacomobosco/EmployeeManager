package com.univr.employeemanager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.Parent;



public class Main extends Application {

    private static Stage stage;



    @Override
    public void start(Stage primaryStage) throws IOException{

        stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 500));


        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }



    public static void main(String[] args) {
        launch(args);

        Date date = new Date(13, 2, 2000);

        Employee giacomo = new Employee("Giacomo", "Bosco", "Isola della Scala", date, "Isola della Scala",
                "giacomoboscoids@gmail.com", "3408294770", null, null);

        giacomo.setSpokenLanguage(Employee.Language.ITALIAN);
        giacomo.setSpokenLanguage(Employee.Language.ENGLISH);
        giacomo.setLicense(Employee.License.B);
    }
}