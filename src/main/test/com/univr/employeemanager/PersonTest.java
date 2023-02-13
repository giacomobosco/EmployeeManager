package com.univr.employeemanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void testConstructorWithValidInputs() {
        Person person = new Person("John", "Doe", "1234567890", "john.doe@example.com");

        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("1234567890", person.getCellNumber());
        assertEquals("john.doe@example.com", person.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidEmail() {
        new Person("John", "Doe", "1234567890", "johndoeexample.com");
    }

    @Test
    public void testSetEmailWithValidInput() {
        Person person = new Person("John", "Doe", "1234567890", "john.doe@example.com");
        person.setEmail("new.email@example.com");

        assertEquals("new.email@example.com", person.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmailWithInvalidInput() {
        Person person = new Person("John", "Doe", "1234567890", "john.doe@example.com");
        person.setEmail("newemailexample.com");
    }

    @Test
    public void testCompareToWithDifferentPersons() {
        Person person1 = new Person("John", "Doe", "1234567890", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "0987654321", "jane.doe@example.com");

        assertTrue(person1.compareTo(person2) != 0);
        assertTrue(person2.compareTo(person1) != 0);
    }

    @Test
    public void testCompareToWithSamePerson() {
        Person person1 = new Person("John", "Doe", "1234567890", "john.doe@example.com");
        Person person2 = new Person("John", "Doe", "1234567890", "john.doe@example.com");

        assertEquals(0, person1.compareTo(person2));
    }


    @Test
    public void compareTo() {
    }
}
