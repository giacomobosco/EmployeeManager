package com.univr.employeemanager;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class EmployeeSingletonTest {
    @Test
    public void testSingleton() {
        EmployeeSingleton instance1 = EmployeeSingleton.getInstance();
        EmployeeSingleton instance2 = EmployeeSingleton.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    public void testGetEmployee() {
        EmployeeSingleton instance = EmployeeSingleton.getInstance();
        Employee employee = new Employee("John", "Doe", "johndoe@email.com", "1234567890");
        instance.setEmployee(employee);

        assertSame(employee, instance.getEmployee());
    }

    @Test
    public void testSetEmployee() {
        EmployeeSingleton instance = EmployeeSingleton.getInstance();
        Employee employee = new Employee("John", "Doe", "johndoe@email.com", "1234567890" );
        instance.setEmployee(employee);

        assertSame(employee, instance.getEmployee());
    }

    @Test
    public void testGetJob() {
        EmployeeSingleton instance = EmployeeSingleton.getInstance();
        Job job = new Job(LocalDate.of(2021, 2, 21), LocalDate.of(2022, 2,21), "Accenture", "Software Development", "Developer", 2000);
        instance.setJob(job);

        assertSame(job, instance.getJob());
    }

}
