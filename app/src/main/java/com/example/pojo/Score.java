package com.example.pojo;

import java.io.Serializable;

public class Score implements Serializable {
    private int id;

    private double midScore;
    private double endScore;
    private int idStudent;
    private int idClass;

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }



    public double getMidScore() {
        return midScore;
    }

    public void setMidScore(double midScore) {
        this.midScore = midScore;
    }

    public double getEndScore() {
        return endScore;
    }

    public void setEndScore(double endScore) {
        this.endScore = endScore;
    }



    public Score() {
    }

    public Score(int id, int idStudent, int idClass, double midScore, double endScore) {
        this.id = id;
        this.idStudent = idStudent;
        this.idClass = idClass;
        this.midScore = midScore;
        this.endScore = endScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
