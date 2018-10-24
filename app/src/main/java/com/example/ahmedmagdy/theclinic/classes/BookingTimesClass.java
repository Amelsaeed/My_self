package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 10/24/2018.
 */

public class BookingTimesClass {
    private String ctid;
    private String ctname;
    private String ctage;

    public  BookingTimesClass(){}

    public BookingTimesClass(String ctid, String ctname, String ctage) {
        this.ctid = ctid;
        this.ctname = ctname;
        this.ctage = ctage;
    }

    public String getCtid() {
        return ctid;
    }

    public String getCtname() {
        return ctname;
    }

    public String getCtage() {
        return ctage;
    }
}
