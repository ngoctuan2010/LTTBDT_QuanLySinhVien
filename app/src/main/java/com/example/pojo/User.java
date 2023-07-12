package com.example.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;

    private int role;
    private int collage_id;

    public User(int id, String username, String password,int role, int collage_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.collage_id = collage_id;
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

    public int getCollage_id() {
        return collage_id;
    }

    public void setCollage_id(int collage_id) {
        this.collage_id = collage_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
