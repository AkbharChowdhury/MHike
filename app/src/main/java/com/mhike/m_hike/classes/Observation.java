package com.mhike.m_hike.classes;

import com.google.android.material.textfield.TextInputLayout;
// time picker https://www.youtube.com/watch?v=c6c1giRekB4
public class Observation {
    private TextInputLayout txtObservation;
    private TextInputLayout txtComments;
    private TextInputLayout txtDate;
    private TextInputLayout txtTime;
    private int hikeID;
    private String observation;
    private String comments;
    private String observationDate;
    private String observationTime;
    public  Observation(){

    }

    public Observation(TextInputLayout txtObservation, TextInputLayout txtComments, TextInputLayout txtDate, TextInputLayout txtTime) {
        this.txtObservation = txtObservation;
        this.txtComments = txtComments;
        this.txtDate = txtDate;
        this.txtTime = txtTime;
    }

    public TextInputLayout getTxtObservation() {
        return txtObservation;
    }

    public void setTxtObservation(TextInputLayout txtObservation) {
        this.txtObservation = txtObservation;
    }

    public TextInputLayout getTxtComments() {
        return txtComments;
    }

    public void setTxtComments(TextInputLayout txtComments) {
        this.txtComments = txtComments;
    }

    public TextInputLayout getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(TextInputLayout txtDate) {
        this.txtDate = txtDate;
    }

    public TextInputLayout getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(TextInputLayout txtTime) {
        this.txtTime = txtTime;
    }

    public int getHikeID() {
        return hikeID;
    }

    public void setHikeID(int hikeID) {
        this.hikeID = hikeID;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(String observationDate) {
        this.observationDate = observationDate;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }
}
