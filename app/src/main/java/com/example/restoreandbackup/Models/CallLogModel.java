package com.example.restoreandbackup.Models;

public class CallLogModel {

    String phNumber;
    String callType;
    String callDate;
    String callDayTime;
    String callDuration;
    String dir1;
    String Name;
    String position;


    public CallLogModel(String position,String phNumber,String dir1,String callDate,String callDuration) {
       this.position = position;
        this.phNumber = phNumber;
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.dir1 = dir1;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public void setCallDayTime(String callDayTime) {
        this.callDayTime = callDayTime;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public void setDir1(String dir1) {
        this.dir1 = dir1;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

