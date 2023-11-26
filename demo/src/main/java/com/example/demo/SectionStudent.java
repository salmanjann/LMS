package com.example.demo;

public class SectionStudent {
    private int id;
    private int userid;
    private int sectionid;

    public SectionStudent() {
        // Default constructor
    }

    public SectionStudent(int userid, int sectionid) {
        this.userid = userid;
        this.sectionid = sectionid;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getSectionid() {
        return sectionid;
    }

    public void setSectionid(int sectionid) {
        this.sectionid = sectionid;
    }
}
