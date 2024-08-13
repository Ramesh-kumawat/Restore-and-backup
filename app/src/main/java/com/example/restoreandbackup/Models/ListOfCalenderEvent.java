package com.example.restoreandbackup.Models;

public class ListOfCalenderEvent {

    String position;
    String calender_id;
    String title;
    String description;
    String dateSt;
    String dateEd;
    String location;
    String allday;

    public ListOfCalenderEvent(String position,String calender_id,String title, String description, String dateSt, String dateEd, String location, String allday) {
        this.position = position;
        this.calender_id = calender_id;
        this.title = title;
        this.description = description;
        this.dateSt = dateSt;
        this.dateEd = dateEd;
        this.location = location;
        this.allday = allday;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCalender_id() {
        return calender_id;
    }

    public void setCalender_id(String calender_id) {
        this.calender_id = calender_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateSt() {
        return dateSt;
    }

    public void setDateSt(String dateSt) {
        this.dateSt = dateSt;
    }

    public String getDateEd() {
        return dateEd;
    }

    public void setDateEd(String dateEd) {
        this.dateEd = dateEd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAllday() {
        return allday;
    }

    public void setAllday(String allday) {
        this.allday = allday;
    }
}
