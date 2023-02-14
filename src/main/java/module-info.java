module com.univr.employeemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires junit;
    requires org.testng;


    opens com.univr.employeemanager to javafx.fxml, com.google.gson;
    exports com.univr.employeemanager;
}
