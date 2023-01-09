package com.univr.employeemanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class Job implements Comparable<Job>{

    private LocalDate begin;
    private LocalDate end;
    private String companyName;
    private String tasks;
    private String jobPlace;
    private Integer dailyPay;

    public Job(LocalDate begin, LocalDate end, String companyName, String tasks, String jobPlace, Integer dailyPay) {
        this.begin = begin;

        if(this.begin.isBefore(end)) this.end = end;
        else throw new IllegalArgumentException("End date must be after begin date");

        this.companyName = companyName;
        this.tasks = tasks;
        this.jobPlace = jobPlace;

        if(dailyPay < 0) throw new IllegalArgumentException("Daily pay must be greater or equal to 0");
        this.dailyPay = dailyPay;
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

    public String getTasks() {
        return tasks;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEnd(LocalDate end) {

        if(this.begin.isBefore(end)) this.end = end;
        else throw new IllegalArgumentException("End date must be after begin date");

    }

    public void setBegin(LocalDate begin) {

        this.begin = begin;
    }

    public void setJobPlace(String jobPlace) {
        this.jobPlace = jobPlace;
    }

    public void setDailyPay(Integer dailyPay) {
        if(dailyPay < 0) throw new IllegalArgumentException("Daily pay must be greater or equal to 0");
        this.dailyPay = dailyPay;
    }

    public void setTask(String tasks) {
        this.tasks = tasks;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Job job) {
        int ret = this.companyName.compareTo(job.getCompanyName());
        if(ret == 0)
            ret = this.getBegin().compareTo(job.getBegin());
        return ret;
    }

}
