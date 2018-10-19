package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 9/23/2018.
 */

public class RegisterClass {
    private String cname;
    private String ccountry;
    private String ccity;
    private String cBirthDay;
    private String cemail;
    private String ctype;

//(mName, mCountry, mCity, mBirthDayCalender,mEmail, mtype);
    public RegisterClass() {}

    public RegisterClass(String cname,String ccountry,String ccity,String cBirthDay,String cemail,  String ctype) {
        this.cname = cname;
        this.ccountry = ccountry;
        this.ccity = ccity;
        this.cBirthDay = cBirthDay;
        this.cemail = cemail;
        this.ctype = ctype;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCcountry() {
        return ccountry;
    }

    public void setCcountry(String ccountry) {
        this.ccountry = ccountry;
    }

    public String getCcity() {
        return ccity;
    }

    public void setCcity(String ccity) {
        this.ccity = ccity;
    }

    public String getcBirthDay() {
        return cBirthDay;
    }

    public void setcBirthDay(String cBirthDay) {
        this.cBirthDay = cBirthDay;
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
