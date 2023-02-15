package com.univr.employeemanager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class JSONReadWriteLoginTest {

    private JSONReadWriteLogin jsonRW;
    private LoginPerson testPerson1;
    private LoginPerson testPerson2;

    @Before
    public void setUp() {
        String filePath = "test.json";
        jsonRW = new JSONReadWriteLogin(filePath);
        testPerson1 = new LoginPerson("John", "Doe", "12345", "password@mail", "Stefan", "password");
        testPerson2 = new LoginPerson("Jane", "Doe", "54321", "password2@mail", "Razvan", "password");
    }

    @After
    public void tearDown() throws Exception {
        jsonRW.eraseJSON();
    }

    @Test
    public void writeTest() throws IOException {
        jsonRW.write(testPerson1);
        TreeSet<LoginPerson> readResult = jsonRW.readJSON();
        assertTrue(readResult.contains(testPerson1));
    }

    @Test
    public void readJSONTest() throws IOException {
        jsonRW.write(testPerson1);
        TreeSet<LoginPerson> readResult = jsonRW.readJSON();
        assertTrue(readResult.contains(testPerson1));
        assertEquals(1, readResult.size());
    }

    @Test
    public void removeTest() throws IOException {
        jsonRW.write(testPerson1);
        jsonRW.remove(testPerson1);
        TreeSet<LoginPerson> readResult = jsonRW.readJSON();
        assertFalse(readResult.contains(testPerson1));
        assertEquals(0, readResult.size());
    }

    @Test
    public void containsTest() throws IOException {
        jsonRW.write(testPerson1);
        assertTrue(jsonRW.contains(testPerson1));
    }

    @Test
    public void eraseJSONTest() throws IOException {
        jsonRW.write(testPerson1);
        jsonRW.eraseJSON();
        TreeSet<LoginPerson> readResult = jsonRW.readJSON();
        assertEquals(0, readResult.size());
    }

}
