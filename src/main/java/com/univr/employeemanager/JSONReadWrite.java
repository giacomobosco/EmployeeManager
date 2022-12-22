package com.univr.employeemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.TreeSet;

public class JSONReadWrite{

    private final Gson gson;
    private FileWriter fileWriter = null;
    private FileReader fileReader = null;
    private final String path;

    public JSONReadWrite(String filePath){

        gson = new GsonBuilder().serializeNulls().create();
        this.path = filePath;
    }

    public void write(Employee employee) throws IOException {

        TreeSet<Employee> previousSet = readJSON();

        previousSet.add(employee);

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
    }

    public void write(TreeSet<Employee> employees) throws IOException {

        TreeSet<Employee> previousSet = readJSON();

        previousSet.addAll(employees);

        fileReader=new FileReader(path);
        char[] buffer=new char[100];

        if(fileReader.read(buffer)==-1)                            //se il file json è vuoto
        {
            fileWriter = new FileWriter(path);
            gson.toJson(previousSet, fileWriter);
            fileWriter.close();
        }
        else
        {

        }

    }

    public void remove(Employee employee) throws IOException {

        TreeSet<Employee> previousSet = readJSON();

        previousSet.remove(employee);

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
    }

    public void eraseJSON() throws IOException {

        fileWriter = new FileWriter(path);
        fileWriter.close();
    }

    public TreeSet<Employee> readJSON() throws IOException {

        fileReader = new FileReader(path);
        Type type = new TypeToken<TreeSet<Employee>>() {
        }.getType();

        TreeSet<Employee> result = gson.fromJson(fileReader, type);

        fileReader.close();

        if(result == null)
            result = new TreeSet<>();

        return result;
    }

}
