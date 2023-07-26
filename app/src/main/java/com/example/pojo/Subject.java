package com.example.pojo;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Subject implements Serializable {
    private int id;
    private String name;
    private int credit;
    private double finalGracePercent;
    private double midGracePercent;

    public double getFinalGracePercent() {
        return finalGracePercent;
    }

    public void setFinalGracePercent(double finalGracePercent) {
        this.finalGracePercent = finalGracePercent;
    }

    public double getMidGracePercent() {
        return midGracePercent;
    }

    public void setMidGracePercent(double midGracePercent) {
        this.midGracePercent = midGracePercent;
    }


    public Subject(int id, String name, int credit, double midGracePercent, double finalGracePercent) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.midGracePercent = midGracePercent;
        this.finalGracePercent = finalGracePercent;
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
