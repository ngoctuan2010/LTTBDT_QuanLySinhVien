package com.example.pojo;

import java.io.Serializable;

public class Score implements Serializable {
    private int id;

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

    private int idStudent;
    private int idClass;
    private double score;

    public Score() {
    }

    public Score(int id, int idStudent, int idClass, double score) {
        this.id = id;
        this.idStudent = idStudent;
        this.idClass = idClass;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
