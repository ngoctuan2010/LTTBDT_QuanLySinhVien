package com.example.pojo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student implements Serializable {
    private int id;
    private String first_name;
    private String last_name;
    private boolean gender;
    private Date birth;
    private String address;
    private String phone;
    private String department;
    private String school_year;
    SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");

    public Student() {
    }

    public Student(String first_name, String last_name, boolean gender, String birth, String address, String phone, String department, String school_year) throws ParseException {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.birth = spdf.parse(birth);
        this.address = address;
        this.phone = phone;
        this.department = department;
        this.school_year=school_year;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(String birth) throws ParseException {
        this.birth = spdf.parse(birth);
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
        String chuoi = "Mã: " + id + "\n" + "Tên: " + last_name + " " + first_name + "\n" + "Giới tính: " + gt + "\n" + "Ngày sinh: " + birth + "\n" + "Địa chỉ: " + address + "\n"
                + "Số điện thoại: " + phone + "\n" + "Khoa: " + department + "\n" + "Năm học: " + school_year + "\n";
        return chuoi;
    }
}
