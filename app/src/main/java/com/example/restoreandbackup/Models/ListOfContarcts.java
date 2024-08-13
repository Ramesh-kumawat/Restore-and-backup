package com.example.restoreandbackup.Models;

import android.graphics.Bitmap;
import android.net.Uri;

public class ListOfContarcts {

    Bitmap bitmap;
    String name;
    String number;
    String date;
    String type;
    Uri uri;

    public ListOfContarcts(Bitmap bitmap, String name, String number, Uri uri) {
        this.bitmap = bitmap;
        this.name = name;
        this.number = number;
        this.uri = uri;

    }
    public ListOfContarcts(String name, String number, String date, String type) {
       this.date = date;
        this.name = name;
        this.number = number;
        this.type = type;
        this.uri = uri;

    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
