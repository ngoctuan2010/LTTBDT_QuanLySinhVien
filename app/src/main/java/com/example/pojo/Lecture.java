package com.example.pojo;

import java.io.Serializable;

public class Lecture implements Serializable {
    private int id;
    private String first_name;
    private String last_name;
    private boolean gender;
    private String birth;
    private String address;
    private String phone;
    private String department;

    public Lecture() {
    }

    public Lecture(int id, String first_name, String last_name, boolean gender, String birth, String address, String phone, String department) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        //True la nu, false la nam
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String toString(){
        return this.getFirst_name() + " " + this.getLast_name() + " - " + this.getId();
    }
}
