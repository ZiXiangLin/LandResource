package com.gis.demo.domain;

public class Nav {
    private String NAV_ID;
    private String CNNAME;
    private String ENNAME;
    private String NAV_ICON;
    private int DIC_ID;
    private String NAV_URL_CN;
    private String NAV_URL_EN;
    private int NAV_LEVEL;
    private String PARENT_ID;
    private int ISLEAF;


    public String getNAV_ID() {
        return NAV_ID;
    }
    public void setNAV_ID(String nAV_ID) {
        NAV_ID = nAV_ID;
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
    public String getNAV_ICON() {
        return NAV_ICON;
    }
    public void setNAV_ICON(String nAV_ICON) {
        NAV_ICON = nAV_ICON;
    }

    public String getNAV_URL_CN() {
        return NAV_URL_CN;
    }
    public void setNAV_URL_CN(String nAV_URL) {
        NAV_URL_CN = nAV_URL;
    }
    public String getNAV_URL_EN() {
        return NAV_URL_EN;
    }
    public void setNAV_URL_EN(String nAV_URL) {
        NAV_URL_EN = nAV_URL;
    }
    public int getNAV_LEVEL() {
        return NAV_LEVEL;
    }
    public void setNAV_LEVEL(int nAV_LEVEL) {
        NAV_LEVEL = nAV_LEVEL;
    }
    public String getPARENT_ID() {
        return PARENT_ID;
    }
    public void setPARENT_ID(String pARENT_ID) {
        PARENT_ID = pARENT_ID;
    }
    public int getISLEAF() {
        return ISLEAF;
    }
    public void setISLEAF(int iSLEAF) {
        ISLEAF = iSLEAF;
    }
    @Override
    public String toString() {
        return "Nav [NAV_ID=" + NAV_ID + ", CNNAME=" + CNNAME + ", ENNAME=" + ENNAME + ", NAV_ICON=" + NAV_ICON
                + ", NAV_URL_CN=" + NAV_URL_CN+ ", NAV_URL_EN=" + NAV_URL_EN + ", NAV_LEVEL=" + NAV_LEVEL
                + ", PARENT_ID=" + PARENT_ID + ", ISLEAF=" + ISLEAF + "]";
    }
    public int getDIC_ID() {
        return DIC_ID;
    }
    public void setDIC_ID(int dIC_ID) {
        DIC_ID = dIC_ID;
    }
}
