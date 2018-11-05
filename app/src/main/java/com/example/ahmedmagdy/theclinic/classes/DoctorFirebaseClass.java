package com.example.ahmedmagdy.theclinic.classes;

import java.io.Serializable;

/**
 * Created by AHMED MAGDY on 9/10/2018.
 */

public class DoctorFirebaseClass implements Serializable {
    private  String cType;
    private  String cEmail;

    private String cId;
    private String cName;
    private String cSpecialty;
    private String cCity;
    private String cUri;

    private String cDegree;
    private String cPhone;
    private String cPrice;
    private String cTime;
    private String cAbout;

    private Boolean checked;


    public DoctorFirebaseClass(){}

    public DoctorFirebaseClass(String cId, String cName, String cSpecialty, String cCity, String cUri,
                               String cDegree,String cPhone,String cPrice,String cTime,String cAbout , Boolean checked) {
        this.cId = cId;
        this.cName = cName;
        this.cSpecialty = cSpecialty;
        this.cCity = cCity;
        this.cUri = cUri;
        this.cDegree = cDegree;
        this.cPhone = cPhone;
        this.cPrice = cPrice;
        this.cTime = cTime;
        this.cAbout = cAbout;
        this.checked = checked;

    }
    public DoctorFirebaseClass(String cId, String cName,String cPhone,  String cCity, String cSpecialty,String cEmail,String cType) {
        this.cId = cId;
        this.cName = cName;
        this.cPhone = cPhone;
        this.cCity = cCity;
        this.cSpecialty = cSpecialty;
        this.cEmail = cEmail;
        this.cType = cType;

        this.cUri = cUri;
        this.cDegree = cDegree;
        this.cPrice = cPrice;
        this.cTime = cTime;
        this.cAbout = cAbout;
        this.checked = checked;

    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public void setcSpecialty(String cSpecialty) {
        this.cSpecialty = cSpecialty;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public void setcUri(String cUri) {
        this.cUri = cUri;
    }

    public String getcName() {
        return cName;
    }

    public String getcSpecialty() {
        return cSpecialty;
    }

    public String getcCity() {
        return cCity;
    }

    public String getcUri() {
        return cUri;
    }


    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcDegree() {
        return cDegree;
    }

    public void setcDegree(String cDegree) {
        this.cDegree = cDegree;
    }

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    public String getcPrice() {
        return cPrice;
    }

    public void setcPrice(String cPrice) {
        this.cPrice = cPrice;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getcAbout() {
        return cAbout;
    }

    public void setcAbout(String cAbout) {
        this.cAbout = cAbout;
    }

    public String getcId() {
        return cId;
    }

    public Boolean getChecked() {
        if (checked == null)
            return false;
        else
            return checked;
    }


    public void setChecked(Boolean checked) {
        this.checked = checked;
    }


}

