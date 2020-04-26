package com.gis.demo.domain;

public class Dic {
    private int ID;
    private String CATEGORY;
    private String CNNAME;
    private String ENNAME;
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getCATEGORY() {
        return CATEGORY;
    }
    public void setCATEGORY(String cATEGORY) {
        CATEGORY = cATEGORY;
    }
    public String getCNNAME() {
        return CNNAME;
    }
    public void setCNNAME(String cNNAME) {
        CNNAME = cNNAME;
    }
    public String getENNAME() {
        return ENNAME;
    }
    public void setENNAME(String eNNAME) {
        ENNAME = eNNAME;
    }
    @Override
    public String toString() {
        return "Dic [ID=" + ID + ", CATEGORY=" + CATEGORY + ", CNNAME=" + CNNAME + ", ENNAME=" + ENNAME + "]";
    }
}
