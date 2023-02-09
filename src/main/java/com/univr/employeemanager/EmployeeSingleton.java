package com.univr.employeemanager;

public class EmployeeSingleton {
    private static EmployeeSingleton instance = null;
    private Employee employee = null;
    private Job job = null;

    private EmployeeSingleton(){}

    public static EmployeeSingleton getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new EmployeeSingleton();
        }
        return instance;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
