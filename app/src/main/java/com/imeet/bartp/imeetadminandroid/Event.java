package com.imeet.bartp.imeetadminandroid;

public class Event {
    private String name, description, date, starttime, endtime, key;

    public Event() {

    }

    public Event(String name, String description, String date, String starttime, String endtime, String key) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
