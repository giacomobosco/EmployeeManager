package com.univr.employeemanager;

public class Person {

    private String firstName;
    private String lastName;
    private String cellNumber = null;
    private String email;

    public Person(String firstName, String lastName, String cellNumber, String email) {

         this.firstName = firstName;
        this.lastName = lastName;

        if(checkCell(cellNumber)) this.cellNumber = cellNumber;

        if(checkEmail(email)) this.email = email;
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
        if(checkCell(cellNumber)) this.cellNumber = cellNumber;
    }

    public void setEmail(String email) {

        if(checkEmail(email)) this.email = email;
    }

    private boolean checkEmail(String email){
        if (email.contains("@")) return true;
        else throw new IllegalArgumentException("Email must contains @");
    }

    private boolean checkCell(String cellNumber){

        if (cellNumber.matches("[0-9]+") && cellNumber.length() > 2) return true;
        else throw new IllegalArgumentException("Cell number must be only numbers");
    }
}