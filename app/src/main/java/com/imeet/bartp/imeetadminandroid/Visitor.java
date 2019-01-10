package com.imeet.bartp.imeetadminandroid;

public class Visitor {
    private String first_name,last_name,street,housenr,postal,phonenr,comments,key;

    public Visitor() {

    }

    public Visitor(String first_name, String last_name, String street, String housenr, String postal, String phonenr, String comments, String key) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.housenr = housenr;
        this.postal = postal;
        this.phonenr = phonenr;
        this.comments = comments;
        this.key = key;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenr() {
        return housenr;
    }

    public void setHousenr(String housenr) {
        this.housenr = housenr;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhonenr() {
        return phonenr;
    }

    public void setPhonenr(String phonenr) {
        this.phonenr = phonenr;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
