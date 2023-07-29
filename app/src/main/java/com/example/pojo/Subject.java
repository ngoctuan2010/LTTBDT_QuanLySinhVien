package com.example.pojo;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Subject implements Serializable {
    private int id;
    private String name;
    private int credit;
    private double finalGradePercent;
    private double midGradePercent;

    public double getFinalGradePercent() {
        return finalGradePercent;
    }

    public void setFinalGradePercent(double finalGradePercent) {
        this.finalGradePercent = finalGradePercent;
    }

    public double getMidGradePercent() {
        return midGradePercent;
    }

    public void setMidGradePercent(double midGradePercent) {
        this.midGradePercent = midGradePercent;
    }


    public Subject(int id, String name, int credit, double midGradePercent, double finalGradePercent) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.midGradePercent = midGradePercent;
        this.finalGradePercent = finalGradePercent;
    }
    public Subject(int id, String name, int credit){
        this.id = id;
        this.name = name;
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String toString() {
        return this.getName();
    }

}
