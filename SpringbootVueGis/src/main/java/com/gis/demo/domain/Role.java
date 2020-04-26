package com.gis.demo.domain;

public class Role {
    private int ID;
    private String USERNAME;
    private String PASSWORD;
    private int AUTHORITY;
    private int DIC_ID;
    private String EMAIL;
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getUSERNAME() {
        return USERNAME;
    }
    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }
    public String getPASSWORD() {
        return PASSWORD;
    }
    public void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }
    public int getAUTHORITY() {
        return AUTHORITY;
    }
    public void setAUTHORITY(int aUTHORITY) {
        AUTHORITY = aUTHORITY;
    }
    public int getDIC_ID() {
        return DIC_ID;
    }
    public void setDIC_ID(int dIC_ID) {
        DIC_ID = dIC_ID;
    }
    @Override
    public String toString() {
        return "Role [ID=" + ID + ", USERNAME=" + USERNAME + ", PASSWORD=" + PASSWORD + ", AUTHORITY=" + AUTHORITY
                + ", DIC_ID=" + DIC_ID +", EMAIL=" + EMAIL + "]";
    }
    public String getEMAIL() {
        return EMAIL;
    }
    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }
}
