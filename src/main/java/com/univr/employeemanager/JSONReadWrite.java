package com.univr.employeemanager;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.TreeSet;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JSONReadWrite{

    private final Gson gson;
    private FileWriter fileWriter = null;
    private FileReader fileReader = null;
    private final String path;

    public JSONReadWrite(String filePath){

        // Crea il TypeAdapter per le date
        TypeAdapter<LocalDate> dateTypeAdapter = new TypeAdapter<LocalDate>() {

            private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public void write(JsonWriter out, LocalDate value) throws IOException {
                out.value(value.format(FORMATTER));
            }

            @Override
            public LocalDate read(JsonReader in) throws IOException {
                return LocalDate.parse(in.nextString(), FORMATTER);
            }
        };

        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, dateTypeAdapter)
                .serializeNulls()
                .create();
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

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
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
