package com.univr.employeemanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Job implements Comparable<Job>{

    private LocalDate begin;
    private LocalDate end;
    private String companyName;
    private ArrayList<String> tasks = new ArrayList<>();
    private String jobPlace;
    private Integer dailyPay;

    public Job(LocalDate begin, LocalDate end, String companyName, ArrayList<String> tasks, String jobPlace, Integer dailyPay) {
        this.begin = begin;

        if(this.begin.isAfter(end)) this.end = end;
        else throw new IllegalArgumentException("End date must be after begin date");

        this.companyName = companyName;
        this.tasks = tasks;
        this.jobPlace = jobPlace;
        this.dailyPay = dailyPay;
    }

    public Job(LocalDate begin, LocalDate end, String companyName, String job, String jobPlace, Integer dailyPay){
        this(begin, end, companyName, new ArrayList<String>(), jobPlace, dailyPay);
        this.tasks.add(job);
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Integer getDailyPay() {
        return dailyPay;
    }

    //in teoria converte la data in numero di giorni a partire dal 1970/1/1
    public Integer getDuration() {
        if(end != null) {
            return (Integer) (int)(end.toEpochDay() - begin.toEpochDay());
        }
        else return -1;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobPlace() {
        return jobPlace;
    }

    public ArrayList<String> getJobs() {
        return tasks;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEnd(LocalDate end) {

        if(this.begin.isAfter(end)) throw new IllegalArgumentException("End date must be after begin date");
        else
            this.end = end;
    }

    public void setJobPlace(String jobPlace) {
        this.jobPlace = jobPlace;
    }

    public void setDailyPay(Integer dailyPay) {
        if(dailyPay < 0) throw new IllegalArgumentException("daily pay must be greater or equal to 0");
        this.dailyPay = dailyPay;
    }

    public void setJob(String job) {
        this.tasks.add(job);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Job job) {
        return this.begin.compareTo(job.begin);
    }
}
