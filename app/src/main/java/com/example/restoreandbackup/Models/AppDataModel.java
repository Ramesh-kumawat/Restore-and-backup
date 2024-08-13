package com.example.restoreandbackup.Models;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.Date;

public class AppDataModel {

    Drawable icon;
    String Name;
    String packegeName;
    Date date1;
    String version;
    String size;
    int Type;
    File file;



    public AppDataModel(Drawable icon, String name, String packegeName, Date date, String version, String size, int type,File file) {
        this.icon = icon;
        Name = name;
        this.packegeName = packegeName;
       this.date1 = date;
        this.version = version;
        this.size = size;
        Type = type;
        this.file = file;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPackegeName() {
        return packegeName;
    }

    public void setPackegeName(String packegeName) {
        this.packegeName = packegeName;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
