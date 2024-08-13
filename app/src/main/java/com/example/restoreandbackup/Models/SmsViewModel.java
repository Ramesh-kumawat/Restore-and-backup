package com.example.restoreandbackup.Models;

public class SmsViewModel {

    String name;
    String detail;
    String Date;

    public SmsViewModel(String name, String detail, String date) {
        this.name = name;
        this.detail = detail;
        Date = date;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
