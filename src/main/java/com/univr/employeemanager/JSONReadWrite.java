package com.univr.employeemanager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.TreeSet;

public class JSONReadWrite {

    private Gson gson;

    public JSONReadWrite(){
        gson = new Gson();
    };

    public void write(Person person) throws IOException {

        FileWriter fileWriter = new FileWriter("src/main/java/com/univr/employeemanager/data.json");
        gson.toJson(person, fileWriter);
        fileWriter.close();
    }

    public void write(TreeSet<Person> people) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/java/com/univr/employeemanager/data.json");
        gson.toJson(people, fileWriter);
        fileWriter.close();
    }

    public Person read() throws IOException {

        FileReader fileReader = new FileReader("src/main/java/com/univr/employeemanager/data.json");
        Person person = gson.fromJson(fileReader, Person.class);
        fileReader.close();
        return person;
    }

    public TreeSet<Person> readSet() throws IOException {
        FileReader fileReader = new FileReader("src/main/java/com/univr/employeemanager/data.json");

        Type type = new TypeToken<TreeSet<Person>>() {
        }.getType();

        TreeSet<Person> result = gson.fromJson(fileReader, type);

        fileReader.close();

        return result;
    }

}
