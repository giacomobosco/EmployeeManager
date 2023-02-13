package com.univr.employeemanager;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class EndToEndTest {
    @Test
    public void testEndToEnd() {
        Employee employee = new Employee("John", "Doe", "johndoe@email.com", "1234567890");
        Job job = new Job(LocalDate.of(2021, 2, 21), LocalDate.of(2022, 2,21), "Accenture", "Software Development", "Developer", 2000);
        LoginPerson loginPerson = new LoginPerson("John", "Doe", "1234567890", "johndoe@email.com", "johndoe", "password");

        EmployeeSingleton instance = EmployeeSingleton.getInstance();
        instance.setEmployee(employee);
        instance.setJob(job);

        assertSame(employee, instance.getEmployee());
        assertSame(job, instance.getJob());
        assertEquals("John", loginPerson.getFirstName());
        assertEquals("Doe", loginPerson.getLastName());
        assertEquals("1234567890", loginPerson.getCellNumber());
        assertEquals("johndoe@email.com", loginPerson.getEmail());
        assertEquals("johndoe", loginPerson.getUsername());
        assertEquals("password", loginPerson.getPassword());
        assertFalse(loginPerson.getAdmin());
    }
}
