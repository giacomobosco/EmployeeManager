package com.univr.employeemanager;

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
    }

    ;

    enum License {
        A, B, C, D, E
    }

    ;

    private String birthPlace = null;
    private Date birthDate = null;
    private String address = null;
    private TreeSet<Job> formerJobs = new TreeSet<>();
    private TreeSet<Language> spokenLanguage = new TreeSet<>();
    private TreeSet<License> licenses = new TreeSet<>();
    private Boolean car = false;
    private Person emergency = null;

    public Employee(String firstName,
                    String lastName,
                    String birthPlace,
                    Date birthDate,
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

    public Date getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

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

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setFormerJob(Job formerJob) {

        if (formerJob.getBegin().compareTo(birthDate) < 0)
            throw new IllegalArgumentException("Job begin date is grater then birth date");
        this.formerJobs.add(formerJob);

    }

    public void removeFormerJob(Job formerJob) {

        this.formerJobs.remove(formerJob);
    }

    public void setSpokenLanguage(Language spokenLanguage) {

        this.spokenLanguage.add(spokenLanguage);
    }

    public void removeLicense(License license) {

        this.licenses.remove(license);
    }

    public void setLicense(License license) {

        this.licenses.add(license);
    }

    public void setCar(Boolean car) {
        this.car = car;
    }
}