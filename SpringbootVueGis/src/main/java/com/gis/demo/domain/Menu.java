package com.gis.demo.domain;

public class Menu {
        private String MENU_ID;
    private int DIC_ID;
        private String CNNAME;
        private String ENNAME;
        private String MENU_ICON;
        public String getMENU_ID() {
            return MENU_ID;
        }
        public void setMENU_ID(String mENU_ID) {
            MENU_ID = mENU_ID;
        }
        public int getDIC_ID() {
        return DIC_ID;
    }
        public void setDIC_ID(int dIC_ID) {
        DIC_ID = dIC_ID;
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
        public String getMENU_ICON() {
            return MENU_ICON;
        }
        public void setMENU_ICON(String mENU_ICON) {
            MENU_ICON = mENU_ICON;
        }
}
