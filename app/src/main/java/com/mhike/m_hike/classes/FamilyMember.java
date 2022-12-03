package com.mhike.m_hike.classes;

public class FamilyMember {
//    @SerializedName("role")
    private String role;
//    @SerializedName("age")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private  String name;

    public FamilyMember(String role, int age) {
        this.role = role;
        this.age = age;
    }
    public FamilyMember(String name){
        this.name = name;

    }
    public  FamilyMember(){

    }

}
