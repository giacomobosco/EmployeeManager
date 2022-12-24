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

        gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        this.path = filePath;
    }

    /**
     * It reads the JSON file, adds the new employee to the set, and then writes the set back to the JSON file
     *
     * @param employee The employee object to be written to the file.
     */
    public void write(Employee employee) throws IOException {

        TreeSet<Employee> previousSet = readJSON();

        if(!previousSet.add(employee))
        {
            System.out.print("\njson write: employee not added");
        }

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
    }

    /**
     * It reads the JSON file, adds the new employees to the set, and writes the new set to the JSON file
     *
     * @param employees The set of employees to be written to the file.
     */
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

    /**
     * It reads the JSON file, removes the employee from the set, and then writes the set back to the JSON file
     *
     * @param employee The employee object to be removed from the JSON file.
     */
    public void remove(Employee employee) throws IOException {

        TreeSet<Employee> previousSet = readJSON();

        previousSet.remove(employee);

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
    }

    /**
     * It creates a new file writer, and then closes it
     */
    public void eraseJSON() throws IOException {

        fileWriter = new FileWriter(path);
        fileWriter.close();
    }

    /**
     * It reads the JSON file, converts it to a TreeSet of Employee objects, and returns the TreeSet
     *
     * @return A TreeSet of Employee objects.
     */
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
