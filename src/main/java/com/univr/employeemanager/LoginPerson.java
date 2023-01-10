package com.univr.employeemanager;

public class LoginPerson extends Person{
    private String username, password;

    public LoginPerson(String firstName, String lastName, String cellNumber, String email, String username, String password) {
        super(firstName, lastName, cellNumber, email);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}