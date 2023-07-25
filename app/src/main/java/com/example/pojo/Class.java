package com.example.pojo;

import java.io.Serializable;
import java.util.Date;

public class Class implements Serializable {
    private int id;

    private String name;
    private int subject_id;
    private int lecture;
    private int quantity;
    private String year;
    private String started;


    public Class() {
    }

    public Class(int id, String name, int subject_id, int lecture, int quantity, String year, String started) {
        this.id = id;
        this.name = name;
        this.subject_id = subject_id;
        this.lecture = lecture;
        this.quantity = quantity;
        this.year = year;
        this.started = started;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getLecture() {
        return lecture;
    }

    public void setLecture(int lecture) {
        this.lecture = lecture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
