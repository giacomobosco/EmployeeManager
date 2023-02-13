package com.univr.employeemanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginPersonTest {

    @Test
    public void testLoginPersonCreation() {
    LoginPerson person = new LoginPerson("John", "Doe", "1234567890", "johndoe@email.com", "johndoe", "password");

    assertEquals("John", person.getFirstName());
    assertEquals("Doe", person.getLastName());
    assertEquals("1234567890", person.getCellNumber());
    assertEquals("johndoe@email.com", person.getEmail());
    assertEquals("johndoe", person.getUsername());
    assertEquals("password", person.getPassword());
    assertFalse(person.getAdmin());
}

    @Test
    public void testSetUsername() {
        LoginPerson person = new LoginPerson("John", "Doe", "1234567890", "johndoe@email.com", "johndoe", "password");
        person.setUsername("newusername");

        assertEquals("newusername", person.getUsername());
    }

    @Test
    public void testSetPassword() {
        LoginPerson person = new LoginPerson("John", "Doe", "1234567890", "johndoe@email.com", "johndoe", "password");
        person.setPassword("newpassword");

        assertEquals("newpassword", person.getPassword());
    }

}
