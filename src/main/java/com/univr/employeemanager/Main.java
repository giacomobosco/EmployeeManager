package com.univr.employeemanager;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;

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


    public static void main(String[] args) {
        launch(args);

    }
}