package com.mhike.m_hike.classes.models;

public class Parking {
    private int parkingID;
    public Parking(){
        
    }

    public Parking(int parkingID, String parkingName) {
        this.parkingID = parkingID;
        this.parkingName = parkingName;
    }

    public int getParkingID() {
        return parkingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    private String parkingName;
}
