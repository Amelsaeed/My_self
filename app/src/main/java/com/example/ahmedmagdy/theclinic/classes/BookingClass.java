package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 10/23/2018.
 */

public class BookingClass {
    private String cbid;
    private String cbtime;
    private String cbaddress;

    public  BookingClass(){}

    public BookingClass(String cbid, String cbtime, String cbaddress) {
        this.cbid = cbid;
        this.cbtime = cbtime;
        this.cbaddress = cbaddress;
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
}
