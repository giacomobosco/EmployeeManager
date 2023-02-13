package com.univr.employeemanager;

import org.junit.Test;

import java.time.LocalDate;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void testEmployeeConstruction() {
        Employee e = new Employee("John", "Doe", "johndoe@example.com", "1234567890");
        assertNotNull(e);
    }

    @Test
    public void testBirthPlace() {
        Employee e = new Employee("John", "Doe", "johndoe@example.com", "1234567890");
        e.setBirthPlace("New York");
        assertEquals("New York", e.getBirthplace());
    }

    @Test
    public void testBirthDate() {
        Employee e = new Employee("John", "Doe", "johndoe@example.com", "1234567890");
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        e.setBirthDate(birthDate);
        assertEquals(birthDate, e.getBirthDate());
    }

    @Test
    public void testAddress() {
        Employee e = new Employee("John", "Doe", "johndoe@example.com", "1234567890");
        e.setAddress("123 Main St");
        assertEquals("123 Main St", e.getAddress());
    }

    @Test
    public void testFormerJobs() {
        Employee e = new Employee("John", "Doe", "johndoe@example.com", "1234567890");
        e.setBirthDate(LocalDate.of(2000, 1, 1));
        Job job1 = new Job(LocalDate.of(2010, 1, 1), LocalDate.of(2012, 1, 1), "Company 1", "Tasks 1", "Place 1", 100);
        Job job2 = new Job(LocalDate.of(2012, 1, 1), LocalDate.of(2014, 1, 1), "Company 2", "Tasks 2", "Place 2", 200);
        e.setFormerJob(job1);
        e.setFormerJob(job2);
        TreeSet<Job> formerJobs = e.getFormerJobs();
        assertEquals(2, formerJobs.size());
        assertTrue(formerJobs.contains(job1));
    }

    @Test
    public void testSetFormerJobWithBeginDateBeforeBirthDate() {
        Employee employee = new Employee("FirstName", "LastName", "email@email", "12345678");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        Job job = new Job( LocalDate.of(1980, 1, 1), LocalDate.of(2000, 1, 1), "Company", "Tasks", "Place", 100);
        try {
            employee.setFormerJob(job);
            fail("Exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Job begin date is grater then birth date", e.getMessage());
        }
    }

}
