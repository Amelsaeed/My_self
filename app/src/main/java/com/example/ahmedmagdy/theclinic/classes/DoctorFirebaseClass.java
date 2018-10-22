package com.example.ahmedmagdy.theclinic.classes;

import java.io.Serializable;

/**
 * Created by AHMED MAGDY on 9/10/2018.
 */

public class DoctorFirebaseClass implements Serializable {
    private String cId;
    private String cName;
    private String cSpecialty;
    private String cCity;
    private String cUri;
    private Boolean checked;


    public DoctorFirebaseClass(){}

    public DoctorFirebaseClass(String cId, String cName, String cSpecialty, String cCity, String cUri, Boolean checked) {
        this.cName = cName;
        this.cSpecialty = cSpecialty;
        this.cCity = cCity;
        this.cUri = cUri;

        this.cId = cId;
        this.checked = checked;

    }

    public DoctorFirebaseClass(String cId, String cName, String cSpecialty, String cCity, String cUri) {
        this.cName = cName;
        this.cSpecialty = cSpecialty;
        this.cCity = cCity;
        this.cUri = cUri;
        this.cId = cId;
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

