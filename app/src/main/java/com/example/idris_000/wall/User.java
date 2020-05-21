package com.example.idris_000.wall;

import com.google.firebase.database.DataSnapshot;

public class User {

    String email;
    String name;
    String dept;
    String mobile;
    String enroll;
    String pass;
    String url;

    public User(String email, String name, String dept, String mobile, String enroll, String pass,String url) {
        this.email = email;
        this.name = name;
        this.dept = dept;
        this.mobile = mobile;
        this.enroll = enroll;
        this.pass = pass;
        this.url = url;
    }

    public User()
    {

    }
    public User(String url)
    {
        this.url=url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}