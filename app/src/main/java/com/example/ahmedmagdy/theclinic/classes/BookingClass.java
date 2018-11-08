package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 10/23/2018.
 */

public class BookingClass {
    private String cbid;
    private String cbtime;
    private String cbaddress;
    private String cbdoctorid;
    private String cblatitude;
    private String cblongitude;
    public  BookingClass(){}

    public BookingClass(String cbid, String cbtime, String cbaddress,String cbdoctorid,String cblatitude,String cblongitude) {
        this.cbid = cbid;
        this.cbtime = cbtime;
        this.cbaddress = cbaddress;
        this.cbdoctorid = cbdoctorid;
        this.cblatitude = cblatitude;
        this.cblongitude = cblongitude;
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

    public String getCblatitude() {
        return cblatitude;
    }

    public void setCblatitude(String cblatitude) {
        this.cblatitude = cblatitude;
    }

    public String getCblongitude() {
        return cblongitude;
    }

    public void setCblongitude(String cblongitude) {
        this.cblongitude = cblongitude;
    }
}
