package com.example.restoreandbackup.Models;

import android.graphics.Bitmap;

public class ListClickModel {

    String position;
    String date;
    String number;
    Bitmap bitmap;
    String name;
    String type;
    String Address;
    String Body;
    String Type;
    String Date;

    public ListClickModel(String position, String address, String date, String body, String type) {
        this.position = position;
        Address = address;
        Date = date;
        Body = body;
        Type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
