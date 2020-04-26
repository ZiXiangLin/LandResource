package com.gis.demo.domain;

public class EmailActive {
    private String USERNAME;
    private String EMAIL;
    private String ACTICODE;
    private String DUE_TIME;
    private String STATUS;
    public String getUSERNAME() {
        return USERNAME;
    }
    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }
    public String getEMAIL() {
        return EMAIL;
    }
    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }
    public String getACTICODE() {
        return ACTICODE;
    }
    public void setACTICODE(String aCTICODE) {
        ACTICODE = aCTICODE;
    }
    public String getDUE_TIME() {
        return DUE_TIME;
    }
    public void setDUE_TIME(String dUE_TIME) {
        DUE_TIME = dUE_TIME;
    }
    public String getSTATUS() {
        return STATUS;
    }
    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }
}
