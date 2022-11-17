package com.univr.employeemanager;

public class Person {

    private String firstName;
    private String lastName;
    private String cellNumber = null;
    private String email;

    public Person(String firstName, String lastName, String cellNumber, String email) {

        this.firstName = firstName;
        this.lastName = lastName;

        if (cellNumber.matches("[0-9]+") && cellNumber.length() > 2)
            this.cellNumber = cellNumber;
        else
            throw new IllegalArgumentException("Cell number must be only numbers");

        if (email.contains("@")) this.email = email;
        else throw new IllegalArgumentException("Email must contains @");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setCellNumber(String cellNumber) {
        if (cellNumber.matches("[0-9]+") && cellNumber.length() > 2)
            this.cellNumber = cellNumber;
        else
            throw new IllegalArgumentException("Cell number must be only numbers");
    }

    public void setEmail(String email) {

        if (email.contains("@")) this.email = email;
        else throw new IllegalArgumentException("Email must contains @");
    }
}