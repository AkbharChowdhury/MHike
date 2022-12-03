package com.mhike.m_hike.classes;

public class HikeJson {

    private String name;
    private String date;
    private String description;
    private String difficulty;


    public HikeJson(String name, String date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public HikeJson(String name, String date, String description, String difficulty) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.difficulty = difficulty;

    }

}
