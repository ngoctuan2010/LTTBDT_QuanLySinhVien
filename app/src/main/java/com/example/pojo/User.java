package com.example.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;

    //0 la ADMIN, 1 la GIANGVIEN
    private int role;
    private int lecture;

    public enum ROLE {
      ADMIN,
      LECTURE
    };

    public User(int id, String username, String password,int role, int lecture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lecture = lecture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLecture() {
        return lecture;
    }

    public void setLecture(int lecture) {
        this.lecture = lecture;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
