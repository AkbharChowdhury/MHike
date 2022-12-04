package com.mhike.m_hike.classes;

public class HikeJson {

    private String date;
    private String name;
    private String description;
    private String location;
    private double distance;
    private double duration;
    private String parking;
    private double elevationGain;
    private double high;
    private String difficulty;


    public HikeJson(String date, String name, String description, String location, double distance, double duration, String parking, double elevationGain, double high, String difficulty) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.location = location;
        this.distance = distance;
        this.duration = duration;
        this.parking = parking;
        this.elevationGain = elevationGain;
        this.high = high;
        this.difficulty = difficulty;
    }
}