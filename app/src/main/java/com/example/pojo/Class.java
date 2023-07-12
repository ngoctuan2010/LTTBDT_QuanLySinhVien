package com.example.pojo;

import java.io.Serializable;
import java.util.Date;

public class Class implements Serializable {
    private int id;
    private int subject_id;
    private int collage_id;
    private int quantity;
    private String year;
    private Date started;



    public Class(int id, int subject_id, int collage_id, int quantity) {
        this.id = id;
        this.subject_id = subject_id;
        this.collage_id = collage_id;
        this.quantity = quantity;
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

    public int getCollage_id() {
        return collage_id;
    }

    public void setCollage_id(int collage_id) {
        this.collage_id = collage_id;
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

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }
}
