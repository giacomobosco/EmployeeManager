package com.univr.employeemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.TreeSet;

public class JSONReadWrite {

    private final Gson gson;
    private FileWriter fileWriter = null;
    private FileReader fileReader = null;
    private final String path;

    public JSONReadWrite(String filePath){

        gson = new GsonBuilder().serializeNulls().create();
        this.path = filePath;
    }

    public void write(Employee person) throws IOException {

        TreeSet<Employee> previousSet = readSet();

        previousSet.add(person);

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

    public void write(TreeSet<Employee> people) throws IOException {

        TreeSet<Employee> previousSet = readSet();

        previousSet.addAll(people);

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
    }

    public TreeSet<Employee> readSet() throws IOException {

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
