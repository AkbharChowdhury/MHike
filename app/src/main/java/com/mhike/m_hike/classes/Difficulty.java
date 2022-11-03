package com.mhike.m_hike.classes;

public class Difficulty {

    private int difficultyID;
    private String difficultyName;

    public Difficulty(){

    }
    public Difficulty(int difficultyID, String difficultyName) {
        this.difficultyID = difficultyID;
        this.difficultyName = difficultyName;
    }
    public int getDifficultyID() {
        return difficultyID;
    }

    public void setDifficultyID(int difficultyID) {
        this.difficultyID = difficultyID;
    }

    public String getDifficultyName() {
        return difficultyName;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }



}
