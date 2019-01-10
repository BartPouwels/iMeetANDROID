package com.imeet.bartp.imeetadminandroid;

public class EventVisitor {
    private String eventid,visitorid,key;

    public EventVisitor() {

    }

    public EventVisitor(String eventid, String visitorid, String key) {
        this.eventid = eventid;
        this.visitorid = visitorid;
        this.key = key;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getVisitorid() {
        return visitorid;
    }

    public void setVisitorid(String visitorid) {
        this.visitorid = visitorid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
