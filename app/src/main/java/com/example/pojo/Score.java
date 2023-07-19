package com.example.pojo;

import java.io.Serializable;

public class Score implements Serializable {
    private int id;
    private Student student;
    private Class student_class;
    private double score;

    public Score() {
    }

    public Score(int id, Student student, Class student_class, double score) {
        this.id = id;
        this.student = student;
        this.student_class = student_class;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getStudent_class() {
        return student_class;
    }

    public void setStudent_class(Class student_class) {
        this.student_class = student_class;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
