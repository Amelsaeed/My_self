package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 9/23/2018.
 */

public class RegisterClass {
    private String cname;
    private String cphone;
    private String ccity;
    private String cbirthday;
    private String cemail;
    private String ctype;

//(mName, mCountry, mCity, mBirthDayCalender,mEmail, mtype);
    public RegisterClass() {}

    public RegisterClass(String cname,String cphone,String ccity,String cbirthday,String cemail,  String ctype) {
        this.cname = cname;
        this.cphone = cphone;
        this.ccity = ccity;
        this.cbirthday = cbirthday;
        this.cemail = cemail;
        this.ctype = ctype;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCcity() {
        return ccity;
    }

    public void setCcity(String ccity) {
        this.ccity = ccity;
    }

    public String getCbirthday() {
        return cbirthday;
    }

    public void setCcbirthday(String cbirthday) {
        this.cbirthday = cbirthday;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }
}
