package com.univr.employeemanager;
import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Employee extends Person {

    enum Language {
        ENGLISH,
        ITALIAN,
        FRENCH,
        SPANISH,
        ARABIC,
        CHINESE,
        PORTOGUESE,
        JAPANESE,
        GERMAN
    };

    enum License {
        A, B, C, D, E, NONE
    };

    private String birthPlace = null;
    private LocalDate birthDate;
    private String address;
    private TreeSet<Job> formerJobs = new TreeSet<>();
    private TreeSet<Language> spokenLanguage = new TreeSet<>();
    private TreeSet<License> licenses = new TreeSet<>();
    private Boolean car = false;
    private Person emergency = null;

    //#####################################################
    private LocalDate periodFrom,periodTo;
    //#####################################################
    public Employee(String firstName,
                    String lastName,
                    String birthPlace,
                    LocalDate birthDate,
                    String address,
                    String email,
                    String cellNumber,
                    Boolean car,
                    Person emergency) {


        super(firstName, lastName, cellNumber, email);
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;


        this.address = address;
        this.car = car;
        this.emergency = emergency;

    }

    public String getBirthplace() {
        return birthPlace;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

   /* public String getBirthDateString() {
        return birthDateString;
    }*/

    public TreeSet<Job> getFormerJobs() {

        return formerJobs;

    }

    public TreeSet<Language> getSpokenLanguage() {
        return spokenLanguage;
    }

    public TreeSet<License> getLicenses() {
        return licenses;
    }

    public Boolean hasCar() {
        return car;
    }

    public Person getEmergency() {
        return emergency;
    }

    //#####################################################
    public LocalDate[] getAvailablePeriod()
    {
        return new LocalDate[]{periodFrom,periodTo};
    }

    public void setAvailablePeriod(LocalDate l1,LocalDate l2)
    {
        if(l1.isAfter(l2))
            throw new IllegalArgumentException("period from is grater than period to");
        else if (l1.isBefore(birthDate)) {
            throw new IllegalArgumentException("period from is before birthdate");

        }

        this.periodFrom=l1;
        this.periodTo=l2;
    }
    //#####################################################

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setFormerJob(Job formerJob) {

        if (formerJob.getBegin().compareTo(birthDate) < 0)
            throw new IllegalArgumentException("Job begin date is grater than birth date");
        this.formerJobs.add(formerJob);

    }

    public void removeFormerJob(Job formerJob) {

        this.formerJobs.remove(formerJob);
    }

    public void setSpokenLanguage(Language spokenLanguage) {

        this.spokenLanguage.add(spokenLanguage);
    }

    public void setLicense(License license) {

        this.licenses.add(license);
    }

    public void setCar(Boolean car) {
        this.car = car;
    }

}
