package com.example.restoreandbackup.Models;

public class ListofCalender {
    String phNumber;
    String callType;
    String callDate;
    String callDayTime;
    String callDuration;
    String dir1;
    String Name;


    public ListofCalender(String Name,String phNumber, String callType, String callDate, String callDayTime, String callDuration, String dir1) {
        this.phNumber = phNumber;
        this.callType = callType;
        this.callDate = callDate;
        this.callDayTime = callDayTime;
        this.callDuration = callDuration;
        this.dir1 = dir1;
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public String getCallDate() {
        return callDate;
    }

    public String getCallDayTime() {
        return callDayTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public String getDir1() {
        return dir1;
    }

}
