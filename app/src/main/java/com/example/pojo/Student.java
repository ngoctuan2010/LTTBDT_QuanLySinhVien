package com.example.pojo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student implements Serializable {
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private boolean gender;
    private String birth;
    private String address;
    private String phone;
    private String department;
    private String school_year;

    public Student() {
    }

    public Student(String name, boolean gender, String birth, String address, String phone, String department, String school_year) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.department = department;
        this.school_year = school_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSchool_year() {
        return school_year;
    }

    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }

    @Override
    public String toString() {
        String gt;
        if (gender)
            gt = "Nam";
        else
            gt = "Nữ";
        String chuoi = "Mã sinh viên: " + id + "\n" + "Tên sinh viên: " + name + "\n" + "Giới tính: " + gt + "\n" + "Ngày sinh: " + birth + "\n" + "Địa chỉ: " + address + "\n"
                + "Số điện thoại: " + phone + "\n" + "Khoa: " + department + "\n" + "Niên khóa: " + school_year + "\n";
        return chuoi;
    }
}
