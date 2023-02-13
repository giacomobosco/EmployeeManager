package com.univr.employeemanager;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class JobTest {

    @Test
    public void testConstructorWithValidData() {
        LocalDate begin = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 12, 31);
        String companyName = "ACME Inc.";
        String tasks = "Software development";
        String jobPlace = "Milan";
        Integer dailyPay = 1000;

        Job job = new Job(begin, end, companyName, tasks, jobPlace, dailyPay);

        assertEquals(begin, job.getBegin());
        assertEquals(end, job.getEnd());
        assertEquals(companyName, job.getCompanyName());
        assertEquals(tasks, job.getTasks());
        assertEquals(jobPlace, job.getJobPlace());
        assertEquals(dailyPay, job.getDailyPay());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidBeginAndEndDates() {
        LocalDate begin = LocalDate.of(2022, 12, 31);
        LocalDate end = LocalDate.of(2022, 1, 1);
        String companyName = "ACME Inc.";
        String tasks = "Software development";
        String jobPlace = "Milan";
        Integer dailyPay = 1000;

        Job job = new Job(begin, end, companyName, tasks, jobPlace, dailyPay);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidDailyPay() {
        LocalDate begin = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 12, 31);
        String companyName = "ACME Inc.";
        String tasks = "Software development";
        String jobPlace = "Milan";
        Integer dailyPay = -1000;

        Job job = new Job(begin, end, companyName, tasks, jobPlace, dailyPay);
    }

    @Test
    public void testGetBeginWithValidData() {
        LocalDate begin = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 12, 31);
        String companyName = "ACME Inc.";
        String tasks = "Software development";
        String jobPlace = "Milan";
        Integer dailyPay = 1000;

        Job job = new Job(begin, end, companyName, tasks, jobPlace, dailyPay);
        assertEquals(begin, job.getBegin());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBeginWithInvalidData() {
        LocalDate end = LocalDate.of(2022, 12, 31);
        String companyName = "ACME Inc.";
        String tasks = "Software development";
        String jobPlace = "Milan";
        Integer dailyPay = 1000;

        Job job = new Job(null, end, companyName, tasks, jobPlace, dailyPay);
        job.getBegin();

    }






    }
