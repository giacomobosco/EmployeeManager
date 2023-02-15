package com.univr.employeemanager;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TreeSet;

public class JSONReadWriteLogin implements JSONReadWrite<LoginPerson>{

    private final Gson gson;
    private FileWriter fileWriter = null;
    private FileReader fileReader = null;
    private final String path;


    public JSONReadWriteLogin(String filePath){

        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();

        gson=gsonBuilder.setPrettyPrinting().create();       //cosi il file non è solo su 1 riga

        this.path = filePath;
    }

    public void write(LoginPerson person) throws IOException {

        TreeSet<LoginPerson> previousSet = readJSON();

        previousSet.add(person);

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
    }

    public TreeSet<LoginPerson> readJSON() throws IOException {

        fileReader = new FileReader(path);
        Type type = new TypeToken<TreeSet<LoginPerson>>() {
        }.getType();

        TreeSet<LoginPerson> result = gson.fromJson(fileReader, type);

        fileReader.close();

        if(result == null)
            result = new TreeSet<>();

        return result;
    }


    public void remove(LoginPerson person) throws IOException {

        TreeSet<LoginPerson> previousSet = readJSON();

        previousSet.remove(person);

        fileWriter = new FileWriter(path);
        gson.toJson(previousSet, fileWriter);
        fileWriter.close();
    }

    public boolean contains(LoginPerson person) throws IOException {

        TreeSet<LoginPerson> previousSet = readJSON();
        return  previousSet.contains(person);
    }

    public void eraseJSON() throws IOException {

        fileWriter = new FileWriter(path);
        fileWriter.close();
    }


}
/*
class LocalDateTimeSerializer implements JsonSerializer <LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}
class LocalDateTimeDeserializer implements JsonDeserializer < LocalDateTime > {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss").withLocale(Locale.ENGLISH));
    }
}
*/
