package com.mhike.m_hike.classes.models;

import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public final class Hike {
    private int hikeID;

    public int getHikeID() {
        return hikeID;
    }

    public void setHikeID(int hikeID) {
        this.hikeID = hikeID;
    }

    private String hikeDate;
    private String hikeName;
    private String description;
    private String location;
    private double distance;
    private double duration;
    private int parkingID;
    private double elevationGain;
    private double high;
    private int difficultyID;
    private String difficultyLevel;

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    private AutoCompleteTextView txtHikeDate;
    private TextInputLayout txtHikeName;
    private TextInputLayout txtDescription;
    private TextInputLayout txtLocation;
    private TextInputLayout txtDistance;
    private TextInputLayout txtDuration;
    private AutoCompleteTextView txtParking;
    private TextInputLayout txtElevationGain;
    private TextInputLayout txtHigh;
    private TextView lblDifficulty;


    public String getHikeDate() {
        return hikeDate;
    }

    public void setHikeDate(String hikeDate) {
        this.hikeDate = hikeDate;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getParkingID() {
        return parkingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public double getElevationGain() {
        return elevationGain;
    }

    public void setElevationGain(double elevationGain) {
        this.elevationGain = elevationGain;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public int getDifficultyID() {
        return difficultyID;
    }

    public void setDifficultyID(int difficultyID) {
        this.difficultyID = difficultyID;
    }

    public AutoCompleteTextView getTxtHikeDate() {
        return txtHikeDate;
    }

    public void setTxtHikeDate(AutoCompleteTextView txtHikeDate) {
        this.txtHikeDate = txtHikeDate;
    }

    public TextInputLayout getTxtHikeName() {
        return txtHikeName;
    }

    public void setTxtHikeName(TextInputLayout txtHikeName) {
        this.txtHikeName = txtHikeName;
    }

    public TextInputLayout getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(TextInputLayout txtDescription) {
        this.txtDescription = txtDescription;
    }

    public TextInputLayout getTxtLocation() {
        return txtLocation;
    }

    public void setTxtLocation(TextInputLayout txtLocation) {
        this.txtLocation = txtLocation;
    }

    public TextInputLayout getTxtDistance() {
        return txtDistance;
    }

    public void setTxtDistance(TextInputLayout txtDistance) {
        this.txtDistance = txtDistance;
    }

    public TextInputLayout getTxtDuration() {
        return txtDuration;
    }

    public void setTxtDuration(TextInputLayout txtDuration) {
        this.txtDuration = txtDuration;
    }

    public AutoCompleteTextView getTxtParking() {
        return txtParking;
    }

    public void setTxtParking(AutoCompleteTextView txtParking) {
        this.txtParking = txtParking;
    }

    public TextInputLayout getTxtElevationGain() {
        return txtElevationGain;
    }

    public void setTxtElevationGain(TextInputLayout txtElevationGain) {
        this.txtElevationGain = txtElevationGain;
    }

    public TextInputLayout getTxtHigh() {
        return txtHigh;
    }

    public void setTxtHigh(TextInputLayout txtHigh) {
        this.txtHigh = txtHigh;
    }

    public TextView getLblDifficulty() {
        return lblDifficulty;
    }

    public void setLblDifficulty(AutoCompleteTextView txtDifficulty) {
        this.lblDifficulty = txtDifficulty;
    }

    public Hike(AutoCompleteTextView txtHikeDate, TextInputLayout txtHikeName, TextInputLayout txtDescription, TextInputLayout txtLocation, TextInputLayout txtDistance, TextInputLayout txtDuration, AutoCompleteTextView txtParking, TextInputLayout txtElevationGain, TextInputLayout txtHigh, TextView lblDifficulty) {
        this.txtHikeDate = txtHikeDate;
        this.txtHikeName = txtHikeName;
        this.txtDescription = txtDescription;
        this.txtLocation = txtLocation;
        this.txtDistance = txtDistance;
        this.txtDuration = txtDuration;
        this.txtParking = txtParking;
        this.txtElevationGain = txtElevationGain;
        this.txtHigh = txtHigh;
        this.lblDifficulty = lblDifficulty;
    }
    public Hike(int id, String name, String description, String hikeDate, String difficultyLevel, int difficultyID){
        hikeID = id;
        hikeName = name;
        this.description = description;
        this.hikeDate = hikeDate;
        this.difficultyLevel = difficultyLevel;
        this.difficultyID = difficultyID;



    }


    public Hike(){

    }


}
