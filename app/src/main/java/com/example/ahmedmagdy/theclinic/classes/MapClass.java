package com.example.ahmedmagdy.theclinic.classes;

/**
 * Created by AHMED MAGDY on 10/23/2018.
 */
public class MapClass {
    private String cmid;

    private String cmdoctorid;
    private String cmlatitude;
    private String cmlongitude;
    private String cmname;
    private String cmdoctorspecialty;
    private String cmdoctorpic;


    public MapClass(){}

    public MapClass(String cmid, String cmdoctorid, String cmlatitude, String cmlongitude, String cmname, String cmdoctorspecialty, String cmdoctorpic) {
        this.cmid = cmid;
        this.cmdoctorid = cmdoctorid;
        this.cmlatitude = cmlatitude;
        this.cmlongitude = cmlongitude;
        this.cmname = cmname;
        this.cmdoctorspecialty = cmdoctorspecialty;
        this.cmdoctorpic = cmdoctorpic;
    }

    public String getCmid() {
        return cmid;
    }

    public void setCmid(String cmid) {
        this.cmid = cmid;
    }

    public String getCmdoctorid() {
        return cmdoctorid;
    }

    public void setCmdoctorid(String cmdoctorid) {
        this.cmdoctorid = cmdoctorid;
    }

    public String getCmlatitude() {
        return cmlatitude;
    }

    public void setCmlatitude(String cmlatitude) {
        this.cmlatitude = cmlatitude;
    }

    public String getCmlongitude() {
        return cmlongitude;
    }

    public void setCmlongitude(String cmlongitude) {
        this.cmlongitude = cmlongitude;
    }

    public String getCmname() {
        return cmname;
    }

    public void setCmname(String cmname) {
        this.cmname = cmname;
    }

    public String getCmdoctorspecialty() {
        return cmdoctorspecialty;
    }

    public void setCmdoctorspecialty(String cmdoctorspecialty) {
        this.cmdoctorspecialty = cmdoctorspecialty;
    }

    public String getCmdoctorpic() {
        return cmdoctorpic;
    }

    public void setCmdoctorpic(String cmdoctorpic) {
        this.cmdoctorpic = cmdoctorpic;
    }
}
