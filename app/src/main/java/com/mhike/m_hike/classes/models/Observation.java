package com.mhike.m_hike.classes.models;

import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
// time picker https://www.youtube.com/watch?v=c6c1giRekB4
public class Observation {
    private TextInputLayout txtObservation;
    private TextInputLayout txtComments;
    private AutoCompleteTextView txtDate;
    private AutoCompleteTextView txtTime;

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

    public AutoCompleteTextView getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(AutoCompleteTextView txtDate) {
        this.txtDate = txtDate;
    }

    public AutoCompleteTextView getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(AutoCompleteTextView txtTime) {
        this.txtTime = txtTime;
    }

    public AutoCompleteTextView getTxtHikeID() {
        return txtHikeID;
    }

    public void setTxtHikeID(AutoCompleteTextView txtHikeID) {
        this.txtHikeID = txtHikeID;
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

    public Observation(AutoCompleteTextView txtHikeID, TextInputLayout txtObservation, TextInputLayout txtComments, AutoCompleteTextView txtDate, AutoCompleteTextView txtTime) {
        this.txtHikeID = txtHikeID;
        this.txtObservation = txtObservation;
        this.txtComments = txtComments;
        this.txtDate = txtDate;
        this.txtTime = txtTime;
    }

    private AutoCompleteTextView txtHikeID;
    private int observationID;

    public int getObservationID() {
        return observationID;
    }

    public void setObservationID(int observationID) {
        this.observationID = observationID;
    }

    private int hikeID;
    private String observation;
    private String comments;
    private String observationDate;
    private String observationTime;


    public  Observation(){

    }
    private boolean view;

    public Observation(int observationID, int hikeID, String observation, String observationDate, String observationTime, boolean view){
        this.observationID = observationID;
        this.hikeID = hikeID;
        this.observation = observation;
        this.observationDate = observationDate;
        this.observationTime = observationTime;
        this.view = view;

    }
}
