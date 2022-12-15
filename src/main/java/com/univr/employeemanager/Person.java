package com.univr.employeemanager;

import javafx.beans.property.SimpleStringProperty;

public class Person {

    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty cellNumber = null;
    private SimpleStringProperty email;

    public Person(String firstName, String lastName, String cellNumber, String email) {

        if(checkName(firstName)) this.firstName = new SimpleStringProperty(firstName);

        if(checkName(lastName)) this.lastName = new SimpleStringProperty(lastName);

        if(checkCell(cellNumber)) this.cellNumber = new SimpleStringProperty(cellNumber);

        if(checkEmail(email)) this.email = new SimpleStringProperty(email);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getCellNumber() {
        return cellNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public void setCellNumber(String cellNumber) {
        if(checkCell(cellNumber)) this.cellNumber.set(cellNumber);
    }

    public void setEmail(String email) {

        if(checkEmail(email)) this.email.set(email);
    }

    private boolean checkEmail(String email){
        if (email.contains("@")) return true;
        else throw new IllegalArgumentException("Email must contains @");
    }

    private boolean checkCell(String cellNumber){

        if (cellNumber.matches("[0-9]+") && cellNumber.length() > 2) return true;
        else throw new IllegalArgumentException("Cell number must be only numbers");
    }

    private boolean checkName(String name){
        if (name.matches("[a-zA-Z]+") && name.length() > 0) return true;
        else throw new IllegalArgumentException("Name must contain only alphabets");
    }
}