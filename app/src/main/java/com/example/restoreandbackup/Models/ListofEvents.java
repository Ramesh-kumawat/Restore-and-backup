package com.example.restoreandbackup.Models;

import java.util.Date;

public class ListofEvents {
    String title;
    String description;
    Date dtstart;
    String dtend;
    String allday;
    String event_timezone;
    String duration;
    String location;
    String calender_id;
    String datestart;



    public ListofEvents( String calender_id,String title, String description,String datestart, Date dtstart, String dtend, String allday, String location) {
        this.title = title;
        this.datestart = datestart;
        this.description = description;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.allday = allday;
        this.location = location;
        this.calender_id = calender_id;
    }

    public ListofEvents(String title, String description, Date dtstart, String dtend, String allday) {
        this.title = title;
        this.description = description;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.allday = allday;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCalender_id() {
        return calender_id;
    }

    public void setCalender_id(String calender_id) {
        this.calender_id = calender_id;
    }

    public String getEvent_timezone() {
        return event_timezone;
    }

    public void setEvent_timezone(String event_timezone) {
        this.event_timezone = event_timezone;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public Date getDtstart() {
        return dtstart;
    }

    public void setDtstart(Date dtstart) {
        this.dtstart = dtstart;
    }

    public String getDtend() {
        return dtend;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    public String getAllday() {
        return allday;
    }

    public void setAllday(String allday) {
        this.allday = allday;
    }
}
