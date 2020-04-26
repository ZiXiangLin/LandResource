package com.gis.demo.domain;

public class PerNav {
    private String NAV_ID;
    private int DIC_ID;
    private String NAV_ICON;
    private String NAV_ADDRESS;
    private int NAV_LEVEL;
    private String PARENT_ID;
    private int ISLEAF;


    public String getNAV_ID() {
        return NAV_ID;
    }
    public void setNAV_ID(String nAV_ID) {
        NAV_ID = nAV_ID;
    }

    public int getDIC_ID() {
        return DIC_ID;
    }
    public void setDIC_ID(int DIC_ID) {
        this.DIC_ID = DIC_ID;
    }

    public String getNAV_ICON() {
        return NAV_ICON;
    }
    public void setNAV_ICON(String nAV_ICON) {
        NAV_ICON = nAV_ICON;
    }

    public String getNAV_ADDRESS() {
        return NAV_ADDRESS;
    }
    public void setNAV_ADDRESS(String NAV_ADDRESS) {
        this.NAV_ADDRESS = NAV_ADDRESS;
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
}
