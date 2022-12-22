module com.univr.employeemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.univr.employeemanager to javafx.fxml, com.google.gson;
    exports com.univr.employeemanager;
}