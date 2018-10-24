package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 10/23/2018.
 */

public class BookingClass {
    private String cbid;
    private String cbtime;
    private String cbaddress;
    private String cbdoctorid;

    public  BookingClass(){}

    public BookingClass(String cbid, String cbtime, String cbaddress,String cbdoctorid) {
        this.cbid = cbid;
        this.cbtime = cbtime;
        this.cbaddress = cbaddress;
        this.cbdoctorid = cbdoctorid;
    }

    public String getCbid() {
        return cbid;
    }

    public void setCbid(String cbid) {
        this.cbid = cbid;
    }

    public String getCbtime() {
        return cbtime;
    }

    public void setCbtime(String cbtime) {
        this.cbtime = cbtime;
    }

    public String getCbaddress() {
        return cbaddress;
    }

    public void setCbaddress(String cbaddress) {
        this.cbaddress = cbaddress;
    }


    public String getCbdoctorid() {
        return cbdoctorid;
    }

    public void setCbdoctorid(String cbdoctorid) {
        this.cbdoctorid = cbdoctorid;
    }
}
