package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 10/24/2018.
 */
//timesid, patientname, patientage, mDate, picuri
public class BookingTimesClass {
    private String ctid;
    private String ctname;
    private String ctage;
    private String ctdate;
    private String ctAddress;
    private String ctPeriod;
    private String ctpicuri;


    public  BookingTimesClass(){}



    public BookingTimesClass(String ctid, String ctname, String ctage, String ctdate,String ctAddress, String ctPeriod, String ctpicuri) {
        this.ctid = ctid;
        this.ctname = ctname;
        this.ctage = ctage;
        this.ctdate = ctdate;
        this.ctAddress = ctAddress;
        this.ctPeriod = ctPeriod;
        this.ctpicuri = ctpicuri;
    }

    public String getCtid() {
        return ctid;
    }

    public void setCtid(String ctid) {
        this.ctid = ctid;
    }

    public String getCtname() {
        return ctname;
    }

    public void setCtname(String ctname) {
        this.ctname = ctname;
    }

    public String getCtage() {
        return ctage;
    }

    public void setCtage(String ctage) {
        this.ctage = ctage;
    }

    public String getCtdate() {
        return ctdate;
    }

    public void setCtdate(String ctdate) {
        this.ctdate = ctdate;
    }

    public String getCtAddress() {
        return ctAddress;
    }

    public String getCtPeriod() {
        return ctPeriod;
    }

    public String getCtpicuri() {
        return ctpicuri;
    }

    public void setCtpicuri(String ctpicuri) {
        this.ctpicuri = ctpicuri;
    }
}
