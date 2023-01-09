package com.univr.employeemanager;

import java.time.LocalDate;
import java.time.ZoneId;
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

    private String birthPlace;
    private LocalDate birthDate, periodFrom, periodTo;
    private String address;
    private TreeSet<Job> formerJobs = new TreeSet<>();
    private TreeSet<Language> spokenLanguage = new TreeSet<>();
    private TreeSet<License> licenses = new TreeSet<>();
    private Boolean car;
    private Person emergency;

    public Employee(String firstName,
                    String lastName,
                    String email,
                    String cellNumber) {

        super(firstName, lastName, cellNumber, email);
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

    public LocalDate[] getAvailablePeriod()
    {
        return new LocalDate[]{periodFrom,periodTo};
    }


    public void setAvailablePeriod(LocalDate l1, LocalDate l2)
    {
        if(l1 == null || l2 == null)
            throw new IllegalArgumentException("Period can't be blank");

        if(l1.isAfter(l2))
            throw new IllegalArgumentException("period from is grater than period to");

        this.periodFrom = l1;
        this.periodTo = l2;
    }

    public void setBirthPlace(String birthPlace) {
        if (checkIsBlank(birthPlace)) this.birthPlace = birthPlace;
    }

    public void setAddress(String address) {
        if (checkIsBlank(address)) this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {

        if(birthDate != null){
            this.birthDate = birthDate;
        }
        else throw new IllegalArgumentException("BirthDate can't be empty");
    }

    public void setFormerJob(Job formerJob) {


        if (formerJob.getBegin().isBefore(birthDate))
            throw new IllegalArgumentException("Job begin date is grater then birth date");

        if (!this.formerJobs.add(formerJob)) throw new IllegalArgumentException("This job already exits");

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

    public void setEmergency(Person person){

        this.emergency = person;
    }

    private boolean checkIsBlank(String string){
        if (string != null && string != "")
            return true;
        else throw new IllegalArgumentException("There are blank fields");
    }

    public int compareTo(Employee person) {

        int ret = super.compareTo((Person)person );
        if (ret == 0)
            ret = this.birthDate.compareTo(person.birthDate);
        if (ret == 0)
            ret = this.birthPlace.compareTo(person.birthPlace);
        if (ret == 0)
            ret = this.periodFrom.compareTo(person.periodFrom);
        if (ret == 0)
            ret = this.periodTo.compareTo(person.periodTo);
        if (ret == 0)
            ret = this.address.compareTo(person.address);
        if (ret == 0)
            ret = this.car.compareTo(person.car);
        if (ret == 0)
            ret = this.spokenLanguage.equals(person.spokenLanguage)? 0 : 1;
        if (ret == 0)
            ret = this.licenses.equals(person.licenses)? 0 : 1;
        if (ret == 0)
            ret = this.emergency.compareTo(person.emergency);
        return ret;
    }
}