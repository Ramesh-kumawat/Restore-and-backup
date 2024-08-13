package com.example.restoreandbackup.Models;

public class SMSModel {

    String Address;
    String Person;
    String Date;
    String Body;
    String Type;

    public SMSModel(String address, String person, String date, String body, String type) {
        Address = address;
        Person = person;
        Date = date;
        Body = body;
        Type = type;
    }

    public SMSModel(String address, String person, String date, String body) {
        Address = address;
        Person = person;
        Date = date;
        Body = body;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
