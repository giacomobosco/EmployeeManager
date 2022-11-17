module com.univr.employeemanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.univr.employeemanager to javafx.fxml;
    exports com.univr.employeemanager;
}