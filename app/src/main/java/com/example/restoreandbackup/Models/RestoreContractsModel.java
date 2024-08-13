package com.example.restoreandbackup.Models;

public class RestoreContractsModel {

    String name;
    String Date;
    String path;

    public RestoreContractsModel(String name, String date, String path) {
        this.name = name;
        Date = date;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
