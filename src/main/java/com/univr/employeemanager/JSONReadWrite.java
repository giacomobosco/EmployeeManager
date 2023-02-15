package com.univr.employeemanager;

import java.io.IOException;
import java.util.TreeSet;

public interface JSONReadWrite <T>{

    void write(T obj) throws IOException;
    TreeSet<T> readJSON() throws IOException;
    void remove(T obj) throws IOException;
}
