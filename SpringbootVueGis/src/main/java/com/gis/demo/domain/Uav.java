package com.gis.demo.domain;

public class Uav {
    private int id;
    private String UAVimagefiledir;
    private double GPS_Latitude;
    private double GPS_Longitude;

    public void setId(int id) {
        this.id = id;
    }

    public void setUAVimagefiledir(String UAVimagefiledir) {
        this.UAVimagefiledir = UAVimagefiledir;
    }

    public void setGPS_Latitude(double GPS_Latitude) {
        this.GPS_Latitude = GPS_Latitude;
    }

    public void setGPS_Longitude(double GPS_Longitude) {
        this.GPS_Longitude = GPS_Longitude;
    }

    public int getId() {
        return id;
    }

    public String getUAVimagefiledir() {
        return UAVimagefiledir;
    }

    public double getGPS_Latitude() {
        return GPS_Latitude;
    }

    public double getGPS_Longitude() {
        return GPS_Longitude;
    }
}
